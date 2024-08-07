package com.example.demo.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "elections")
public class Election {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String electionName;
    private Date startDate;
    private Date endDate;
    private String electionType;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getElectionName() {
		return electionName;
	}
	public void setElectionName(String electionName) {
		this.electionName = electionName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getElectionType() {
		return electionType;
	}
	public void setElectionType(String electionType) {
		this.electionType = electionType;
	}
	public Election(Long id, String electionName, Date startDate, Date endDate, String electionType) {
		super();
		this.id = id;
		this.electionName = electionName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.electionType = electionType;
	}
	public Election() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Election(String electionName, Date startDate, Date endDate, String electionType) {
		super();
		this.electionName = electionName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.electionType = electionType;
	}
}
