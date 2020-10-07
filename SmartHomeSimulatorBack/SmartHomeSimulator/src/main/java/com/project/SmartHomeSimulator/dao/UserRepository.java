package com.project.SmartHomeSimulator.dao;

import com.project.SmartHomeSimulator.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, String> {
    User findByUsername(String username);
    long deleteByUsername (String username);
}
