$(document).ready(function(){
	//禁止jquery ajax缓存
	$.ajaxSetup({cache:false})
	//我要发布点击事件
	$('#publish_btn').click(function(){
		//跳转到发布博文页面
		window.location.href="/blog/status1.html"
	})
	
	//显示好友及粉丝数
	displayFriends()
	//获取并显示博文
	getTimelineMe()
})

function displayFriends(){
	var userId=getCurrentUserId();
	
	$.ajax({
		url:'/user/'+userId,
		method:'GET',
		//在请求头中添加访问令牌
		headers:{
			'accessToken':getAccessToken()
		},
		success:function(user){
			//成功则更新
			$('#friend_number').text(user.friends.length)
			
		},
		error:function(e){
			//控制台打印错误
			console.log(e)
		}
	})
	
	$.ajax({
		url:'/friendship/followers/'+userId,
		method:'GET',
		//在请求头中添加访问令牌
		headers:{
			'accessToken':getAccessToken()
		},
		success:function(users){
			console.log(users)
			//成功则更新
			$('#follower_number').text(users.length)
			
		},
		error:function(e){
			//控制台打印错误
			console.log(e)
		}
	})
}

function getTimelineMe(){
	//从查询字符窜中获取userId
	var userId=getCurrentUserId();
	
	//使用ajax获取
	$.ajax({
		url:'/timeline/user/'+userId,
		method:'GET',
		//在请求头中添加访问令牌
		headers:{
			'accessToken':getAccessToken()
		},
		success:function(data){
			//显示博文列表
			displayTimeline(data)	
			//点赞事件绑定
			bindIncreaseUp()
			//评论事件绑定
			bindComment()
			//删除事件绑定
			bindDelete()
		},
		error:function(e){
			//控制台打印错误
			console.log(e)
		}
	})
}

function bindDelete(){
	$('.remove_link').click(function(){
		//通过父类标签的自定义属性d-status-id获取该条博文id
		var statusId=$(this).parent().attr('d-status-id')
		
		//使用ajax提交
		$.ajax({
			url:'/status/destry/'+statusId,
			method:'DELETE',
			//在请求头中添加访问令牌
			headers:{
				'accessToken':getAccessToken()
			},
			success:function(status){
				//成功刷新
				getTimelineMe()
				
			},
			error:function(e){
				//控制台打印错误
				console.log(e)
			}
		})
	})
}

function bindComment(){
	$('.comment_link').click(function(){
		//通过父类标签的自定义属性d-status-id获取该条博文id
		var statusId=$(this).parent().attr('d-status-id')
		
		//跳转到评论页面，statusId作为查询字符串
		window.location.href="/blog/comment.html?statusId="+statusId
	})
}

function bindIncreaseUp(){
	$('.up_link').click(function(){
		//通过父类标签的自定义属性d-status-id获取该条博文id
		var statusId=$(this).parent().attr('d-status-id')
		
		var link=$(this)//连接对象
		
		//使用ajax提交
		$.ajax({
			url:'/status/like/'+statusId,
			method:'GET',
			//在请求头中添加访问令牌
			headers:{
				'accessToken':getAccessToken()
			},
            success:function(data){
				
				if(data.success){
					var status=data.status
					alert(data.msg)
					link.html('<span class="glyphicon glyphicon-thumbs-up"></span>&nbsp;赞('+status.zans.length+')')
				}else{
					alert(data.msg)
				}
			
			},
			error:function(e){
				//控制台打印错误
				console.log(e)
			}
		})
	})
}

function displayTimeline(data){
	//清空页面博文
	$('#status_list').html('')
	for(var i=0;i<data.length;i++){
		//逐条读取博文
		var status=data[i]
		//构造<li>标签
		var item=$('<li class="list-group-item" />')
		
		//发布字段
		var time='发布于：'+formateTimestamp(status.createTime)
		
		//<li>插入时间作者
		item.append('<p>'+time+'</p>')
		
		//插入博文内容
		item.append('<p>'+status.content+'</p>')
		
		//点赞连接,并使用字体图标
		var remove='<a class="remove_link" href="#"><span class="glyphicon glyphicon-trash"></span>&nbsp;删除</a>'
		
		//点赞连接,并使用字体图标
		var up='<a class="up_link" href="#"><span class="glyphicon glyphicon-thumbs-up"></span>&nbsp;赞('+status.zans.length+')</a>'
		
		//评论连接,并使用字体图标
		var comment='<a class="comment_link" href="#"><span class="glyphicon glyphicon-comment"></span>&nbsp;评论('+status.comments.length+')</a>'
		
		//<li>插入点赞及评论
		item.append('<p d-status-id="'+status.id+'">'+up+'&nbsp;&nbsp;&nbsp;&nbsp;'+comment+'&nbsp;&nbsp;&nbsp;&nbsp;'+remove+'</p>')
		
		//<ul>插入<il>
		$('#status_list').append(item)
	}
}