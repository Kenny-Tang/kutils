package com.github.tky.kutils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.junit.Test;

import com.github.tky.kutils.mail.Email;

public class EmialTest {

	@Test
	public void testEmail() {
		String id = "report@jftx-tech.com"; //发送账户
	    String pwd = "Re123456"; //账户密码
		String smtp = "smtp.qiye.aliyun.com"; //smtp地址
		Email email = new Email() ;
		email.setContent("测试邮件内容");
		email.setFromAddress(id);
		List<String> receiveList = new ArrayList<String>() ;
		receiveList.add("kenny-tang@hotmail.com") ;
		email.setReceiveList(receiveList);
		email.setSubject("test send ");
		email.setContent(smtp);
		
		Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", smtp);
        props.setProperty("mail.smtp.port", "25");
        props.setProperty("mail.smtp.auth", "true");
        Session session = Session.getInstance(props,
            new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(id, pwd);
                }
            });

		email.send(session);
	}
}
