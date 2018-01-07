function storeUser(user){
	Cookies.set('userId',user.user.id)
	Cookies.set('userName',user.user.userName)
	Cookies.set('accessToken',user.accessToken)
}

function getAccessToken(){
	var accessToken=Cookies.get('accessToken')
	if(accessToken===undefined){
		//Cookie中没有令牌，用户未登录，跳转登录页面
		window.location.href="/blog/login.html"
			return
	}
	
	return accessToken
}

function formateTimestamp(timestamp){
	var d=new Date(timestamp)
		return d.toString()
}

function getCurrentUserName(){
	return Cookies.get('userName')
}

function getCurrentUserId(){
	return Cookies.get('userId')
}

function getParammeterByName(name){
	//使用JavaScript获取查询串参数
	var url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
	
}

function isEmail(str){
    var reg = /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/;
    return reg.test(str);
}