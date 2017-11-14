package com.boot.entity.save;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
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
import com.boot.jpa.domain.User.USER_TYPE;
import com.boot.jpa.repository.user.UserRepository;
import com.boot.jpa.domain.UserHistory;
import com.boot.jpa.domain.UserInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringJpaQuerydslSampleApplication.class)
@WebAppConfiguration
@Rollback
@Transactional
public class EntityPersistSaveTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void one_to_one() {
		User user = new User();
		user.setType(USER_TYPE.USER);

		UserInfo userInfo = new UserInfo();
		userInfo.setEmail("foo@bar.com");
		userInfo.setUser(user);

		user.setUserInfo(userInfo);
		User savedUser = userRepository.save(user);

		Assert.assertNotNull(savedUser);
		Assert.assertEquals(savedUser.getType(), user.getType());
		Assert.assertEquals(savedUser.getUserInfo().getEmail(), userInfo.getEmail());
	}

	@Test
	public void one_to_many() {
		User user = new User();
		user.setType(USER_TYPE.USER);

		UserHistory firstUserHistory = new UserHistory();
		firstUserHistory.setLatestLogin(getDate(-2));
		firstUserHistory.setUser(user);

		UserHistory secondUserHistory = new UserHistory();
		secondUserHistory.setLatestLogin(getDate(-1));
		secondUserHistory.setUser(user);

		List<UserHistory> userHistories = Arrays.asList(firstUserHistory, secondUserHistory);
		user.setUserHistories(userHistories);
		User savedUser = userRepository.save(user);
		Assert.assertNotNull(savedUser);

		List<UserHistory> savedUserHistories = savedUser.getUserHistories();
		Assert.assertNotNull(savedUserHistories);
		Assert.assertTrue(savedUserHistories.size() == 2);
		Assert.assertTrue(savedUserHistories.get(0).getLatestLogin().before(savedUserHistories.get(1).getLatestLogin()));
	}

	private Date getDate(int date){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, date);
		return calendar.getTime();
	}

}
