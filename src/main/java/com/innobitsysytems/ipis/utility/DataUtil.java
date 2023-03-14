/**
 * Name: Amit Yadav
 * Copyright: Innobit Systems, 2021
 * Purpose: Data Util
 */
package com.innobitsysytems.ipis.utility;

import com.innobitsysytems.ipis.model.device.Device;
import com.innobitsysytems.ipis.model.device.DeviceType;
import com.innobitsysytems.ipis.repository.DeviceRepository;
import com.innobitsysytems.ipis.services.ResponseService;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.awt.Color;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.stereotype.Component;

@Component
public class DataUtil {
	private static final Logger logger = LoggerFactory.getLogger(DataUtil.class);

    @Autowired
    private DeviceRepository deviceRepo;

    String[] platform = { "0" };

    public static byte[] stringToByte(String data) {

        byte[] b = data.getBytes(StandardCharsets.UTF_16);

        return b;

    }

    public static int stringToInt(String param) {

        return Integer.parseInt(param);

    }

    public int getSAMSB() {

        int temp = 0;
        try {
            InetAddress ip;
            String hostname;

            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
            String ipstr = ip.toString();
            System.out.println("ip address of source"+ipstr);
            String ipstrarr[] = ipstr.split("/");
            
            String[] ipArray = ipstrarr[1].split("[, . ']+");

            temp = DataUtil.hexToInt(decimalToHex(ipArray[2]));

            System.out.println("third octet   =" + temp);

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        return temp;

    }

    public int getSALSB() {

        int temp = 0;
        Device device = deviceRepo.getIdByDeviceType(DeviceType.cdcMaster, platform);

        try {
            InetAddress ip;
            String hostname;

            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
            String ipstr = ip.toString();
            System.out.println("ip adresssssss" + ipstr);
            String ipstrarr[] = ipstr.split("/");
            System.out.println(ipstrarr);
            // ipADD=ipstrarr[1];

            String[] ipArray = ipstrarr[1].split("[, . ']+");

            temp = DataUtil.hexToInt(decimalToHex(ipArray[3]));

            DataUtil.hexToInt(decimalToHex(ipArray[3]));
            System.out.println("fourth octet   =" + temp);

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        System.out.println(temp + "temp 148");

        return temp;

    }

    public String getIntensityLevel(String param) {
    	
        return this.decimalToHex(param);
        
        
        

    }

    public String decimalToHex(String param) {

        return "0x" + Integer.toHexString(DataUtil.stringToInt(param));

    }

    public String decimalToHex(int param) {

        return "0x" + Integer.toHexString(param);

    }
    
    public String decimalToHex1(byte param) {

    	//Integer.toHexString(param & 0xFF);
    	String hex=Integer.toHexString(param & 0xFF);
    	if(hex.length()<2)
    	{
    		 hex = "0" + hex;
    	}
    	  return "0x"+hex;

    	
    }

    
    public int[] colorConvert(String colorStr) {

        Color tempColor = this.hex2Rgb(colorStr);

        String red = "0x" + Integer.toHexString(tempColor.getRed());
        String green = "0x" + Integer.toHexString(tempColor.getGreen());
        String blue = "0x" + Integer.toHexString(tempColor.getBlue());

     

        int[] finalData = new int[] { hexToInt(red), hexToInt(green), hexToInt(blue) };
        return finalData;
    }

    public Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf(colorStr.substring(1, 3), 16),
                Integer.valueOf(colorStr.substring(3, 5), 16),
                Integer.valueOf(colorStr.substring(5, 6), 16)); // (5,7) does not return anything
    }

    public static int hexToInt(String param) {

        return Integer.decode(param);

    }

