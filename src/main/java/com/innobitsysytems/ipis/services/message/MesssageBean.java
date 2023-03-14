/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Message Bean
 */

package com.innobitsysytems.ipis.services.message;

import java.util.*;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.Message;
import com.innobitsysytems.ipis.model.device.Device;
import com.innobitsysytems.ipis.model.device.DeviceType;
import com.innobitsysytems.ipis.model.setup.BoardSetting;
import com.innobitsysytems.ipis.repository.DeviceRepository;
import com.innobitsysytems.ipis.repository.MessageRepository;
import com.innobitsysytems.ipis.threadmgnt.RequestTypes;
import com.innobitsysytems.ipis.threadmgnt.RequestWrapper;
import com.innobitsysytems.ipis.utility.CommonUtil;
import com.innobitsysytems.ipis.utility.CustomUtil;

@Service
public class MesssageBean implements MessageService{
	
	@Autowired
	public MessageRepository messageRepository;
	
	@Autowired
	public DeviceRepository deviceRepository;
	
	@Autowired
	public CustomUtil customUtil;
	
	@Autowired
	private RequestWrapper requestWrapper;
	
	@Autowired
	public CommonUtil commonUtil;
	
	@Override
	public List allMessage()  throws HandledException{
		
		List <Message> msgData = new ArrayList<Message>();
		
		try{
			
			msgData = messageRepository.findAll();
			
		}catch(Exception e) {
			
			 System.out.println(e);
			
		}
		
		List msgList = new ArrayList<>() ;
		
		for (int i = 0; i < msgData.size(); i++){  
			
			msgList.add(customResponseMsg(msgData.get(i)));  
		}  
		
		return msgList;
	}
	
	@Override
	public HashMap<String, Object> getSingleMessage(Long id) throws HandledException {
		
		Message msg = messageRepository.findById(id)
	            .orElseThrow(() -> new HandledException("NOT_FOUND", "Message not found on :" + id));
		
	    return customResponseMsg(msg);
	}
	
	@Override
	public HashMap<String, Object> createMessage ( HttpServletRequest request, Message message ) throws HandledException {
		
		Message msgInDb =  messageRepository.getMsgByDeviceType(message.getDisplayBoard(), message.getPlatformNo(), message.getDeviceId());
		HashMap<String, Object> msgMap = new HashMap<>();
		
		if (msgInDb == null) {
			System.out.println("message gap"+message.getCharacterGap());
			message.setCreatedBy(String.valueOf(customUtil.getIdFromToken()));
			message.setDisplayStatus(false);
			commonUtil.updateActivities("Message added ", String.valueOf(customUtil.getIdFromToken()));
			messageRepository.save(message);
			msgMap = customResponseMsg(message);
			
		}else {
			
			 Optional<Message> msg = messageRepository.findById(msgInDb.getId());
			 
			 msg.get().setDisplayBoard(message.getDisplayBoard());
			 msg.get().setDisplayStatus(message.getDisplayStatus());
			 msg.get().setMessageEnglish(message.getMessageEnglish());
			 msg.get().setMessageHindi(message.getMessageHindi());
			 msg.get().setMessageRegional(message.getMessageRegional());
			 msg.get().setMessageEffect(message.getMessageEffect());
			 msg.get().setSpeed(message.getSpeed());
			 msg.get().setLetterSize(message.getLetterSize());
			 msg.get().setCharacterGap(message.getCharacterGap());
			 msg.get().setPageChangeTime(message.getPageChangeTime());	 
			 msg.get().setDisplayStatus(message.getDisplayStatus());
			 msg.get().setUpdatedBy(String.valueOf(customUtil.getIdFromToken()));
			 commonUtil.updateActivities("Message updated ", String.valueOf(customUtil.getIdFromToken()));
			 messageRepository.save(msg.get());
			 msgMap = customResponseMsg(msg.get());
			
		}
		
		return msgMap;
	}
	
	//not in use
	@Override
	public  HashMap<String, Object> putMessage(Long id, Message message) throws HandledException{
		
		Message msgData = messageRepository.findById(id)
	            .orElseThrow(() -> new HandledException("NOT_FOUND", "Message not found on :" + id));
		
		 msgData.setMessageEnglish(message.getMessageEnglish());
		 msgData.setMessageHindi(message.getMessageHindi());
		 msgData.setMessageRegional(message.getMessageRegional());
		 msgData.setMessageEffect(message.getMessageEffect());
		 msgData.setSpeed(message.getSpeed());
		 msgData.setDisplayStatus(message.getDisplayStatus());
		 msgData.setLetterSize(message.getLetterSize());
		 msgData.setCharacterGap(message.getCharacterGap());
		 msgData.setPageChangeTime(message.getPageChangeTime());
		 msgData.setUpdatedBy(String.valueOf(customUtil.getIdFromToken()));
		 messageRepository.save(msgData);
		 return customResponseMsg(msgData);
		
	}
	
	
	@Override
	public  Map<String, Boolean> deleteMessage(Long id) throws HandledException {
		
		Message msg  =  messageRepository.findById(id)
	            .orElseThrow(() -> new HandledException("NOT_FOUND", "Message not found on : " + id));
		 
		 commonUtil.updateActivities("Message deleted ", String.valueOf(customUtil.getIdFromToken()));
		 messageRepository.delete(msg);
		 Map<String, Boolean> response = new HashMap<>();
		 response.put("deleted", Boolean.TRUE);
		 return response;
	
	}
	
