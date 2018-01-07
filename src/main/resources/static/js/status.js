$(document).ready(function(){
	//禁止jquery ajax缓存
	$.ajaxSetup({cache:false})
	//登录表单提交事件
	$('#status_form').submit(function(e){
		//禁止表单默认的提交
		e.preventDefault()
		//检查博文长度是否合法
		if(!ckeckLength()) return
		//使用ajax提交用户登录数据并进行验证
		$.ajax({
			url:'/status',
			method:'POST',
			headers:{
				'accessToken':getAccessToken()
			},
			data:{
				content:$('#content').val()
			},
			success:function(data){
				//跳转到主页
				window.location.href="/blog/home.html"
				
			},
			error:function(e){
				//控制台打印错误
				console.log(e)
			}
		})
	})
})

//检查博文长度
function ckeckLength(){
	//清空错误信息
	$('#content_group').removeClass('has-error')
	$('#error_message').text('')
	
	//博文是否为空
	if($('#content').val().length==0){
		$('#content').focus()
		
		return false;
	}
	
	//博文长度是否超出140个字
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