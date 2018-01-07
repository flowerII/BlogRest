package com.qianshu.blogrest.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qianshu.blogrest.entity.Status;
import com.qianshu.blogrest.entity.User;
import com.qianshu.blogrest.entity.Zan;
import com.qianshu.blogrest.repository.AuthRepository;
import com.qianshu.blogrest.repository.StatusRepository;
import com.qianshu.blogrest.repository.ZanRepository;

/**
*@author qianshu
*@date   2017年10月19日
*/
@RestController
@RequestMapping("/status")
@Transactional
public class StatusController {
	
	@Autowired
	private AuthRepository authRepository;
	
	@Autowired
	private StatusRepository statusRepository;
	
	@Autowired
	private ZanRepository zanRepository;
	
	/**
	 * 发布微博
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Status statusPost(@RequestHeader("accessToken") String accessToken,Status status) {
		if(status.getContent().length()>0) {
			User user=this.authRepository.findFirstByAccessToken(accessToken).getUser();
			status.setUser(user);
			this.statusRepository.save(status);
		}
		return status;
	}
	
	/**
	 * 获取一条微博
	 */
	@RequestMapping(value="/{statusId}",method=RequestMethod.GET)
	public Status statusGet(@PathVariable("statusId") int statusId,@RequestHeader("accessToken") String accessToken) {
		return this.statusRepository.findOne(statusId);
	}
	
	/**
	 * 点赞
	 */
	@RequestMapping(value="/like/{statusId}")
	public Map<String,Object> statusLike(@PathVariable int statusId,@RequestHeader("accessToken") String accessToken) {
		
		User user=this.authRepository.findFirstByAccessToken(accessToken).getUser();
		Map<String,Object> data =new HashMap<String,Object>();
		
		Zan z=this.zanRepository.findFirstByStatusIdAndUserId(statusId, user.getId());
		
		if(z!=null) {
			data.put("success", false);
			data.put("msg", "你已为它点过赞了，不能重复点赞！");
			return data;
		}
		else {
			Zan zan=new Zan();
			zan.setStatusId(statusId);
			zan.setUserId(user.getId());
			this.zanRepository.save(zan);
			
			Status status=this.statusRepository.findOne(statusId);
			status.getZans().add(zan);
			data.put("success", true);
			data.put("msg", "点赞成功！");
			data.put("status", status);
			return data;
		}
	}
	
	/**
	 * 删除微博
	 */
	@RequestMapping(value="/destry/{statusId}",method=RequestMethod.DELETE)
	public Status statusDestry(@PathVariable("statusId") int statusId) {
		Status status=this.statusRepository.findOne(statusId);
		this.statusRepository.delete(status);
		return status;
	}

}
