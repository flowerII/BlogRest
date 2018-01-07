package com.qianshu.blogrest.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qianshu.blogrest.entity.Auth;
import com.qianshu.blogrest.entity.User;
import com.qianshu.blogrest.repository.AuthRepository;



/**
*@author qianshu
*@date   2017年10月19日
*/
@Service
@Transactional
public class AuthService {
	
	@Autowired
	private AuthRepository authRepository;
	
	/**
	 * 生成新的accessToken
	 */
	public Auth getAccessToken(User user) {
		Auth auth=new Auth();
		auth.setUser(user);
		Timestamp expireTime=new Timestamp(System.currentTimeMillis()+1000L*60*60*24*30);
		auth.setExpireTime(expireTime);
		this.authRepository.save(auth);
		
		return auth;
	}

}
