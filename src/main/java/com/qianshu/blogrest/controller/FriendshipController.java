package com.qianshu.blogrest.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qianshu.blogrest.entity.User;
import com.qianshu.blogrest.repository.AuthRepository;
import com.qianshu.blogrest.repository.UserRepository;
import com.qianshu.blogrest.service.UserService;

/**
*@author qianshu
*@date   2017年10月20日
*/
@RestController
@RequestMapping("/friendship")
@Transactional
public class FriendshipController {
	
	@Autowired
	private AuthRepository authRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 关注好友
	 */
	@RequestMapping("/create/{friendId}")
	public Set<User> create(@RequestHeader("accessToken") String accessToken,@PathVariable("friendId") int friendId) {
		User user=this.authRepository.findFirstByAccessToken(accessToken).getUser();
		
		return this.userService.createFriendship(user, friendId).getFriends();
	}
	
	/**
	 * 取消关注好友
	 */
	@RequestMapping("/destry/{friendId}")
	public Set<User> destry(@RequestHeader("accessToken") String accessToken,@PathVariable("friendId") int friendId) {
		User user=this.authRepository.findFirstByAccessToken(accessToken).getUser();
		
		return this.userService.destryFriendship(user, friendId).getFriends();
	}
	
	/**
	 * 获取用户粉丝
	 */
	@RequestMapping(value="followers/{userId}",method=RequestMethod.GET)
	public Set<User> followerGet(@PathVariable("userId") int userId){
		
		return this.userRepository.findOne(userId).getFollowers();
	}
	

}
