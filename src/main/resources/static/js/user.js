$(document).ready(function(){
	//禁止jquery ajax缓存
	$.ajaxSetup({cache:false})
	//我要发布点击事件
	$('#publish_btn').click(function(){
		//跳转到发布博文页面
		window.location.href="/blog/status1.html"
	})
	
	//显示关注开关按钮
	displayFollow()
	
	//绑定关注事件
	bindFollow()
	
	//获取并显示某人博文
	getTimelineUser()
})

function bindFollow(){
	$('#follow_toggle').change(function(){
		//获取开关状态
		var checked=$(this).prop('checked')
		//获得两人id
		var curUserId=getCurrentUserId()
    	var userId=getParammeterByName('userId')
    	
    	//url
    	var url=checked? '/friendship/destry/'+userId:'/friendship/create/'+userId
    	
		$.ajax({
			url: url,
			method:'GET',
			//在请求头中添加访问令牌
			headers:{
				'accessToken':getAccessToken()
			},
			success:function(){
				//刷新
				displayFollow()
			},
			error:function(e){
				//控制台打印错误
				console.log(e)
			}
		})
	})
}

function displayFollow(){
	var curUserId=getCurrentUserId()
	//博文用户id
	var userId=getParammeterByName('userId')
	
	$.ajax({
			url:'/user/'+curUserId,
			method:'GET',
			//在请求头中添加访问令牌
			headers:{
				'accessToken':getAccessToken()
			},
			success:function(user){
				console.log(user)	
				var friends=user.friends
				var isFriend=false
				for(var i=0;i<friends.length;i++){
					var friend=friends[i]
					if(friend.id==userId){
						isFriend=true
						break
					}
				}
				//
				if(isFriend){
					$('#follow_toggle').bootstrapToggle('off')
				}else{
					$('#follow_toggle').bootstrapToggle('on')
				}
			},
			error:function(e){
				//控制台打印错误
				console.log(e)
			}
		})
}

function getTimelineUser(){
	//从查询字符窜中获取userId
	var userId=getParammeterByName('userId')
	
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
		},
		error:function(e){
			//控制台打印错误
			console.log(e)
		}
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

	for(var i=0;i<data.length;i++){
		//逐条读取博文
		var status=data[i]
		//构造<li>标签
		var item=$('<li class="list-group-item" />')
		
		//添加用户页面连接,
		var author='作者：'+status.user.userName
		
		//发布字段
		var time='发布于：'+formateTimestamp(status.createTime)
		
		//<li>插入时间作者
		item.append('<p>'+author+','+time+'</p>')
		
		//插入博文内容
		item.append('<p>'+status.content+'</p>')
		
		//点赞连接,并使用字体图标
		var up='<a class="up_link" href="#"><span class="glyphicon glyphicon-thumbs-up"></span>&nbsp;赞('+status.zans.length+')</a>'
		
		//评论连接,并使用字体图标
		var comment='<a class="comment_link" href="#"><span class="glyphicon glyphicon-comment"></span>&nbsp;评论('+status.comments.length+')</a>'
		
		//<li>插入点赞及评论
		item.append('<p d-status-id="'+status.id+'">'+up+'&nbsp;&nbsp;&nbsp;&nbsp;'+comment+'</p>')
		
		//<ul>插入<il>
		$('#status_list').append(item)
		
		$(document).attr("title","");
		$("title").html(status.user.userName+"的博客");
	}
}