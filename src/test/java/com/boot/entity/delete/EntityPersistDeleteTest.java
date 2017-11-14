package com.boot.entity.delete;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.boot.SpringJpaQuerydslSampleApplication;
import com.boot.jpa.domain.User;
import com.boot.jpa.domain.UserInfo;
import com.boot.jpa.repository.user.UserRepository;
import com.boot.jpa.repository.userinfo.UserInfoRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringJpaQuerydslSampleApplication.class)
@WebAppConfiguration
@Rollback
@Transactional
public class EntityPersistDeleteTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Before
	public void before(){
		// insert user
		User user = new User();
		user.setType(User.USER_TYPE.USER);

		UserInfo userInfo = new UserInfo();
		userInfo.setEmail("foo@bar.com");
		userInfo.setUser(user);

		user.setUserInfo(userInfo);
		userRepository.save(user);
	}

	@Test
	public void delete_user_and_userinfo_with_jpa() {
		UserInfo userInfo = userInfoRepository.findByEmail("foo@bar.com");
		User user = userInfo.getUser();

		userRepository.delete(user); // Must be "OrphanRemoval = true" setting is enabled.
		UserInfo deletedUserInfo = userInfoRepository.findByEmail("foo@bar.com");
		Assert.assertNull(deletedUserInfo);
	}

	@Test
	public void delete_userinfo_and_user_with_jpa() {
		UserInfo userInfo = userInfoRepository.findByEmail("foo@bar.com");

		userInfoRepository.delete(userInfo); // Must be "OrphanRemoval = true" setting is enabled.
		UserInfo deletedUserInfo = userInfoRepository.findByEmail("foo@bar.com");
		Assert.assertNull(deletedUserInfo);
	}

	@Test
	public void delete_only_userinfo_with_querydsl() {
		UserInfo userInfo = userInfoRepository.findByEmail("foo@bar.com");

		userInfoRepository.deleteByEmail("foo@bar.com");

		UserInfo deletedUserInfo = userInfoRepository.findByEmail("foo@bar.com");
		Assert.assertNull(deletedUserInfo);

		/*
		 * DML clauses in JPA don't take JPA level cascade rules into account
		 * and don't provide finegrained second level cache interaction.
		 * @see http://www.querydsl.com/static/querydsl/4.1.4/reference/html_single/#d0e388
		 */
		Long userNo = userInfo.getUserNo();
		User user = userRepository.findOne(userNo);
		Assert.assertNotNull(user);
	}

}
