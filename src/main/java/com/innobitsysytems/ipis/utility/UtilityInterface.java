/**
 * Name: Amit Yadav
 * Copyright: Innobit Systems, 2021
 * Purpose: Utility Interface
 */
package com.innobitsysytems.ipis.utility;

import java.util.List;

import com.innobitsysytems.ipis.model.Message;
import com.innobitsysytems.ipis.model.device.Device;
import com.innobitsysytems.ipis.model.onlineTrain.OnlineTrain;

public interface UtilityInterface {
    
    public void sendCommand( Device device, String command);
    public void sendCommand( String ip, String command);
    public void sendConfiguration(List <Device> device, int data);
    public void sendConfiguration(List <Device> device);

	public void sendCgdbData(Device device, String trainNo, String[] coachDetails, String speed, String letterSize, String effectCode);
	
  
    public void sendMessage(Device device, String data, String speed, String effect, String letterSize);
    
    public void displayTadb(List<Device> device, List<OnlineTrain> onlinetrain, String speed, String effect, String letterSize,int gap);
    public void displayTadb(List<Device> device, List<OnlineTrain> onlinetrain, String speed, String effect, String letterSize);

    public void sendCgdbData(Device device, String trainNo, String[] coachDetails, String speed, String letterSize,
            String effectCode, int gap);

	void sendConfiguration(List<Device> device, int data, int intDisplayTimeMldb);

	void sendPackage(String ipAddress, int intensity, int intDisplayTimePfdb);

	void sendConfigurationManualSingle(String ipAddress, int intensity, int intDisplayTimeMldb);

	void sendMessage(String ipAddress, String data, String speed, String effect, String letterSize, int gap);

	void sendDefaultData(List<Device> device, String data, String speed, String effect, String letterSize, int gap);

	void sendDefaultData(List<Device> device, String[] data, String speed, String effect, String letterSize);

	void sendDefaultData(Device device, String[] data, String speed, String effect, String letterSize);

	void sendDefaultData(List<Device> device, String data, String speed, String effect, String letterSize);

	void displayTadb(List<Device> device, List<OnlineTrain> onlineTrain, String speed, String effect, String letterSize,
			int gap, int timeDelay);

	
	public void sendCgdbData(Device device, String trainNo, String[] coachDetails, String speed, String letterSize,
            String effectCode, int gap,int timeDelay);

	void sendMessage(String ipAddress, Message messageDetails, String speed, String effect, String letterSize, int gap);

	void sendMessage(String ipAddress, Message messageDetails, String speed, String effect, String letterSize, int gap,
			int timeDelay);

	void sendDefaultData(List<Device> device, String data, String speed, String effect, String letterSize, int gap,
			int timeDelay);

	
    
}
