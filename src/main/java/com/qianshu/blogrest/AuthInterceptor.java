package com.qianshu.blogrest;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.qianshu.blogrest.entity.Auth;
import com.qianshu.blogrest.exception.AuthException;
import com.qianshu.blogrest.repository.AuthRepository;

/**
*@author qianshu
*@date   2017年10月23日
*/
public class AuthInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private AuthRepository authRepository;
	
	/**
	 * 验证accessToken
	 */
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws Exception{
		super.preHandle(request, response, handler);
		
		/*String url=request.getRequestURI();
		if("/".equals(url)) {
			return true;
		}
		else {*/
			String accessToken=request.getHeader("accessToken");
			if(accessToken==null) {
				throw new AuthException("accessToken is null ! !");
			}
			
			Auth auth=this.authRepository.findFirstByAccessToken(accessToken);
			if(auth==null) {
				throw new AuthException("invalid accessToken! !");
			}
			
			if(auth.getExpireTime().before(new Date())) {
				throw new AuthException("accessToken expired! !");
			}
			
			return true;
		/*}*/
	}

}
