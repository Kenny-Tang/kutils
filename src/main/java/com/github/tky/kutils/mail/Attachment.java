package com.github.tky.kutils.mail;

import java.io.File;
import java.io.InputStream;

public class Attachment {
	
	// 文件类型附件
	private File attachmentFile ;
	// 字节流类型附件
	private byte[] attachmentBytes;
	// 输入流类型附件
	private InputStream inputStream ;
	// 文件MIME类型
	private String mimeType ;
	// 附件名称
	private String filename ;
	
	public File getAttachmentFile() {
		return attachmentFile;
	}
	public void setAttachmentFile(File attachmentFile) {
		this.attachmentFile = attachmentFile;
	}
	public byte[] getAttachmentBytes() {
		return attachmentBytes;
	}
	public void setAttachmentBytes(byte[] attachmentBytes) {
		this.attachmentBytes = attachmentBytes;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
}
