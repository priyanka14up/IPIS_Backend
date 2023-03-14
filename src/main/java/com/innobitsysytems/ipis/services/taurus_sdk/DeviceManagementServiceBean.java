package com.innobitsysytems.ipis.services.taurus_sdk;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import novj.platform.vxkit.common.bean.BrightnessAdjustPolicy;
import novj.platform.vxkit.common.bean.AdjustModeInfo;
import novj.platform.vxkit.common.bean.BrightnessAdjustBean;
import novj.platform.vxkit.common.bean.BrightnessAdjustPolicyEntity;
import novj.platform.vxkit.common.bean.FirmwareInfo;
import novj.platform.vxkit.common.bean.RatioBean;
import novj.platform.vxkit.common.bean.SourceBean;
import novj.platform.vxkit.common.bean.TimeZoneParam;
import novj.platform.vxkit.common.bean.TimingAdjustBrightnessArgument;
import novj.platform.vxkit.common.bean.version.SoftwareBean;
import novj.platform.vxkit.common.bean.version.SoftwareBean.SoftwareResult;
import novj.platform.vxkit.common.result.DefaultOnResultListener;
import novj.platform.vxkit.common.result.OnResultListener;
import novj.platform.vxkit.common.result.OnResultListenerN;
import novj.platform.vxkit.handy.api.ScreenShotManager;
import novj.platform.vxkit.handy.net.transfer.OnFileTransferListener;
import novj.publ.api.NovaOpt;
import novj.publ.net.exception.ErrorDetail;
import novj.publ.net.svolley.Request.IRequestBase;

@Service
public class DeviceManagementServiceBean {


	
	private String downLoadScreenshotMsg;
	private String obtainingAmbientBrightness;
	private String settingBrightnessAdjustModeMsg;
	private String getingBrightnessAdjustModeMsg;
	private String rebootMsg;
	private String gettInstallVersionMsg;
	private String availableMemoryDataMsg;
	private String obtainScreenBrightnessMsg;
	private String settingScreenBrightnessMsg;
	private String setBrightnessPolicyMsg;
	private String getBrightnesssPolicyMsg;
	private String calibrateTimeMsg;
	

	
	
	public String reboot(String deviceSn) {
		
		NovaOpt.GetInstance().reboot(deviceSn,new OnResultListenerN() {
			@Override
			public void onError(IRequestBase response, Object arg1) {
				rebootMsg="faile to reboot"+response;
				
			}

			@Override
			public void onSuccess(IRequestBase response, Object arg1) {
				rebootMsg="reboot  successfully"+response;

			}});
			return rebootMsg;

		
	}

	

	public String getInstalledPackageVersions(String deviceSn, String[] packageNames) {
		
		
		  NovaOpt.GetInstance().getInstalledPackageVersions(deviceSn, packageNames, new OnResultListenerN<List<SoftwareResult>, ErrorDetail>() {

			@Override
			public void onError(IRequestBase arg0, ErrorDetail errorDetail) {
				gettInstallVersionMsg="obtaining installed package vesion failed"+errorDetail.toString();

			}

			@Override
			public void onSuccess(IRequestBase arg0, List<SoftwareResult> result) {
				gettInstallVersionMsg="obtaining installed package vesion succeeded"+result.toString();

			}

			 
		 });
		  return gettInstallVersionMsg;
	}
	
	@SuppressWarnings("unchecked")
	public String getAvailableMemoryData(String deviceSn) {
		NovaOpt.GetInstance().getAvailableMemoryData(deviceSn,
				(OnResultListenerN<Float, ErrorDetail>) new OnResultListener<Float, ErrorDetail>() {

					@Override
					public void onError(ErrorDetail errorDetail) {
						availableMemoryDataMsg="obtaining Availeable Memory data failed"+errorDetail.toString();

					}

					@Override
					public void onSuccess(Float responce) {
						availableMemoryDataMsg="obtaining Availeable Memory data succeeded"+responce.toString();

					}
				});
		return availableMemoryDataMsg;
	}

	/*
	 * download Screenshot
	 */
	public String downLoadScreenshot(String deviceSn, int width, int height, String downLoadDirectoryPath, String pictureName) {
	 NovaOpt.GetInstance().downLoadScreenshot(deviceSn, width, height,
				ScreenShotManager.PictureType.PICTURE_PNG, downLoadDirectoryPath, pictureName,
				new OnFileTransferListener() {

					

					@Override
					public void onSuccess(Integer response) {
						 downLoadScreenshotMsg="Screenshot download success";

					}

					@Override
					public void onStartTransfer() {
						 downLoadScreenshotMsg="Transfer started";

					}

					@Override
					public void onTransferred(long arg0) {
						downLoadScreenshotMsg="Transfer success";
					}
					
					@Override
					public void onError(ErrorDetail errorDetail) {
						downLoadScreenshotMsg="error in downloading screen shot";

					}
				});
	 return downLoadScreenshotMsg;
	}

	/*
	 * Obtaining the ambient brightness of screens
	 */

	public String getEnvironmentBrightness(String deviceSn) {
	 NovaOpt.GetInstance().getEnvironmentBrightness(deviceSn,new OnResultListenerN() {
				
					@Override
					public void onError(IRequestBase arg0, Object arg1) {
						// TODO Auto-generated method stub
						obtainingAmbientBrightness="brightness obtained successfully";

					}

					@Override
					public void onSuccess(IRequestBase arg0, Object arg1) {
						// TODO Auto-generated method stub
						obtainingAmbientBrightness="brightness obtained successfully";

					}
				});
	 return obtainingAmbientBrightness;

	}

	/*
	 * Setting brightness adjustment mode
	 */

