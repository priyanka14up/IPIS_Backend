package com.innobitsysytems.ipis.library;

import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.services.notification.NotificationService;
import com.innobitsysytems.ipis.utility.Constants;
import com.innobitsysytems.ipis.utility.DataUtil;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innobitsysytems.ipis.model.setup.GetIntensity;
import com.innobitsysytems.ipis.repository.GetIntensityRepo;


@Component
public class TcpClient {
	private static final Logger logger = LoggerFactory.getLogger(TcpClient.class);

	ServerSocket serverSocket = null;
	Socket socket = null;
	InputStream incoming = null;
	OutputStream outgoing = null;

	@Autowired
	private DataUtil dataUtil;
	
	@Autowired
	private GetIntensityRepo getIntensityRepo;
	
//	@Autowired
//	private GetIntensity getIntensity;
	

	@Autowired
	private NotificationService notificationService;

	HashMap<String, Socket> map = new HashMap<>();
	@PostConstruct
	public void acceptConnectionFromDefaultTcpPort() {
		try {
			serverSocket = new ServerSocket(25000);
			logger.info("Post Contruct of TCPClient");
			(new Thread(() -> startListenFromDefaultTcpPort())).start();
		}catch(Exception e) {
			logger.error("Error in acceptConnectionFromDefaultTcpPort",e.getMessage());
		}
	}

	public TcpClient() {
		super();
	}

	private void startListenFromDefaultTcpPort() {
		while (Boolean.TRUE.equals(Boolean.valueOf("true")))  {
			try {
				socket = serverSocket.accept();
				logger.info("incoming call..."+socket.getInetAddress().getHostAddress());
				map.put(socket.getInetAddress().getHostAddress(), socket);
			} catch (IOException e) {
				System.out.println("I/O error: " + e);
			}
			//			if(map.get(socket.getInetAddress().getHostAddress()) == null) {
			//				(new Thread(() -> packetTypeAndHexCode())).start();
			//			}

		}
	}

	public void packetTypeAndHexCode() {
		try {
			map.put(socket.getInetAddress().getHostAddress(), socket);

			while(true) {
				logger.info("Inside while of packetTypeAndHexCode");
				incoming = socket.getInputStream();
				outgoing = socket.getOutputStream();
				int availableByte = incoming.available();
				if (availableByte <= 0) {
					//					socket.close();
					return;
				} else {
					byte[] response = new byte[availableByte];
					int totalByteRead = incoming.read(response);
					String packetTypeHexCode = dataUtil.decimalToHex1(response[Constants.PACKET_TYPE]);
					logger.info("totalByteRead: {} and hexCode: {}",totalByteRead,packetTypeHexCode);
					if(packetTypeHexCode.equalsIgnoreCase("0x85")) {
						//14th n 15th bytes are get config data
						int intensity=response[14];
						int timeOut=response[15];
						System.out.println("intensity="+intensity);
						System.out.println("timeOut="+timeOut);
						logger.info("Get Configuration--: {}",packetTypeHexCode);


					}else if(packetTypeHexCode.equalsIgnoreCase("0xc0")) {
						logger.info("Link Check Response--: {}",packetTypeHexCode);
					}else  {
						String successOrError = dataUtil.getErrorOrSuccess(dataUtil.decimalToHex1(response[Constants.ERROR_TYPE_BYTE]));
						notificationService.sendNotification(successOrError);
						logger.info("Error Communication--: {}",dataUtil.getErrorOrSuccess(packetTypeHexCode));
					} 
				}
			}
		} catch (IOException e) {
			logger.error("Error occured in run block :{} ", e.getMessage());
			try {
				outgoing.close();
				incoming.close();
				socket.close();
				serverSocket.close();
			} catch (IOException ex) {
				logger.error("Error occured in run finally block :{} ", ex.getMessage());
			}
		}
	}

	public TcpClient(Socket socket) {
		this.socket = socket;
	}

