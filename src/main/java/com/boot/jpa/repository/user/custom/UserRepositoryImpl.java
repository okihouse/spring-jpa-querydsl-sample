package com.boot.jpa.repository.user.custom;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

import com.boot.jpa.domain.QUser;
import com.boot.jpa.domain.QUserInfo;
import com.boot.jpa.domain.User;
import com.boot.jpa.domain.User.USER_TYPE;
import com.boot.jpa.vo.ResultVO;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.Projections;

@Transactional
public class UserRepositoryImpl extends QueryDslRepositorySupport implements UserCustomRepository {

	public UserRepositoryImpl() {
		super(User.class);
	}

	@PersistenceContext
	private EntityManager entityManager;

	private final QUser user = QUser.user;
	private final QUserInfo userInfo = QUserInfo.userInfo;

	@Override
	public List<ResultVO> findByTypeQuerydsl(USER_TYPE userType) {
		JPAQuery query = new JPAQuery(entityManager);

		return query.from(user)
				.innerJoin(user.userInfo, userInfo)
				.where(user.type.eq(userType))
				.list(Projections.bean(ResultVO.class, user.userNo, user.type, userInfo.email));
	}

	@Override
	public Page<ResultVO> findByTypeQuerydsl(USER_TYPE userType, Pageable pageable) {
		JPAQuery query = new JPAQuery(entityManager);
		query = (JPAQuery) super.getQuerydsl().applyPagination(pageable, query);

		List<ResultVO> results = query.from(user)
									  .innerJoin(user.userInfo, userInfo)
									  .where(user.type.eq(userType))
									  .list(Projections.bean(ResultVO.class, user.userNo, user.type, userInfo.email));

		return new PageImpl<ResultVO>(results, pageable, results.size());
	}

}
