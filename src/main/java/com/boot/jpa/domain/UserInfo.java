package com.boot.jpa.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "userinfo")
@Data
public class UserInfo {

	@Id
	private Long userNo;

	@Column(name = "EMAIL")
	private String email;

	@MapsId
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "UNO")
	private User user;

}
