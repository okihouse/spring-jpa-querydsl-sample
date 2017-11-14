package com.boot.jpa.repository.userinfo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.jpa.domain.UserInfo;
import com.boot.jpa.repository.userinfo.custom.UserInfoCustomRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long>, UserInfoCustomRepository{

	UserInfo findByEmail(String email);

}
