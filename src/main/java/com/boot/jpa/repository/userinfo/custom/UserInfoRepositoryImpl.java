package com.boot.jpa.repository.userinfo.custom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.boot.jpa.domain.QUserInfo;
import com.mysema.query.jpa.impl.JPADeleteClause;

public class UserInfoRepositoryImpl implements UserInfoCustomRepository {

	@PersistenceContext
	private EntityManager entityManager;

	private final QUserInfo userInfo = QUserInfo.userInfo;

	@Override
	public void deleteByEmail(String email) {
		new JPADeleteClause(entityManager, userInfo)
		.where(userInfo.email.eq(email))
		.execute();
	}

}
