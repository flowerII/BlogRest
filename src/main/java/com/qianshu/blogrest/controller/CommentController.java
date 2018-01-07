package com.qianshu.blogrest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qianshu.blogrest.entity.Comment;
import com.qianshu.blogrest.entity.Status;
import com.qianshu.blogrest.entity.User;
import com.qianshu.blogrest.repository.AuthRepository;
import com.qianshu.blogrest.repository.CommentRepository;
import com.qianshu.blogrest.repository.StatusRepository;

/**
*@author qianshu
*@date   2017年10月20日
*/
@RestController
@RequestMapping("/comment")
@Transactional
public class CommentController {
	
	@Autowired
	private AuthRepository authRepository;
	
	@Autowired
	private StatusRepository statusRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	/**
	 * 评论微博
	 */
	@RequestMapping(value="/{statusId}",method=RequestMethod.POST)
	public Status commentPost(Comment comment,@PathVariable("statusId") int statusId,@RequestHeader("accessToken") String accessToken) {
		
		Status status=statusRepository.findOne(statusId);
		
		if(comment.getContent().length()>0) {
			User user=this.authRepository.findFirstByAccessToken(accessToken).getUser();
			comment.setUserName(user.getUserName());
			this.commentRepository.save(comment);
			status.getComments().add(comment);
			this.statusRepository.save(status);
		}
		return status;
	}

}
