package com.innobitsysytems.ipis.licensing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;

import org.springframework.stereotype.Service;
import com.innobitsysytems.ipis.exception.HandledException;

@Service
public class LicenseBean implements LicenseService {

	@Override
	public boolean keyAuthenticate(KeyInputDto keyInputDto) throws HandledException {
		String key = keyInputDto.getKey();
		boolean flag = true;

		try {
			
			String generatedKey = keyGenerate(keyInputDto);
			if (key.equals(generatedKey)) {
				flag = true;

			} else {
				
				flag = false;

			}
			return flag;
		} catch (Exception e) {
			throw new HandledException("Exception", "key authentication exception");
		}

	}

	public String keyGenerate(KeyInputDto keyInputDto) throws HandledException {
		try {

			String userName = keyInputDto.getUserName();
			String inputForKey;
		
			inputForKey = userName.concat("@#$%&*(-)/@");
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] array = md.digest(inputForKey.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
			throw new HandledException("Key generation exception", "Exception in generating key");
		}
	}

	public boolean macAuthenticate(KeyInputDto keyInputDto) throws HandledException {
		try {
			String maC = generateMac();
			
			String mac = hashGenerate(maC);
			
			File f = new File("key.txt");
			f.createNewFile();
			String path = gettingFilePath(f);
			hide(f);
			String IntialFilecontent = readFile(path);
			insertIntoFile(path, mac, IntialFilecontent, keyInputDto);
			String fileCode = readFile(path);
			return authenticateMac(mac, fileCode);
		} catch (Exception e) {
			
			throw new HandledException("Exception", "key authentication failed");
		}
	}
	public void hide(File src) throws InterruptedException, IOException {
	    Process p = Runtime.getRuntime().exec("attrib +h " + src.getPath());
	    p.waitFor();
	}

	public void insertIntoFile(String path, String mac, String fileCode, KeyInputDto keyInputDto) throws Exception {
		boolean keyauth = keyAuthenticate(keyInputDto);
		if (fileCode == null && keyauth == true) {	
			FileWriter fw = new FileWriter(path, true);
			
			String lineToAppend = mac;
			fw.write(lineToAppend);
			fw.close();
			
		}

	}

	public String gettingFilePath(File file) {
		String absolute = file.getAbsolutePath();
		return absolute;
	}

	public String generateMac() {
		try {

			InetAddress localHost = InetAddress.getLocalHost();
			NetworkInterface ni = NetworkInterface.getByInetAddress(localHost);
			byte[] hardwareAddress = ni.getHardwareAddress();

			String[] hexadecimal = new String[hardwareAddress.length];
			for (int i = 0; i < hardwareAddress.length; i++) {
				hexadecimal[i] = String.format("%02X", hardwareAddress[i]);
			}
			String macAddress = String.join("-", hexadecimal);
			return macAddress;

		} catch (Exception e) {
			return "Exception ";
		}
	}

	public void createNewFile() {
		try {
			File f = new File("key.txt");
			f.createNewFile();
		} catch (Exception e) { 
			System.out.println(e);
		}
	}

	public boolean authenticateMac(String mac, String filecontent) throws HandledException {
		try {
		
		String hasFilecontent = hashGenerate(filecontent);
		
		if (filecontent.equals(mac)) {
			return true;
		} else {
			return false;
		}
		}
		 catch (Exception e) {
				throw new HandledException("Exception", "device authentication exception");
			}

		
	}

	public String readFile(String path) throws Exception {
		try {
			String filedata = null;
			BufferedReader br = new BufferedReader(new FileReader(path));
			String st;
			while ((st = br.readLine()) != null) {
				filedata = st;
			}
			return filedata;
		} catch (Exception e) {
			throw new HandledException("Exception", "Exception in reading file");
		}

	}
	
	public String hashGenerate(String str) throws HandledException {
		try {
			String name = str;
			String inputForKey;
			inputForKey = name.concat("@#$%&*(-)/@$$$$$$$$$##%@@!");
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] array = md.digest(inputForKey.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
			throw new HandledException("Key generation exception", "Exception in generating key");
		}
		
		
	}

}
