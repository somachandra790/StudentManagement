package com.poc.app.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import com.poc.app.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{

   boolean existsByEmail(String email);

}
