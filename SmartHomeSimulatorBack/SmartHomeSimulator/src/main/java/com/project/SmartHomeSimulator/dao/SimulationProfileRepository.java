package com.project.SmartHomeSimulator.dao;

import com.project.SmartHomeSimulator.model.SimulationProfile;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface SimulationProfileRepository extends PagingAndSortingRepository<SimulationProfile, String> {
    SimulationProfile findByName(String name);
    long deleteByName(String name);
    ArrayList<SimulationProfile> findAll();
}
