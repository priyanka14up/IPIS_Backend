package com.innobitsysytems.ipis.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.innobitsysytems.ipis.model.onlineTrain.PacketCount;
import com.innobitsysytems.ipis.repository.OnlineTrainRepository;
import com.innobitsysytems.ipis.repository.PacketCountRepository;
import com.innobitsysytems.ipis.services.notification.NotificationService;
import com.innobitsysytems.ipis.utility.Constants;
import com.innobitsysytems.ipis.utility.DataUtil;



@Service
public class ResponseService {
	private static final Logger logger = LoggerFactory.getLogger(ResponseService.class);
//	@PostConstruct
//	public void postContruct() {
//		try {
//
//			 (new Thread(() -> startListen())).start();
//			 (new Thread(() -> startListenConfiguration())).start();
//			 (new Thread(() -> startListenForLinkResponse())).start();
//			 
//
//		}catch(Exception e) {
//			logger.error("Error occured in Post Contruct: {}",e.getMessage());
//		}
//
//	}
	@Value("${ipis-task-start-or-stop}")
	private String taskStartOrStop;
	
	@Value("${ipis-link-start-or-stop}")
	private String linkStartOrStop;
	
	@Value("${ipis-configuration-start-or-stop}")
	private String configurationStartOrStop;

	@Autowired
	private PacketCountRepository packetCountRepository;

	@Value("${ipis-response-handler-server-port}")
	private String serverPortForReceivingMessage;
	
	@Value("${ipis-link-response-port}")
	private String ipisLinkResponse;
	
	@Value("${ipis-configuraion-response}")
	private String ipisConfiguarationResponse;
	
	@Autowired
	private DataUtil dataUtil;

	@Autowired
	private NotificationService notificationService;

	public void startListen() {
		Socket socket = null;
		InputStream incoming = null;
		OutputStream outgoing = null;
		ServerSocket serverSocket = null;
		try {
			reconnectingToSocket(socket, incoming, outgoing, serverSocket);
		} catch (Exception e) {
			logger.error("Error occured in startListen: {} ", e.getMessage());
		} finally {
			logger.error("Error occured in startListen finally block");
			reconnectingToSocket(socket, incoming, outgoing, serverSocket);
		}
	}
	public void startListenForLinkResponse() {
		Socket socket = null;
		InputStream incoming = null;
		OutputStream outgoing = null;
		ServerSocket serverSocket = null;
		try {
			reconnectingToSocketForLinkResponse(socket, incoming, outgoing, serverSocket);
		} catch (Exception e) {
			logger.error("Error occured in startListen: {} ", e.getMessage());
		} finally {
			logger.error("Error occured in startListen finally block");
			reconnectingToSocketForLinkResponse(socket, incoming, outgoing, serverSocket);
		}
	}
	public void startListenConfiguration() {
		Socket socket = null;
		InputStream incoming = null;
		OutputStream outgoing = null;
		ServerSocket serverSocket = null;
		try {
			reconnectingToSocketForConfiguration(socket, incoming, outgoing, serverSocket);
		} catch (Exception e) {
			logger.error("Error occured in startListen: {} ", e.getMessage());
		} finally {
			logger.error("Error occured in startListen finally block");
			reconnectingToSocketForConfiguration(socket, incoming, outgoing, serverSocket);
		}
	}
	public void reconnectingToSocket(Socket socket, InputStream incoming, OutputStream outgoing,
			ServerSocket serverSocket) {
		try {
			while (Boolean.TRUE.equals(Boolean.valueOf(taskStartOrStop))) {
				logger.info("Listening on port: {} ", Integer.parseInt(serverPortForReceivingMessage));
				serverSocket = new ServerSocket(Integer.parseInt(serverPortForReceivingMessage));
				socket = serverSocket.accept();
				logger.info("incoming call...");
				incoming = socket.getInputStream();
				outgoing = socket.getOutputStream();
				getData(incoming);
				outgoing.close();
				incoming.close();
				socket.close();
				serverSocket.close();
			}
		} catch (Exception e) {
			logger.error("Error occured in startListen finally block :{} ", e.getMessage());
		} finally {
			try {
				outgoing.close();
				incoming.close();
				socket.close();
				serverSocket.close();
			} catch (IOException e) {
				logger.error("Error occured in startListen finally block :{} ", e.getMessage());
			}
		}
	}
	
