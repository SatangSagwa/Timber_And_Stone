package com.AirBnb.TimberAndStone.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import com.AirBnb.TimberAndStone.models.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);



}