	public String setBrightnessAdjustMode(String deviceSn, AdjustModeInfo modeInfo, DefaultOnResultListener listener) {
		
		 NovaOpt.GetInstance().setBrightnessAdjustMode(deviceSn,new AdjustModeInfo("SCREEN_BRIGHTNESS", new SourceBean(0, 1), "AUTO"),(OnResultListenerN) new DefaultOnResultListener() {
					@Override
					public void onSuccess(Integer response) {
						settingBrightnessAdjustModeMsg="success Brightness adjustment mode:" + response;
					}

					@Override
					public void onError(ErrorDetail error) {
						settingBrightnessAdjustModeMsg="error Brightness adjustment mode:" + error.toString();
					}
				});
		 return settingBrightnessAdjustModeMsg;

	}
	
	
	/*
	 * getting brightness adjustment mode
	 */	

	public String getBrightnessAdjustMode(String deviceSn ) {
		
		NovaOpt.GetInstance().getBrightnessAdjustMode(deviceSn,new OnResultListenerN<AdjustModeInfo, ErrorDetail>() {

		@Override
		public void onError(IRequestBase arg0, ErrorDetail errorDetail) {
			getingBrightnessAdjustModeMsg="Error  in obtaining adjust mode  "+errorDetail;		

		}
		@Override
		public void onSuccess(IRequestBase arg0, AdjustModeInfo argadjAdjustModeInfo) {
			getingBrightnessAdjustModeMsg="screen bringhtness adjust successfully "+ argadjAdjustModeInfo.toString();

		}});
		return getingBrightnessAdjustModeMsg;
		
		
	}
	
	/*
	 * Obtaining brightness
	 */

	public String getScreenBrightness(String deviceSn) {

		 NovaOpt.GetInstance().getScreenBrightness(deviceSn,new OnResultListenerN<RatioBean<Float>, ErrorDetail>() {
					@Override
					public void onError(IRequestBase arg0, ErrorDetail error) {
						obtainScreenBrightnessMsg="fail Obtain brightness" + error;
						
					}

					@Override
					public void onSuccess(IRequestBase response, RatioBean<Float> ratio) {
						obtainScreenBrightnessMsg="success Obtain brightness:" + ratio;
						
					}
				});
		return obtainScreenBrightnessMsg;
	}
	
	
	
	/*
	 * setting Screen Brightness
	 */	
	
	public String  setScreenBrightness(String deviceSn, float ratio) {
		 NovaOpt.GetInstance().setScreenBrightness(deviceSn,ratio, new OnResultListenerN() {


			@Override
			public void onError(IRequestBase response, Object arg1) {
				settingScreenBrightnessMsg="fail to set screen Brightness"+response;
				
			}

			@Override
			public void onSuccess(IRequestBase response, Object arg1) {
				settingScreenBrightnessMsg="Successfully set screen Brightness"+response;
				
			}});
		return settingScreenBrightnessMsg;
	
	}
	
	
	
	/*
	 * setting brightness policy
	 */	
	public IRequestBase setBrightnessPolicy(String deviceSn) {
		
		List<BrightnessAdjustPolicy> policyList = new ArrayList<>();
		TimingAdjustBrightnessArgument timingArgument = new TimingAdjustBrightnessArgument(70);
		BrightnessAdjustPolicy policy = new BrightnessAdjustPolicy();
		policy.argument = timingArgument;
		policy.enable = true;
		policy.repeat = 127;
		policy.startMilliseconds = System.currentTimeMillis();
		
		policyList.add(policy);
		BrightnessAdjustPolicyEntity entity = new BrightnessAdjustPolicyEntity(true,policyList);
		
		return NovaOpt.GetInstance().setBrightnessPolicy( deviceSn, entity,new OnResultListenerN() {

			@Override
			public void onError(IRequestBase response, Object arg1) {
				setBrightnessPolicyMsg="failed Brightness adjustment policy: "+response;
				
			}

			@Override
			public void onSuccess(IRequestBase response, Object arg1) {
				setBrightnessPolicyMsg="success Brightness adjustment policy: "+response;
				
			}
		
	});
	
	}
	
	
	/*
	 * obtaining brightness policy
	 */	
	
	public String getBrightnessPolicy(String deviceSn, OnResultListener<BrightnessAdjustPolicyEntity, ErrorDetail> listener) {
		
	NovaOpt.GetInstance().getBrightnessPolicy(deviceSn, new OnResultListenerN<BrightnessAdjustBean, ErrorDetail>() {


			@Override
			public void onError(IRequestBase arg0, ErrorDetail arg1) {
				getBrightnesssPolicyMsg="failed to get Brightness policy";
				
			}

			@Override
			public void onSuccess(IRequestBase arg0, BrightnessAdjustBean brightnessAdjustPolicyEntity) {
				getBrightnesssPolicyMsg="success to get Brightness policy" + brightnessAdjustPolicyEntity;
				
			}
		});
		return getBrightnesssPolicyMsg;
	}
	
	
	public String calibrateTime(String deviceSn, TimeZoneParam timeZone) {
		
	 NovaOpt.GetInstance().calibrateTime(deviceSn, timeZone, new OnResultListenerN<TimeZoneParam, ErrorDetail>(){
		

		@Override
		public void onError(IRequestBase arg0, ErrorDetail erroDetail) {
			// TODO Auto-generated method stub
			calibrateTimeMsg="Time Synchronization failed"+erroDetail;

		}

		@Override
		public void onSuccess(IRequestBase arg0, TimeZoneParam timeZoneParam) {
			
			calibrateTimeMsg="Time Synchronization succeeded"+timeZoneParam;
			
		}
	});
	return calibrateTimeMsg;
	}
	
	
	
}
