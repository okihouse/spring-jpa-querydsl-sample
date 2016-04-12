# spring-jpa-querydsl-sample
spring-jpa-querydsl-sample (join and paging)


## Description

This is sample code with spring-jpa-querydsl.

It has two types of @Query(@NamedQuery) and QueryDsl.

#### @Query(@NamedQuery)
```bash
  // Using @Query example. (Actual using @NamedQuery in {@link com.boot.jpa.domain.User})
	@Query(value = "select new com.boot.jpa.vo.ResultVO("
						+ "u.userNo,"
						+ "u.type,"
						+ "ui.email) "
				 + "from User u inner join u.userInfo ui "
				 + "where u.type = :type"
				)
```

#### QueryDsl
```bash
	JPAQuery query = new JPAQuery(entityManager);
		
	query.from(qUser)
			 .innerJoin(qUser.userInfo, qUserInfo)
			 .where(qUser.type.eq(User.USER_TYPE.USER))
			 .list(Projections.bean(ResultVO.class, qUser.userNo, qUser.type, qUserInfo.email));
```

## Run
```bash
	mvn test
```
