package com.revature.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import com.revature.models.User;

public interface UserDao extends JpaRepository<User, Integer>{
    Optional<User> findByUserName(String username);

    @Transactional
    @Modifying 
    @Query(value= "insert into users values (default, :username , :password", nativeQuery = true)
    void createUser(@Param("username") String userName, @Param("password") String passWord);

    @Transactional
    @Modifying 
    @Query(value= "update users set username = :username , password = :password where id = :id", nativeQuery = true)
    int updateUser(@Param("username") String userName, @Param("password") String passWord);
}
