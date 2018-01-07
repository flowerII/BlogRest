package com.qianshu.blogrest.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.qianshu.blogrest.entity.User;

/**
*@author qianshu
*@date   2017年10月19日
*/
public interface UserRepository extends CrudRepository<User, Integer> {
	
	User findFirstByUserNameAndPassword(String userName,String password);
	
	User findFirstByUserName(String userName);
	
	List<User> findByIdAndFriends(int userId,User friend);

}