    public static int getDeviceHeader(String type) {

        int header = 0;

        if (type.equals(DeviceType.pdc.toString())) {

            header = 0x01;

        } else if (type.equals(DeviceType.cgdb.toString())) {

            header = 0x02;

        } else if (type.equals(DeviceType.ivd.toString())) {

            header = 0x06;

        } else if (type.equals(DeviceType.mldb.toString())) {

            header = 0x05;

        } else if (type.equals(DeviceType.pfdb.toString())) {

            header = 0x03;

        } else if (type.equals(DeviceType.ovd.toString())) {

            header = 0x07;

        } else if (type.equals(DeviceType.agdb.toString())) {

            header = 0x04;

        } else if (type.equals(DeviceType.cdcMaster.toString())) {

            header = 0x00;

        }

        return header;
    }
    public static int getCommandPacketHeaderForColorStatus(String status,String arrDep) {
        int packetHeaderForColorStatus = 0;
        if (status.equals("Running Right Time") && arrDep.equals("A"))  packetHeaderForColorStatus = 0x01;
        else if (status.equals("Will Arrive Shortly") && arrDep.equals("A")) packetHeaderForColorStatus = 0x02;
        else if (status.equals("Is Arriving On") && arrDep.equals("A")) packetHeaderForColorStatus = 0x03;
        else if (status.equals("Has Arrived On") && arrDep.equals("A"))  packetHeaderForColorStatus = 0x04;
        else if (status.equals("Running Late") && arrDep.equals("A"))  packetHeaderForColorStatus = 0x05;
        else if (status.equals("Cancelled") && arrDep.equals("A") )  packetHeaderForColorStatus = 0x06;
        else if (status.equals("Indefinite Late") && arrDep.equals("A")) packetHeaderForColorStatus = 0x07;
        else if (status.equals("Terminated") && arrDep.equals("A")) packetHeaderForColorStatus = 0x08;
        else if (status.equals("Platform Change") && arrDep.equals("A")) packetHeaderForColorStatus = 0x0A;
        else if (status.equals("Running Right Time") && arrDep.equals("D"))  packetHeaderForColorStatus = 0x0B;
        else if (status.equals("Cancelled") && arrDep.equals("D"))  packetHeaderForColorStatus = 0x0C;
        else if (status.equals("Is Ready To Leave") && arrDep.equals("D"))  packetHeaderForColorStatus = 0x0D;
        else if (status.equals("Is On Platform") && arrDep.equals("D"))  packetHeaderForColorStatus = 0x0E;
        else if (status.equals("Has Left") && arrDep.equals("D")) packetHeaderForColorStatus = 0x0F;
        else if (status.equals("Rescheduled") && arrDep.equals("D")) packetHeaderForColorStatus = 0x10;
        else if (status.equals("Diverted") && arrDep.equals("D")) packetHeaderForColorStatus = 0x11;
        else if (status.equals("Scheduled departure")) packetHeaderForColorStatus = 0x12;
        else if (status.equals("Platform change") && arrDep.equals("D")) packetHeaderForColorStatus = 0x13;
        return packetHeaderForColorStatus;
    }
    
    
    
    public static int getCommandPacketHeader(String type) {

        int packetHeader = 0;

        if (type.equals("OptionalLinkCheck")) {

            packetHeader = 0x80;

        }
        else if (type.equals("Data Transfer")) {

            packetHeader = 0x81;

        }else if (type.equals("Stop")) {

            packetHeader = 0x82;

        } else if (type.equals("Start")) {

            packetHeader = 0x83;

        } 
        else if (type.equals("SetConfiguration")) {

            packetHeader = 0x84;

        }
        else if (type.equals("GetConfiguration")) {

            packetHeader = 0x85;

        } else if (type.equals("SoftReset")) {

            packetHeader = 0x86;

        } 
        else if (type.equals("DefaultDataPacket")) {

            packetHeader = 0x87;

        }
        else if (type.equals("MessageDataPacket")) {

            packetHeader = 0x88;

        }
        else if (type.equals("DeleteAllData")) {

            packetHeader = 0x89;

        }

        return packetHeader;
    }

    public static int getDataPacketHeader(String type) {

        int packetHeader = 0;

        if (type.equals("DataTransfer")) {
            System.out.println(" packet header match");

            packetHeader = 0x81;

        } else if (type.equals("SetConfiguration")) {

            packetHeader = 0x84;

        } else if (type.equals("DefaultDataPacket")) {

            packetHeader = 0x87;

        } else if (type.equals("MessageDataPacket")) {

            packetHeader = 0x88;

        }

        System.out.println(packetHeader + "  packetHeader");
        return packetHeader;
    }

    public static int getIntensityHeader(int type) {

        int packetHeader = 0;

        if (type == 25) {

            packetHeader = 0x01;

        } else if (type == 50) {

            packetHeader = 0x02;

        } else if (type == 75) {

            packetHeader = 0x03;

        } else if (type == 100) {

            packetHeader = 0x04;

        }

        return packetHeader;
    }

    public static String getResponseStatus(byte response) {

        String res;

        if (response == 0x01) {

            res = "OK";

        } else {

            res = "FAIL";

        }

        return res;
    }

    public static int getEffectCode(String messageEffect) {

        int packetHeader = 0;

        switch (messageEffect) {

            case "Reserved":

                packetHeader = 0x00;
                break;
            case "Curtain Left to Right":

                packetHeader = 0x01;
                break;
            case "Curtain Top to Bottom":

                packetHeader = 0x02;
                break;
            case "Curtain Bottom to Top":

                packetHeader = 0x03;
                break;
            case "Typing Left to Right":

                packetHeader = 0x04;
                break;
            case "Running Right to Left":

                packetHeader = 0x05;
                break;
            case "Running Top to Bottom":

                packetHeader = 0x06;
                break;
            case "Running Bottom to Top":

                packetHeader = 0x07;
                break;
            case "Flashing":

                packetHeader = 0x08;
                break;
            case "stable":
            	packetHeader = 0x09;
                break;

        }

        return packetHeader;
    }

