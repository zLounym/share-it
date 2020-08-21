package cz.zlounym.shareit.configuration.mongo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.github.mongobee.Mongobee;
import com.github.mongobee.exception.MongobeeException;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Profile("!integrationTest")
public class MongoBeeConfig {

    protected static final String CHANGELOG_PACKAGE = "net.twill.account.demo.data.changelock";

    private final String mongoUri;

    public MongoBeeConfig(@Value("${spring.data.mongodb.uri}") final String mongoUri) {
        this.mongoUri = mongoUri;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationEvent() {
        final Mongobee runner = new Mongobee(mongoUri);
        final MongoClientURI mongoClientUri = new MongoClientURI(mongoUri);
        final MongoClient mongoClient = new MongoClient(mongoClientUri);
        final String databaseName = mongoClientUri.getDatabase();
        final MongoTemplate mongoTemplate =
                new MongoTemplate(mongoClient, databaseName);

        runner.setMongoTemplate(mongoTemplate);
        runner.setChangeLogsScanPackage(CHANGELOG_PACKAGE);

        try {
            runner.execute();
        } catch (MongobeeException e) {
            log.info("Cannot run mongodb migration {}", e.getMessage());
        }
    }
}
