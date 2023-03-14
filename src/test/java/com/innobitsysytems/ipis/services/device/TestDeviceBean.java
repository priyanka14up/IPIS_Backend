
package com.innobitsysytems.ipis.services.device;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.innobitsysytems.ipis.IpisApplication;
import com.innobitsysytems.ipis.dto.PublicAnnouncementDto;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.LinkStatus;
import com.innobitsysytems.ipis.model.User;
import com.innobitsysytems.ipis.model.announcement.PublicAnnouncement;
import com.innobitsysytems.ipis.model.device.ChildrenDetails;
import com.innobitsysytems.ipis.model.device.CoachesDetail;
import com.innobitsysytems.ipis.model.device.Device;
import com.innobitsysytems.ipis.model.device.DeviceStatus;
import com.innobitsysytems.ipis.model.device.DeviceType;
import com.innobitsysytems.ipis.repository.DeviceRepository;
import com.innobitsysytems.ipis.repository.LinkStatusRepository;
import com.innobitsysytems.ipis.services.announcement.AnnouncementBean;
import com.innobitsysytems.ipis.threadmgnt.RequestTypes;
import com.innobitsysytems.ipis.threadmgnt.RequestWrapper;
import com.innobitsysytems.ipis.utility.CustomUtil;

@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IpisApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestDeviceBean {

	Date date = new Date();

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@InjectMocks
	DeviceBean DeviceService;

	@Autowired
	public LinkStatusRepository linkStatusRepository;

	@Mock
	public CustomUtil customUtil;

	@Mock
	private RequestWrapper requestWrapper;

	@Autowired
	public DeviceRepository deviceRepository;

	// for get all devices
	@Test
	public void testList() {

		HashMap<String, Object> deviceMap = new HashMap<>();
		Device devicepare = deviceRepository.findByDeviceType(DeviceType.cds);
		if (devicepare != null) {
			deviceMap.put("id", devicepare.getId());
			deviceMap.put("ipAddress", devicepare.getIpAddress());
			deviceMap.put("deviceType", devicepare.getDeviceType());
			deviceMap.put("deviceName", devicepare.getDeviceName());
			deviceMap.put("children", devicepare.getChildren());

			Assert.assertNotNull(deviceMap);
		} else {
			exceptionRule.expect(HandledException.class);
			exceptionRule.expectMessage("No Device information is present in Database");
		}
	}

	@Test
	public void getSingleDevice() throws HandledException {

		HashMap<String, Object> deviceMap = new HashMap<>();

		Device device = deviceRepository.findById(21L)

				.orElseThrow(() -> new HandledException("NOT_FOUND", "Device not found on : "));

		switch (device.getDeviceType().toString()) {

		case "pdc": {

			deviceMap.put("id", device.getId());
			deviceMap.put("ipAddress", device.getIpAddress());
			deviceMap.put("deviceType", device.getDeviceType());
			deviceMap.put("deviceName", device.getDeviceName());
			deviceMap.put("portNumber", device.getPortNumber());
			deviceMap.put("platformNo", device.getPlatformNo());
			deviceMap.put("children", device.getChildren());
			break;
		}

		case "cdcMaster": {

			deviceMap.put("id", device.getId());
			deviceMap.put("ipAddress", device.getIpAddress());
			deviceMap.put("deviceType", device.getDeviceType());
			deviceMap.put("portNumber", device.getPortNumber());
			break;
		}

		case "cdcSlave": {

			deviceMap.put("id", device.getId());
			deviceMap.put("ipAddress", device.getIpAddress());
			deviceMap.put("deviceType", device.getDeviceType());
			deviceMap.put("portNumber", device.getPortNumber());
			break;
		}

		case "cgdb": {

			deviceMap.put("id", device.getId());
			deviceMap.put("deviceType", device.getDeviceType());
			deviceMap.put("portNumber", device.getPortNumber());
			deviceMap.put("platformNo", device.getPlatformNo());
			deviceMap.put("noOfCoaches", device.getNoOfCoaches());
			deviceMap.put("startId", device.getStartId());
			deviceMap.put("englishInfoDisplay", device.getEnglishInfoDisplay());
			deviceMap.put("hindiInfoDisplay", device.getHindiInfoDisplay());
			deviceMap.put("coaches", device.getCoaches());
			break;
		}

		case "mldb": {

			deviceMap.put("id", device.getId());
			deviceMap.put("ipAddress", device.getIpAddress());
			deviceMap.put("deviceType", device.getDeviceType());
			deviceMap.put("deviceName", device.getDeviceName());
			deviceMap.put("boardType", device.getBoardType());
			deviceMap.put("portNumber", device.getPortNumber());
			deviceMap.put("noOfLines", device.getNoOfLines());
			deviceMap.put("messageLine", device.getMessageLine());
			deviceMap.put("enableMsgLine", device.getEnableMsgLine());
			break;
		}

		case "tvDisplay": {

			deviceMap.put("id", device.getId());
			deviceMap.put("ipAddress", device.getIpAddress());
			deviceMap.put("deviceType", device.getDeviceType());
			deviceMap.put("deviceName", device.getDeviceName());
			deviceMap.put("portNumber", device.getPortNumber());
			deviceMap.put("noOfLines", device.getNoOfLines());
			deviceMap.put("messageLine", device.getMessageLine());
			deviceMap.put("enableMsgLine", device.getEnableMsgLine());
			deviceMap.put("platformNo", device.getPlatformNo());
			break;

		}

		case "ivd": {

			deviceMap.put("id", device.getId());
			deviceMap.put("ipAddress", device.getIpAddress());
			deviceMap.put("deviceType", device.getDeviceType());
			deviceMap.put("deviceName", device.getDeviceName());
			deviceMap.put("portNumber", device.getPortNumber());
			deviceMap.put("noOfLines", device.getNoOfLines());
			deviceMap.put("messageLine", device.getMessageLine());
			deviceMap.put("enableMsgLine", device.getEnableMsgLine());
			deviceMap.put("platformNo", device.getPlatformNo());
			break;

		}

		case "ovd": {

			deviceMap.put("id", device.getId());
			deviceMap.put("ipAddress", device.getIpAddress());
			deviceMap.put("deviceType", device.getDeviceType());
			deviceMap.put("deviceName", device.getDeviceName());
			deviceMap.put("portNumber", device.getPortNumber());
			deviceMap.put("noOfLines", device.getNoOfLines());
			deviceMap.put("messageLine", device.getMessageLine());
			deviceMap.put("enableMsgLine", device.getEnableMsgLine());
			deviceMap.put("platformNo", device.getPlatformNo());
			break;
		}

		case "pfdb": {

			deviceMap.put("id", device.getId());
			deviceMap.put("ipAddress", device.getIpAddress());
			deviceMap.put("deviceType", device.getDeviceType());
			deviceMap.put("deviceName", device.getDeviceName());
			deviceMap.put("portNumber", device.getPortNumber());
			deviceMap.put("platformNo", device.getPlatformNo());
			deviceMap.put("boardType", device.getBoardType());
			break;

		}

		case "agdb": {

			deviceMap.put("id", device.getId());
			deviceMap.put("ipAddress", device.getIpAddress());
			deviceMap.put("deviceType", device.getDeviceType());
			deviceMap.put("deviceName", device.getDeviceName());
			deviceMap.put("boardType", device.getBoardType());
			deviceMap.put("portNumber", device.getPortNumber());
			deviceMap.put("fobIndicatorPosition", device.getFobIndicatorPosition());
			deviceMap.put("platformNo", device.getPlatformNo());
			break;

		}
		}

		Assert.assertNotNull(deviceMap);
	}

	@Test
	public void create() throws HandledException {
		String[] plateforms = new String[] { "1", "2" };
		Device device = new Device();
		device.setId(40L);
		device.setPortNumber("14");
		device.setPlatformNo(plateforms);
		device.setDeviceType(DeviceType.ovd);
		device.setIpAddress("192.168.0.41");

		// Device device = deviceRepository.findByIpAddress("192.168.1.1");

		String[] empty = {};
		LinkStatus linkStatus = new LinkStatus();

		// Storing Parent in the array
		String port = device.getPortNumber();
		List<ChildrenDetails> newParent = new ArrayList<>();

		String[] platArray = device.getPlatformNo();
		String ip = device.getIpAddress();
		String portNumber = device.getPortNumber();

		DeviceType devicetype = device.getDeviceType();
		Device existIp = deviceRepository.findByIpAddress(ip);

		Device existPort = deviceRepository.findByPortNumber(portNumber);
		HashMap<String, Object> deviceMap = new HashMap<>();

		// Getting createdBy and updatedBy
		long token = customUtil.getIdFromToken();

		if (existPort == null) {

			if (ipValues(ip).length == 4) {

				if (existIp == null) {

					int oct3 = Integer.parseInt(ipValues(ip)[2]);

					if (devicetype == DeviceType.cds) {

						if (ipValues(ip)[2].equals("0")) {

							if (ipValues(ip)[3].equals("1")) {

								// Set as CDS
								String[] max = (device.getEnableMsgLine() == null) ? empty : device.getEnableMsgLine();
								device.setEnableMsgLine(max);
								String[] max1 = (device.getPlatformNo() == null) ? empty : device.getPlatformNo();
								device.setPlatformNo(max1);
								device.setStatus(DeviceStatus.Connected);
								device.setCreatedBy(String.valueOf(token));
								deviceRepository.save(device);

								deviceMap.put("id", device.getId());
								deviceMap.put("ipAddress", device.getIpAddress());
								deviceMap.put("deviceType", device.getDeviceType());

							} else {
								exceptionRule.expect(HandledException.class);
								exceptionRule.expectMessage("Check for the 4th octate of IPaddress of CDS");

							}
						} else {
							exceptionRule.expect(HandledException.class);
							exceptionRule.expectMessage("Check for the 3rd octate of IPaddress of CDS");
						}

					} else if (devicetype == DeviceType.pdc) {
						if (ipValues(ip)[3].equals("1")) {
							if (oct3 > 0 && oct3 < 31) {
								if (ipValues(ip)[2].equals(platArray[0])) {
									// Setting as PDC
									linkStatus.setLinkTime(date);
									linkStatus.setResponseTime(date);
									linkStatus.setResponse("OK");

									String[] max = (device.getEnableMsgLine() == null) ? empty
											: device.getEnableMsgLine();
									device.setEnableMsgLine(max);
									device.setCreatedBy(String.valueOf(token));
									device.setStatus(DeviceStatus.Connected);
									device.setLinkStatus(linkStatus);
									deviceRepository.save(device);

									deviceMap.put("id", device.getId());
									deviceMap.put("ipAddress", device.getIpAddress());
									deviceMap.put("deviceType", device.getDeviceType());
									deviceMap.put("deviceName", device.getDeviceName());
									deviceMap.put("portNumber", device.getPortNumber());
									deviceMap.put("platformNo", device.getPlatformNo());
									deviceMap.put("children", device.getChildren());

									// Searching the Parent
									Device devicepare = deviceRepository.findByDeviceType(DeviceType.cds);
									newParent.add(new ChildrenDetails(port, device.getId(), devicetype));

									// Updating the Parent
									List child = devicepare.getChildren();
									if (child == null) {

										devicepare.setChildren(newParent);
										deviceRepository.save(devicepare);

									} else {

										devicepare.getChildren().addAll(newParent);
										deviceRepository.save(devicepare);

									}

								} else {
									exceptionRule.expect(HandledException.class);
									exceptionRule.expectMessage("Check the Platform number of PDC");
								}

							} else {

								if (oct3 > 100 && oct3 < 111) {

									String specialPlatformNo = String.valueOf(Integer.parseInt(ipValues(ip)[2]) - 100)
											+ "A";

									if (specialPlatformNo.equals(platArray[0])) {

										// Setting PDC for Special Platform
										linkStatus.setLinkTime(date);
										linkStatus.setResponseTime(date);
										linkStatus.setResponse("OK");

										String[] max = (device.getEnableMsgLine() == null) ? empty
												: device.getEnableMsgLine();
										device.setEnableMsgLine(max);
										device.setCreatedBy(String.valueOf(token));
										device.setStatus(DeviceStatus.Connected);
										device.setLinkStatus(linkStatus);
										deviceRepository.save(device);

										deviceMap.put("id", device.getId());
										deviceMap.put("ipAddress", device.getIpAddress());
										deviceMap.put("deviceType", device.getDeviceType());
										deviceMap.put("deviceName", device.getDeviceName());
										deviceMap.put("portNumber", device.getPortNumber());
										deviceMap.put("platformNo", device.getPlatformNo());
										deviceMap.put("children", device.getChildren());

										// Searching the Parent
										Device devicepare = deviceRepository.findByDeviceType(DeviceType.cds);
										newParent.add(new ChildrenDetails(port, device.getId(), devicetype));

										// Updating the Parent
										List child = devicepare.getChildren();
										if (child == null) {

											devicepare.setChildren(newParent);
											deviceRepository.save(devicepare);

										} else {

											devicepare.getChildren().addAll(newParent);
											deviceRepository.save(devicepare);
										}

									} else {
										exceptionRule.expect(HandledException.class);
										exceptionRule.expectMessage("Check the Special Platform number of PDC");

									}
								} else {
									exceptionRule.expect(HandledException.class);
									exceptionRule.expectMessage("Check the 3rd octate of IPaddress of PDC");
								}
							}

						} else {
							exceptionRule.expect(HandledException.class);
							exceptionRule.expectMessage("Check for the 4th octate IPaddress of PDC ");

						}

					} else if (devicetype == DeviceType.cgdb) {

						Device existpdc = deviceRepository.getIdByDeviceType(DeviceType.pdc, platArray);

						if (existpdc != null) {

							if (device.getStartId() > 1 && device.getStartId() < 40) {

								// set as CGDB
								linkStatus.setLinkTime(date);
								linkStatus.setResponseTime(date);
								linkStatus.setResponse("N/A");

								String emp = "";
								String[] max = (device.getEnableMsgLine() == null) ? empty : device.getEnableMsgLine();
								device.setEnableMsgLine(max);
								String max1 = (device.getIpAddress() != null) ? emp : device.getIpAddress();
								device.setIpAddress(max1);
								device.setCreatedBy(String.valueOf(token));
								device.setStatus(DeviceStatus.Connected);
								device.setLinkStatus(linkStatus);
								deviceRepository.save(device);

								deviceMap.put("id", device.getId());
								deviceMap.put("deviceType", device.getDeviceType());
								deviceMap.put("portNumber", device.getPortNumber());
								deviceMap.put("platformNo", device.getPlatformNo());
								deviceMap.put("noOfCoaches", device.getNoOfCoaches());
								deviceMap.put("startId", device.getStartId());
								deviceMap.put("englishInfoDisplay", device.getEnglishInfoDisplay());
								deviceMap.put("hindiInfoDisplay", device.getHindiInfoDisplay());
								deviceMap.put("coaches", device.getCoaches());

								// Searching the Parent
								Device devicepare = deviceRepository.getIdByDeviceType(DeviceType.pdc, platArray);
								newParent.add(new ChildrenDetails(port, device.getId(), devicetype));

								// Updating the Parent
								List child = devicepare.getChildren();
								if (child == null) {

									devicepare.setChildren(newParent);
									deviceRepository.save(devicepare);

								} else {

									devicepare.getChildren().addAll(newParent);
									deviceRepository.save(devicepare);

								}

							} else {
								exceptionRule.expect(HandledException.class);
								exceptionRule.expectMessage("Check the startId of CGDB");
							}

						} else {
							exceptionRule.expect(HandledException.class);
							exceptionRule.expectMessage("Pdc for this platform no. does not exist");
						}

					} else if (devicetype == DeviceType.ovd) {

						if (Integer.parseInt(ipValues(ip)[3]) > 39 && Integer.parseInt(ipValues(ip)[3]) < 71) {

							if (ipValues(ip)[2].equals("0")) {

								// Set as OVD
								linkStatus.setLinkTime(date);
								linkStatus.setResponseTime(date);
								linkStatus.setResponse("OK");
								device.setCreatedBy(String.valueOf(token));
								device.setStatus(DeviceStatus.Connected);
								device.setLinkStatus(linkStatus);

								deviceMap.put("id", device.getId());
								deviceMap.put("ipAddress", device.getIpAddress());
								deviceMap.put("deviceType", device.getDeviceType());
								deviceMap.put("deviceName", device.getDeviceName());
								deviceMap.put("portNumber", device.getPortNumber());
								deviceMap.put("noOfLines", device.getNoOfLines());
								deviceMap.put("messageLine", device.getMessageLine());
								deviceMap.put("enableMsgLine", device.getEnableMsgLine());
								deviceMap.put("platformNo", device.getPlatformNo());

								// Searching the Parent
								Device devicepare = deviceRepository.findByDeviceType(DeviceType.cds);
								newParent.add(new ChildrenDetails(port, device.getId(), devicetype));

								// Updating the Parent
								List child = devicepare.getChildren();
								if (child == null) {

									devicepare.setChildren(newParent);
									deviceRepository.save(devicepare);

								} else {

									devicepare.getChildren().addAll(newParent);
									deviceRepository.save(devicepare);

								}

							} else {
								exceptionRule.expect(HandledException.class);
								exceptionRule.expectMessage("Check for the 3rd octate IPaddress of OVD");

							}
						} else {
							exceptionRule.expect(HandledException.class);
							exceptionRule.expectMessage("Check for the 4th octate IPaddress of OVD");
						}

					} else if (devicetype == DeviceType.ivd) {

						if (Integer.parseInt(ipValues(ip)[3]) > 70 && Integer.parseInt(ipValues(ip)[3]) < 101) {

							if (ipValues(ip)[2].equals("0")) {

								if (ipValues(ip)[2].equals(platArray[0])) {

									// Set as IVD
									linkStatus.setLinkTime(date);
									linkStatus.setResponseTime(date);
									linkStatus.setResponse("N/A");
									device.setCreatedBy(String.valueOf(token));
									device.setStatus(DeviceStatus.Disconnected);
									device.setLinkStatus(linkStatus);
									deviceRepository.save(device);

									deviceMap.put("id", device.getId());
									deviceMap.put("ipAddress", device.getIpAddress());
									deviceMap.put("deviceType", device.getDeviceType());
									deviceMap.put("deviceName", device.getDeviceName());
									deviceMap.put("portNumber", device.getPortNumber());
									deviceMap.put("noOfLines", device.getNoOfLines());
									deviceMap.put("messageLine", device.getMessageLine());
									deviceMap.put("enableMsgLine", device.getEnableMsgLine());
									deviceMap.put("platformNo", device.getPlatformNo());

									// Searching the Parent
									Device devicepare = deviceRepository.findByDeviceType(DeviceType.cds);
									newParent.add(new ChildrenDetails(port, device.getId(), devicetype));

									// Updating the Parent
									List child = devicepare.getChildren();
									if (child == null) {

										devicepare.setChildren(newParent);
										deviceRepository.save(devicepare);

									} else {

										devicepare.getChildren().addAll(newParent);
										deviceRepository.save(devicepare);
									}
								} else {
									exceptionRule.expect(HandledException.class);
									exceptionRule.expectMessage("Check for the Platform number of General IVD");
								}

							} else if (oct3 > 0 && oct3 < 31) {

								if (ipValues(ip)[2].equals(platArray[0])) {

									linkStatus.setLinkTime(date);
									linkStatus.setResponseTime(date);
									linkStatus.setResponse("N/A");
									device.setCreatedBy(String.valueOf(token));
									device.setStatus(DeviceStatus.Disconnected);
									device.setLinkStatus(linkStatus);
									deviceRepository.save(device);

									deviceMap.put("id", device.getId());
									deviceMap.put("ipAddress", device.getIpAddress());
									deviceMap.put("deviceType", device.getDeviceType());
									deviceMap.put("deviceName", device.getDeviceName());
									deviceMap.put("portNumber", device.getPortNumber());
									deviceMap.put("noOfLines", device.getNoOfLines());
									deviceMap.put("messageLine", device.getMessageLine());
									deviceMap.put("enableMsgLine", device.getEnableMsgLine());
									deviceMap.put("platformNo", device.getPlatformNo());

									// Searching the Parent
									Device devicepare = deviceRepository.findByDeviceType(DeviceType.cds);
									newParent.add(new ChildrenDetails(port, device.getId(), devicetype));

									// Updating the Parent
									List child = devicepare.getChildren();
									if (child == null) {

										devicepare.setChildren(newParent);
										deviceRepository.save(devicepare);

									} else {

										devicepare.getChildren().addAll(newParent);
										deviceRepository.save(devicepare);

									}

								} else {
									exceptionRule.expect(HandledException.class);
									exceptionRule.expectMessage("Check the platform no. of IVD");
								}

							} else if (oct3 > 100 && oct3 < 111) {

								// For special platform of IVD
								String specialPlatformIvd = String.valueOf(Integer.parseInt(ipValues(ip)[2]) - 100)
										+ "A";

								if (specialPlatformIvd.equals(platArray[0])) {

									linkStatus.setLinkTime(date);
									linkStatus.setResponseTime(date);
									linkStatus.setResponse("N/A");
									device.setCreatedBy(String.valueOf(token));
									device.setStatus(DeviceStatus.Disconnected);
									device.setLinkStatus(linkStatus);
									deviceRepository.save(device);

									deviceMap.put("id", device.getId());
									deviceMap.put("ipAddress", device.getIpAddress());
									deviceMap.put("deviceType", device.getDeviceType());
									deviceMap.put("deviceName", device.getDeviceName());
									deviceMap.put("portNumber", device.getPortNumber());
									deviceMap.put("noOfLines", device.getNoOfLines());
									deviceMap.put("messageLine", device.getMessageLine());
									deviceMap.put("enableMsgLine", device.getEnableMsgLine());
									deviceMap.put("platformNo", device.getPlatformNo());

									// Searching the Parent
									Device devicepare = deviceRepository.findByDeviceType(DeviceType.cds);
									newParent.add(new ChildrenDetails(port, device.getId(), devicetype));

									// Updating the Parent
									List child = devicepare.getChildren();
									if (child == null) {

										devicepare.setChildren(newParent);
										deviceRepository.save(devicepare);

									} else {

										devicepare.getChildren().addAll(newParent);
										deviceRepository.save(devicepare);
									}

								} else {
									exceptionRule.expect(HandledException.class);
									exceptionRule.expectMessage("Check the special platform no. of IVD");
								}

							} else {
								exceptionRule.expect(HandledException.class);
								exceptionRule.expectMessage("Check for the 3rd octate IPaddress of IVD");
							}

						} else {
							exceptionRule.expect(HandledException.class);
							exceptionRule.expectMessage("Check for the 4th octate IP address of IVD");
						}

					} else if (devicetype == DeviceType.mldb) {

						if (Integer.parseInt(ipValues(ip)[3]) > 100 && Integer.parseInt(ipValues(ip)[3]) < 131) {

							// Set as MLDB
							if (ipValues(ip)[2].equals("0")) {

								linkStatus.setLinkTime(date);
								linkStatus.setResponseTime(date);
								linkStatus.setResponse("OK");
								String[] zeroPlatformNo = { "0" };
								String[] max1 = (device.getPlatformNo() == null) ? zeroPlatformNo
										: device.getPlatformNo();
								device.setPlatformNo(max1);
								device.setCreatedBy(String.valueOf(token));
								device.setStatus(DeviceStatus.Connected);
								device.setLinkStatus(linkStatus);
								deviceRepository.save(device);

								deviceMap.put("id", device.getId());
								deviceMap.put("ipAddress", device.getIpAddress());
								deviceMap.put("deviceType", device.getDeviceType());
								deviceMap.put("deviceName", device.getDeviceName());
								deviceMap.put("boardType", device.getBoardType());
								deviceMap.put("portNumber", device.getPortNumber());
								deviceMap.put("noOfLines", device.getNoOfLines());
								deviceMap.put("messageLine", device.getMessageLine());
								deviceMap.put("enableMsgLine", device.getEnableMsgLine());

								// Searching the Parent
								Device devicepare = deviceRepository.findByDeviceType(DeviceType.cds);
								newParent.add(new ChildrenDetails(port, device.getId(), devicetype));

								// Updating the Parent
								List child = devicepare.getChildren();
								if (child == null) {

									devicepare.setChildren(newParent);
									deviceRepository.save(devicepare);

								} else {

									devicepare.getChildren().addAll(newParent);
									deviceRepository.save(devicepare);

								}

							} else if ((oct3 > 0 && oct3 < 31) || (oct3 > 100 && oct3 < 111)) {

								linkStatus.setLinkTime(date);
								linkStatus.setResponseTime(date);
								linkStatus.setResponse("OK");

								String specialPlatformMldb = String.valueOf(Integer.parseInt(ipValues(ip)[2]) - 100)
										+ "A";
								String[] special = { specialPlatformMldb };
								String[] normal = { ipValues(ip)[2] };
								String[] setplatform = (oct3 > 100) ? special : normal;
								device.setPlatformNo(setplatform);
								device.setCreatedBy(String.valueOf(token));
								device.setStatus(DeviceStatus.Connected);
								device.setLinkStatus(linkStatus);
								deviceRepository.save(device);

								deviceMap.put("id", device.getId());
								deviceMap.put("ipAddress", device.getIpAddress());
								deviceMap.put("deviceType", device.getDeviceType());
								deviceMap.put("deviceName", device.getDeviceName());
								deviceMap.put("boardType", device.getBoardType());
								deviceMap.put("portNumber", device.getPortNumber());
								deviceMap.put("noOfLines", device.getNoOfLines());
								deviceMap.put("messageLine", device.getMessageLine());
								deviceMap.put("enableMsgLine", device.getEnableMsgLine());

								// Searching the Parent
								Device devicepare = deviceRepository.findByDeviceType(DeviceType.cds);
								newParent.add(new ChildrenDetails(port, device.getId(), devicetype));

								// Updating the Parent
								List child = devicepare.getChildren();
								if (child == null) {

									devicepare.setChildren(newParent);
									deviceRepository.save(devicepare);

								} else {

									devicepare.getChildren().addAll(newParent);
									deviceRepository.save(devicepare);

								}

							} else {
								exceptionRule.expect(HandledException.class);
								exceptionRule.expectMessage("Check the 3RD octate IP address of MLDB");
							}

						} else {
							exceptionRule.expect(HandledException.class);
							exceptionRule.expectMessage("Check the 4TH octate IP address of MLDB");
						}

					} else if (devicetype == DeviceType.agdb) {

						if (Integer.parseInt(ipValues(ip)[3]) > 130 && Integer.parseInt(ipValues(ip)[3]) < 161) {

							if (ipValues(ip)[2].equals("0")) {

								linkStatus.setLinkTime(date);
								linkStatus.setResponseTime(date);
								linkStatus.setResponse("OK");
								String[] max = (device.getEnableMsgLine() == null) ? empty : device.getEnableMsgLine();
								device.setEnableMsgLine(max);
								device.setCreatedBy(String.valueOf(token));
								device.setStatus(DeviceStatus.Connected);
								device.setLinkStatus(linkStatus);
								deviceRepository.save(device);

								deviceMap.put("id", device.getId());
								deviceMap.put("ipAddress", device.getIpAddress());
								deviceMap.put("deviceType", device.getDeviceType());
								deviceMap.put("deviceName", device.getDeviceName());
								deviceMap.put("boardType", device.getBoardType());
								deviceMap.put("portNumber", device.getPortNumber());
								deviceMap.put("fobIndicatorPosition", device.getFobIndicatorPosition());
								deviceMap.put("platformNo", device.getPlatformNo());

								// Searching the Parent
								Device devicepare = deviceRepository.findByDeviceType(DeviceType.cds);
								newParent.add(new ChildrenDetails(port, device.getId(), devicetype));

								// Updating the Parent
								List child = devicepare.getChildren();
								if (child == null) {

									devicepare.setChildren(newParent);
									deviceRepository.save(devicepare);

								} else {

									devicepare.getChildren().addAll(newParent);
									deviceRepository.save(devicepare);

								}

							} else {
								exceptionRule.expect(HandledException.class);
								exceptionRule.expectMessage("Check 3RD octate of Ip address AGDB");
							}

						} else {
							exceptionRule.expect(HandledException.class);
							exceptionRule.expectMessage("Check 4th octate of Ip address AGDB");
						}

					} else if (devicetype == DeviceType.pfdb) {

						String[] platfprm = { platArray[0] };
						Device existpdc = deviceRepository.getIdByDeviceType(DeviceType.pdc, platfprm);

						if (existpdc != null) {

							if (Integer.parseInt(ipValues(ip)[3]) > 160 && Integer.parseInt(ipValues(ip)[3]) < 191) {

								if (ipValues(ip)[2].equals(platArray[0])) {

									// Set as PFDB
									linkStatus.setLinkTime(date);
									linkStatus.setResponseTime(date);
									linkStatus.setResponse("OK");
									String[] max = (device.getEnableMsgLine() == null) ? empty
											: device.getEnableMsgLine();
									device.setEnableMsgLine(max);
									device.setCreatedBy(String.valueOf(token));
									device.setStatus(DeviceStatus.Connected);
									device.setLinkStatus(linkStatus);
									deviceRepository.save(device);

									deviceMap.put("id", device.getId());
									deviceMap.put("ipAddress", device.getIpAddress());
									deviceMap.put("deviceType", device.getDeviceType());
									deviceMap.put("deviceName", device.getDeviceName());
									deviceMap.put("portNumber", device.getPortNumber());
									deviceMap.put("platformNo", device.getPlatformNo());
									deviceMap.put("boardType", device.getBoardType());

									// Searching the Parent
									Device devicepare = deviceRepository.getIdByDeviceType(DeviceType.pdc, platfprm);
									newParent.add(new ChildrenDetails(port, device.getId(), devicetype));

									// Updating the Parent
									List child = devicepare.getChildren();
									if (child == null) {

										devicepare.setChildren(newParent);
										deviceRepository.save(devicepare);

									} else {

										devicepare.getChildren().addAll(newParent);
										deviceRepository.save(devicepare);

									}

								} else {

									// Setting PFDB for special platform no.
									String platformNo = String.valueOf(Integer.parseInt(ipValues(ip)[2]) - 100) + "A";

									if (platformNo.equals(platArray[0])) {

										// Set as PFDB
										linkStatus.setLinkTime(date);
										linkStatus.setResponseTime(date);
										linkStatus.setResponse("OK");
										String[] max = (device.getEnableMsgLine() == null) ? empty
												: device.getEnableMsgLine();
										device.setEnableMsgLine(max);
										device.setCreatedBy(String.valueOf(token));
										device.setStatus(DeviceStatus.Connected);
										device.setLinkStatus(linkStatus);
										deviceRepository.save(device);

										deviceMap.put("id", device.getId());
										deviceMap.put("ipAddress", device.getIpAddress());
										deviceMap.put("deviceType", device.getDeviceType());
										deviceMap.put("deviceName", device.getDeviceName());
										deviceMap.put("portNumber", device.getPortNumber());
										deviceMap.put("platformNo", device.getPlatformNo());
										deviceMap.put("boardType", device.getBoardType());

										// Searching the Parent
										Device devicepare = deviceRepository.getIdByDeviceType(DeviceType.pdc,
												platfprm);
										newParent.add(new ChildrenDetails(port, device.getId(), devicetype));

										// Updating the Parent
										List child = devicepare.getChildren();
										if (child == null) {

											devicepare.setChildren(newParent);
											deviceRepository.save(devicepare);

										} else {

											devicepare.getChildren().addAll(newParent);
											deviceRepository.save(devicepare);

										}

									} else {
										exceptionRule.expect(HandledException.class);
										exceptionRule.expectMessage("Check PFDB 3RD octate of IP address");

									}

								}

							} else {
								exceptionRule.expect(HandledException.class);
								exceptionRule.expectMessage("Check PFDB 4TH octate of IP address");
							}

						} else {
							exceptionRule.expect(HandledException.class);
							exceptionRule.expectMessage("Pdc for this platform no does not exist");

						}

					} else if (devicetype == DeviceType.tvDisplay) {
						if (Integer.parseInt(ipValues(ip)[3]) > 190 && Integer.parseInt(ipValues(ip)[3]) < 221) {

							if (ipValues(ip)[2].equals("0")) {

								if (ipValues(ip)[2].equals(platArray[0])) {

									// Set as TV Display
									linkStatus.setLinkTime(date);
									linkStatus.setResponseTime(date);
									linkStatus.setResponse("N/A");
									device.setCreatedBy(String.valueOf(token));
									device.setStatus(DeviceStatus.Disconnected);
									device.setLinkStatus(linkStatus);
									deviceRepository.save(device);

									deviceMap.put("id", device.getId());
									deviceMap.put("ipAddress", device.getIpAddress());
									deviceMap.put("deviceType", device.getDeviceType());
									deviceMap.put("deviceName", device.getDeviceName());
									deviceMap.put("portNumber", device.getPortNumber());
									deviceMap.put("noOfLines", device.getNoOfLines());
									deviceMap.put("messageLine", device.getMessageLine());
									deviceMap.put("enableMsgLine", device.getEnableMsgLine());
									deviceMap.put("platformNo", device.getPlatformNo());

									// Searching the Parent
									Device devicepare = deviceRepository.findByDeviceType(DeviceType.cds);
									newParent.add(new ChildrenDetails(port, device.getId(), devicetype));

									// Updating the Parent
									List child = devicepare.getChildren();
									if (child == null) {

										devicepare.setChildren(newParent);
										deviceRepository.save(devicepare);

									} else {

										devicepare.getChildren().addAll(newParent);
										deviceRepository.save(devicepare);
									}

								} else {
									exceptionRule.expect(HandledException.class);
									exceptionRule.expectMessage("Check for the Platform number of General TV Display");
								}

							} else if (oct3 > 0 && oct3 < 31) {

								if (ipValues(ip)[2].equals(platArray[0])) {

									linkStatus.setLinkTime(date);
									linkStatus.setResponseTime(date);
									linkStatus.setResponse("N/A");
									device.setCreatedBy(String.valueOf(token));
									device.setStatus(DeviceStatus.Disconnected);
									device.setLinkStatus(linkStatus);
									deviceRepository.save(device);

									deviceMap.put("id", device.getId());
									deviceMap.put("ipAddress", device.getIpAddress());
									deviceMap.put("deviceType", device.getDeviceType());
									deviceMap.put("deviceName", device.getDeviceName());
									deviceMap.put("portNumber", device.getPortNumber());
									deviceMap.put("noOfLines", device.getNoOfLines());
									deviceMap.put("messageLine", device.getMessageLine());
									deviceMap.put("enableMsgLine", device.getEnableMsgLine());
									deviceMap.put("platformNo", device.getPlatformNo());

									// Searching the Parent
									Device devicepare = deviceRepository.findByDeviceType(DeviceType.cds);
									newParent.add(new ChildrenDetails(port, device.getId(), devicetype));

									// Updating the Parent
									List child = devicepare.getChildren();
									if (child == null) {

										devicepare.setChildren(newParent);
										deviceRepository.save(devicepare);

									} else {

										devicepare.getChildren().addAll(newParent);
										deviceRepository.save(devicepare);

									}

								} else {
									exceptionRule.expect(HandledException.class);
									exceptionRule.expectMessage("Check the platform no. of TV Display");
								}

							} else {

								if (oct3 > 100 && oct3 < 111) {

									// For special platform of TV display
									String platformNo = String.valueOf(Integer.parseInt(ipValues(ip)[2]) - 100) + "A";

									if (platformNo.equals(platArray[0])) {

										linkStatus.setLinkTime(date);
										linkStatus.setResponseTime(date);
										linkStatus.setResponse("N/A");
										device.setCreatedBy(String.valueOf(token));
										device.setStatus(DeviceStatus.Disconnected);
										device.setLinkStatus(linkStatus);
										deviceRepository.save(device);

										deviceMap.put("id", device.getId());
										deviceMap.put("ipAddress", device.getIpAddress());
										deviceMap.put("deviceType", device.getDeviceType());
										deviceMap.put("deviceName", device.getDeviceName());
										deviceMap.put("portNumber", device.getPortNumber());
										deviceMap.put("noOfLines", device.getNoOfLines());
										deviceMap.put("messageLine", device.getMessageLine());
										deviceMap.put("enableMsgLine", device.getEnableMsgLine());
										deviceMap.put("platformNo", device.getPlatformNo());

										// Searching the Parent
										Device devicepare = deviceRepository.findByDeviceType(DeviceType.cds);
										newParent.add(new ChildrenDetails(port, device.getId(), devicetype));

										// Updating the Parent
										List child = devicepare.getChildren();
										if (child == null) {

											devicepare.setChildren(newParent);
											deviceRepository.save(devicepare);

										} else {

											devicepare.getChildren().addAll(newParent);
											deviceRepository.save(devicepare);
										}

									} else {
										exceptionRule.expect(HandledException.class);
										exceptionRule.expectMessage("Check the special platform no. of TV Display");
									}

								} else {
									exceptionRule.expect(HandledException.class);
									exceptionRule.expectMessage("Check for the 3rd octate IPaddress of TV Display");
								}

							}

						} else {
							exceptionRule.expect(HandledException.class);
							exceptionRule.expectMessage("Check for the 4th octate IP address of TV Display");
						}

					} else if (devicetype == DeviceType.cdcMaster) {

						if (ipValues(ip)[2].equals("0")) {

							if (ipValues(ip)[3].equals("253")) {

								// Set as CDCmaster
								linkStatus.setLinkTime(date);
								linkStatus.setResponseTime(date);
								linkStatus.setResponse("OK");
								String[] msgLine = (device.getEnableMsgLine() == null) ? empty
										: device.getEnableMsgLine();
								device.setEnableMsgLine(msgLine);
								String[] max1 = (device.getPlatformNo() == null) ? empty : device.getPlatformNo();
								device.setPlatformNo(max1);
								device.setCreatedBy(String.valueOf(token));
								device.setStatus(DeviceStatus.Connected);
								device.setLinkStatus(linkStatus);
								deviceRepository.save(device);

								deviceMap.put("id", device.getId());
								deviceMap.put("ipAddress", device.getIpAddress());
								deviceMap.put("deviceType", device.getDeviceType());
								deviceMap.put("portNumber", device.getPortNumber());

								// Searching the Parent
								Device devicepare = deviceRepository.findByDeviceType(DeviceType.cds);
								newParent.add(new ChildrenDetails(port, device.getId(), devicetype));

								// Updating the Parent
								List child = devicepare.getChildren();
								if (child == null) {

									devicepare.setChildren(newParent);
									deviceRepository.save(devicepare);

								} else {

									devicepare.getChildren().addAll(newParent);
									deviceRepository.save(devicepare);
								}

							} else {
								exceptionRule.expect(HandledException.class);
								exceptionRule.expectMessage("\"Check the 4th octate of IPaddress of CDC Master");
							}

						} else {
							exceptionRule.expect(HandledException.class);
							exceptionRule.expectMessage("\"Check the 3rd octate of IPaddress of CDC Master");
						}

					} else if (devicetype == DeviceType.cdcSlave) {

						if (ipValues(ip)[2].equals("0")) {

							if (ipValues(ip)[3].equals("254")) {

								// set as slave
								linkStatus.setLinkTime(date);
								linkStatus.setResponseTime(date);
								linkStatus.setResponse("OK");
								String[] msgLine = (device.getEnableMsgLine() == null) ? empty
										: device.getEnableMsgLine();
								device.setEnableMsgLine(msgLine);
								String[] max1 = (device.getPlatformNo() == null) ? empty : device.getPlatformNo();
								device.setPlatformNo(max1);
								device.setCreatedBy(String.valueOf(token));
								device.setStatus(DeviceStatus.Connected);
								device.setLinkStatus(linkStatus);
								deviceRepository.save(device);

								deviceMap.put("id", device.getId());
								deviceMap.put("ipAddress", device.getIpAddress());
								deviceMap.put("deviceType", device.getDeviceType());
								deviceMap.put("portNumber", device.getPortNumber());

								// Searching the Parent
								Device devicepare = deviceRepository.findByDeviceType(DeviceType.cds);
								newParent.add(new ChildrenDetails(port, device.getId(), devicetype));

								// Updating the Parent
								List child = devicepare.getChildren();
								if (child == null) {

									devicepare.setChildren(newParent);
									deviceRepository.save(devicepare);

								} else {

									devicepare.getChildren().addAll(newParent);
									deviceRepository.save(devicepare);
								}

							} else {
								exceptionRule.expect(HandledException.class);
								exceptionRule.expectMessage("\"Check for the 4th octate of IPaddress of CDC Slave");
							}

						} else {
							exceptionRule.expect(HandledException.class);
							exceptionRule.expectMessage("\"Check for the 3rd octate of IPaddress of CDC Slave");

						}

					} else {
						exceptionRule.expect(HandledException.class);
						exceptionRule.expectMessage("\"No device found");
					}

				} else {
					exceptionRule.expect(HandledException.class);
					exceptionRule.expectMessage("\"Ip address exist");
				}

			} else {
				exceptionRule.expect(HandledException.class);
				exceptionRule.expectMessage("Ip address shouldn't contain null values");
			}

		} else {
			exceptionRule.expect(HandledException.class);
			exceptionRule.expectMessage("Port Number should be empty");

		}

		Assert.assertNotNull(deviceMap);
	}

	@Test
	public void update() throws HandledException {

		Device deviceDetails = new Device();
		long token = customUtil.getIdFromToken();
		Device device = deviceRepository.findById(22L)
				.orElseThrow(() -> new HandledException("NOT_FOUND", "Device not found for this id : "));

		String ip = device.getIpAddress();
		DeviceType type = device.getDeviceType();
		HashMap<String, Object> deviceMap = new HashMap<>();

		Device existIp = deviceRepository.findByIpAddress(deviceDetails.getIpAddress());

		if (existIp == null || deviceDetails.getIpAddress().equals(device.getIpAddress())) {

			if (type == DeviceType.cgdb) {

				if (deviceDetails.getStartId() > 1 && deviceDetails.getStartId() < 40) {

					device.setStartId(deviceDetails.getStartId());
					device.setCoaches(deviceDetails.getCoaches());
					device.setNoOfCoaches(deviceDetails.getNoOfCoaches());
					device.setEnglishInfoDisplay(deviceDetails.getEnglishInfoDisplay());
					device.setHindiInfoDisplay(deviceDetails.getHindiInfoDisplay());
					device.setUpdatedBy(String.valueOf(token));

					deviceMap.put("id", device.getId());
					deviceMap.put("ipAddress", device.getIpAddress());
					deviceMap.put("deviceType", device.getDeviceType());
					deviceMap.put("portNumber", device.getPortNumber());
					deviceMap.put("platformNo", device.getPlatformNo());
					deviceMap.put("noOfCoaches", device.getNoOfCoaches());
					deviceMap.put("startId", device.getStartId());
					deviceMap.put("englishInfoDisplay", device.getEnglishInfoDisplay());
					deviceMap.put("hindiInfoDisplay", device.getHindiInfoDisplay());
					deviceMap.put("coaches", device.getCoaches());

				} else {
					exceptionRule.expect(HandledException.class);
					exceptionRule.expectMessage("Check the startId to update the CGDB");

				}

			} else if (type == DeviceType.ovd) {

				if (Integer.parseInt(ipValues(ip)[3]) > 39 && Integer.parseInt(ipValues(ip)[3]) < 71) {

					device.setIpAddress(deviceDetails.getIpAddress());
					device.setDeviceName(deviceDetails.getDeviceName());
					device.setNoOfLines(deviceDetails.getNoOfLines());
					device.setMessageLine(deviceDetails.getMessageLine());
					device.setEnableMsgLine(deviceDetails.getEnableMsgLine());
					device.setCreatedBy(String.valueOf(token));

					deviceMap.put("id", device.getId());
					deviceMap.put("ipAddress", device.getIpAddress());
					deviceMap.put("deviceType", device.getDeviceType());
					deviceMap.put("deviceName", device.getDeviceName());
					deviceMap.put("portNumber", device.getPortNumber());
					deviceMap.put("noOfLines", device.getNoOfLines());
					deviceMap.put("messageLine", device.getMessageLine());
					deviceMap.put("enableMsgLine", device.getEnableMsgLine());
					deviceMap.put("platformNo", device.getPlatformNo());

				} else {
					exceptionRule.expect(HandledException.class);
					exceptionRule.expectMessage("Check the Ip address to update the OVD");

				}

			} else if (type == DeviceType.ivd) {

				if (Integer.parseInt(ipValues(ip)[3]) > 70 && Integer.parseInt(ipValues(ip)[3]) < 101) {

					device.setIpAddress(deviceDetails.getIpAddress());
					device.setDeviceName(deviceDetails.getDeviceName());
					device.setNoOfLines(deviceDetails.getNoOfLines());
					device.setMessageLine(deviceDetails.getMessageLine());
					device.setEnableMsgLine(deviceDetails.getEnableMsgLine());
					device.setCreatedBy(String.valueOf(token));

					deviceMap.put("id", device.getId());
					deviceMap.put("ipAddress", device.getIpAddress());
					deviceMap.put("deviceType", device.getDeviceType());
					deviceMap.put("deviceName", device.getDeviceName());
					deviceMap.put("portNumber", device.getPortNumber());
					deviceMap.put("noOfLines", device.getNoOfLines());
					deviceMap.put("messageLine", device.getMessageLine());
					deviceMap.put("enableMsgLine", device.getEnableMsgLine());
					deviceMap.put("platformNo", device.getPlatformNo());

				} else {
					exceptionRule.expect(HandledException.class);
					exceptionRule.expectMessage("Check the Ip address to update the IVD");
				}

			} else if (type == DeviceType.mldb) {

				if (Integer.parseInt(ipValues(ip)[3]) > 100 && Integer.parseInt(ipValues(ip)[3]) < 131) {

					device.setIpAddress(deviceDetails.getIpAddress());
					device.setDeviceName(deviceDetails.getDeviceName());
					device.setNoOfLines(deviceDetails.getNoOfLines());
					device.setMessageLine(deviceDetails.getMessageLine());
					device.setEnableMsgLine(deviceDetails.getEnableMsgLine());
					device.setCreatedBy(String.valueOf(token));

					deviceMap.put("id", device.getId());
					deviceMap.put("ipAddress", device.getIpAddress());
					deviceMap.put("deviceType", device.getDeviceType());
					deviceMap.put("deviceName", device.getDeviceName());
					deviceMap.put("boardType", device.getBoardType());
					deviceMap.put("portNumber", device.getPortNumber());
					deviceMap.put("noOfLines", device.getNoOfLines());
					deviceMap.put("messageLine", device.getMessageLine());
					deviceMap.put("enableMsgLine", device.getEnableMsgLine());

				} else {
					exceptionRule.expect(HandledException.class);
					exceptionRule.expectMessage("Check the Ip address to update the MLDB");

				}

			} else if (type == DeviceType.agdb) {

				if (Integer.parseInt(ipValues(ip)[3]) > 130 && Integer.parseInt(ipValues(ip)[3]) < 161) {

					device.setIpAddress(deviceDetails.getIpAddress());
					device.setDeviceName(deviceDetails.getDeviceName());
					device.setFobIndicatorPosition(deviceDetails.getFobIndicatorPosition());
					device.setBoardType(deviceDetails.getBoardType());
					device.setCreatedBy(String.valueOf(token));

					deviceMap.put("id", device.getId());
					deviceMap.put("ipAddress", device.getIpAddress());
					deviceMap.put("deviceType", device.getDeviceType());
					deviceMap.put("deviceName", device.getDeviceName());
					deviceMap.put("boardType", device.getBoardType());
					deviceMap.put("portNumber", device.getPortNumber());
					deviceMap.put("fobIndicatorPosition", device.getFobIndicatorPosition());
					deviceMap.put("platformNo", device.getPlatformNo());

				} else {
					exceptionRule.expect(HandledException.class);
					exceptionRule.expectMessage("Check the Ip address to update the AGDB");
				}

			} else if (type == DeviceType.pfdb) {

				if (Integer.parseInt(ipValues(ip)[3]) > 160 && Integer.parseInt(ipValues(ip)[3]) < 191) {

					device.setIpAddress(deviceDetails.getIpAddress());
					device.setDeviceName(deviceDetails.getDeviceName());
					device.setBoardType(deviceDetails.getBoardType());
					device.setCreatedBy(String.valueOf(token));

					deviceMap.put("id", device.getId());
					deviceMap.put("ipAddress", device.getIpAddress());
					deviceMap.put("deviceType", device.getDeviceType());
					deviceMap.put("deviceName", device.getDeviceName());
					deviceMap.put("portNumber", device.getPortNumber());
					deviceMap.put("platformNo", device.getPlatformNo());
					deviceMap.put("boardType", device.getBoardType());

				} else {
					exceptionRule.expect(HandledException.class);
					exceptionRule.expectMessage("Check the Ip address to update the PFDB");

				}

			} else if (type == DeviceType.tvDisplay) {

				if (Integer.parseInt(ipValues(ip)[3]) > 190 && Integer.parseInt(ipValues(ip)[3]) < 221) {

					device.setIpAddress(deviceDetails.getIpAddress());
					device.setDeviceName(deviceDetails.getDeviceName());
					device.setNoOfLines(deviceDetails.getNoOfLines());
					device.setMessageLine(deviceDetails.getMessageLine());
					device.setEnableMsgLine(deviceDetails.getEnableMsgLine());
					device.setCreatedBy(String.valueOf(token));

					deviceMap.put("id", device.getId());
					deviceMap.put("ipAddress", device.getIpAddress());
					deviceMap.put("deviceType", device.getDeviceType());
					deviceMap.put("deviceName", device.getDeviceName());
					deviceMap.put("portNumber", device.getPortNumber());
					deviceMap.put("noOfLines", device.getNoOfLines());
					deviceMap.put("messageLine", device.getMessageLine());
					deviceMap.put("enableMsgLine", device.getEnableMsgLine());
					deviceMap.put("platformNo", device.getPlatformNo());

				} else {
					exceptionRule.expect(HandledException.class);
					exceptionRule.expectMessage("Check the Ip address to update the TV DISPLAY");
				}

			}

		} else {
			exceptionRule.expect(HandledException.class);
			exceptionRule.expectMessage("IP Address exist");
		}

		deviceRepository.save(device);
		Assert.assertNotNull(device);
	}

	@Test
	public void getCdsStatus() {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		Device devicepare = deviceRepository.findByDeviceType(DeviceType.cds);
		List<ChildrenDetails> cdsChildren = devicepare.getChildren();

		if (cdsChildren != null) {

			for (int i = 0; i < cdsChildren.size(); i++) {

				HashMap<String, Object> deviceMap = new HashMap<>();
				Object obj = cdsChildren.get(i);
				Map myMap = (Map) obj;
				String stringId = String.valueOf(myMap.get("id"));
				Long id = Long.parseLong(stringId);
				Device dataChild = deviceRepository.findAllById(id);
				if (dataChild == null) {
					exceptionRule.expect(HandledException.class);
					exceptionRule.expectMessage("dataChild doesnt exist");
				} else {
					deviceMap.put("deviceType", dataChild.getDeviceType());
					deviceMap.put("portNumber", dataChild.getPortNumber());
					deviceMap.put("status", dataChild.getStatus());
					deviceMap.put("linkTime", dataChild.getLinkStatus().getLinkTime());
					deviceMap.put("responseTime", dataChild.getLinkStatus().getResponseTime());
					deviceMap.put("response", dataChild.getLinkStatus().getResponse());
					list.add(deviceMap);
				}
			}

		} else {
			exceptionRule.expect(HandledException.class);
			exceptionRule.expectMessage("CDS has no children");
		}

		Assert.assertNotNull(list);

	}

	@Test
	public void getPdcStatus() {
		String[] platformNo = { "1" };
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		Device pdcData = deviceRepository.getIdByDeviceType(DeviceType.pdc, platformNo);

		if (pdcData != null) {

			List<ChildrenDetails> pdcChildren = pdcData.getChildren();
			if (pdcChildren != null) {

				for (int i = 0; i < pdcChildren.size(); i++) {

					Object obj = pdcChildren.get(i);
					Map myMap = (Map) obj;
					String stringId = String.valueOf(myMap.get("id"));
					Long id = Long.parseLong(stringId);
					Device dataChild = deviceRepository.findAllById(id);
					DeviceType devicetype = dataChild.getDeviceType();

					if (devicetype == DeviceType.cgdb) {

						List<CoachesDetail> coaches = dataChild.getCoaches();
						for (int j = 0; j < coaches.size(); j++) {

							HashMap<String, Object> deviceMap = new HashMap<>();
							Object ipObj = coaches.get(j);
							Map ipMap = (Map) ipObj;
							String stringIp = String.valueOf(ipMap.get("ipAddress"));
							String stringStatus = String.valueOf(ipMap.get("status"));
							deviceMap.put("id", dataChild.getId());
							deviceMap.put("ipAddress", stringIp);
							deviceMap.put("deviceType", dataChild.getDeviceType());
							deviceMap.put("portNumber", dataChild.getPortNumber());
							deviceMap.put("status", stringStatus);
							deviceMap.put("linkTime", dataChild.getLinkStatus().getLinkTime());
							deviceMap.put("responseTime", dataChild.getLinkStatus().getResponseTime());
							deviceMap.put("response", dataChild.getLinkStatus().getResponse());
							list.add(deviceMap);
						}

					} else {

						HashMap<String, Object> deviceMap = new HashMap<>();
						deviceMap.put("id", dataChild.getId());
						deviceMap.put("ipAddress", dataChild.getIpAddress());
						deviceMap.put("deviceType", dataChild.getDeviceType());
						deviceMap.put("portNumber", dataChild.getPortNumber());
						deviceMap.put("status", dataChild.getStatus());
						deviceMap.put("linkTime", dataChild.getLinkStatus().getLinkTime());
						deviceMap.put("responseTime", dataChild.getLinkStatus().getResponseTime());
						deviceMap.put("response", dataChild.getLinkStatus().getResponse());
						list.add(deviceMap);

					}

				}

			} else {
				exceptionRule.expect(HandledException.class);
				exceptionRule.expectMessage("PFDB & CGDB for this platform doesn't exist");

			}

		} else {
			exceptionRule.expect(HandledException.class);
			exceptionRule.expectMessage("PDC for this platform doesn't exist");

		}

		Assert.assertNotNull(list);

	}

	public void deletev1() {
		Device device = deviceRepository.getById(21L);
		if (device == null) {
			System.out.println("Device not found on");
		} else {
			Long delId = device.getId();
			String ip = device.getIpAddress();
			DeviceType deviceType = device.getDeviceType();
			String[] platformNumber = device.getPlatformNo();

			if (deviceType == DeviceType.cdcMaster || deviceType == DeviceType.cdcSlave || deviceType == DeviceType.pdc
					|| deviceType == DeviceType.mldb || deviceType == DeviceType.agdb || deviceType == DeviceType.ivd
					|| deviceType == DeviceType.ovd || deviceType == DeviceType.tvDisplay) {
				Device cdsData = deviceRepository.findByDeviceType(DeviceType.cds);
				List cdsChildren = cdsData.getChildren();
				this.deleteChildern(cdsChildren, device);

				if (deviceType == DeviceType.pdc) {

					Device pdc = deviceRepository.getIdByDeviceType(DeviceType.pdc, platformNumber);
					List pdcChildren = pdc.getChildren();

					if (pdcChildren != null) {

						for (int i = 0; i < pdcChildren.size(); i++) {

							Object obj = pdcChildren.get(i);
							Map myMap = (Map) obj;
							String stringToConvert = String.valueOf(myMap.get("id"));
							Long convertedLong = Long.parseLong(stringToConvert);
							Device delpdc = deviceRepository.findAllById(convertedLong);
							deviceRepository.delete(delpdc);
						}

					}

				}

			} else {

				String[] platform = { platformNumber[0] };
				Device pdcData = deviceRepository.getIdByDeviceType(DeviceType.pdc, platform);
				List pdcChildren = pdcData.getChildren();
				this.deleteChildern(pdcChildren, device);

			}

			deviceRepository.delete(device);
			Map<String, Boolean> response = new HashMap<>();
			response.put("deleted", Boolean.TRUE);

			Assert.assertNotNull(response);
		}
	}

	private String[] ipValues(String ip) {

		String[] ipAddress = ip.split("[, . ']+");
		return ipAddress;

	}

	private void deleteChildern(List param1, Device device) {

		for (int i = 0; i < param1.size(); i++) {

			Object obj = param1.get(i);
			Map myMap = (Map) obj;
			String stringToConvert = String.valueOf(myMap.get("id"));
			Long convertedLong = Long.parseLong(stringToConvert);

			if (device.getId().equals(convertedLong)) {

				param1.remove(obj);
				break;

			}
		}

	}

	public void testSetConfiguration() throws HandledException {

		requestWrapper.postRequest(RequestTypes.SET_CONFIGURATION);

	}

}
