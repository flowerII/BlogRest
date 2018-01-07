package com.qianshu.blogrest.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qianshu.blogrest.entity.Status;
import com.qianshu.blogrest.entity.User;
import com.qianshu.blogrest.repository.AuthRepository;
import com.qianshu.blogrest.repository.StatusRepository;
import com.qianshu.blogrest.repository.UserRepository;

/**
*@author qianshu
*@date   2017年10月20日
*/
@RestController
@RequestMapping("/timeline")
@Transactional
public class TimelineController {
	
	@Autowired
	private AuthRepository authRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private StatusRepository statusRepository;
	
	/**
	 * 广场时间线
	 */
	@RequestMapping(value="/public")
	public List<Status> timelinePublic() {
		
		return this.statusRepository.findByOrderByCreateTimeDesc();
	}
	
	/**
	 * 用户的时间线
	 */
	@RequestMapping(value="/user/{userId}")
	public List<Status> timelineUser(@PathVariable("userId") int userId) {
		User user=this.userRepository.findOne(userId);
		
		return this.statusRepository.findByUserOrderByCreateTimeDesc(user);
	}
	
	/**
	 * 我的时间线
	 */
	@RequestMapping(value="/home")
	public List<Status> timelineHome(@RequestHeader("accessToken") String accessToken) {
		User user=this.authRepository.findFirstByAccessToken(accessToken).getUser();
		Set<User> users=new HashSet<User>(user.getFriends());
		users.add(user);
		
		return this.statusRepository.findByFriendsAndMyStatus(users);
	}
	
	/**
	 * 我的主页
	 */
	@RequestMapping(value="/me")
	public String timelineMe(@ModelAttribute("authUserId") int authUserId,Model model) {
		User user=this.userRepository.findOne(authUserId);
		List<Status> statuses=this.statusRepository.findByUserOrderByCreateTimeDesc(user);
		model.addAttribute(statuses);
		model.addAttribute("friendSize", user.getFriends().size());
		model.addAttribute("followerSize", user.getFollowers().size());

		return "me";
	}

}
