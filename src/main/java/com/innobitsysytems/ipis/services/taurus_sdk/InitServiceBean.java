package com.innobitsysytems.ipis.services.taurus_sdk;

import org.springframework.stereotype.Service;

//import beans.DeviceInfo;
import javafx.application.Platform;
//import beans.DeviceInfo;
import javafx.scene.control.Alert;
import novj.platform.vxkit.common.bean.login.LoginResultBean;
import novj.platform.vxkit.common.bean.search.SearchResult;
import novj.platform.vxkit.common.result.DefaultOnResultListener;
import novj.platform.vxkit.common.result.OnResultListenerN;
import novj.platform.vxkit.handy.api.SearchManager;
import novj.platform.vxkit.handy.api.SearchManager.OnScreenSearchListener;
import novj.publ.api.NovaOpt;
import novj.publ.net.exception.ErrorDetail;
//import sample.Contacts;
//import uiexdata.ViewModel;
//import sample.Contacts;
import novj.publ.net.svolley.Request.IRequestBase;

@Service
public class InitServiceBean {

	DeviceInfo deviceInfo = new DeviceInfo();
	Object loginRes = null;

	public void initialize(int platform) {
		// TODO Auto-generated method stub

		NovaOpt.GetInstance().initialize(platform);

	}

	public DeviceInfo searchScreen(String remoteIp, String localIp) {
		NovaOpt.GetInstance().searchScreen(new SearchManager.OnScreenSearchListener() {

			@Override
			public void onSuccess(SearchResult searchResult) {

				deviceInfo.setSn(searchResult.sn);
				deviceInfo.setProductName(searchResult.productName);
				deviceInfo.setAliasName(searchResult.aliasName);
				deviceInfo.setIpAddress(searchResult.ipAddress);
				deviceInfo.setLogined(searchResult.logined);
				deviceInfo.searchResult = searchResult;
				connectDevice(deviceInfo.searchResult);

			}

			@Override
			public void onError(ErrorDetail errorDetail) {
				// TODO Auto-generated method stub
				

			}

		}, remoteIp, localIp);
		return deviceInfo;
	}
	
	


	public void connectDevice(SearchResult searchResult) {

		NovaOpt.GetInstance().connectDevice(searchResult, new DefaultOnResultListener() {
			@Override
			public void onSuccess(Integer response) {

			}

			@Override
			public void onError(ErrorDetail error) {
				// tip

			}
		}, wrapper -> {

		});
	}

	public Object Login(String username, String password) {

		NovaOpt.GetInstance().login(deviceInfo.getSn(), username, password,
				new OnResultListenerN<LoginResultBean, ErrorDetail>() {

					@Override
					public void onError(IRequestBase arg0, ErrorDetail arg1) {
						loginRes = arg1;
					}

					@Override
					public void onSuccess(IRequestBase arg0, LoginResultBean arg1) {
						loginRes = arg1;

					}
				});

		return loginRes;

	}
	
}
