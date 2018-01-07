package com.qianshu.blogrest.repository;

import org.springframework.data.repository.CrudRepository;

import com.qianshu.blogrest.entity.Auth;



/**
*@author qianshu
*@date   2017年10月19日
*/
public interface AuthRepository extends CrudRepository<Auth, Integer> {
	
	Auth findFirstByAccessToken(String accessToken);

}
