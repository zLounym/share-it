package cz.zlounym.shareit.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import cz.zlounym.shareit.data.domain.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
}
