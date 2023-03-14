package com.innobitsysytems.ipis.services.taurus_sdk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import novj.platform.vxkit.common.bean.TransferInfo;
import novj.platform.vxkit.common.bean.TransferInfo.ProgramInfos;
import novj.platform.vxkit.common.result.DefaultOnResultListener;
import novj.platform.vxkit.common.result.OnResultListener;
import novj.platform.vxkit.common.result.OnResultListenerN;
import novj.platform.vxkit.handy.api.ProgramSendManager;
import novj.platform.vxkit.handy.api.ProgramSendManager.OnProgramTransferListener;
import novj.publ.api.NovaOpt;
import novj.publ.api.actions.ProgramManager;
import novj.publ.net.exception.ErrorDetail;
import novj.publ.net.svolley.Request.IRequestBase;
import novj.publ.api.beans.ProgramInfoBean;
@Service
public class ProgramManagementServiceBean {
	String depletePlayListMsg;
	String getProgramInfoMsg;
	String transferMessage;

	public String startTransfer(String deviceSn, String sendProgramFilePath, String programName,String deviceIdentifier) {

		try {
			NovaOpt.GetInstance().startTransfer(deviceSn, sendProgramFilePath, programName, deviceIdentifier, true, new ProgramSendManager.OnProgramTransferListener() {

						@Override
						public void onStarted() {
							transferMessage = "Transfer start";

						}

						@Override
						public void onTransfer(long arg0, long arg1) {

							transferMessage = "Publishing ";

						}

						@Override
						public void onAborted() {
							transferMessage = "Publishing declined";
						}

						@Override
						public void onCompleted() {
							transferMessage = "Transfer Completed";

						}

						@Override
						public void onError(ErrorDetail errorDetail) {
							transferMessage = "Failed to publish:" + " " + errorDetail.description;

						}
						

					});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return transferMessage;
	}

	
	public String deletePlayList(String sn,String programBeanName, String deviceIdentifier) {

		ProgramInfoBean bean = new ProgramInfoBean();
		bean.setName(programBeanName);
		bean.setIdentifier(deviceIdentifier);
		List<ProgramInfoBean> beans = new ArrayList<>();
		beans.add(bean);

		NovaOpt.GetInstance().deletePlayList(Contacts.DEVICE_SN, beans,new OnResultListenerN() {
				
				
					@Override
					public void onSuccess(IRequestBase response, Object arg1) {
						depletePlayListMsg = "successfully delete playback:" + response;

					}

					@Override
					public void onError(IRequestBase response, Object arg1) {
						depletePlayListMsg = "failed to delete playback:" + response;

					}

				});
		return depletePlayListMsg;
	}

	/*
	 * obtaining playlist
	 */

	@SuppressWarnings("unchecked")
	public String getProgramInfo(String deviceSn) {
		
	
	 NovaOpt.GetInstance().getProgramInfo(deviceSn,  (OnResultListenerN<ProgramInfos, ErrorDetail>) new OnResultListener<TransferInfo.ProgramInfos, ErrorDetail>() {
				@Override
				public void onSuccess(TransferInfo.ProgramInfos response) {
					getProgramInfoMsg="obtaining program info success";
				}

				@Override
				public void onError(ErrorDetail errorDetail) {
					

					
					getProgramInfoMsg="obtaining program info failed";
					
					
				}
				});
	 	return getProgramInfoMsg;
	
		}
	
	
		/*
		 * parsing solution
		 */
	
	public int readProgram(String programpath) {
			 return ProgramManager. getInstance ().readProgram(programpath);
			 
	}
	
	
	
	
	}
