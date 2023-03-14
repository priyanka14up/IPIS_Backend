package com.innobitsysytems.ipis.services.onlineTrain;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.jupiter.api.Test;
import com.innobitsysytems.ipis.IpisApplication;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.announcement.PublicAnnouncement;
import com.innobitsysytems.ipis.model.onlineTrain.OnlineTrain;
import com.innobitsysytems.ipis.repository.OnlineTrainRepository;

@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IpisApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestOnlineTrainBean {

	@Autowired
	public OnlineTrainRepository onlineTrainRepository;

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	Date date = new Date();

	@Test
	public void allTrains() {

		List<OnlineTrain> onlineTrain = onlineTrainRepository.findAll();
		Collections.reverse(onlineTrain);
		Assert.assertNotNull(onlineTrain);

	}

	@Test
	public void updateTrain() {
		OnlineTrain onlineTrain = new OnlineTrain();
		onlineTrain.setArrDep("abc");
		onlineTrain.setTrainStatus("raeched");
		onlineTrain.setPlatformNo("1");
		onlineTrain.isTD();
		onlineTrain.isCGDB();
		onlineTrain.isAnnouncement();
		onlineTrain.setRepeatAnnouncement(3);
		Long trainNumber = 4306L;
		OnlineTrain onlineTrainData = onlineTrainRepository.getById(trainNumber);
		if (onlineTrainData == null) {
			onlineTrainData.setArrDep(onlineTrain.getArrDep());
			onlineTrainData.setTrainStatus(onlineTrain.getTrainStatus());
			onlineTrainData.setPlatformNo(onlineTrain.getPlatformNo());
			onlineTrainData.setTD(onlineTrain.isTD());
			onlineTrainData.setCGDB(onlineTrain.isCGDB());
			onlineTrainData.setAnnouncement(onlineTrain.isAnnouncement());
			onlineTrainData.setRepeatAnnouncement(onlineTrain.getRepeatAnnouncement());
			onlineTrainData.setCoaches(onlineTrain.getCoaches());
			onlineTrainRepository.save(onlineTrainData);

			Assert.assertNotNull(onlineTrainData);

		} else {
			exceptionRule.expect(HandledException.class);
			exceptionRule.expectMessage("no records");

		}
	}

	@Test
	public void deleteTrainData() {
		Long trainNumber = 4306L;
		OnlineTrain onlineTrain = onlineTrainRepository.getById(trainNumber);

		if (onlineTrain == null) {
			exceptionRule.expect(HandledException.class);
			exceptionRule.expectMessage("TrainData not found");
			Assert.assertNull(onlineTrain);
		} else {
			Assert.assertNotNull(onlineTrain);
			onlineTrainRepository.delete(onlineTrain);
		}
	}

}
