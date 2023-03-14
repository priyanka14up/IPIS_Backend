/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Link Status Scheduler
 */
package com.innobitsysytems.ipis.scheduler;

import java.io.IOException;
import java.net.InetAddress;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.innobitsysytems.ipis.model.LinkStatus;
import com.innobitsysytems.ipis.model.device.ChildrenDetails;
import com.innobitsysytems.ipis.model.device.Device;
import com.innobitsysytems.ipis.model.device.DeviceStatus;
import com.innobitsysytems.ipis.model.device.DeviceType;
import com.innobitsysytems.ipis.repository.DeviceRepository;
import com.innobitsysytems.ipis.repository.LinkStatusRepository;
import com.innobitsysytems.ipis.threadmgnt.RequestTypes;
import com.innobitsysytems.ipis.threadmgnt.RequestWrapper;

@Component
public class LinkStatusScheduler {

	@Autowired 
	DeviceRepository deviceRepository;
	
	@Autowired 
	RequestWrapper requestWrapper;
	
	@Autowired 
	LinkStatusRepository linkStatusRepository;
	
@Scheduled(fixedRate = 1000)
    public void pingDevice(){
		
		List<Device> data = deviceRepository.findAll();
		
//		for (int i = 0; i < data.size(); i++){
//			
//			Device deviceData  = data.get(i);
//			
//			//requestWrapper.linkCheck(RequestTypes.OptionalLinkCheck, deviceData);		
//			
//			}	
		
	    
			for(int i=0;i<data.size();i++)
			{
				 InetAddress geek = null;
			        try {
			        	String ip=data.get(i).getIpAddress();
			            geek = InetAddress.getByName(ip);
//			            InetAddress geek1 = InetAddress.getByName("10.10.1.180");

			            Date date = new Date();


			            if (geek.isReachable(5000))
			            {
			            	System.out.println("Host is reachable"+ip);
			        
			            	System.out.println(ip);
			            		
			            			//cdsChildren.get(i).getId();
			            			
			                        data.get(i).setStatus(DeviceStatus.Connected);
			                       
			                        Long linkId=data.get(i).getLinkStatus().getId();
			                        if(linkId!=null)
			                        {
			                        Optional<LinkStatus> linkStatus= linkStatusRepository.findById(linkId);
			                        
			                        	 linkStatus.get().setLinkTime(date);
											linkStatus.get().setResponseTime(date);
											linkStatus.get().setResponse("OK");
											deviceRepository.save(data.get(i));
											linkStatusRepository.save(linkStatus.get());
			                        }
			                       
									
			            		
			            		//all children must be set connected
			       
			            }
			            else
			            {
			            	System.out.println("Sorry ! We can't reach to this host"+data.get(i).getIpAddress());
			            	
			            		
			                        data.get(i).setStatus(DeviceStatus.Disconnected);
			                        //dataChild.setStatus(DeviceStatus.Disconnected);
			                        
			                        Long linkId=data.get(i).getLinkStatus().getId();
			                        if(linkId!=null)
			                        {
			                        Optional<LinkStatus> linkStatus= linkStatusRepository.findById(linkId);
			                        
			                        linkStatus.get().setLinkTime(date);
									linkStatus.get().setResponseTime(date);
									linkStatus.get().setResponse("Fail");
									deviceRepository.save(data.get(i));
									linkStatusRepository.save(linkStatus.get());
			                        }
			 
			              
			            }
			            }
			            
			        
			
			         catch (IOException e) {
			            e.printStackTrace();
			        }
			
			
			
	   
	       
		
		
	
			        }
}
}    
   
	


