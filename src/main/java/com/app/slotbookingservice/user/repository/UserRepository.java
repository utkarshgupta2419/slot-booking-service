package com.app.slotbookingservice.user.repository;

import com.app.slotbookingservice.user.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
