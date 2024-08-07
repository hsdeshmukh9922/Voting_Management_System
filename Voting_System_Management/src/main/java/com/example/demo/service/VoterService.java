package com.example.demo.service;

import com.example.demo.entity.Voter;
import com.example.demo.repository.VoterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoterService {

    @Autowired
    private VoterRepository voterRepo;

    public void saveVoter(Voter voter) {
        try {
            voterRepo.save(voter);
            System.out.println("Voter saved successfully.");
        } catch (Exception e) {
            System.out.println("Error saving voter.");
            e.printStackTrace();
        }
    }
    public Voter authenticateVoter(String mobile, String password) {
        Voter voter = voterRepo.findByMobile(mobile);
        if (voter != null) {
            System.out.println("Voter found with mobile: " + mobile);
            if (voter.getPassword().equals(password)) {
                System.out.println("Password matches for voter with mobile: " + mobile);
                return voter;
            } else {
                System.out.println("Password does not match for voter with mobile: " + mobile);
            }
        } else {
            System.out.println("No voter found with mobile: " + mobile);
        }
        return null;
    }

    public List<Voter> getAllVoters() {
        List<Voter> voters = voterRepo.findAll();
        System.out.println("Fetched " + voters.size() + " voters.");
        return voters;
    }

    public void deleteVoterById(Long id) {
        try {
            voterRepo.deleteById(id);
            System.out.println("Voter with ID " + id + " deleted successfully.");
        } catch (Exception e) {
            System.out.println("Error deleting voter with ID: " + id);
            e.printStackTrace();
        }
    }

    public boolean existsByMobile(String mobile) {
        return voterRepo.existsByMobile(mobile);
    }

    public boolean existsByAadhaar(String aadhaar) {
        return voterRepo.existsByAadhaar(aadhaar);
    }
}
