package com.webside.ofp.model;

import java.util.Date;

public class AttachmentEntity {

	private int attachmentId;
	private int productId;
	private String attachmentName;
	private Date createTime;
	private int status;

	public int getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(int attachmentId) {
		this.attachmentId = attachmentId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "AttachmentEntity [attachmentId=" + attachmentId + ", productId=" + productId + ", attachmentName="
				+ attachmentName + ", createTime=" + createTime + ", status=" + status + "]";
	}
}
