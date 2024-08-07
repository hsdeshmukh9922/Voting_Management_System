package com.example.demo.controller;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Election;
import com.example.demo.entity.Voter;
import com.example.demo.service.AdminService;
import com.example.demo.service.ElectionService;
import com.example.demo.service.VoterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/")
public class VoterController {
    
    @Autowired
    private AdminService adminService;
    
    @Autowired
    private VoterService voterService;

    @Autowired
    private ElectionService electionService;

    @GetMapping("/chooseRole")
    public String chooseRole() {
        return "RegisterRole";
    }

    @PostMapping("/roleSelection")
    public String roleSelection(@RequestParam("role") String role, Model model) {
        if ("voter".equalsIgnoreCase(role)) {
            return "VoterRegistration";
        } else if ("admin".equalsIgnoreCase(role)) {
            return "AdminRegistration";
        } else {
            model.addAttribute("message", "Invalid role selected");
            return "RegisterRole";
        }
    }

    @GetMapping("/voter/register")
    public String showVoterRegistrationForm() {
        return "VoterRegistration";
    }

    @PostMapping("/voter/register")
    public String registerVoter(
            @RequestParam("name") String name,
            @RequestParam("age") int age,
            @RequestParam("mobile") String mobile,
            @RequestParam("aadhaar") String aadhaar,
            @RequestParam("password") String password,
            Model model
    ) {
        if (name == null || name.isEmpty()) {
            model.addAttribute("message", "Name is required.");
            return "VoterRegistration";
        }
        if (mobile == null || mobile.isEmpty()) {
            model.addAttribute("message", "Mobile number is required.");
            return "VoterRegistration";
        }
        if (aadhaar == null || aadhaar.isEmpty()) {
            model.addAttribute("message", "Aadhaar number is required.");
            return "VoterRegistration";
        }
        if (password == null || password.isEmpty()) {
            model.addAttribute("message", "Password is required.");
            return "VoterRegistration";
        }

        boolean mobileExists = voterService.existsByMobile(mobile);
        boolean aadhaarExists = voterService.existsByAadhaar(aadhaar);
        if (mobileExists || aadhaarExists) {
            String errorMessage = "";
            if (mobileExists) {
                errorMessage += "Mobile number already exists. ";
            }
            if (aadhaarExists) {
                errorMessage += "Aadhaar number already exists. ";
            }
            model.addAttribute("message", errorMessage.trim());
            return "VoterRegistration";
        }

        Voter voter = new Voter();
        voter.setName(name);
        voter.setAge(age);
        voter.setMobile(mobile);
        voter.setAadhaar(aadhaar);
        voter.setPassword(password);

        voterService.saveVoter(voter);
        model.addAttribute("message", "Registration successful. Please login.");

        return "VoterLogin";
    }

    @GetMapping("/voter/login")
    public String showVoterLoginForm() {
        return "VoterLogin";
    }

    @PostMapping("/voter/login")
    public String loginVoter(
            @RequestParam("mobile") String mobile,
            @RequestParam("password") String password,
            Model model,
            HttpSession session
    ) {
        Voter voter = voterService.authenticateVoter(mobile, password);
        if (voter != null) {
            session.setAttribute("voter", voter);
            return "redirect:/voter/dashboard";
        } else {
            model.addAttribute("message", "Invalid mobile number or password");
            return "VoterLogin";
        }
    }

    @GetMapping("/voter/dashboard")
    public String showVoterDashboard(HttpSession session, Model model) {
        Voter voter = (Voter) session.getAttribute("voter");
        if (voter == null) {
            model.addAttribute("message", "Please login to access the dashboard");
            return "VoterLogin";
        }
        return "VoterDashboard";
    }

    @GetMapping("/admin/register")
    public String showAdminRegistrationForm() {
        return "AdminRegistration";
    }

    @PostMapping("/admin/register")
    public String registerAdmin(
            @RequestParam("adminId") String adminId,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            Model model
    ) {
        if (adminService.existsByAdminId(adminId)) {
            model.addAttribute("message", "Admin ID already exists");
            return "AdminRegistration";
        } else {
            Admin admin = new Admin(adminId, password, name);
            admin.setId(adminId);
            admin.setPassword(password);
            admin.setName(name);
            adminService.saveAdmin(admin);
            return "AdminLogin";
        }
    }

