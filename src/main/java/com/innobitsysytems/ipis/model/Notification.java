package com.innobitsysytems.ipis.model;

public class Notification {
	
	    private String senderName;
	    private String receiverName;
	    private String message;
	    private String date;
	    
		public String getSenderName() {
			return senderName;
		}
		public void setSenderName(String senderName) {
			this.senderName = senderName;
		}
		public String getReceiverName() {
			return receiverName;
		}
		public void setReceiverName(String receiverName) {
			this.receiverName = receiverName;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		@Override
		public String toString() {
			return "Notification [senderName=" + senderName + ", receiverName=" + receiverName + ", message=" + message
					+ ", date=" + date + "]";
		}
	    
	    

}