    public static int getSpeed(String speed) {

        int packetHeader = 0;

        switch (speed) {

            case "Lowest":
                packetHeader = 0x00;
                break;
            case "Low":

                packetHeader = 0x01;
                break;
            case "Medium":

                packetHeader = 0x02;
                break;
            case "High":

                packetHeader = 0x03;
                break;
            case "Highest":

                packetHeader = 0x04;
                break;
        }

        return packetHeader;
    }

    public static int getFont(String fontSize) {

        int packetHeader = 0;

        switch (fontSize) {

            case "7":

                packetHeader = 0x00;
                break;
            case "8":

                packetHeader = 0x01;
                break;
            case "10":

                packetHeader = 0x02;
                break;
            case "12":

                packetHeader = 0x03;
                break;
            case "14":

                packetHeader = 0x04;
                break;
            case "16":

                packetHeader = 0x05;
                break;
        }

        return packetHeader;
    }
    
  

    public static int getGap(int gap) {

        int packetHeader = 0;

        switch (gap) {

            case 0:

                packetHeader = 0x00;
                break;
            case 1:

                packetHeader = 0x01;
                break;
            case 2:

                packetHeader = 0x02;
                break;
            case 3:

                packetHeader = 0x03;
                break;
            case 4:

                packetHeader = 0x04;
                break;
            case 5:

                packetHeader = 0x05;
                break;
            case 6:

                packetHeader = 0x06;
                break;
            case 7:

                packetHeader = 0x07;
                break;
        }

        return packetHeader;
    }

    public static String byteToString(byte[] data) {

        String str = new String(data);

        return str;

    }

    public void getDataPacketLength() {

    }

    public byte[] hexToByteArray(String arg) {

        byte[] val = new byte[arg.length() / 2];
        for (int i = 0; i < val.length; i++) {
            int index = i * 2;
            int j = Integer.parseInt(arg.substring(index, index + 2), 16);
            val[i] = (byte) j;
        }

        return val;

    }

    public byte[] hexToByteArray2(String arg) {

        int it = Integer.parseInt(arg, 16);
        BigInteger bigInt = BigInteger.valueOf(it);
        byte[] bytearray = (bigInt.toByteArray());

        return bytearray;
    }

    public byte hexToByte(String hexString) {
        int firstDigit = toDigit(hexString.charAt(0));
        int secondDigit = toDigit(hexString.charAt(1));
        return (byte) ((firstDigit << 4) + secondDigit);
    }

    private int toDigit(char hexChar) {
        int digit = Character.digit(hexChar, 16);
        if (digit == -1) {
            throw new IllegalArgumentException(
                    "Invalid Hexadecimal Character: " + hexChar);
        }
        return digit;
    }
    
    
    
    
    public static String getErrorOrSuccess(String hexCode) {
        String errorOrSuccessType = "";
        switch (hexCode) {
            case "0xe0":
            	errorOrSuccessType = "CRC fail";
                break;
            case "0xe1":
            	errorOrSuccessType = "Invalid destination address";
                break;
            case "0xe2":
            	errorOrSuccessType = "Invalid from address";
                break;
            case "0xe3":
            	errorOrSuccessType = "Invalid function code";
                break;
            case "0xe4":
            	errorOrSuccessType = "No configuration";
                break;
            case "0xe5":
            	errorOrSuccessType = "Abnormal start of data packet";
                break;
            case "0xe6":
            	errorOrSuccessType = "Mismatch in serial number of data packet";
                break;
            case "0xe7":
            	errorOrSuccessType = "Internal buffer overflow";
                break;
            case "0xe8":
            	errorOrSuccessType = "Invalid data length";
                break;
            case "0xe9":
            	errorOrSuccessType = "Invalid data";
            	break;
            case "0xea":
            	errorOrSuccessType = "Internal write error";
                break;
            case "0xeb":
            	errorOrSuccessType = "Operation fail due to other conditions";
                break;
            case "0xec":
            	errorOrSuccessType = "Success";
                break;
                
        }
		logger.info("errorOrSuccessType: {}", errorOrSuccessType);

        return errorOrSuccessType;
    }
    
    public static byte[] getDataOnTwoBytesParam(short param)
    {
    	byte[] paramBytes = new byte[2];
    	paramBytes[0] = (byte) (param >> 8);
    	paramBytes[1] = (byte) param;
    	return paramBytes;
    }
    
}