	public  void sendTcpMsg(byte[] data, InetAddress ipAddress, int portNumber,List<byte[]> videoByteArray) throws HandledException {
		try {

			Socket s = map.get(socket.getInetAddress().getHostAddress());
			
			//sending data to server
			DataInputStream din = new DataInputStream(s.getInputStream());

			
			// sends output to the socket
			DataOutputStream dout = new DataOutputStream(s.getOutputStream()); 

			//takes input from socket
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			String str1 = "";
			String str2 = "";
			
			while (true) {

				if(videoByteArray!=null){
					for(int i=0;i<videoByteArray.size();i++) {
						dout.write(videoByteArray.get(i));
					}
				}
				else{
					dout.write(data);
				}
				
				dout.flush();
				
				Thread.sleep(100);
				int availableByte = din.available();
				if (availableByte == 0) {
					Thread.sleep(100);
					availableByte = din.available();
				}
				logger.info("------"+str2);
				if (str1.equals("stop")) {
					return;
				} else {
					byte[] response = new byte[availableByte];
					int totalByteRead = din.read(response);
					if(totalByteRead == 0) {
						logger.error("NO data rec..");
						 dout.close();
						 s.close();
						return;
					}
					
					String packetTypeHexCode = dataUtil.decimalToHex1(response[Constants.PACKET_TYPE]);
					logger.info("totalByteRead: {} and hexCode: {}",totalByteRead,packetTypeHexCode);
					String sentPacketLength1HexCode = dataUtil.decimalToHex1(data[Constants.PACKET_LENGTH1]);
					String receivedPacketLength1HexCode = dataUtil.decimalToHex1(response[Constants.PACKET_LENGTH1]);
					String sentPacketLength2HexCode = dataUtil.decimalToHex1(data[Constants.PACKET_LENGTH2]);
					String receivedPacketLength2HexCode = dataUtil.decimalToHex1(response[Constants.PACKET_LENGTH2]);
					
					String sentSourceMsbHexCode = dataUtil.decimalToHex1(data[Constants.SRC_MSB]);
					String receivedDestinationMsbHexCode = dataUtil.decimalToHex1(response[Constants.DES_MSB]);
					
					String sentSourceLsbHexCode = dataUtil.decimalToHex1(data[Constants.SRC_LSB]);
					String receivedDestinationLsbHexCode = dataUtil.decimalToHex1(response[Constants.DES_LSB]);
					
					String sentDestinationMsbHexCode = dataUtil.decimalToHex1(data[Constants.DES_MSB]);
					String receivedSourceMsbHexCode = dataUtil.decimalToHex1(response[Constants.SRC_MSB]);
					
					String sentDestinationLsbHexCode = dataUtil.decimalToHex1(data[Constants.DES_LSB]);
					String receivedSourceLsbHexCode = dataUtil.decimalToHex1(response[Constants.SRC_LSB]);
					
					String sentCRCLsbHexCode = dataUtil.decimalToHex1(data[data.length-1]);
					String receivedCRCLsbHexCode = dataUtil.decimalToHex1(response[response.length-1]);
					
					String sentCRCMsbHexCode = dataUtil.decimalToHex1(data[data.length-2]);
					String receivedCRCMsbHexCode = dataUtil.decimalToHex1(response[response.length-2]);
					
//					if(sentPacketLength1HexCode.equals(receivedPacketLength1HexCode)&&sentPacketLength1HexCode.equals(receivedPacketLength1HexCode))
//					{
						if(sentSourceMsbHexCode.equals(receivedDestinationMsbHexCode)&&sentSourceLsbHexCode.equals(receivedDestinationLsbHexCode))
						{
							
							if(sentDestinationMsbHexCode.equals(receivedSourceMsbHexCode)&&sentDestinationLsbHexCode.equals(receivedSourceLsbHexCode))
							{
							
//								if(sentCRCLsbHexCode.equals(receivedCRCLsbHexCode)&&sentCRCMsbHexCode.equals(receivedCRCMsbHexCode))
//								{
									if(packetTypeHexCode.equalsIgnoreCase("0x85"))
									{	
										logger.info("Get Configuration--: {}",packetTypeHexCode);
										String intensity=dataUtil.decimalToHex1(response[12]);
										String timeOut=dataUtil.decimalToHex1(response[13]);
										String crcMsb=dataUtil.decimalToHex1(response[15]);
										String crcLsb=dataUtil.decimalToHex1(response[16]);
										logger.info("intensity from get configuration--: {}",intensity);
										logger.info("timeout from get configuration--: {}",timeOut);
										logger.info("crc msb from get configuration--: {}",crcMsb);
										logger.info("crc lsb from get configuration--: {}",crcLsb);
										
										GetIntensity getIntensity=new GetIntensity();
										getIntensity.setIntensityValue(Integer.parseInt(dataUtil.getIntensityLevel(intensity)));
										
										int platfotm=(response[Constants.SRC_MSB])&0xff;
										String strPlatform = String.valueOf(platfotm);
										getIntensity.setPlatform(strPlatform);
										int deviceId = (response[Constants.SRC_LSB])&0xff;
										String strDeviceId = String.valueOf(deviceId);
										getIntensity.setDeviceId(strDeviceId);
//										getIntensity.setPlatform();
										getIntensityRepo.save(getIntensity);	
										notificationService.sendNotification("Get configuration");
										
									}
									else if(packetTypeHexCode.equalsIgnoreCase("0xc0")) {
										int intensity=response[14];
										int timeOut=response[15];
										System.out.println("intensity"+intensity);
										System.out.println("timeOut"+timeOut);
										logger.info("Link Check Response--: {}",packetTypeHexCode);
										notificationService.sendNotification("link scheduler");
									}
									else  {
										String successOrError = dataUtil.getErrorOrSuccess(dataUtil.decimalToHex1(response[Constants.ERROR_TYPE_BYTE]));
										notificationService.sendNotification(successOrError);

										logger.info("Error Communication--: {}",dataUtil.getErrorOrSuccess(packetTypeHexCode));
									}
									str1 = "stop";
//								}
//								else
//								{
//									System.out.println("sent packet CRC and response packet CRC is not matched");
//								}
					
							}
							else
							{
								System.out.println("sent packet destination and response source is not matched");
							}
						}
						else
						{
							System.out.println("sent packet source and response destination is not matched");
							
						}	
//					}
//					else
//					{
//						System.out.println("packet length not matched with response packet");
//					}
				}
				dout.close();
				s.close();
			}
		}//end of try

		//			socket = serverSocket.accept();
		//			Socket socketFromMap = map.get(socket.getInetAddress().getHostAddress());
		//			if(socketFromMap == null) {
		//				logger.error("NO Socket Found with InetAddress of: {}",ipAddress.getHostAddress());
		//				return;
		//			}
		//			DataOutputStream dout = new DataOutputStream(socketFromMap.getOutputStream()); 
		//			dout.write(data);
		//			dout.flush();
		//dout.close();
		//			socket.close();
		//			outgoing.close();
		//			incoming.close();
		//			serverSocket.close();
		catch (Exception e) {
			throw new HandledException("COMM_EXCEPTION", "Exception " + e);
		}
	}

	public static byte[] sendReceiveTcpMsg(byte[] data, InetAddress ipAddress, int portNumber) throws HandledException {
		byte[] response = new byte[0];
		try {
			Socket s = new Socket(ipAddress, portNumber);
			// sends output to the socket
			DataOutputStream dout = new DataOutputStream(s.getOutputStream()); 
			//takes input from socket
			DataInputStream din = new DataInputStream(s.getInputStream());
			// sending data to server
			dout.write(data);
			dout.flush();
			// Receiving reply from server
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte buffer[] = new byte[1024];
			baos.write(buffer, 0 , din.read(buffer));
			response = baos.toByteArray();
		} catch (Exception e) {
			throw new HandledException("COMM_EXCEPTION", "Exception " + e);
		}
		return response;
	}
}
