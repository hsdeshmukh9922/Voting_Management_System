package com.example.demo.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Election;
import com.example.demo.repository.ElectionRepo;
@Service
public class ElectionService {

    @Autowired
    private ElectionRepo electionRepo;

    public List<Election> getUpcomingElections() {
        return electionRepo.findByStartDateAfter(new Date(System.currentTimeMillis()));
    }

    public List<Election> getAllElections() {
        return electionRepo.findAll();
    }

    public void saveElection(Election election) {
        electionRepo.save(election);
    }
}
