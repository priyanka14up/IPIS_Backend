package com.innobitsysytems.ipis.services.notification;

public interface NotificationService {
	
	public void sendNotification(String message);
	
	public void tvDisplayNotification(String message);
	
	public void tvSchedulerMedia(String message);
	public void tvSchedulerData(String message);

	//can be used Later
	//public void sendNotification(String ipAddress, String message);

	

}
