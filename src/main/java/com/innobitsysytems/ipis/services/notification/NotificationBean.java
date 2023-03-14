package com.innobitsysytems.ipis.services.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
public class NotificationBean implements NotificationService{
	
	@Autowired
	private MessageSendingOperations<String> messageSendingOperations;
	
	@Override
	public void sendNotification(String message) {
    	
   	 String broadcast = String.format("Message"+message);
     messageSendingOperations.convertAndSend("/user", broadcast);
   	
   }

//	@Override
//	public void sendNotification(String ipAddress,String message) {
//    	
//   	 String broadcast = String.format(message+" from this "+ipAddress);
//     messageSendingOperations.convertAndSend("/user", broadcast);
//   	
//   }
	
	@Override
	public void tvDisplayNotification(String message) {
		// TODO Auto-generated method stub
		String broadcast = String.format("Message"+message);
	     messageSendingOperations.convertAndSend("/tv", broadcast);
		
	}

	@Override
	public void tvSchedulerMedia(String message) {
		
		String broadcast = String.format("Message"+message);
	     messageSendingOperations.convertAndSend("/tvMedia", broadcast);
		
	}

	@Override
	public void tvSchedulerData(String message) {
		// TODO Auto-generated method stub
		String broadcast = String.format("Message"+message);
	     messageSendingOperations.convertAndSend("/tvData", broadcast);
		
	}

	

	


}
