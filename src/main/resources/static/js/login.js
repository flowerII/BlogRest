/**
 * 
 */
$(document).ready(function(){
	//禁止jquery ajax缓存
	$.ajaxSetup({cache:false})
	
	//
	$('#register').click(function(){
		window.location.href="/blog/index.html"
	})
	
	$('#repassword').click(function(){
		window.location.href="/blog/repassword.html"
	})
	
	//登录表单提交事件
	$('#login_form').submit(function(e){
		//禁止表单默认的提交
		e.preventDefault()
		//使用ajax提交用户登录数据并进行验证
		$.ajax({
			url:'/user/auth',
			method:'POST',
			data:{
				userName:$('#userName').val(),
				password:$('#password').val()
			},
			success:function(data){
				//保存用户令牌等数据到cookies
				storeUser(data)
				//跳转到主页
				window.location.href="/blog/home.html"
				
			},
			error:function(e){
				$('p.text-danger').text('登录错误 ! !')
				//清除密码并设置焦点
				$('#password').val('').focus()
			}
		})
	})
})