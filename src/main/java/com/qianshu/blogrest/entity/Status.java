package com.qianshu.blogrest.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
*@author qianshu
*@date   2017年10月19日
*/
@Entity
public class Status {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private Timestamp createTime;
	
	@Column(length=140)
	private String content;
	
	@ManyToOne
	private User user;
	
	@OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.REMOVE)
	private Set<Comment> comments=new HashSet<>();
	
	@OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.REMOVE)
	private Set<Zan> zans=new HashSet<>();

	public Status() {
		this.createTime=new Timestamp(System.currentTimeMillis());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Set<Zan> getZans() {
		return zans;
	}

	public void setZans(Set<Zan> zans) {
		this.zans = zans;
	}
	
	

}
