package com.boot.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.jpa.domain.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

	UserInfo findByEmail(String email);

}
