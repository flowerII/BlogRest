package com.qianshu.blogrest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qianshu.blogrest.entity.Zan;
import com.qianshu.blogrest.repository.ZanRepository;


/**
*@author qianshu
*@date   2017年10月19日
*/
@Service
public class StatusService {
	
	@Autowired
	private ZanRepository zanRepository;
	
	/**
	 * 点赞
	 */
	public Zan incLike(Zan zan) {
		return this.zanRepository.save(zan);		
	}

}
