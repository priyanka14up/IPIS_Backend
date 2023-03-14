/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: To communicate with Thread
 */
package com.innobitsysytems.ipis.threadmgnt;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import com.innobitsysytems.ipis.model.onlineTrain.OnlineTrain;
import com.innobitsysytems.ipis.model.setup.StationDetails;
import com.innobitsysytems.ipis.repository.OnlineTrainRepository;
import com.innobitsysytems.ipis.repository.setup.StationDetailsRepository;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.Message;
import com.innobitsysytems.ipis.model.device.Device;
import com.innobitsysytems.ipis.model.device.DeviceType;

@Component
public class RequestWrapper {
	
	@Autowired
	StationDetailsRepository stationDetailRepo;
	
	@Autowired
	OnlineTrainRepository onlineTrainRepository;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	//Set Configuration
	public void postRequest(Enum url) {
		
		String urlStr = url.toString();
		CommThread t1 = new CommThread(urlStr);
		applicationContext.getAutowireCapableBeanFactory().autowireBean(t1);
		t1.start();
			
		}
	
	//Default Message
	public void postRequest(String data, Enum url, String boardType, String platform, String speed, String effect, String letterSize,int gap,int timeDelay) {
		System.out.println(url);
		String urlStr = url.toString();
		CommThread t1 = new CommThread(data, urlStr, boardType, platform, speed, effect, letterSize,gap,timeDelay);
		applicationContext.getAutowireCapableBeanFactory().autowireBean(t1);
		t1.start();
		
	}
	
	//Default Message for CGDB
	public void postRequest(String[] data, Enum url, String boardType, String platformNo, String speed, String effect, String letterSize,int gap,int timeDelay) {
		System.out.println("in post request of CGDB");
		String urlStr = url.toString();
		
		CommThread t1 = new CommThread(data, urlStr, boardType, platformNo, speed, effect, letterSize, gap,timeDelay);
		
		applicationContext.getAutowireCapableBeanFactory().autowireBean(t1);
		
				t1.start();
			
		}

	// To set Message
	public void postRequest(Message messageDetails, Enum url, String displayBoard, String platform, String deviceId, String speed, String effect,String letterSize,int gap,int timeDelay) {

		String urlStr = url.toString();
		CommThread t1 = new CommThread(messageDetails, urlStr, displayBoard, platform, deviceId, speed, effect, letterSize,gap,timeDelay);
		applicationContext.getAutowireCapableBeanFactory().autowireBean(t1);
		t1.start();
		
	}

	
	// To Set Intensity (manual)all
	public void postRequest(Integer data, Enum url, String mode, String intensityMode) {

		String urlStr = url.toString();
		CommThread t1 = new CommThread(data, urlStr, mode, intensityMode);
		applicationContext.getAutowireCapableBeanFactory().autowireBean(t1);
		t1.start();
		
	}
	
	// To Set Intensity (manual)single
		public void postRequest(Integer intensity,Enum url,String device,String deviceId,String platform,String mode,String intensityMode) {

			String urlStr = url.toString();
			CommThread t1 = new CommThread(intensity,urlStr,device,deviceId,platform,mode,intensityMode);
			applicationContext.getAutowireCapableBeanFactory().autowireBean(t1);
			t1.start();
			
		}
	
	
	// To set Intensity (Auto) new
	public void postRequest(Integer data, Enum url,String getIntensityMode) {
		
		String urlStr = url.toString();
		CommThread t1 = new CommThread(data, urlStr,getIntensityMode);
		applicationContext.getAutowireCapableBeanFactory().autowireBean(t1);
		t1.start();
		
	}
	
	// For TADB
	public void tadb(List<OnlineTrain> onlineTrain, Enum url) {
			System.out.println("in request wrapper");
			String urlStr = url.toString();
			CommThread t1 = new CommThread(onlineTrain, urlStr);
			applicationContext.getAutowireCapableBeanFactory().autowireBean(t1);
			t1.start();
			
		}
		
	// For CGS
	public void cgs(List<OnlineTrain> onlineTrain, Enum url) {
			
			String urlStr = url.toString();
			CommThread t1 = new CommThread(onlineTrain, urlStr);
			applicationContext.getAutowireCapableBeanFactory().autowireBean(t1);
			t1.start();
					
		}	
	
	// For Link Check
	public void linkCheck(Enum url, Device device) {
		
		String urlStr = url.toString();
		CommThread t1 = new CommThread( urlStr, device);
		applicationContext.getAutowireCapableBeanFactory().autowireBean(t1);
		t1.start();
		
	   }
	
	// For CGDB
	public void cgdb(Enum url, List<OnlineTrain> onlineTrainCgdb, String speed, String letterSize, String effectCode,int gap,int timeDelay ) {
	
		String urlStr = url.toString();
		CommThread t1 = new CommThread( urlStr, onlineTrainCgdb, speed, letterSize, effectCode,gap,timeDelay);
		applicationContext.getAutowireCapableBeanFactory().autowireBean(t1);
		t1.start();
	}

	
	// For software request
		public void softResetRequest(String boardType, Enum url) {
				
				String urlStr = url.toString();
				CommThread t1 = new CommThread(boardType, urlStr);
				applicationContext.getAutowireCapableBeanFactory().autowireBean(t1);
				t1.start();
				
			}
			
		// For get CONfiguration request
				public void getConfigRequest(String boardType,String platform,String deviceId,Enum url) {
						
						String urlStr = url.toString();
						CommThread t1 = new CommThread(boardType,platform,deviceId,urlStr);
						applicationContext.getAutowireCapableBeanFactory().autowireBean(t1);
						t1.start();
						
					}
	
	}
	


	