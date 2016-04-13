package com.boot.jpa.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "user_history")
@Data
public class UserHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userHistoryNo;
	
	private Date latestLogin;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userNo")
	private User user;
	
}
