package com.boot.jpa.repository.custom;

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
	
	private final QUser qUser = QUser.user;
	private final QUserInfo qUserInfo = QUserInfo.userInfo;

	@Override
	public List<ResultVO> findByTypeQuerydsl(USER_TYPE user) {
		JPAQuery query = new JPAQuery(entityManager);
		
		return query.from(qUser)
				.innerJoin(qUser.userInfo, qUserInfo)
				.where(qUser.type.eq(User.USER_TYPE.USER))
				.list(Projections.bean(ResultVO.class, qUser.userNo, qUser.type, qUserInfo.email));
	}

	@Override
	public Page<ResultVO> findByTypeQuerydsl(USER_TYPE user, Pageable pageable) {
		JPAQuery query = new JPAQuery(entityManager);
		query = (JPAQuery) super.getQuerydsl().applyPagination(pageable, query);
		
		List<ResultVO> results = query.from(qUser)
									  .innerJoin(qUser.userInfo, qUserInfo)
									  .where(qUser.type.eq(User.USER_TYPE.USER))
									  .list(Projections.bean(ResultVO.class, qUser.userNo, qUser.type, qUserInfo.email));
		
		return new PageImpl<ResultVO>(results, pageable, results.size());
	}

}
