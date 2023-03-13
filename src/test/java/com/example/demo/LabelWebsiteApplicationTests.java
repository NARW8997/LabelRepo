package com.example.demo;

import com.example.demo.domain.User;
import com.example.demo.domain.UserLabel;
import com.example.demo.mapper.UserLabelMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.services.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LabelWebsiteApplicationTests {
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserLabelMapper userLabelMapper;
	@Autowired
	private IUserService userService;

	@Test
	void contextLoads() {
//		User user = new User();
//		user.setUsername("wang666");
//		user.setPassword("222");
//		user.setFirstName("zeqing");
//		user.setLastName("wang");
//		userMapper.insert(user);
		System.out.println(userService.insertLabelWithUserId(1, "kitchen"));
	}


}