    @GetMapping("/admin/login")
    public String showAdminLoginForm() {
        return "AdminLogin";
    }

    @PostMapping("/admin/login")
    public String loginAdmin(
            @RequestParam("adminId") String adminId,
            @RequestParam("password") String password,
            Model model,
            HttpSession session
    ) {
        Admin admin = adminService.authenticateAdmin(adminId, password);
        if (admin != null) {
            session.setAttribute("admin", admin);
            return "redirect:/admin/dashboard";
        } else {
            model.addAttribute("message", "Invalid admin ID or password");
            return "AdminLogin";
        }
    }

    @GetMapping("/admin/dashboard")
    public String showAdminDashboard(HttpSession session, Model model) {
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin != null) {
            model.addAttribute("adminId", admin.getId());
            model.addAttribute("adminName", admin.getName());
            List<Voter> voters = voterService.getAllVoters();
            model.addAttribute("voters", voters);
            return "AdminDashboard";
        } else {
            model.addAttribute("error", "Admin not logged in");
            return "AdminLogin";
        }
    }

    @GetMapping("/admin/scheduleElection")
    public String showScheduleElectionForm() {
        return "ScheduleElection";
    }

    @PostMapping("/admin/scheduleElection")
    public String scheduleElection(
            @RequestParam String electionName,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam String electionType,
            HttpSession session,
            Model model
    ) {
        Date sqlStartDate = Date.valueOf(startDate);
        Date sqlEndDate = Date.valueOf(endDate);
        Election election = new Election(electionName, sqlStartDate, sqlEndDate, electionType);
        electionService.saveElection(election);

        Admin admin = (Admin) session.getAttribute("admin");
        if (admin != null) {
            model.addAttribute("adminId", admin.getId());
            model.addAttribute("adminName", admin.getName());
            List<Voter> voters = voterService.getAllVoters();
            model.addAttribute("voters", voters);
            model.addAttribute("message", "Election scheduled successfully");
            return "AdminDashboard";
        } else {
            model.addAttribute("error", "Admin not logged in");
            return "AdminLogin";
        }
    }
    @DeleteMapping("/admin/deleteVoter/{id}")
    public ResponseEntity<String> deleteVoter(@PathVariable Long id) {
        voterService.deleteVoterById(id);
        return ResponseEntity.ok("Voter deleted successfully.");
    }

    @GetMapping("/voter/viewProfile")
    public String viewProfile(HttpSession session, Model model) {
        Voter voter = (Voter) session.getAttribute("voter");
        if (voter != null) {
            model.addAttribute("voter", voter);
            return "ViewProfile";
        } else {
            model.addAttribute("message", "Please login to view profile");
            return "VoterLogin";
        }
    }

    @GetMapping("/voter/updateProfile")
    public String showUpdateProfileForm(HttpSession session, Model model) {
        Voter voter = (Voter) session.getAttribute("voter");
        if (voter != null) {
            model.addAttribute("voter", voter);
            return "UpdateProfile";
        } else {
            model.addAttribute("message", "Please login to update profile");
            return "VoterLogin";
        }
    }

    @PostMapping("/voter/updateProfile")
    public String updateProfile(
            @RequestParam("name") String name,
            @RequestParam("age") int age,
            @RequestParam("mobile") String mobile,
            @RequestParam("aadhaar") String aadhaar,
            HttpSession session,
            Model model
    ) {
        Voter voter = (Voter) session.getAttribute("voter");
        if (voter != null) {
            if (name == null || name.isEmpty()) {
                model.addAttribute("message", "Name is required.");
                return "UpdateProfile";
            }
            if (mobile == null || mobile.isEmpty()) {
                model.addAttribute("message", "Mobile number is required.");
                return "UpdateProfile";
            }
            if (aadhaar == null || aadhaar.isEmpty()) {
                model.addAttribute("message", "Aadhaar number is required.");
                return "UpdateProfile";
            }

            voter.setName(name);
            voter.setAge(age);
            voter.setMobile(mobile);
            voter.setAadhaar(aadhaar);

            voterService.saveVoter(voter);
            session.setAttribute("voter", voter);
            model.addAttribute("message", "Profile updated successfully.");
            return "ViewProfile";
        } else {
            model.addAttribute("message", "Please login to update profile");
            return "VoterLogin";
        }
    }
}
