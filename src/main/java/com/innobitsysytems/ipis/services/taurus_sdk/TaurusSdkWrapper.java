package com.innobitsysytems.ipis.services.taurus_sdk;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.innobitsysytems.ipis.dto.DisplayDto;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.device.Device;
import com.innobitsysytems.ipis.model.tvdisplay.Display;
import com.innobitsysytems.ipis.repository.tvdisplay.DisplayRepository;

import novj.platform.vxkit.common.bean.VideoControlInfo;
import novj.platform.vxkit.common.bean.VideoTimingPolicy;
import novj.platform.vxkit.common.bean.programinfo.PageItem;
import novj.platform.vxkit.common.bean.programinfo.Widget;
import novj.platform.vxkit.common.result.DefaultOnResultListener;
import novj.publ.api.NovaOpt;
import novj.publ.net.exception.ErrorDetail;

@Service
public class TaurusSdkWrapper {

	ProgramMakeServiceBean programService = new ProgramMakeServiceBean();
	ProgramManagementServiceBean programManagementService = new ProgramManagementServiceBean();
	PlayControlServiceBean playControlService = new PlayControlServiceBean();
	DeviceManagementServiceBean deviceManagementService = new DeviceManagementServiceBean();

	DeviceInfo deviceInfo = new DeviceInfo();
	Device device = new Device();

	@Value("${spring.username}")
	private String username;

	@Value("${spring.password}")
	private String password;

	@Value("${spring.pageId}")
	private int pageId;

	@Value("${spring.widgetId}")
	private int widgetId;

	private String outPutPath=System.getProperty("user.home");

	private PageItem pageItem = new PageItem();

	private String deviceSn = deviceInfo.getSn();

	private String sendProgramFilePath=System.getProperty("user.home");

	@Value("${spring.programName}")
	private String programName;

	@Value("${spring.deviceIdentifier}")
	private String deviceIdentifier;

	@Value("${spring.packageNames}")
	private String[] packageNames;

	
	private String downloadDirectoryPath=System.getProperty("user.home")+"/Downloads";

	@Value("${spring.ratio}")
	private float ratio;

	@Value("${spring.progrmaName}")
	private String progrmaName;

	private double programWidth = 100.00;

	private double programHeight = 100.00;

	@Autowired
	public DisplayRepository displayRepository;

	Widget widget = new Widget();

	InitServiceBean initService = new InitServiceBean();

	public void start(List<Device> device) throws HandledException {
		try {

			initService.initialize(2);
			for (int i = 0; i < device.size(); i++) {
				String localIp = null;
				String remoteIp = device.get(i).getIpAddress();
				DeviceInfo deviceinfo = initService.searchScreen(remoteIp, localIp);

			}

			initService.Login(username, password);

		} catch (Exception e) {

			throw new HandledException("Initialization ", "Exception " + e);

		}

	}

	/*
	 * programMake
	 */

	public void ProgramMake() throws HandledException {

		try {
			List<Display> listOfDisplay = displayRepository.findAll();

			int numberOfDisplay = listOfDisplay.size();

			programService.createProgram(progrmaName, programWidth, programHeight);

			programService.addPage(pageItem);

			for (int i = 0; i < numberOfDisplay; i++) {

				System.out.println("135");

				if (listOfDisplay.get(i).getEnableDisplay()) {
					programService.addWidget(pageId);
				}
			}

			programService.getPageItem(pageId);

			programService.setPageParam(pageId);

			programService.getWidgetParam(pageId, widgetId);

			programService.setWidgetParam(pageId, widgetId, widget);
			
			programService.makeProgram(outPutPath);

		} catch (Exception e) {
			throw new HandledException("program make ","Exception at program make", e);
		}

	}

	/*
	 * program management
	 */

	public void startTransfer() throws HandledException {

		try {

			programManagementService.startTransfer(deviceSn, sendProgramFilePath, programName, deviceIdentifier);

		} catch (Exception e) {

			throw new HandledException("program Management", "Program Management Exception", e);
		}
	}

	public void getProgramInfo() throws HandledException {
		try {
			programManagementService.getProgramInfo(deviceSn);

		} catch (Exception e) {

			throw new HandledException("program Management", "program info exception", e);

		}
	}

	public void deletePlayList() throws HandledException {
		try {
			programManagementService.deletePlayList(deviceSn, programName, deviceIdentifier);

		} catch (Exception e) {

			throw new HandledException("program Management", "program info exception", e);

		}
	}

	/*
	 * Play Controll
	 */

	public void startPlay() throws HandledException {
		try {

			playControlService.startPlay(deviceSn, deviceIdentifier);

		} catch (Exception e) {

			throw new HandledException("playback controll Exception ", "start Play Exception " + e.toString(), e);
		}
	}

	public void stopPlay() throws HandledException {
		try {
			playControlService.stopPlay(deviceSn, deviceIdentifier);
		} catch (Exception e) {
			throw new HandledException("play controll Exception ", "stop play controll excetion", e);

		}

	}

	public void pausePlay() throws HandledException {
		try {
			playControlService.pausePlay(deviceSn, deviceIdentifier);
		} catch (Exception e) {
			throw new HandledException("paly control Exception ", "pause paly exception", e);

		}

	}
	

	public void resumePlay() throws HandledException {
		try {
			playControlService.resumePlay(deviceSn, deviceIdentifier);
		} catch (Exception e) {
			throw new HandledException("paly control Exception ", "resume paly exception", e);

		}

	}

	/*
	 * device Management
	 */
	public void reboot() throws HandledException {
		try {
			deviceManagementService.reboot(deviceSn);

		} catch (Exception e) {
			throw new HandledException("Device Management", e.toString(), e);

		}
	}

	public void getInstalledPackageVersions() throws HandledException {
		try {
			deviceManagementService.getInstalledPackageVersions(deviceSn, packageNames);

		} catch (Exception e) {
			throw new HandledException("Device Management", " exception in getting install package version"+e.toString(), e);

		}
	}

	public void downLoadScreenshot() throws HandledException {
		try {
			deviceManagementService.downLoadScreenshot(deviceSn, widgetId, pageId, downloadDirectoryPath,deviceIdentifier);

		} catch (Exception e) {
			throw new HandledException("Device Management", " exception in downloadingScreenShot "+e.toString(), e);

		}
	}

	public void getEnvironmentBrightness() throws HandledException {
		try {
			deviceManagementService.getEnvironmentBrightness(deviceSn);

		} catch (Exception e) {
			throw new HandledException("Device Management", " exception in obtaining  Ambient Brightness", e);

		}
	}

	public void getBrightnessAdjustMode() throws HandledException {
		try {
			deviceManagementService.getBrightnessAdjustMode(deviceSn);

		} catch (Exception e) {
			throw new HandledException("Device Management", " exception in obtaining  Ambient Brightness", e);

		}
	}

	public void setScreenBrightness() throws HandledException {
		try {
			deviceManagementService.setScreenBrightness(deviceSn, ratio);

		} catch (Exception e) {
			throw new HandledException("Device Management", " exception in obtaining  Ambient Brightness", e);

		}
	}

	public void getScreenBrightness() throws HandledException {
		try {
			deviceManagementService.getScreenBrightness(deviceSn);

		} catch (Exception e) {
			throw new HandledException("Device Management", " exception in obtaining  Ambient Brightness", e);

		}
	}

	public void setBrightnessPolicy() throws HandledException {
		try {
			deviceManagementService.setBrightnessPolicy(deviceSn);

		} catch (Exception e) {
			throw new HandledException("Device Management", " exception in setting Brightness policy", e);

		}
	}
	
	

}