	public void reconnectingToSocketForLinkResponse(Socket socket, InputStream incoming, OutputStream outgoing,
			ServerSocket serverSocket) {
		try {
			while (Boolean.TRUE.equals(Boolean.valueOf(linkStartOrStop))) {
				logger.info("Listening on port: {} ", Integer.parseInt(ipisLinkResponse));
				serverSocket = new ServerSocket(Integer.parseInt(ipisLinkResponse));
				socket = serverSocket.accept();
				logger.info("incoming call...");
				incoming = socket.getInputStream();
				outgoing = socket.getOutputStream();
				getLinkData(incoming);
				outgoing.close();
				incoming.close();
				socket.close();
				serverSocket.close();
			}
		} catch (Exception e) {
			logger.error("Error occured in startListen finally block :{} ", e.getMessage());
		} finally {
			try {
				outgoing.close();
				incoming.close();
				socket.close();
				serverSocket.close();
			} catch (IOException e) {
				logger.error("Error occured in startListen finally block :{} ", e.getMessage());
			}
		}
	}
	public void reconnectingToSocketForConfiguration(Socket socket, InputStream incoming, OutputStream outgoing,
			ServerSocket serverSocket) {
		try {
			while (Boolean.TRUE.equals(Boolean.valueOf(configurationStartOrStop))) {
				logger.info("Listening on port: {} ", Integer.parseInt(ipisConfiguarationResponse));
				serverSocket = new ServerSocket(Integer.parseInt(ipisConfiguarationResponse));
				socket = serverSocket.accept();
				logger.info("incoming call...");
				incoming = socket.getInputStream();
				outgoing = socket.getOutputStream();
				getConfigurationData(incoming);
				outgoing.close();
				incoming.close();
				socket.close();
				serverSocket.close();
			}
		} catch (Exception e) {
			logger.error("Error occured in startListen finally block :{} ", e.getMessage());
		} finally {
			try {
				outgoing.close();
				incoming.close();
				socket.close();
				serverSocket.close();
			} catch (IOException e) {
				logger.error("Error occured in startListen finally block :{} ", e.getMessage());
			}
		}
	}
	
	private void getConfigurationData(InputStream incoming) {
		// TODO Auto-generated method stub
		
	}
	private void getLinkData(InputStream incoming) {
		// TODO Auto-generated method stub
		
	}
	private void getData(InputStream stream2server) {

		try {
			logger.info("reading incoming data...");
			byte[] response = new byte[stream2server.available()];
			int totalByteRead = stream2server.read(response);
			logger.info("totalByteRead: {}", totalByteRead);
			String successOrError = dataUtil.getErrorOrSuccess(dataUtil.decimalToHex1(response[Constants.ERROR_TYPE_BYTE]));
			notificationService.sendNotification(successOrError);
			if(!successOrError.equalsIgnoreCase("success") || !successOrError.contains("success")) {
				synchronized(this) {
					PacketCount packetCount = packetCountRepository.findBydate(LocalDate.now());
					if(packetCount == null) {
						logger.error("No data found for the mentioned date");
						return;
					}
					packetCount.setPacketCount(packetCount.getPacketCount()-1);
					packetCountRepository.save(packetCount);
				}
			}
			//can be used later
			//			int a=(int)response[Constants.SOURCE_ADDRESS_MSB];
			//			int b=(int)response[Constants.SOURCE_ADDRESS_LSB];
			//		
			//			Constants.SOURCE_IP=Constants.SOURCE_IP.concat(Integer.toString(a)).concat(".").concat(Integer.toString(b));
			//			System.out.println("Constants.SOURCE_IP"+Constants.SOURCE_IP);
			//notificationService.sendNotification(Constants.SOURCE_IP, dataUtil.getErrorOrSuccess(dataUtil.decimalToHex1(response[Constants.ERROR_TYPE_BYTE])));
		} catch (IOException ex) {
			logger.error("Error occured in getData: {}", ex.getMessage());
			ex.printStackTrace();
		}
		// TODO Auto-generated method stub

	}
}