	@Override
	public List<String> getDeviceId( DeviceType deviceType ,String platformNo) throws HandledException {
		
		List <Device> devices=deviceRepository.findAllBydeviceType(deviceType);
		List<String> list = new ArrayList<String>();
		String thirdOctate="";
		
		for(int i=0;i<devices.size();i++)
		{
		String ip=devices.get(i).getIpAddress();
		String[] platArray=devices.get(i).getPlatformNo();
		
		if(platformNo.equals(platArray[0]))
		{

			 thirdOctate=ipValues(ip)[3];
			 list.add(thirdOctate);
			
		}
		
		}
		return list;

	}

	private String[] ipValues(String ip) {
		
		String[] ipAddress = ip.split("[, . ']+");
		return ipAddress;
		
	}

	@Override
	public HashSet<String> getDevicePlatform(DeviceType deviceType) throws HandledException{
		
		List<Device> deviceData = deviceRepository.findAllBydeviceType(deviceType);
		HashSet<String> platform = new HashSet<String>();
		
		if(deviceData.size() != 0) {
			
			for (int i = 0; i < deviceData.size(); i++) {
				
				String[] platArray = deviceData.get(i).getPlatformNo();
				platform.add(platArray[0]);
				
			}	
			
		}else {

			throw new HandledException("CHECK_PARAMETERS","Display Board doesn't Exist.");
		}
		
		return platform;
	}
	
	@Override
	public void displayMedia() throws HandledException{
		
		List<Message> msgToDisplay =  messageRepository.findByDisplayStatus(true);
			
		for(int i = 0; i < msgToDisplay.size(); i++) {
			
			switch (msgToDisplay.get(i).getDisplayBoard()) {
			
			case "mldb" : 
				
				requestWrapper.postRequest(msgToDisplay.get(i),RequestTypes.MessageDataPacket, msgToDisplay.get(i).getDisplayBoard(),  msgToDisplay.get(i).getPlatformNo(),  msgToDisplay.get(i).getDeviceId(), msgToDisplay.get(i).getSpeed(), msgToDisplay.get(i).getMessageEffect(),msgToDisplay.get(i).getLetterSize(),Integer.parseInt(msgToDisplay.get(i).getCharacterGap()),Integer.parseInt(msgToDisplay.get(i).getPageChangeTime()));
				
				break;
				
			case "pfdb" :
				
				requestWrapper.postRequest(msgToDisplay.get(i),RequestTypes.MessageDataPacket, msgToDisplay.get(i).getDisplayBoard(),  msgToDisplay.get(i).getPlatformNo(),  msgToDisplay.get(i).getDeviceId(), msgToDisplay.get(i).getSpeed(), msgToDisplay.get(i).getMessageEffect(),msgToDisplay.get(i).getLetterSize(),Integer.parseInt(msgToDisplay.get(i).getCharacterGap()),Integer.parseInt(msgToDisplay.get(i).getPageChangeTime()));
				
				break;
				
			case "agdb" :
				
				requestWrapper.postRequest(msgToDisplay.get(i),RequestTypes.MessageDataPacket, msgToDisplay.get(i).getDisplayBoard(),  msgToDisplay.get(i).getPlatformNo(),  msgToDisplay.get(i).getDeviceId(), msgToDisplay.get(i).getSpeed(), msgToDisplay.get(i).getMessageEffect(),msgToDisplay.get(i).getLetterSize(),Integer.parseInt(msgToDisplay.get(i).getCharacterGap()),Integer.parseInt(msgToDisplay.get(i).getPageChangeTime()));
				
				break;
				
			default :
				break;
			
			}
			
			commonUtil.updateActivities("Displayed Selected Messages ", String.valueOf(customUtil.getIdFromToken()));
			
		}
		
	}
		
	//custom response
	private HashMap<String, Object> customResponseMsg( Message msgData) {
		
		HashMap<String, Object> msgMap =  new HashMap<>();
		
		msgMap.put("id", msgData.getId());
		msgMap.put("displayBoard", msgData.getDisplayBoard());
		msgMap.put("messageEnglish", msgData.getMessageEnglish());
		msgMap.put("messageRegional", msgData.getMessageRegional());
		msgMap.put("messageHindi", msgData.getMessageHindi());
		msgMap.put("platformNo", msgData.getPlatformNo());
		msgMap.put("deviceId", msgData.getDeviceId());
		msgMap.put("speed", msgData.getSpeed());
		msgMap.put("messageEffect", msgData.getMessageEffect());
		msgMap.put("displayStatus", msgData.getDisplayStatus());
		msgMap.put("characterGap", msgData.getCharacterGap());
		msgMap.put("letterSize", msgData.getLetterSize());
		msgMap.put("pageChangeTime", msgData.getPageChangeTime());
		
		return msgMap;
		
	}
	
}

