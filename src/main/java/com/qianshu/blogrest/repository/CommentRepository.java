package com.qianshu.blogrest.repository;

import org.springframework.data.repository.CrudRepository;

import com.qianshu.blogrest.entity.Comment;



/**
*@author qianshu
*@date   2017年10月19日
*/
public interface CommentRepository extends CrudRepository<Comment, Integer> {

}
