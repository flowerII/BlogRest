package com.qianshu.blogrest.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.qianshu.blogrest.entity.Status;
import com.qianshu.blogrest.entity.User;

/**
*@author qianshu
*@date   2017年10月19日
*/
public interface StatusRepository extends CrudRepository<Status, Integer> {
	
	List<Status> findByOrderByCreateTimeDesc();
	
	List<Status> findByUserOrderByCreateTimeDesc(User user);
	
	@Query("select s from Status s where s.user in (?1) order by s.createTime desc")
	List<Status> findByFriendsAndMyStatus(Set<User> user);

}
