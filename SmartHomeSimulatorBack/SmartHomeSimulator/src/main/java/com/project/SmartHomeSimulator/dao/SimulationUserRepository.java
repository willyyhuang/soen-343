package com.project.SmartHomeSimulator.dao;

import com.project.SmartHomeSimulator.model.SimulationUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Repository
public interface SimulationUserRepository extends PagingAndSortingRepository<SimulationUser, String> {
    SimulationUser findByUsername(String username);
    long deleteByUsername (String username);
    ArrayList<SimulationUser> findAll();
}
