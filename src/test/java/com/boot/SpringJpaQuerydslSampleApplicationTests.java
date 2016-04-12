package com.boot;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.boot.jpa.domain.User;
import com.boot.jpa.domain.UserInfo;
import com.boot.jpa.repository.UserRepository;
import com.boot.jpa.vo.ResultVO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringJpaQuerydslSampleApplication.class)
@WebAppConfiguration
@Rollback
@Transactional
public class SpringJpaQuerydslSampleApplicationTests {

	@Autowired
	private UserRepository userRepository;
	
	@Before
	public void before(){
		// insert user
		User user = new User();
		user.setType(User.USER_TYPE.USER);
		
		UserInfo userInfo = new UserInfo();
		userInfo.setEmail("user@test.com");
		userInfo.setUser(user);
		
		user.setUserInfo(userInfo);
		
		userRepository.save(user);
	}
	
	@Test
	public void querydsl_join() {
		List<ResultVO> results = userRepository.findByTypeQuerydsl(User.USER_TYPE.USER);
		Assert.assertNotNull(results);
		Assert.assertEquals(1, results.size());
		Assert.assertEquals("user@test.com", results.get(0).getEmail());
	}
	
	@Test
	public void querydsl_join_with_paging() {
		Pageable pageable = new PageRequest(0, 10, new Sort(Direction.DESC, "userNo"));
		Page<ResultVO> results = userRepository.findByTypeQuerydsl(User.USER_TYPE.USER, pageable);
		
		Assert.assertNotNull(results);
		Assert.assertTrue(results.isLast());
		Assert.assertEquals(1, results.getTotalPages());
		Assert.assertEquals("user@test.com", results.getContent().get(0).getEmail());
	}
	
	@Test
	public void query_join() {
		List<ResultVO> results = userRepository.findByType(User.USER_TYPE.USER);
		
		Assert.assertNotNull(results);
		Assert.assertEquals(1, results.size());
		Assert.assertEquals("user@test.com", results.get(0).getEmail());
	}

	@Test
	public void query_join_with_paging() {
		Pageable pageable = new PageRequest(0, 10, new Sort(Direction.DESC, "userNo"));
		Page<ResultVO> results = userRepository.findByType(User.USER_TYPE.USER, pageable);
		
		Assert.assertNotNull(results);
		Assert.assertTrue(results.isLast());
		Assert.assertEquals(1, results.getTotalPages());
		Assert.assertEquals("user@test.com", results.getContent().get(0).getEmail());
	}
	
}
