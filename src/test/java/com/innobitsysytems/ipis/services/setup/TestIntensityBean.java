package com.innobitsysytems.ipis.services.setup;

import java.util.HashMap;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.innobitsysytems.ipis.IpisApplication;
import com.innobitsysytems.ipis.model.setup.Intensity;
import com.innobitsysytems.ipis.repository.setup.IntensityRepository;
import com.innobitsysytems.ipis.threadmgnt.RequestTypes;
import com.innobitsysytems.ipis.threadmgnt.RequestWrapper;
import com.innobitsysytems.ipis.utility.CustomUtil;

@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IpisApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestIntensityBean {
	private static final Logger log = LoggerFactory.getLogger(IntensityBean.class);

	@Autowired
	private IntensityRepository intensityRepo;

	@Autowired
	private RequestWrapper requestWrapper;

	@Autowired
	public CustomUtil customUtil;

	@Test
	public void TestGetAutoIntensity() {
		Intensity intensityData = intensityRepo.findByIntensityMode("Auto");
		customAutoIntensity(intensityData);
		Assert.assertNotNull(intensityData);
	}

	@Test
	public void create() {
		Intensity intensity = new Intensity();
//		intensity.setId(54);
		intensity.setIntensityValue(null);
		intensity.setDayIntensity(55);
		intensity.setDayStartTime("14:55");
		intensity.setIntensityMode("Auto");

		HashMap<String, Object> intensityMap = new HashMap<>();
		Intensity intensityData = intensityRepo.findByIntensityMode("Auto");

		if (intensity.getIntensityMode().equals("Auto")) {

			if (intensityData == null) {
				Assert.assertNull(intensityData);
				intensity.setCreatedBy((int) customUtil.getIdFromToken());
				intensityRepo.save(intensity);
				intensityMap = customAutoIntensity(intensity);
				//requestWrapper.postRequest(intensity.getDayIntensity(), RequestTypes.SET_INTENSITY);

			} else {

				intensityData.setCreatedBy(intensityData.getCreatedBy());
				intensityData.setIntensityMode(intensity.getIntensityMode());
				intensityData.setDayStartTime(intensity.getDayStartTime());
				intensityData.setNightStartTime(intensity.getNightStartTime());
				intensityData.setDayIntensity(intensity.getDayIntensity());
				intensityData.setNightIntensity(intensity.getNightIntensity());
				Assert.assertNotNull(intensityData);
				intensityRepo.save(intensityData);
				intensityMap = customAutoIntensity(intensityData);
				//requestWrapper.postRequest(intensity.getDayIntensity(), RequestTypes.SET_INTENSITY);

			}

		} else if (intensity.getIntensityMode().equals("Manual")) {

			if (intensity.getMode().equals("All")) {

				//requestWrapper.postRequest(intensity.getIntensityValue(), RequestTypes.SET_INTENSITY);

			} else {

				String dataIntensity = intensity.getIntensityValue().toString();
				Assert.assertNotNull(dataIntensity);
				//requestWrapper.postRequest(dataIntensity, RequestTypes.SET_INTENSITY, intensity.getDevice(),intensity.getPlatform());
			}

		}
		Assert.assertNotNull(intensityMap);
	}

	// Custom Response Using HashMap

	private HashMap<String, Object> customAutoIntensity(Intensity intensity) {

		HashMap<String, Object> intensityMap = new HashMap<>();

		intensityMap.put("id", intensity.getId());
		intensityMap.put("intensityMode", intensity.getIntensityMode());
		intensityMap.put("dayStartTime", intensity.getDayStartTime());
		intensityMap.put("nightStartTime", intensity.getNightStartTime());
		intensityMap.put("dayIntensity", intensity.getDayIntensity());
		intensityMap.put("nightIntensity", intensity.getNightIntensity());
		return intensityMap;

	}
}
