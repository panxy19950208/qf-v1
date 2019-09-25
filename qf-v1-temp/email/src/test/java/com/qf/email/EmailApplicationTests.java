package com.qf.email;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailApplicationTests {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private TemplateEngine templateEngine;


	@Test
	public void contextLoads() {
	}

	@Test
	public void sendMail(){
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		//设置邮件主题
		mailMessage.setSubject("网易邮箱与前锋达成深度合作");
		//邮件内容
		mailMessage.setText("SpringBoot整合email成功");
		//发送人账号
		mailMessage.setFrom("panxy281601577@163.com");
		//接受人账号
		mailMessage.setTo("632105204@qq.com");
		//发送
		javaMailSender.send(mailMessage);
	}

	@Test
	public void sendMail2() throws MessagingException {
		//设置邮件主题
		MimeMessage message = javaMailSender.createMimeMessage();
		//设置可以支持发送html格式
		MimeMessageHelper messageHelper = new MimeMessageHelper(message,true);
		message.setSubject("网易邮箱与前锋达成深度合作");
		//邮件内容
		messageHelper.setText("SpringBoot整合email成功<a href='http://www.baidu.com'>点击激活</a>",true);
		//发送人账号
		messageHelper.setFrom("panxy281601577@163.com");
		//接受人账号
		messageHelper.setTo("632105204@qq.com");
		//发送
		javaMailSender.send(message);
	}


	@Test
	public void sendMail3() throws MessagingException {
		//设置邮件主题
		MimeMessage message = javaMailSender.createMimeMessage();
		//设置可以支持发送html格式
		MimeMessageHelper messageHelper = new MimeMessageHelper(message,true);
		message.setSubject("网易邮箱与前锋达成深度合作");
		//邮件内容
		messageHelper.setText("SpringBoot整合email成功<a href='http://www.baidu.com'>点击激活</a>",true);
		//发送人账号
		messageHelper.setFrom("panxy281601577@163.com");
		//接受人账号
		messageHelper.setTo("632105204@qq.com");
		//添加附件
		String filePath = "C://Users//Administrator//Desktop//321.gif";
		FileSystemResource fileSystemResource = new FileSystemResource(filePath);
		messageHelper.addAttachment("321.gif",fileSystemResource);
		//发送
		javaMailSender.send(message);

	}

	@Test
	public void sendTemplateEmail()throws MessagingException {
		//设置邮件主题
		MimeMessage message = javaMailSender.createMimeMessage();
		//设置可以支持发送html格式
		MimeMessageHelper messageHelper = new MimeMessageHelper(message,true);
		message.setSubject("网易邮箱与前锋达成深度合作");
		//读取模板信息
		//设置要发送给模板的参数
		Context context = new Context();
		context.setVariable("username","panxy");
		//第一个参数是模板的名字，第二个是给模板的参数，info是组合成的HTML代码
		String info = templateEngine.process("activate", context);
		//发送邮件内容
		messageHelper.setText(info,true);
		//发送人账号
		messageHelper.setFrom("panxy281601577@163.com");
		//接受人账号
		messageHelper.setTo("632105204@qq.com");

		//发送
		javaMailSender.send(message);
	}


}

