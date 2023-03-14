package com.innobitsysytems.ipis.highavailibility;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Properties;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

//import com.innobitsysytems.ipis.exception.HandledException;

public class Ha_Initialization {
	String default_pc;
	String SendingMsg;

	int sender_port;
	int receiver_port;

	String virtual_ip;
	int counter_max;

	int timer_value;

	String destination_ip;

	String adapter_name;
	String subnet_mask;
	String gateway;
	String dns1;
	String dns2;

	String listen_port;
	String connect_port;
	String listen_address;
	String connect_address;

	File file = new File("HaConfig.properties");

	String path = file.getAbsolutePath();

	File configFile = new File(path);

	Properties prop = new Properties();

	public Ha_Initialization() {
		try {
			FileReader read = new FileReader(configFile);

			prop.load(read);

			SendingMsg = prop.getProperty("SendingMsg");

			default_pc = prop.getProperty("default_pc");

			counter_max = (Integer.parseInt(prop.getProperty("counter_max")));

			receiver_port = (Integer.parseInt(prop.getProperty("receiver_port")));

			sender_port = (Integer.parseInt(prop.getProperty("sender_port")));

			destination_ip = prop.getProperty("destination_ip");

			virtual_ip = prop.getProperty("virtual_ip");

			adapter_name = prop.getProperty("adapter_name");

			subnet_mask = prop.getProperty("subnet_mask");

			gateway = prop.getProperty("gateway");

			dns1 = prop.getProperty("dns1");

			dns2 = prop.getProperty("dns2");

			listen_port = prop.getProperty("listen_port");

			connect_port = prop.getProperty("connect_port");

			listen_address = prop.getProperty("listenaddress");

			connect_address = prop.getProperty("connectaddress");

			timer_value = (Integer.parseInt(prop.getProperty("timer_value")));

			read.close();
		} catch (Exception e) {
			System.out.println("Property not found");

		}

	}

	public void changeMsg(String key, String msg) throws Exception {
		PropertiesConfiguration config;

		try {

			config = new PropertiesConfiguration(path);

			config.setProperty(key, msg);

			config.save();
		} catch (ConfigurationException e) {

System.out.println(e.getMessage());
		}

	}

	// vip config
	public void configureVip(String adapter_name, String virtual_ip, String subnet_mask, String gateway, String dns1,
			String dns2) throws Exception {
		String[] command = { "cmd", };
		Process process;
		try {
			process = Runtime.getRuntime().exec(command);
			new Thread(new SyncPipe(process.getErrorStream(), System.err)).start();
			new Thread(new SyncPipe(process.getInputStream(), System.out)).start();

			PrintWriter stdin = new PrintWriter(process.getOutputStream());

			stdin.println("netsh interface ipv4 add address " + adapter_name + " " + virtual_ip + " " + subnet_mask
					+ " " + gateway + " ");

			stdin.println("netsh interface ipv4 set dns " + adapter_name + " static " + dns1 + " primary");

			stdin.println("netsh interface ipv4 add dns " + adapter_name + " " + dns2 + " index=2 ");

			stdin.close();

			process.waitFor();

		} catch (Exception e) {
		System.out.println(e.getMessage());
		}
	}

	// unconfig_ip
	public void unconfigureVip(String adapter_name, String virtual_ip, String mask, String gateway)
			throws Exception {
		String[] command = { "cmd", };

		Process process;

		try {
			process = Runtime.getRuntime().exec(command);
			new Thread(new SyncPipe(process.getErrorStream(), System.err)).start();
			new Thread(new SyncPipe(process.getInputStream(), System.out)).start();

			PrintWriter stdin = new PrintWriter(process.getOutputStream());

			stdin.println("netsh -c interface ipv4 delete address " + adapter_name + " " + virtual_ip);

			stdin.close();

			process.waitFor();
		} catch (Exception e) {
//			throw new HandledException("Unconfiguration Exception!!",
//					"Virtual ip address not unonfigured successfully");
			System.out.println("Virtual ip address not unonfigured successfully "+e.getMessage());
		}
	}

	// port forwarding
	
	public void portForwarding(String listenaddress, String listenport, String connectaddress, String connectport)
			throws Exception {
		String[] command = { "cmd", };

		Process process;
		try {
			process = Runtime.getRuntime().exec(command);
			new Thread(new SyncPipe(process.getErrorStream(), System.err)).start();
			new Thread(new SyncPipe(process.getInputStream(), System.out)).start();

			PrintWriter stdin = new PrintWriter(process.getOutputStream());
			stdin.println("netsh interface portproxy add v4tov4 listenaddress=" + listenaddress + " listenport="
					+ listenport + " connectaddress=" + connectaddress + " connectport=" + connectport);
			stdin.close();

			process.waitFor();
		} catch (Exception e) {
//			throw new Exception("Port is not forwarded!!", "Check all the parameters in command");
			System.out.println(e.getMessage());

		}
	}

}