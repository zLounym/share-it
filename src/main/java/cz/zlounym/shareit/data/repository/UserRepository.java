package cz.zlounym.shareit.data.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import cz.zlounym.shareit.data.domain.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmailAndPassword(String email, String password);

}
