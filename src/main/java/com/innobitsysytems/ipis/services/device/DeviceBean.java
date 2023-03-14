/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Device Bean
 */
package com.innobitsysytems.ipis.services.device;

import java.util.*;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.HashMapParent;
import com.innobitsysytems.ipis.model.LinkStatus;
import com.innobitsysytems.ipis.model.device.ChildrenDetails;
import com.innobitsysytems.ipis.model.device.CoachesDetail;
import com.innobitsysytems.ipis.model.device.Device;
import com.innobitsysytems.ipis.model.device.DeviceStatus;
import com.innobitsysytems.ipis.model.device.DeviceType;
import com.innobitsysytems.ipis.model.device.HashMapCoachDetails;
import com.innobitsysytems.ipis.repository.DeviceRepository;
import com.innobitsysytems.ipis.repository.LinkStatusRepository;
import com.innobitsysytems.ipis.threadmgnt.RequestTypes;
import com.innobitsysytems.ipis.threadmgnt.RequestWrapper;
import com.innobitsysytems.ipis.utility.CommonUtil;
import com.innobitsysytems.ipis.utility.CustomUtil;

@Service
public class DeviceBean implements DeviceService {

	Date date = new Date();

	@Autowired
	public DeviceRepository deviceRepository;

	@Autowired
	public LinkStatusRepository linkStatusRepository;

	@Autowired
	public CustomUtil customUtil;

	@Autowired
	private RequestWrapper requestWrapper;

	@Autowired
	private CommonUtil commonUtil;

	@Override
	public HashMap<String, Object> list() throws HandledException {

		HashMap<String, Object> deviceMap = new HashMap<>();
		Device devicepare = deviceRepository.findByDeviceType(DeviceType.cds);

		if (devicepare != null) {

			deviceMap.put("id", devicepare.getId());
			deviceMap.put("ipAddress", devicepare.getIpAddress());
			deviceMap.put("deviceType", devicepare.getDeviceType());
			deviceMap.put("deviceName", devicepare.getDeviceName());
			deviceMap.put("children", devicepare.getChildren());

			return deviceMap;

		} else {

			throw new HandledException("NOT_FOUND", "No Device information is present in Database");
		}

	}

	@Override
	public HashMap<String, Object> getSingleDevice(Long id) throws HandledException {

		HashMap<String, Object> deviceMap = new HashMap<>();

		Device device = deviceRepository.findById(id)
				.orElseThrow(() -> new HandledException("NOT_FOUND", "Device not found on : " + id));
		int oct3 = 1;
		String ip = device.getIpAddress();
		if (device.getDeviceType().toString().equals("cgdb")) {
			oct3 = 1;
		} else {
			oct3 = Integer.parseInt(ipValues(ip)[2]);

		}
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

			String port = device.getPortNumber();
			String[] portArray = port.split("Z");
			deviceMap.put("id", device.getId());
			deviceMap.put("deviceType", device.getDeviceType());
			deviceMap.put("portNumber", "Z" + portArray[1]);
			deviceMap.put("platformNo", device.getPlatformNo());
			deviceMap.put("noOfCoaches", device.getNoOfCoaches());
			deviceMap.put("startId", device.getStartId());
			deviceMap.put("englishInfoDisplay", device.getEnglishInfoDisplay());
			deviceMap.put("hindiInfoDisplay", device.getHindiInfoDisplay());
			deviceMap.put("coaches", device.getCoaches());
			break;
		}

		case "mldb": {
			String port = device.getPortNumber();
			if (port.contains("Z")) {
				String[] portArray = port.split("Z");
				System.out.println(portArray[1] + " portt");
				deviceMap.put("portNumber", "Z" + portArray[1]);
			} else {
				deviceMap.put("portNumber", port);

			}
			deviceMap.put("id", device.getId());
			deviceMap.put("ipAddress", device.getIpAddress());
			deviceMap.put("deviceType", device.getDeviceType());
			deviceMap.put("deviceName", device.getDeviceName());
			deviceMap.put("boardType", device.getBoardType());
//			deviceMap.put("portNumber", device.getPortNumber());
			deviceMap.put("noOfLines", device.getNoOfLines());
			deviceMap.put("messageLine", device.getMessageLine());
			deviceMap.put("enableMsgLine", device.getEnableMsgLine());
			break;
		}
		case "tvDisplay": {
			String port = device.getPortNumber();
			if (port.contains("Z")) {
				String[] portArray = port.split("Z");
				System.out.println(portArray[1] + " portt");
				deviceMap.put("portNumber", "Z" + portArray[1]);
			} else {
				deviceMap.put("portNumber", port);

			}

			deviceMap.put("id", device.getId());
			deviceMap.put("ipAddress", device.getIpAddress());
			deviceMap.put("deviceType", device.getDeviceType());
			deviceMap.put("deviceName", device.getDeviceName());
//			deviceMap.put("portNumber", device.getPortNumber());
			deviceMap.put("noOfLines", device.getNoOfLines());
			deviceMap.put("messageLine", device.getMessageLine());
			deviceMap.put("enableMsgLine", device.getEnableMsgLine());
			deviceMap.put("platformNo", device.getPlatformNo());
			break;

		}

		case "ivd": {
			String port = device.getPortNumber();
			if (port.contains("Z")) {
				String[] portArray = port.split("Z");
				System.out.println(portArray[1] + " portt");
				deviceMap.put("portNumber", "Z" + portArray[1]);
			} else {
				deviceMap.put("portNumber", port);

			}

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

			if (oct3 == 0) {
				String port = device.getPortNumber();
				deviceMap.put("id", device.getId());
				deviceMap.put("ipAddress", device.getIpAddress());
				deviceMap.put("deviceType", device.getDeviceType());
				deviceMap.put("deviceName", device.getDeviceName());
				deviceMap.put("platformNo", device.getPlatformNo());
				deviceMap.put("portNumber", device.getPortNumber());
				deviceMap.put("boardType", device.getBoardType());
				break;

			} else {
				String port = device.getPortNumber();
				String[] portArray = port.split("Z");
				deviceMap.put("id", device.getId());
				deviceMap.put("ipAddress", device.getIpAddress());
				deviceMap.put("deviceType", device.getDeviceType());
				deviceMap.put("deviceName", device.getDeviceName());
				deviceMap.put("portNumber", "Z" + portArray[1]);
				deviceMap.put("platformNo", device.getPlatformNo());
				deviceMap.put("boardType", device.getBoardType());
				break;
			}

		}

