package com.qianshu.blogrest.controller;

import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sun.mail.util.MailSSLSocketFactory;



/**
*@author qianshu
*@date   2017年10月19日
*/
@RestController
@RequestMapping("/email")
public class EmailController {
	
	/**
	 * 发送邮箱验证码
	 */
	@RequestMapping(value="/code",method=RequestMethod.POST)
	public void emailCode(HttpServletRequest request, HttpServletResponse response) {
		
		String email=request.getParameter("email");
		int a = (int)(Math.random()*(9999-1000+1))+1000;
		String s=String.valueOf(a);
		HttpSession session2=request.getSession();
		session2.setAttribute("Validcode", s);
		  
	    try {
	    	String from = "1479676948@qq.com";
		     String host = "smtp.qq.com";  
		     Properties properties = System.getProperties();
		     properties.setProperty("mail.smtp.host", host);
		     properties.put("mail.smtp.auth", "true");	
	         MailSSLSocketFactory sf;
			 try {
				 sf = new MailSSLSocketFactory();
				 sf.setTrustAllHosts(true);
			     properties.put("mail.smtp.ssl.enable", "true");
			     properties.put("mail.smtp.ssl.socketFactory", sf);
			 } catch (GeneralSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 }			      			      
			 Session session = Session.getInstance(properties,new Authenticator(){
				 public PasswordAuthentication getPasswordAuthentication()
			     {
			         return new PasswordAuthentication("1479676948@qq.com", "nbfkjcrkculajgbb"); //�������ʼ��û���������
			     }
			 });
		     try{
		         MimeMessage message = new MimeMessage(session);
		         message.setFrom(new InternetAddress(from));
		         message.addRecipient(Message.RecipientType.TO,
		                                  new InternetAddress(email));
		         message.setSubject("您正在注册博客！！！");
		         message.setText("验证码："+s+",请勿泄漏，如非本人操作请忽略！！");
		         Transport.send(message);
		     }catch (MessagingException mex) {
		         mex.printStackTrace();
		     }
		 } catch (Exception e1) {
			e1.printStackTrace();
		 }		
	}

	
}
