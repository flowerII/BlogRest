package com.qianshu.blogrest.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qianshu.blogrest.entity.Auth;
import com.qianshu.blogrest.entity.User;
import com.qianshu.blogrest.exception.AuthException;
import com.qianshu.blogrest.repository.UserRepository;
import com.qianshu.blogrest.service.AuthService;
import com.qianshu.blogrest.service.UserService;



/**
*@author qianshu
*@date   2017年10月19日
*/
@RestController
@RequestMapping("/user")
@Transactional
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthService authService;
	
	@RequestMapping("/init")
	public String initUser() {
		this.userService.initUser();
		return "initUser success";
	}
	
	/**
	 * 生成用户验证
	 */
	@RequestMapping(value="/auth",method=RequestMethod.POST)
	public Auth auth(User user) {
		
		User authUser=this.userService.auth(user);
		if(authUser!=null) {
			Auth auth=this.authService.getAccessToken(authUser);
			return auth;
		}
		
		throw new AuthException("Auth fail");
		
	}
	
	/**
	 * 获取用户信息
	 */
	@RequestMapping(value="/{userId}",method=RequestMethod.GET)
	public User userGet(@PathVariable int userId) {
		return this.userRepository.findOne(userId);
	}
	
	/**
	 * 注册
	 */
	@RequestMapping(value="/registry",method=RequestMethod.POST)
	public Map<String,Object> registry(User user,HttpServletRequest request, HttpServletResponse response) {
		
		String code=request.getParameter("code");
		Map<String,Object> data =new HashMap<String,Object>();		
		HttpSession session=request.getSession();
		String Validcode=(String)session.getAttribute("Validcode");
		
		if(!Validcode.equals(code)) {
			data.put("success", false);
			data.put("msg", "验证码不正确！");
			return data;
		}
		
		User isRegistryUser=this.userRepository.findFirstByUserName(user.getUserName());
		if(isRegistryUser==null) {
			data.put("success", true);
			this.userRepository.save(user);
			return data;
		}		
		else {
			data.put("success", false);
			data.put("msg", "账号已存在，请重新注册！");
			return data;
		}
		
	}
	
	/**
	 * 重置密码
	 */
	@RequestMapping(value="/repassword",method=RequestMethod.POST)
	public Map<String,Object> rePassword(User user,HttpServletRequest request, HttpServletResponse response) {
		
		String code=request.getParameter("code");
		Map<String,Object> data =new HashMap<String,Object>();		
		HttpSession session=request.getSession();
		String Validcode=(String)session.getAttribute("Validcode");
		
		if(!Validcode.equals(code)) {
			data.put("success", false);
			data.put("msg", "验证码不正确！");
			return data;
		}
		
		User isRegistryUser=this.userRepository.findFirstByUserName(user.getUserName());
		if(isRegistryUser!=null) {
			isRegistryUser.setPassword(user.getPassword());
			this.userRepository.save(isRegistryUser);
			data.put("success", true);
			return data;
		}		
		else {
			data.put("success", false);
			data.put("msg", "账号不存在! !");
			return data;
		}
		
	}
}
