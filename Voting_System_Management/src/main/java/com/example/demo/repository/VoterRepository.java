package com.example.demo.repository;

import com.example.demo.entity.Voter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoterRepository extends JpaRepository<Voter, Long> {
    Voter findByMobile(String mobile);
    boolean existsByMobile(String mobile);
    boolean existsByAadhaar(String aadhaar);
}
