package com.innobitsysytems.ipis.highavailibility;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class ReceivingHeartBeats extends TimerTask {

	Ha_Initialization ti = new Ha_Initialization();
	public String msg="";
	public String sendMsg="";
	public int jCount=0;
	Process rewindOutput;
	public String receiveHeartBeat(int receiverPort) throws Exception {

		try {
			DatagramSocket serverSocket = new DatagramSocket(receiverPort);

			byte[] receivingDataBuffer = new byte[1024];

			DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);

			serverSocket.receive(inputPacket);

			String receivedData = new String(inputPacket.getData());

			System.out.println("recived from the other machine: " + receivedData);

			serverSocket.close();

			return receivedData;

		}

		catch (SocketException e) {

			return e.toString();
		}
	}

	public void switchover() throws Exception {
		ti.changeMsg("SendingMsg", "t");

		ti.configureVip(ti.adapter_name, ti.virtual_ip, ti.subnet_mask, ti.gateway, ti.dns1, ti.dns2);

		ti.portForwarding(ti.listen_address, ti.listen_port, ti.connect_address, ti.connect_port);

		System.out.println("switch over become master");
		
               
        Runtime.getRuntime().exec("cmd /c start cmd.exe /K \" pg_ctl promote -D \"C:\\Program Files\\PostgreSQL\\14\\data\"");         


	}

	public void timeout() throws Exception {
		try {

			ExecutorService executor = Executors.newCachedThreadPool();

			Callable<Object> task = new Callable<Object>() {
				public Object call() throws Exception {
					try {
						msg = receiveHeartBeat(ti.receiver_port);
						return msg;
					} catch (Exception e) {
						return e;
					}
				}
			};

			Future<Object> future = executor.submit(task);

			try {
System.out.println("timer valu="+ti.timer_value);
				Object result = future.get(ti.timer_value, TimeUnit.SECONDS);

			} catch (TimeoutException ex) {
				if(!ti.SendingMsg.equals("m")) {
				switchover();
				}

			} catch (InterruptedException e) {
				throw new Exception();
			} catch (ExecutionException e) {
				throw new Exception();

			} finally {
				future.cancel(true);
			}

		} catch (Exception e) {
			throw new Exception();
		}
	}

	@Override
	public void run() {
		try {
			Ha_Initialization ts = new Ha_Initialization();

			System.out.println(" 1111111111111111111111 11111");
			// long startTime = System.currentTimeMillis();
			// Process test=Runtime.getRuntime().exec("cmd /c start cmd.exe /K \" ping 192.168.2.3");
			// long stopTime = System.currentTimeMillis();
			// long elapsedTime = stopTime - startTime;
			// System.out.println(elapsedTime+"time 0000000000000000000000000000000000");
			timeout();


			char c = msg.charAt(0);
			System.out.println(msg + "   106");
			switch (c) {
			case 'm':
			System.out.println(" 33333333333333333333333333");

				ti.unconfigureVip(ti.adapter_name, ti.virtual_ip, ti.subnet_mask, ti.gateway);

				ti.changeMsg("SendingMsg", "s");     

				break;

			case 's':
			System.out.println(" 4444444444444444444444444411");

				ti.changeMsg("SendingMsg", "m");

				ti.configureVip(ti.adapter_name, ti.virtual_ip, ti.subnet_mask, ti.gateway, ti.dns1, ti.dns2);

				ti.portForwarding(ti.listen_address, ti.listen_port, ti.connect_address, ti.connect_port);

				break;
				
			case 't':
			System.out.println(" 5555555555555555555555555");

				ti.changeMsg("SendingMsg","s");

				ti.unconfigureVip(ti.adapter_name, ti.virtual_ip, ti.subnet_mask, ti.gateway);
				long startTime = System.currentTimeMillis();

	             Process test= Runtime.getRuntime().exec("cmd /c start cmd.exe /K \" net stop postgresql-x64-14 && pg_rewind -R -P -D \"C:\\Program Files\\PostgreSQL\\14\\data\" --source-server=\"host="+ti.destination_ip+" port=5432 user=postgres dbname=postgres\" && net start postgresql-x64-14 \"");     

				//  long stopTime = System.currentTimeMillis();
				//  long elapsedTime = stopTime - startTime;
				//  System.out.println(elapsedTime+"time 0000000000000000000000000000000000");
				//  Thread.sleep(elapsedTime);
				//  Runtime.getRuntime().exec("cmd /c start cmd.exe /K \" net start postgresql-x64-14");   
				
				break;

			case 'j':

				if(ts.SendingMsg.equals("s")){
					switchover();	
				}
				break;
			
			default: {

				if (ti.SendingMsg.equals("u")) {

					ti.unconfigureVip(ti.adapter_name, ti.virtual_ip, ti.subnet_mask, ti.gateway);

				} else if (ti.SendingMsg.equals("m")) {

					ti.configureVip(ti.adapter_name, ti.virtual_ip, ti.subnet_mask, ti.gateway, ti.dns1, ti.dns2);

				} else if (ti.SendingMsg.equals("s")) {

					ti.unconfigureVip(ti.adapter_name, ti.virtual_ip, ti.subnet_mask, ti.gateway);
				}
				break;
			}

			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}