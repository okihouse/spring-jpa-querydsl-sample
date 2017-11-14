package com.boot.jpa.repository.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.boot.jpa.domain.User;
import com.boot.jpa.domain.User.USER_TYPE;
import com.boot.jpa.repository.user.custom.UserCustomRepository;
import com.boot.jpa.vo.ResultVO;

public interface UserRepository extends JpaRepository<User, Long>, UserCustomRepository {


	// Using @Query example. (Actual using @NamedQuery in {@link com.boot.jpa.domain.User})
//	@Query(value = "select new com.boot.jpa.vo.ResultVO("
//						+ "u.userNo,"
//						+ "u.type,"
//						+ "ui.email) "
//				 + "from User u inner join u.userInfo ui "
//				 + "where u.type = :type"
//				)
	List<ResultVO> findByType(@Param("type") USER_TYPE user);

	// Using @Query example. (Actual using @NamedQuery in {@link com.boot.jpa.domain.User})
//	@Query(value = "select new com.boot.jpa.vo.ResultVO("
//						+ "u.userNo,"
//						+ "u.type,"
//						+ "ui.email) "
//				 + "from User u inner join u.userInfo ui "
//				 + "where u.type = :type"
//				)
	Page<ResultVO> findByType(@Param("type") USER_TYPE user, Pageable pageable);

}
