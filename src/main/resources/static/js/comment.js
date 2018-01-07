$(document).ready(function(){
	//禁止jquery ajax缓存
	$.ajaxSetup({cache:false})
	
	//我要发布点击事件
	$('#publish_btn').click(function(){
		//跳转到发布博文页面
		window.location.href="/blog/status1.html"
	})
	
	//从查询窜中获取statusId
	var statusId=getParammeterByName('statusId')
	
	//获取评论
	getComment(statusId)
	
	//评论提交事件
	$('#comment_form').submit(function(e){
		//禁止表单默认的提交
		e.preventDefault()
		//检查评论长度是否合法
		if(!ckeckLength()) return
		
		//使用ajax提交用户登录数据并进行验证
		$.ajax({
			url:'/comment/'+statusId,
			method:'POST',
			headers:{
				'accessToken':getAccessToken()
			},
			data:{
				content:$('#content').val()
			},
			success:function(data){
				alert('评论成功！！')
				//跳转到主页
				window.location.href="/blog/comment.html?statusId="+statusId
				
			},
			error:function(e){
				//控制台打印错误
				console.log(e)
			}
		})
	})
})


//获得评论
function getComment(statusId){
	$.ajax({
		url:'/status/'+statusId,
		method:'GET',
		//在请求头中添加访问令牌
		headers:{
			'accessToken':getAccessToken()
		},
		success:function(data){
			//显示评论列表
			console.log(data)
			displayStatus(data)
			displayComment(data.comments)	
		},
		error:function(e){
			//控制台打印错误
			console.log(e)
		}
	})
}

//博文内容
function displayStatus(data){
	var status=data
	//构造<li>标签
	var item=$('<li class="list-group-item" />')
	
	//作者字段
	var displayName=status.user.userName

	//添加用户页面连接,并将userId作为查询字符串
	var author='作者：'+displayName
	
	//发布字段
	var time='发布于：'+formateTimestamp(status.createTime)
	
	//<li>插入时间作者
	item.append('<p>'+author+','+time+'</p>')
	
	//插入博文内容
	item.append('<p>'+status.content+'</p>')
	
	//<ul>插入<il>
	$('#status_list').append(item)
}

//
function displayComment(data){
	data.sort(Sorts)
	console.log(data)
	for(var i=data.length-1;i>=0;i--){
		//逐条读取评论
		var comment=data[i]
		//构造<li>标签
		var item=$('<li class="list-group-item" />')		
		//<li>插入时间作者
		item.append('<p>作者：'+comment.userName+',评论于：'+formateTimestamp(comment.createTime)+'</p>')
		
		//插入博文内容
		item.append('<p>'+comment.content+'</p>')
		
		//<ul>插入<il>
		$('#comment_list').append(item)
	}
}

//检查评论长度
function ckeckLength(){
	//清空错误信息
	$('#content_group').removeClass('has-error')
	$('#error_message').text('')
	
	//评论是否为空
	if($('#content').val().length==0){
		$('#content').focus()
		
		return false;
	}
	
	//评论长度是否超出140个字
	if($('#content').val().length>140){
		//显示错误信息
		$('#error_message').text('长度超出140个字符')
		$('#content_group').addClass('has-error')
		$('#content').focus()
		
		return false;
	}
	
	//返回true
	return true;
}

function  Sorts(a,b){
    return a.id-b.id;
}