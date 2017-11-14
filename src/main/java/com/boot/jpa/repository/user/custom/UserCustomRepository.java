package com.boot.jpa.repository.user.custom;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.boot.jpa.domain.User.USER_TYPE;
import com.boot.jpa.vo.ResultVO;

public interface UserCustomRepository {

	List<ResultVO> findByTypeQuerydsl(USER_TYPE user);

	Page<ResultVO> findByTypeQuerydsl(USER_TYPE user, Pageable pageable);

}
