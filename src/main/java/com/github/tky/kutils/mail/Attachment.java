package com.github.tky.kutils.mail;

import java.io.File;
import java.io.InputStream;

public class Attachment {
	
	private File attachmentFile ;
	private byte[] attachmentBytes;
	private InputStream inputStream ;
	private String mimeType ;
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
