package com.qianshu.blogrest.repository;

import org.springframework.data.repository.CrudRepository;

import com.qianshu.blogrest.entity.Zan;



/**
*@author qianshu
*@date   2017年10月19日
*/
public interface ZanRepository extends CrudRepository<Zan, Integer> {
	
	Zan findFirstByStatusIdAndUserId(Integer statusId,Integer userId);

}
