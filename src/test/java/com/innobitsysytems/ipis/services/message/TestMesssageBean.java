package com.innobitsysytems.ipis.services.message;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.innobitsysytems.ipis.IpisApplication;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.Message;
import com.innobitsysytems.ipis.model.announcement.PublicAnnouncement;
import com.innobitsysytems.ipis.model.device.Device;
import com.innobitsysytems.ipis.model.device.DeviceType;
import com.innobitsysytems.ipis.repository.DeviceRepository;
import com.innobitsysytems.ipis.repository.MessageRepository;
import com.innobitsysytems.ipis.threadmgnt.RequestTypes;
import com.innobitsysytems.ipis.threadmgnt.RequestWrapper;
import com.innobitsysytems.ipis.utility.CustomUtil;

@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IpisApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestMesssageBean {
	@Autowired
	public MessageRepository messageRepository;

	@Autowired
	public DeviceRepository deviceRepository;

	@Mock
	public CustomUtil customUtil;

	@Mock
	private RequestWrapper requestWrapper;

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Test
	public void allMessage() {

		List<Message> msgData = messageRepository.findAll();
		if (msgData == null) {
			exceptionRule.expect(HandledException.class);
			exceptionRule.expectMessage("no records");
		} else {
			List msgList = new ArrayList<>();

			for (int i = 0; i < msgData.size(); i++) {
				msgList.add(customResponseMsg(msgData.get(i)));
			}

			Assertions.assertNotNull(msgList);
		}
	}

	@Test
	public void getSingleMessage() {
		HashMap<String, Object> msgMap = new HashMap<>();
		Message msg = messageRepository.getById(21L);
		if (msg == null) {
			exceptionRule.expect(HandledException.class);
			exceptionRule.expectMessage("message doesnt exist");
		} else {
			Assert.assertNotNull(msg);
		}
	}

	@Test
	public void createMessage() {
		Message message = new Message();

		String displayBoard = message.getDisplayBoard();
		String platformNo = message.getPlatformNo();
		String deviceId = message.getDeviceId();
		Message msgInDb = messageRepository.getMsgByDeviceType(displayBoard, platformNo, deviceId);
		if (msgInDb == null) {
			exceptionRule.expect(HandledException.class);
			exceptionRule.expectMessage("message doesnt exist");

		} else {
			HashMap<String, Object> msgMap = new HashMap<>();

			if (msgInDb == null) {

				message.setCreatedBy(String.valueOf(customUtil.getIdFromToken()));
				message.setDisplayStatus(false);
				messageRepository.save(message);
				msgMap = customResponseMsg(message);

			} else {

				long id = msgInDb.getId();
				Optional<Message> msg = messageRepository.findById(id);
				Message msgData = msg.get();

				msgData.setDisplayBoard(displayBoard);
				msgData.setDisplayStatus(message.getDisplayStatus());
				msgData.setMessageEnglish(message.getMessageEnglish());
				msgData.setMessageHindi(message.getMessageHindi());
				msgData.setMessageRegional(message.getMessageRegional());
				msgData.setMessageEffect(message.getMessageEffect());
				msgData.setSpeed(message.getSpeed());
				msgData.setDisplayStatus(message.getDisplayStatus());
				msgData.setUpdatedBy(String.valueOf(customUtil.getIdFromToken()));
				messageRepository.save(msgData);
				msgMap = customResponseMsg(msgData);

			}

			Assert.assertNotNull(msgMap);
		}
	}

	@Test
	public void putMessage() {
		Message message = new Message();
		Message msgData = messageRepository.getById(21L);

		if (msgData == null) {

			msgData.setMessageEnglish(message.getMessageEnglish());
			msgData.setMessageHindi(message.getMessageHindi());
			msgData.setMessageRegional(message.getMessageRegional());
			msgData.setMessageEffect(message.getMessageEffect());
			msgData.setSpeed(message.getSpeed());
			msgData.setDisplayStatus(message.getDisplayStatus());
			msgData.setUpdatedBy(String.valueOf(customUtil.getIdFromToken()));
			messageRepository.save(msgData);
			Assert.assertNotNull(msgData);
		} else {
			exceptionRule.expect(HandledException.class);
			exceptionRule.expectMessage("already exists");

		}
	}

	@Test
	public void deleteMessage() {
		Message msg = messageRepository.getById(19L);
		if (msg == null) {
			exceptionRule.expect(HandledException.class);
			exceptionRule.expectMessage("Message not found on");
			Assert.assertNull(msg);
		} else {
			Assert.assertNotNull(msg);
			messageRepository.delete(msg);
			Map<String, Boolean> response = new HashMap<>();
			response.put("deleted", Boolean.TRUE);

		}
	}

	@Test
	public void getDeviceId() {
		String[] platformNo = { "1", "2" };
		List<Device> deviceData = deviceRepository.getAllBydeviceType(DeviceType.cds, platformNo);
		String[] ipAddress = new String[deviceData.size()];

		if (deviceData.size() > 0) {

			for (int i = 0; i < deviceData.size(); i++) {

				String ip = deviceData.get(i).getIpAddress();
				String[] ipArray = ip.split("[, . ']+");
				ipAddress[i] = ipArray[3];
			}
		} else {
			exceptionRule.expect(HandledException.class);
			exceptionRule.expectMessage("Display Board for this platform doesn't exist.");
		}
		Assert.assertNotNull(ipAddress);
	}

	@Test
	public void displayMedia() {
		String displayBoard = "display";
		String platformNo = "1";
		String deviceId = "2";

		try {

			Message msgToDisplay = messageRepository.getMsgByDeviceType(displayBoard, platformNo, deviceId);
			String msgData = msgToDisplay.getMessageEnglish();
			String speed = msgToDisplay.getSpeed();
			String effect = msgToDisplay.getMessageEffect();

			switch (displayBoard) {

			case "mldb":

				//requestWrapper.postRequest(msgData, RequestTypes.MessageDataPacket, displayBoard, platformNo, deviceId,
					//	speed, effect, "16");
				break;

			case "pfdb":

				//requestWrapper.postRequest(msgData, RequestTypes.MessageDataPacket, displayBoard, platformNo, deviceId,
						//speed, effect, "16");
				break;

			case "agdb":

				//requestWrapper.postRequest(msgData, RequestTypes.MessageDataPacket, displayBoard, platformNo, deviceId,
						//speed, effect, "16");
				break;

			default:

				break;

			}

		} catch (NullPointerException e) {

			exceptionRule.expect(HandledException.class);
			exceptionRule.expectMessage("NullPointerException Data not found in Database");

		}

	}

	private HashMap<String, Object> customResponseMsg(Message msgData) {

		HashMap<String, Object> msgMap = new HashMap<>();

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

		return msgMap;

	}

}
