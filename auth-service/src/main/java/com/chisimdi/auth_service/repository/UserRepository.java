package com.chisimdi.auth_service.repository;

import com.chisimdi.auth_service.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    Boolean existsByUserName(String userName);
    User findByUserName(String userName);
}
