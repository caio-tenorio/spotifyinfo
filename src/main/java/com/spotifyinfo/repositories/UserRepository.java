package com.spotifyinfo.repositories;

import com.spotifyinfo.domain.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findById(long id);
    User findByEmail(String email);
}