		case "agdb": {
			String port = device.getPortNumber();
			if (port.contains("Z")) {
				String[] portArray = port.split("Z");
				System.out.println(portArray[1] + " portt");
				deviceMap.put("portNumber", "Z" + portArray[1]);
			} else {
				deviceMap.put("portNumber", port);

			}

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
		case "pfdbByCds": {

			String port = device.getPortNumber();
			deviceMap.put("id", device.getId());
			deviceMap.put("ipAddress", device.getIpAddress());
			deviceMap.put("deviceType", device.getDeviceType());
			deviceMap.put("deviceName", device.getDeviceName());
			deviceMap.put("platformNo", device.getPlatformNo());
			deviceMap.put("portNumber", device.getPortNumber());
			deviceMap.put("boardType", device.getBoardType());
			break;

		}
		}

		return deviceMap;
	}

	@Override
	public HashMap<String, Object> create(HttpServletRequest request, Device device) throws HandledException {
		List<Device> deviceList = deviceRepository.findAll();

		String[] empty = {};
		LinkStatus linkStatus = new LinkStatus();

		// Storing Parent in the array
		String port = device.getPortNumber();
		List<ChildrenDetails> newParent = new ArrayList<>();

		String[] platArray = device.getPlatformNo();
		String ip = device.getIpAddress();
		String portNumber = device.getPortNumber();

		String PortNumber = device.getPortNumber();

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
								commonUtil.updateActivities("Configuration Setting in Interface for CDS",
										String.valueOf(customUtil.getIdFromToken()));
								deviceRepository.save(device);

								deviceMap.put("id", device.getId());
								deviceMap.put("ipAddress", device.getIpAddress());
								deviceMap.put("deviceType", device.getDeviceType());

							} else {

								throw new HandledException("CHECK_PARAMETERS",
										"Check for the 4th octate of IPaddress of CDS");

							}

						} else {

							throw new HandledException("CHECK_PARAMETERS",
									"Check for the 3rd octate of IPaddress of CDS");
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
									commonUtil.updateActivities("Configuration Setting in Interface for PDC",
											String.valueOf(customUtil.getIdFromToken()));
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

									throw new HandledException("CHECK_PARAMETERS", "Check the Platform number of PDC");
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
										commonUtil.updateActivities("Configuration Setting in Interface for PDC",
												String.valueOf(customUtil.getIdFromToken()));
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

										throw new HandledException("CHECK_PARAMETERS",
												"Check the Special Platform number of PDC");

									}

								} else {

									throw new HandledException("CHECK_PARAMETERS",
											"Check the 3rd octate of IPaddress of PDC");
								}
							}

						} else {

							throw new HandledException("CHECK_PARAMETERS",
									"Check for the 4th octate IPaddress of PDC ");

						}

					} else if (devicetype == DeviceType.cgdb) {

						Device existpdc = deviceRepository.getIdByDeviceType(DeviceType.pdc, platArray);

						if (existpdc != null) {
							String portCgdb1 = existpdc.getPortNumber() + device.getPortNumber();
							Device existPortCgdb = deviceRepository.findByPortNumber(portCgdb1);
							if (existPortCgdb == null) {

								if (device.getStartId() > 1 && device.getStartId() < 40) {

									// set as CGDB
									linkStatus.setLinkTime(date);
									linkStatus.setResponseTime(date);
									linkStatus.setResponse("N/A");

									String portCgdb = existpdc.getPortNumber() + device.getPortNumber();
									String emp = "";
									String[] max = (device.getEnableMsgLine() == null) ? empty
											: device.getEnableMsgLine();
									device.setEnableMsgLine(max);
									String max1 = (device.getIpAddress() != null) ? emp : device.getIpAddress();
									device.setIpAddress(max1);
									// device.setCreatedAt(date);
									device.setCreatedBy(String.valueOf(token));
									device.setStatus(DeviceStatus.Connected);
									device.setLinkStatus(linkStatus);
									device.setPortNumber(portCgdb);
									commonUtil.updateActivities("Configuration Setting in Interface for CGDB",
											String.valueOf(customUtil.getIdFromToken()));
									deviceRepository.save(device);

									deviceMap.put("id", device.getId());
									deviceMap.put("deviceType", device.getDeviceType());
									deviceMap.put("portNumber", portCgdb);
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

									throw new HandledException("CHECK_PARAMETERS", "Check the startId of CGDB");
								}

							} else {
								throw new HandledException("PORT_ASSIGNED", "this port is already assigned");
							}

						} else {

							throw new HandledException("CHECK_PARAMETERS", "Pdc for this platform no. " + platArray[0]
									+ " and" + platArray[1] + " does not exist. Check the platform no.");
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
								String arr[] = { "0" };
								device.setPlatformNo(arr);
								device.setLinkStatus(linkStatus);
								commonUtil.updateActivities("Configuration Setting in Interface for OVD",
										String.valueOf(customUtil.getIdFromToken()));
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

								throw new HandledException("CHECK_PARAMETERS",
										"Check for the 3rd octate IPaddress of OVD");

							}

						} else {

							throw new HandledException("CHECK_PARAMETERS", "Check for the 4th octate IPaddress of OVD");
						}

					} else if (devicetype == DeviceType.ivd) {

						if (Integer.parseInt(ipValues(ip)[3]) > 70 && Integer.parseInt(ipValues(ip)[3]) < 101) {

							if (ipValues(ip)[2].equals("0")) {

//								if (ipValues(ip)[2].equals(platArray[0])) {

								// Set as IVD
								linkStatus.setLinkTime(date);
								linkStatus.setResponseTime(date);
								linkStatus.setResponse("N/A");
								device.setCreatedBy(String.valueOf(token));
								device.setStatus(DeviceStatus.Disconnected);
								device.setLinkStatus(linkStatus);
								String arr[] = { "0" };
								device.setPlatformNo(arr);
								commonUtil.updateActivities("Configuration Setting in Interface for IVD",
										String.valueOf(customUtil.getIdFromToken()));
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

//								} else {
//
//									throw new HandledException("CHECK_PARAMETERS",
//											"Check for the Platform number of General IVD ");
//								}

							} else if (oct3 > 0 && oct3 < 31) {

								String[] platfprm = { platArray[0] };

								Device existpdc = deviceRepository.getIdByDeviceType(DeviceType.pdc, platfprm);

								if (existpdc != null) {
									Device existPortIvd = null;

									String portpfdb1 = existpdc.getPortNumber() + device.getPortNumber();
									existPortIvd = deviceRepository.findByPortNumber(portpfdb1);
									if (existPortIvd == null) {

										if (ipValues(ip)[2].equals(platArray[0])) {

											linkStatus.setLinkTime(date);
											linkStatus.setResponseTime(date);
											linkStatus.setResponse("N/A");
											device.setCreatedBy(String.valueOf(token));
											device.setStatus(DeviceStatus.Disconnected);
											device.setLinkStatus(linkStatus);
											commonUtil.updateActivities("Configuration Setting in Interface for IVD",
													String.valueOf(customUtil.getIdFromToken()));

											String portIvd = null;
											portIvd = existpdc.getPortNumber() + device.getPortNumber();

											device.setPortNumber(portIvd);
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

											Device devicepare = null;
											devicepare = deviceRepository.getIdByDeviceType(DeviceType.pdc, platfprm);
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

											throw new HandledException("CHECK_PARAMETERS",
													"Check the platform no. of IVD");

										}
									} else {
										throw new HandledException("PORT_ASSIGNED", "This port is already assigned");
									}
								}

							} else if (oct3 > 100 && oct3 < 111) {
								// For special platform of IVD
								String specialPlatformIvd = String.valueOf(Integer.parseInt(ipValues(ip)[2]) - 100)
										+ "A";
								String[] platform = { platArray[0] };
								Device existpdc = deviceRepository.getIdByDeviceType(DeviceType.pdc, platform);

								if (existpdc != null) {
									Device existPortIvd = null;

									String portpfdb1 = existpdc.getPortNumber() + device.getPortNumber();
									existPortIvd = deviceRepository.findByPortNumber(portpfdb1);
									if (existPortIvd == null) {
										if (specialPlatformIvd.equals(platArray[0])) {

											linkStatus.setLinkTime(date);
											linkStatus.setResponseTime(date);
											linkStatus.setResponse("N/A");
											device.setCreatedBy(String.valueOf(token));
											device.setStatus(DeviceStatus.Disconnected);
											device.setLinkStatus(linkStatus);
											commonUtil.updateActivities("Configuration Setting in Interface for IVD",
													String.valueOf(customUtil.getIdFromToken()));

											String portIvd = null;
											portIvd = existpdc.getPortNumber() + device.getPortNumber();

											device.setPortNumber(portIvd);

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
											Device devicepare = deviceRepository.getIdByDeviceType(DeviceType.pdc,
													platform);
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

											throw new HandledException("CHECK_PARAMETERS",
													"Check the special platform no. of IVD");
										}
									} else {
										throw new HandledException("CHECK_PDC", "port number already assigned");
									}
								} else {
									throw new HandledException("CHECK_PDC",
											"pdc for platform number " + platArray[0] + " not found");
								}

							} else {

								throw new HandledException("CHECK_PARAMETERS",
										"Check for the 3rd octate IPaddress of IVD");
							}

						} else {

							throw new HandledException("CHECK_PARAMETERS",
									"Check for the 4th octate IP address of IVD ");
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
								commonUtil.updateActivities("Configuration Setting in Interface for MLDB",
										String.valueOf(customUtil.getIdFromToken()));

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

							} else if (oct3 > 0 && oct3 < 31) {
								String[] platform = { platArray[0] };

								Device existpdc = deviceRepository.getIdByDeviceType(DeviceType.pdc, platform);

								if (existpdc != null) {
									Device existPortMldb = null;

									String portpfdb1 = existpdc.getPortNumber() + device.getPortNumber();
									existPortMldb = deviceRepository.findByPortNumber(portpfdb1);
									if (existPortMldb == null) {
										if (ipValues(ip)[2].equals(platArray[0])) {
											linkStatus.setLinkTime(date);
											linkStatus.setResponseTime(date);
											linkStatus.setResponse("OK");
											device.setCreatedBy(String.valueOf(token));
											device.setStatus(DeviceStatus.Disconnected);
											device.setLinkStatus(linkStatus);
											String portIvd = null;
											portIvd = existpdc.getPortNumber() + device.getPortNumber();

											device.setPortNumber(portIvd);
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
											deviceMap.put("platformNo", device.getPlatformNo());

											// Searching the Parent
											Device devicepare = null;
											devicepare = deviceRepository.getIdByDeviceType(DeviceType.pdc, platform);
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
											throw new HandledException("CHECK_PARAMETERS",
													"Check the platform no. of MLDB");
										}
									} else {
										throw new HandledException("PORT_ASSIGNED", "This Port already assigned");
									}
								} else {
									throw new HandledException("PDC_NOT_CREATED",
											"PDC For platform number " + platArray[0] + " not created");
								}

							} else if (oct3 > 100 && oct3 < 111) {
								String[] platform = { platArray[0] };
								Device existpdc = deviceRepository.getIdByDeviceType(DeviceType.pdc, platform);
								String specialPlatformMldb = String.valueOf(Integer.parseInt(ipValues(ip)[2]) - 100)
										+ "A";
								if (existpdc != null) {
									Device existPortMldb = null;

									String portpfdb1 = existpdc.getPortNumber() + device.getPortNumber();
									existPortMldb = deviceRepository.findByPortNumber(portpfdb1);
									if (existPortMldb == null) {
										if (specialPlatformMldb.equals(platArray[0])) {

											linkStatus.setLinkTime(date);
											linkStatus.setResponseTime(date);
											linkStatus.setResponse("OK");

											String[] special = { specialPlatformMldb };
											String[] normal = { ipValues(ip)[2] };
											String[] setplatform = (oct3 > 100) ? special : normal;
											device.setPlatformNo(setplatform);
											device.setCreatedBy(String.valueOf(token));
											device.setStatus(DeviceStatus.Connected);
											device.setLinkStatus(linkStatus);
											commonUtil.updateActivities("Configuration Setting in Interface for MLDB",
													String.valueOf(customUtil.getIdFromToken()));

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
											Device devicepare = null;
											devicepare = deviceRepository.getIdByDeviceType(DeviceType.pdc, platform);
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
											throw new HandledException("CHECK_PARAMETERS",
													"Check the platform no. of MLDB");
										}
									} else {
										throw new HandledException("PORT_ASSIGNED", "This port is already assigned");
									}
								}

								else {
									throw new HandledException("PDC_NOT_CREATED",
											"PDC For platform number " + platArray[0] + " not created");
								}
							} else {

								throw new HandledException("CHECK_PARAMETERS",
										"Check the 3RD octate IP address of MLDB ");
							}

						} else {

							throw new HandledException("CHECK_PARAMETERS", "Check the 4TH octate IP address of MLDB ");
						}

					} else if (devicetype == DeviceType.agdb) {

						if (Integer.parseInt(ipValues(ip)[3]) > 130 && Integer.parseInt(ipValues(ip)[3]) < 161) {

							if (ipValues(ip)[2].equals("0")) {

								// For general platform of agdb
								linkStatus.setLinkTime(date);
								linkStatus.setResponseTime(date);
								linkStatus.setResponse("OK");
								String[] max = (device.getEnableMsgLine() == null) ? empty : device.getEnableMsgLine();
								device.setEnableMsgLine(max);
								device.setCreatedBy(String.valueOf(token));
								device.setStatus(DeviceStatus.Connected);
								device.setLinkStatus(linkStatus);
								String agdbPlatform[] = { "0" };
								device.setPlatformNo(agdbPlatform);
								commonUtil.updateActivities("Configuration Setting in Interface for AGDB",
										String.valueOf(customUtil.getIdFromToken()));
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

							} else if (oct3 > 0 && oct3 < 31) {

								String[] platform = { platArray[0] };
								String[] platform1 = { platArray[1] };

								Device existpdc = deviceRepository.getIdByDeviceType(DeviceType.pdc, platform);
								Device existpdc1 = deviceRepository.getIdByDeviceType(DeviceType.pdc, platform1);

								if (existpdc != null || existpdc1 != null) {
									Device existPortPfdb = null;
									if (existpdc1 == null) {
										String portpfdb1 = existpdc.getPortNumber() + device.getPortNumber();
										existPortPfdb = deviceRepository.findByPortNumber(portpfdb1);
									} else {
										String portpfdb1 = existpdc1.getPortNumber() + device.getPortNumber();
										existPortPfdb = deviceRepository.findByPortNumber(portpfdb1);

									}
									if (existPortPfdb == null) {

										if (ipValues(ip)[2].equals(platArray[0])
												|| ipValues(ip)[2].equals(platArray[1])) {

											// For normal platform of agdb
											linkStatus.setLinkTime(date);
											linkStatus.setResponseTime(date);
											linkStatus.setResponse("OK");
											String[] max = (device.getEnableMsgLine() == null) ? empty
													: device.getEnableMsgLine();
											device.setEnableMsgLine(max);
											device.setCreatedBy(String.valueOf(token));
											device.setStatus(DeviceStatus.Connected);
											device.setLinkStatus(linkStatus);
											commonUtil.updateActivities("Configuration Setting in Interface for AGDB",
													String.valueOf(customUtil.getIdFromToken()));

											String portPfdb = null;
											if (existpdc1 == null) {

												portPfdb = existpdc.getPortNumber() + device.getPortNumber();
											} else {
												portPfdb = existpdc1.getPortNumber() + device.getPortNumber();

											}

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
											Device devicepare = null;
											if (deviceRepository.getIdByDeviceType(DeviceType.pdc, platform) != null) {
												devicepare = deviceRepository.getIdByDeviceType(DeviceType.pdc,
														platform);
											} else {
												devicepare = deviceRepository.getIdByDeviceType(DeviceType.pdc,
														platform1);

											}
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

											throw new HandledException("CHECK_PARAMETERS",
													"Check the platform no. of AGDB by CDS");

										}
									} else {
										throw new HandledException("PORT_ASSIGNED", "This port is already assigned");
									}
								} else {
									throw new HandledException("PDC_CREATED", "PDC for platform " + platArray[0]
											+ " and " + platArray[1] + " not created");
								}

							} else if (oct3 > 100 && oct3 < 111) {

								// For special platform of agdb
								String specialPlatform = String.valueOf(Integer.parseInt(ipValues(ip)[2]) - 100) + "A";

								String[] platform = { platArray[0] };
								String[] platform1 = { platArray[1] };

								Device existpdc = deviceRepository.getIdByDeviceType(DeviceType.pdc, platform);
								Device existpdc1 = deviceRepository.getIdByDeviceType(DeviceType.pdc, platform1);

								if (existpdc != null || existpdc1 != null) {
									Device existPortPfdb = null;
									if (existpdc1 == null) {
										String portpfdb1 = existpdc.getPortNumber() + device.getPortNumber();
										existPortPfdb = deviceRepository.findByPortNumber(portpfdb1);
									} else {
										String portpfdb1 = existpdc1.getPortNumber() + device.getPortNumber();
										existPortPfdb = deviceRepository.findByPortNumber(portpfdb1);

									}
									if (existPortPfdb == null) {

										if (specialPlatform.equals(platArray[0])
												|| specialPlatform.equals(platArray[1])) {

											// Set for special platform
											linkStatus.setLinkTime(date);
											linkStatus.setResponseTime(date);
											linkStatus.setResponse("OK");
											String[] max = (device.getEnableMsgLine() == null) ? empty
													: device.getEnableMsgLine();
											device.setEnableMsgLine(max);
											device.setCreatedBy(String.valueOf(token));
											device.setStatus(DeviceStatus.Connected);
											device.setLinkStatus(linkStatus);
											commonUtil.updateActivities("Configuration Setting in Interface for AGDB",
													String.valueOf(customUtil.getIdFromToken()));

											String portPfdb = null;
											if (existpdc1 == null) {

												portPfdb = existpdc.getPortNumber() + device.getPortNumber();
											} else {
												portPfdb = existpdc1.getPortNumber() + device.getPortNumber();

											}

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
											Device devicepare = null;
											if (deviceRepository.getIdByDeviceType(DeviceType.pdc, platform) != null) {
												devicepare = deviceRepository.getIdByDeviceType(DeviceType.pdc,
														platform);
											} else {
												devicepare = deviceRepository.getIdByDeviceType(DeviceType.pdc,
														platform1);

											}
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

											throw new HandledException("CHECK_PARAMETERS",
													"Check the special platform no. of AGDB by CDS");
										}
									} else {
										throw new HandledException("PORT_ASSIGNED", "This port is already assigned");
									}
								} else {
									{
										throw new HandledException("PDC_NOT_CREATED", "PDC for platform number "
												+ platArray[0] + " and " + platArray[1] + "not created");
									}
								}

							} else {

								throw new HandledException("CHECK_PARAMETERS",
										"Check for the 3rd octate IPaddress of AGDB by CDS");
							}

						} else {

							throw new HandledException("CHECK_PARAMETERS",
									"Check for the 4th octate IP address of AGDB by CDS");
						}

					} else if (devicetype == DeviceType.pfdb) {
						if (Integer.parseInt(ipValues(ip)[3]) > 160 && Integer.parseInt(ipValues(ip)[3]) < 191) {
						if (ipValues(ip)[2].equals("0")) {



							// For general platform of pfdbByCDS
							linkStatus.setLinkTime(date);
							linkStatus.setResponseTime(date);
							linkStatus.setResponse("N/A");

							String[] max = (device.getEnableMsgLine() == null) ? empty : device.getEnableMsgLine();
							device.setCreatedBy(String.valueOf(token));
							device.setEnableMsgLine(max);
							device.setStatus(DeviceStatus.Connected);
							device.setPortNumber(device.getPortNumber());
							device.setLinkStatus(linkStatus);
							commonUtil.updateActivities("Configuration Setting in Interface for pfdb by CDS",
									String.valueOf(customUtil.getIdFromToken()));
							String platfrmArr[] = { "0" };
							device.setPlatformNo(platfrmArr);
							deviceRepository.save(device);

							deviceMap.put("id", device.getId());
							deviceMap.put("ipAddress", device.getIpAddress());
							deviceMap.put("deviceType", device.getDeviceType());
							deviceMap.put("deviceName", device.getDeviceName());
							deviceMap.put("portNumber", device.getPortNumber());
							deviceMap.put("platformNo", device.getPlatformNo());
							deviceMap.put("boardType", device.getBoardType());

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

							String[] platfprm = { platArray[0] };
							String[] platfprm1 = { platArray[1] };

							Device existpdc = deviceRepository.getIdByDeviceType(DeviceType.pdc, platfprm);
							Device existpdc1 = deviceRepository.getIdByDeviceType(DeviceType.pdc, platfprm1);

							if (existpdc != null || existpdc1 != null) {
								Device existPortPfdb = null;
								if (existpdc1 == null) {
									String portpfdb1 = existpdc.getPortNumber() + device.getPortNumber();
									existPortPfdb = deviceRepository.findByPortNumber(portpfdb1);
								} else {
									String portpfdb1 = existpdc1.getPortNumber() + device.getPortNumber();
									existPortPfdb = deviceRepository.findByPortNumber(portpfdb1);

								}
								if (existPortPfdb == null) {
									if (Integer.parseInt(ipValues(ip)[3]) > 160
											&& Integer.parseInt(ipValues(ip)[3]) < 191) {

										if (ipValues(ip)[2].equals(platArray[0])
												|| ipValues(ip)[2].equals(platArray[1])) {

											// Set as PFDB
											linkStatus.setLinkTime(date);
											linkStatus.setResponseTime(date);
											linkStatus.setResponse("OK");
											String portPfdb = null;
											if (existpdc1 == null) {

												portPfdb = existpdc.getPortNumber() + device.getPortNumber();
											} else {
												portPfdb = existpdc1.getPortNumber() + device.getPortNumber();

											}
											String[] max = (device.getEnableMsgLine() == null) ? empty
													: device.getEnableMsgLine();
											device.setEnableMsgLine(max);
											device.setCreatedBy(String.valueOf(token));
											device.setStatus(DeviceStatus.Connected);
											device.setLinkStatus(linkStatus);
											device.setPortNumber(portPfdb);
											commonUtil.updateActivities("Configuration Setting in Interface for PFDB",
													String.valueOf(customUtil.getIdFromToken()));
											deviceRepository.save(device);

											deviceMap.put("id", device.getId());
											deviceMap.put("ipAddress", device.getIpAddress());
											deviceMap.put("deviceType", device.getDeviceType());
											deviceMap.put("deviceName", device.getDeviceName());
											deviceMap.put("portNumber", portPfdb);
											deviceMap.put("platformNo", device.getPlatformNo());
											deviceMap.put("boardType", device.getBoardType());

											// Searching the Parent
											Device devicepare = null;
											if (deviceRepository.getIdByDeviceType(DeviceType.pdc, platfprm) != null) {
												devicepare = deviceRepository.getIdByDeviceType(DeviceType.pdc,
														platfprm);
											} else {
												devicepare = deviceRepository.getIdByDeviceType(DeviceType.pdc,
														platfprm1);

											}
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
											String platformNo = String.valueOf(Integer.parseInt(ipValues(ip)[2]) - 100)
													+ "A";

											if (platformNo.equals(platArray[0]) || platformNo.equals(platArray[1])) {

												// Set as PFDB
												linkStatus.setLinkTime(date);
												linkStatus.setResponseTime(date);
												linkStatus.setResponse("OK");
												String portPfdb = null;
												if (existpdc1 == null) {

													portPfdb = existpdc.getPortNumber() + device.getPortNumber();
												} else {
													portPfdb = existpdc1.getPortNumber() + device.getPortNumber();

												}
//											String portPfdb = existpdc.getPortNumber() + device.getPortNumber();
												String[] max = (device.getEnableMsgLine() == null) ? empty
														: device.getEnableMsgLine();
												device.setEnableMsgLine(max);
												device.setCreatedBy(String.valueOf(token));
												device.setStatus(DeviceStatus.Connected);
												device.setLinkStatus(linkStatus);
												device.setPortNumber(portPfdb);
												commonUtil.updateActivities(
														"Configuration Setting in Interface for PFDB",
														String.valueOf(customUtil.getIdFromToken()));
												deviceRepository.save(device);

												deviceMap.put("id", device.getId());
												deviceMap.put("ipAddress", device.getIpAddress());
												deviceMap.put("deviceType", device.getDeviceType());
												deviceMap.put("deviceName", device.getDeviceName());
												deviceMap.put("portNumber", portPfdb);
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

												throw new HandledException("CHECK_PARAMETERS",
														"Check PFDB 3RD octate of IP address");

											}

										}

									} else {

										throw new HandledException("CHECK_PARAMETERS",
												"Check PFDB 4TH octate of IP address");
									}
								} else {
									throw new HandledException("PORT_ASSIGNED", "This Port is assigned");

								}

							} else {

								throw new HandledException("CHECK_PARAMETERS", "Pdc for platform no." + platArray[0]
										+ " and " + platArray[1] + " does not exist. Check the platform no.");
							}
						}
						}
						else {

							throw new HandledException("CHECK_PARAMETERS",
									"Check PFDB 4TH octate of IP address");
						}
					}

					else if (devicetype == DeviceType.tvDisplay) {

						if (Integer.parseInt(ipValues(ip)[3]) > 190 && Integer.parseInt(ipValues(ip)[3]) < 221) {

							if (ipValues(ip)[2].equals("0")) {

								// Set as TV Display
								linkStatus.setLinkTime(date);
								linkStatus.setResponseTime(date);
								linkStatus.setResponse("N/A");
								device.setCreatedBy(String.valueOf(token));
								device.setStatus(DeviceStatus.Disconnected);
								device.setLinkStatus(linkStatus);
								commonUtil.updateActivities("Configuration Setting in Interface for TV Display",
										String.valueOf(customUtil.getIdFromToken()));
								String arr[] = { "0" };
								device.setPlatformNo(arr);
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


							} else if (oct3 > 0 && oct3 < 31) {
								String[] platform = { platArray[0] };

								Device existpdc = deviceRepository.getIdByDeviceType(DeviceType.pdc, platform);

								if (existpdc != null) {
									Device existPortTv = null;

									String portpfdb1 = existpdc.getPortNumber() + device.getPortNumber();
									existPortTv = deviceRepository.findByPortNumber(portpfdb1);
									if (existPortTv == null) {

										if (ipValues(ip)[2].equals(platArray[0])) {

											linkStatus.setLinkTime(date);
											linkStatus.setResponseTime(date);
											linkStatus.setResponse("N/A");
											device.setCreatedBy(String.valueOf(token));
											device.setStatus(DeviceStatus.Disconnected);
											device.setLinkStatus(linkStatus);
											commonUtil.updateActivities(
													"Configuration Setting in Interface for TV Display",
													String.valueOf(customUtil.getIdFromToken()));

											String portTv = existpdc.getPortNumber() + device.getPortNumber();
											device.setPortNumber(portTv);
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
											Device devicepare = deviceRepository.getIdByDeviceType(DeviceType.pdc,
													platform);
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

											throw new HandledException("CHECK_PARAMETERS",
													"Check the platform no. of TV Display");
										}
									}

									else {
										throw new HandledException("PORT_ASSIGNED", "This Port already assigned");
									}

								} else {
									throw new HandledException("PDC_NOT_CREATED",
											"Pdc for platform " + platArray[0] + " not created");

								}

							} else {
								if (oct3 > 100 && oct3 < 111) {

									// For special platform of TV display
									String platformNo = String.valueOf(Integer.parseInt(ipValues(ip)[2]) - 100) + "A";
									String[] platform = { platArray[0] };

									Device existpdc = deviceRepository.getIdByDeviceType(DeviceType.pdc, platform);

									if (existpdc != null) {
										Device existPortTv = null;

										String portpfdb1 = existpdc.getPortNumber() + device.getPortNumber();
										existPortTv = deviceRepository.findByPortNumber(portpfdb1);
										if (existPortTv == null) {

											if (platformNo.equals(platArray[0])) {

												linkStatus.setLinkTime(date);
												linkStatus.setResponseTime(date);
												linkStatus.setResponse("N/A");
												device.setCreatedBy(String.valueOf(token));
												device.setStatus(DeviceStatus.Disconnected);
												device.setLinkStatus(linkStatus);
												commonUtil.updateActivities(
														"Configuration Setting in Interface for TV Display",
														String.valueOf(customUtil.getIdFromToken()));
												String portTv = existpdc.getPortNumber() + device.getPortNumber();
												device.setPortNumber(portTv);
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
												Device devicepare = deviceRepository.getIdByDeviceType(DeviceType.pdc,
														platform);
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

												throw new HandledException("CHECK_PARAMETERS",
														"Check the special platform no. of TV Display");
											}
										} else {
											throw new HandledException("PORT_ASSIGNED",
													"This is port already assigned ");
										}
									} else {
										throw new HandledException("PDC_NOT_CREATED",
												"PDC for platform number " + platArray[0] + " not created");
									}

								} else {

									throw new HandledException("CHECK_PARAMETERS",
											"Check for the 3rd octate IPaddress of TV Display");

								}

							}

						} else {

							throw new HandledException("CHECK_PARAMETERS",
									"Check for the 4th octate IP address of TV Display ");
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
								commonUtil.updateActivities("Configuration Setting in Interface for CDC Master",
										String.valueOf(customUtil.getIdFromToken()));
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

								throw new HandledException("CHECK_PARAMETERS",
										"Check the 4th octate of IPaddress of CDC Master");
							}

						} else {

							throw new HandledException("CHECK_PARAMETERS",
									"Check the 3rd octate of IPaddress of CDC Master");
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
								commonUtil.updateActivities("Configuration Setting in Interface for CDC Slave",
										String.valueOf(customUtil.getIdFromToken()));
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

								throw new HandledException("CHECK_PARAMETERS",
										"Check for the 4th octate of IPaddress of CDC Slave");
							}

						} else {

							throw new HandledException("CHECK_PARAMETERS",
									"Check for the 3rd octate of IPaddress of CDC Slave");
						}

					} else {

						throw new HandledException("CHECK_PARAMETERS", "No device found");
					}

				} else {

					throw new HandledException("CHECK_PARAMETERS", "Ip address exist");
				}

			} else {

				throw new HandledException("CHECK_PARAMETERS", "Ip address shouldn't contain null values");
			}

		} else {

			throw new HandledException("CHECK_PARAMETERS", "This Port Number is assigned ");

		}

		return deviceMap;
	}

	static String getNbr(String str) {
		// Replace each non-numeric number with a space
		str = str.replaceAll("[^\\d]", " ");
		// Remove leading and trailing spaces
		str = str.trim();
		// Replace consecutive spaces with a single space
		str = str.replaceAll(" +", " ");

		return str;
	}

	@Override
	public HashMap<String, Object> update(Long id, Device deviceDetails) throws HandledException {

		long token = customUtil.getIdFromToken();
		Device device = deviceRepository.findById(id)
				.orElseThrow(() -> new HandledException("NOT_FOUND", "Device not found for this id : " + id));

		String ip = deviceDetails.getIpAddress();
		System.out.println(ip);
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

					throw new HandledException("CHECK_PARAMETERS", "Check the startId to update the CGDB");

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

					throw new HandledException("CHECK_PARAMETERS", "Check the Ip address to update the OVD");

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

					throw new HandledException("CHECK_PARAMETERS", "Check the Ip address to update the IVD");

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

					throw new HandledException("CHECK_PARAMETERS", "Check the Ip address to update the MLDB");

				}

			} else if (type == DeviceType.agdb) {
				if(Integer.parseInt(ipValues(ip)[2])>0) {
				if (Integer.parseInt(ipValues(ip)[3]) > 130 && Integer.parseInt(ipValues(ip)[3]) < 161) {
					device.setIpAddress(deviceDetails.getIpAddress());
					device.setDeviceName(deviceDetails.getDeviceName());
					System.out.println(deviceDetails.getPlatformNo());
					device.setPlatformNo(deviceDetails.getPlatformNo());
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

					throw new HandledException("CHECK_PARAMETERS", "Check the Ip address to update the AGDB");

				}
				}
				else if(Integer.parseInt(ipValues(ip)[2])==0) {
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

						throw new HandledException("CHECK_PARAMETERS", "Check the Ip address to update the AGDB");

					}
				}

			} else if (type == DeviceType.pfdb) {
				if (ipValues(ip)[2].equals("0")) {
					if (Integer.parseInt(ipValues(ip)[3]) > 160 && Integer.parseInt(ipValues(ip)[3]) < 191) {
						String arr[] = {"0"};

						device.setIpAddress(deviceDetails.getIpAddress());
						device.setDeviceName(deviceDetails.getDeviceName());
						device.setBoardType(deviceDetails.getBoardType());
						device.setPlatformNo(arr);
						device.setCreatedBy(String.valueOf(token));

						deviceMap.put("id", device.getId());
						deviceMap.put("ipAddress", device.getIpAddress());
						deviceMap.put("deviceType", device.getDeviceType());
						deviceMap.put("deviceName", device.getDeviceName());
						deviceMap.put("portNumber", device.getPortNumber());
						deviceMap.put("platformNo", device.getPlatformNo());
						deviceMap.put("boardType", device.getBoardType());

					} else {

						throw new HandledException("CHECK_PARAMETERS", "Check the Ip address to update the PFDB");

					}
				} else if (Integer.parseInt(ipValues(ip)[2]) > 0) {
					if (Integer.parseInt(ipValues(ip)[3]) > 160 && Integer.parseInt(ipValues(ip)[3]) < 191) {
						device.setIpAddress(deviceDetails.getIpAddress());
						device.setDeviceName(deviceDetails.getDeviceName());
						device.setBoardType(deviceDetails.getBoardType());
						device.setPlatformNo(deviceDetails.getPlatformNo());
						device.setCreatedBy(String.valueOf(token));

						deviceMap.put("id", device.getId());
						deviceMap.put("ipAddress", device.getIpAddress());
						deviceMap.put("deviceType", device.getDeviceType());
						deviceMap.put("deviceName", device.getDeviceName());
						deviceMap.put("portNumber", device.getPortNumber());
						deviceMap.put("platformNo", device.getPlatformNo());
						deviceMap.put("boardType", device.getBoardType());

					} else {

						throw new HandledException("CHECK_PARAMETERS", "Check the Ip address to update the PFDB");

					}
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

					throw new HandledException("CHECK_PARAMETERS", "Check the Ip address to update the TV DISPLAY");

				}

			}

			else if (type == DeviceType.pdc) {
				String[] platformNumber = device.getPlatformNo();

				if (ipValues(ip)[3].equals("1")) {
					device.setDeviceName(deviceDetails.getDeviceName());
					device.setPlatformNo(deviceDetails.getPlatformNo());
					
					
					
					Device pdc = deviceRepository.getIdByDeviceType(DeviceType.pdc, platformNumber);
					List pdcChildren = pdc.getChildren();
					
					deviceMap.put("id", device.getId());
					deviceMap.put("ipAddress", device.getIpAddress());
					deviceMap.put("deviceType", device.getDeviceType());
					deviceMap.put("deviceName", device.getDeviceName());
					deviceMap.put("portNumber", device.getPortNumber());
					deviceMap.put("platformNo", device.getPlatformNo());
					
					

					if (pdcChildren != null) {

						for (int i = 0; i < pdcChildren.size(); i++) {

							Object obj = pdcChildren.get(i);
							Map myMap = (Map) obj;
							String stringToConvert = String.valueOf(myMap.get("id"));
							Long convertedLong = Long.parseLong(stringToConvert);
							Device delpdc = deviceRepository.findAllById(convertedLong);
							DeviceType devicetype = delpdc.getDeviceType();
							String[] plat= deviceDetails.getPlatformNo();

							if(devicetype.equals(DeviceType.pfdb)|| devicetype.equals(DeviceType.agdb)||devicetype.equals(DeviceType.mldb)||devicetype.equals(DeviceType.tvDisplay)||devicetype.equals(DeviceType.ivd)) {
								String childip = delpdc.getIpAddress();
								String firstOct=ipValues(childip)[0];
								String secondOct=ipValues(childip)[1];
								String thirdOct=ipValues(childip)[2];
								String fourthOct=ipValues(childip)[3];
								String ipAddress=firstOct+"."+secondOct+"."+plat[0]+"."+fourthOct;
								System.out.println(childip);
								System.out.println(ipAddress+" child ip");
								delpdc.setIpAddress(ipAddress);
								deviceRepository.save(delpdc);
								
							}
							 if(devicetype.equals(DeviceType.cgdb)) {
//								System.out.println("fdggfdg 2360");
//								delpdc.setPlatformNo(plat);
//								
//								Device dev=new Device();
//								List<CoachesDetail> coacheslist=delpdc.getCoaches();
//								
////								coacheslist.get(0).setIpAddress(max1);
//							
//								HashMapCoachDetails hashmap=new HashMapCoachDetails();
//								String str= hashmap.convertToDatabaseColumn(coacheslist);
//								HashMap<String, String> map = new HashMap<String, String>();
//								try
//								{
//								JSONArray jsonarray = new JSONArray(str);
//								for(int j=0;j<jsonarray.length();j++)
//								{
//									JSONObject e = jsonarray.getJSONObject(j);
//	                                map.put("coachNo",  String.valueOf("coachNo"));
//	                                map.put("coachName", "coachName :" + e.getString("coachName"));
//	                                map.put("ipAddress", "ipAddress : " +  e.getString("ipAddress"));
//	                                map.put("status", "status : " +  e.getString("status"));
//	                                //System.out.println(map.get("ipAddress"));
//	                                String se=map.get("ipAddress");
//	                                
//									
//								}
//								}
//								catch(Exception e)
//								{
//									System.out.println(e);
//								}
//								
							}
						}

					}
				}

			}

			commonUtil.updateActivities("Updated Configuration Settings in Interface ",
					String.valueOf(customUtil.getIdFromToken()));

		} else {

			throw new HandledException("CHECK_PARAMETERS", "IP Address exist");

		}

		deviceRepository.save(device);
		return deviceMap;

	}

	@Override
	public Map<String, Boolean> deletev1(Long id) throws HandledException {

		Device device = deviceRepository.findById(id)
				.orElseThrow(() -> new HandledException("NOT_FOUND", "Device not found on : " + id));

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
		return response;
	}

	@Override
	public Map<String, Object> deletev2(Long id) throws HandledException {

		Device device = deviceRepository.findById(id)
				.orElseThrow(() -> new HandledException("NOT_FOUND", "Device not found on : " + id));
		int oct3 = 1;

		DeviceType deviceType = device.getDeviceType();
		String[] platformNumber = device.getPlatformNo();
		Device cdsData = deviceRepository.findByDeviceType(DeviceType.cds);
		if (deviceType != DeviceType.cgdb) {
			String ip = device.getIpAddress();
			oct3 = Integer.parseInt(ipValues(ip)[2]);
		}
		if (((deviceType == DeviceType.cdcMaster || deviceType == DeviceType.cdcSlave || deviceType == DeviceType.mldb
				|| deviceType == DeviceType.agdb || deviceType == DeviceType.ivd || deviceType == DeviceType.ovd
				|| deviceType == DeviceType.tvDisplay || deviceType == DeviceType.pfdb) && oct3 == 0)
				|| deviceType == DeviceType.pdc) {

			List cdsChildren = cdsData.getChildren();
			this.deleteChildern(cdsChildren, device);
			System.out.println("inside if");
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
			System.out.println("inside else");

			String[] platfprm = { platformNumber[0] };
			Device pdcData = deviceRepository.getIdByDeviceType(DeviceType.pdc, platfprm);
			List pdcChildren = pdcData.getChildren();
			this.deleteChildern(pdcChildren, device);

		}
		deviceRepository.delete(device);
		Map<String, Object> response = new HashMap<>();

		response.put("id", cdsData.getId());
		response.put("ipAddress", cdsData.getIpAddress());
		response.put("deviceType", cdsData.getDeviceType());
		response.put("deviceName", cdsData.getDeviceName());
		response.put("children", cdsData.getChildren());

		return response;
	}

	@Override
	public ArrayList getCdsStatus() throws HandledException {

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

		} else {

			throw new HandledException("CHECK_PARAMETERS", "CDS has no children");
		}

		return list;

	}

	@Override
	public ArrayList getPdcStatus(String[] platformNo) throws HandledException {

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

				throw new HandledException("CHECK_PARAMETERS", "PFDB & CGDB for this platform doesn't exist");

			}

		} else {

			throw new HandledException("NOT_FOUND", "PDC for this platform doesn't exist.");
		}

		return list;

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

	@Override
	public void setConfiguration() throws HandledException {

		requestWrapper.postRequest(RequestTypes.SET_CONFIGURATION);

	}

}
