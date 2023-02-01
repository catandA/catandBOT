package com.catand.catandBOT.plugin.SPDNet;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class QQSMSUtils {
	public static void main(String[] args) throws Exception {
		testMail();
	}

	private static void testMail() throws Exception {
		final String from = "2735951230@qq.com";
		// 邮箱的用户名
		final String username = "2735951230@qq.com";
		// 邮箱授权码，刚刚保存的授权码，不是qq密码
		final String password = "ezjsbsgmgteadgjj";
		// 发送邮件的服务器地址，QQ服务器
		final String host = "smtp.qq.com";
		// 接收人邮箱 Fang猫猫的（
		final String to = "1548336875@qq.com";
		// 邮件主题
		final String title = "邮箱测试";

		// 使用QQ邮箱时配置
		Properties prop = new Properties();
		// 设置QQ邮件服务器
		prop.setProperty("mail.host", "smtp.qq.com");
		// 邮件发送协议
		prop.setProperty("mail.transport.protocol", "smtp");
		prop.setProperty("mail.smtp.auth", "true");
		// 需要验证用户名和密码
		// 关于QQ邮箱，还要设置SSL加密，其他邮箱不需要
		MailSSLSocketFactory sf = new MailSSLSocketFactory();
		sf.setTrustAllHosts(true);
		prop.put("mail.smtp.ssl.enable", "true");
		prop.put("mail.smtp.ssl.socketFactory", sf);

		// 创建定义整个邮件程序所需的环境信息的 Session 对象，QQ才有，其他邮箱就不用了
		Session session = Session.getDefaultInstance(prop, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// 发件人邮箱用户名，授权码
				return new PasswordAuthentication(username, password);
			}
		});

		// 开启 Session 的 debug 模式，这样就可以查看程序发送 Email 的运行状态
		session.setDebug(true);

		// 通过 session 得到 transport 对象
		Transport ts = session.getTransport();

		// 使用邮箱的用户名和授权码连上邮箱服务器
		ts.connect(host, username, password);

		// 创建邮件，写邮件
		MimeMessage message = new MimeMessage(session);
        // 指明邮件的发件人
        message.setFrom(new InternetAddress(from));
        // 指明邮件的收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        // 邮件主题
        message.setSubject(title);
        // 邮件内容
        message.setContent("你的SPDNETKey为：aef129d1hsw3", "text/html;charset=utf-8");

		// 发送邮件
		ts.sendMessage(message, message.getAllRecipients());
		System.out.println("验证码发送成功");
		// 释放资源
		ts.close();
	}
}
