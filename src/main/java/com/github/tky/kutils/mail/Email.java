package com.github.tky.kutils.mail;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

import org.slf4j.Logger;

public class Email {

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(Email.class);

	private String fromAddress;
	private String subject;
	private String content;
	private List<String> receiveList;
	private List<Attachment> attachments;

	/**
	 * 
	 * @param session
	 * @param fromAddress
	 * @param subject
	 * @param content
	 * @param receiveList
	 * @param fileBytes
	 */
	public void send(Session session) {
		MimeMessage message = new MimeMessage(session);
		InternetAddress[] toArray = new InternetAddress[receiveList.size()];
		try {
			// 接收列表
			for (int i = 0; i < toArray.length; i++) {
				toArray[i] = new InternetAddress(receiveList.get(i));
			}
			message.setSubject(subject);
			message.setSentDate(new Date());
			message.setFrom(new InternetAddress(fromAddress, subject));
			message.addRecipients(RecipientType.TO, toArray);

			// 创建多重消息
			Multipart multipart = new MimeMultipart();
			// 创建消息部分
			// 设置文本消息部分
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(content, "text/html;charset=utf-8");
			multipart.addBodyPart(messageBodyPart);

			if (attachments != null && attachments.size() > 0) {
				for (Attachment attachment : attachments) {
					// 附件部分
					messageBodyPart = new MimeBodyPart();
					// 设置要发送附件的文件路径
					DataSource source = getDataSource(attachment);
					messageBodyPart.setDataHandler(new DataHandler(source));
					// 处理附件名称中文（附带文件路径）乱码问题
					messageBodyPart.setFileName(MimeUtility.encodeText(attachment.getFilename()));
					multipart.addBodyPart(messageBodyPart);
				}
			}
			// 发送完整消息
			message.setContent(multipart);
			// 发送消息
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private DataSource getDataSource(Attachment attachment) throws IOException {
		if (attachment.getAttachmentFile() != null) {
			return new FileDataSource(attachment.getAttachmentFile());
		}
		if (attachment.getAttachmentBytes() != null) {
			return new ByteArrayDataSource(attachment.getAttachmentBytes(), attachment.getMimeType());
		}
		if (attachment.getInputStream() != null) {
			return new ByteArrayDataSource(attachment.getAttachmentBytes(), attachment.getMimeType());
		}

		throw new RuntimeException("there is not attachment found, or it is not support yet, please check your code !");
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		Email.logger = logger;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	/**
	 * 设置邮件正文内容
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getReceiveList() {
		return receiveList;
	}

	public void setReceiveList(List<String> receiveList) {
		this.receiveList = receiveList;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

}
