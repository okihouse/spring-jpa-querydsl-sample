package com.boot.jpa.vo;

import com.boot.jpa.domain.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVO {

	private Long userNo;
	
	private User.USER_TYPE type;
	
	private String email;
}
