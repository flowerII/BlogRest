package com.qianshu.blogrest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qianshu.blogrest.entity.User;
import com.qianshu.blogrest.repository.UserRepository;

/**
*@author qianshu
*@date   2017年10月19日
*/
@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public void initUser() {
		User user=new User();
		user.setUserName("zhang");
		user.setPassword("zhang");
		this.userRepository.save(user);
		
		user=new User();
		user.setUserName("wang");
		user.setPassword("wang");
		this.userRepository.save(user);
		
		user=new User();
		user.setUserName("chen");
		user.setPassword("chen");
		this.userRepository.save(user);
	}
	
	/**
	 * 验证用户密码
	 * @param user
	 * @return
	 */
	public User auth(User user) {
		return this.userRepository.findFirstByUserNameAndPassword(user.getUserName(), user.getPassword());
	}
	
	/**
	 * 关注某用户
	 */
	public User createFriendship(User user,int friendId) {
		User friend=this.userRepository.findOne(friendId);
		
		user.getFriends().add(friend);
		friend.getFollowers().add(user);
		
		this.userRepository.save(user);
		this.userRepository.save(friend);
		
		return user;
	}
	/**
	 * 取消关注
	 */
	public User destryFriendship(User user,int friendId) {
		User friend=this.userRepository.findOne(friendId);
		
		user.getFriends().remove(friend);
		friend.getFollowers().remove(user);
		
		this.userRepository.save(user);
		this.userRepository.save(friend);
		
		return user;
	}
	
	/**
	 * 判断是否为好友
	 */
	/*public boolean isFriend(int userId,int friendId) {
		User friend=this.userRepository.findOne(friendId);
		return !this.userRepository.findByIdAndFriends(userId, friend).isEmpty();
	}*/

}
