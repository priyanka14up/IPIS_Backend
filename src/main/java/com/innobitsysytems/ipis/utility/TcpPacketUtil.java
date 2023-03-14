/**
 * Name: Amit Yadav
 * Copyright: Innobit Systems, 2021
 * Purpose: Tcp Packet Util
 */
package com.innobitsysytems.ipis.utility;

import java.lang.reflect.Field;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innobitsysytems.ipis.model.device.DeviceType;
import com.innobitsysytems.ipis.model.onlineTrain.PacketCount;
import com.innobitsysytems.ipis.model.setup.IvdScreenColorConfig;
import com.innobitsysytems.ipis.model.setup.TrainStatusColor;
import com.innobitsysytems.ipis.repository.PacketCountRepository;

import sun.misc.Unsafe;

@Component
public class TcpPacketUtil {
	private static final Logger logger = LoggerFactory.getLogger(TcpPacketUtil.class);
	@Autowired
	private DataUtil dataUtil;

	private int header_start_first = 0xAA;
	@Autowired
	private PacketCountRepository packetCountRepository;
	private int header_start_second = 0xCC;

	private static Unsafe unsafe;

	static {
		try {
			Field field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			unsafe = (Unsafe) field.get(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static long addressOf(String trainNo) throws Exception {
		String[] array = new String[] { trainNo };
		long baseOffset = unsafe.arrayBaseOffset(String[].class);

		return (baseOffset);
	}

	public byte[] getConfigDataPacket(String dviceType, String PacketType, String ipAddress, byte[] data) {

		String[] ipArray = ipAddress.split("[, . ']+");

		byte[] tempData = new byte[0];

		byte[] a_data = new byte[0];

		int packetIdentifier = DataUtil.getDeviceHeader(dviceType);

		byte[] actualData = data;

		int fixedLength = 7;

		int varibleLength = actualData.length + 3; // N+3 //88

		int packetLength = fixedLength + varibleLength; // 95

		System.out.println("packetLength--------------------------------->" + packetLength);

		byte[] length = new byte[2];

		// old in little endien-ness
// 	 	length[0]= (byte)((packetLength >>8) & 0xff);
// 	 	length[1]=(byte)(packetLength & 0Xff);

		length[0] = (byte) ((packetLength >> 8) & 0xff);
		length[1] = (byte) (packetLength & 0Xff);

		//int destAddressMsb = DataUtil.hexToInt(dataUtil.decimalToHex(ipArray[2]));
		int destAddressMsb = DataUtil.hexToInt(dataUtil.decimalToHex(0));
		
		//int destAddressLsb = DataUtil.hexToInt(dataUtil.decimalToHex(ipArray[3]));
		int destAddressLsb = DataUtil.hexToInt(dataUtil.decimalToHex(224));
		
		//int sourecAddressMsb = dataUtil.getSAMSB();
		int sourecAddressMsb = dataUtil.getSAMSB();

		int sourecAddressLsb = dataUtil.getSALSB();
		int serialNumber =0;
		synchronized(this) {
			PacketCount packetCount = packetCountRepository.findBydate(LocalDate.now());
			if(packetCount == null) {
				packetCount = new PacketCount();
				packetCount.setPacketCount(0);
				packetCount.setDate(LocalDate.now());
				packetCountRepository.save(packetCount);
			}
			 serialNumber = packetCount.getPacketCount()+1;
			 //serialNumber = 1;
			packetCount.setPacketCount(serialNumber);
			packetCountRepository.save(packetCount);
		}
		 
		int packetType = DataUtil.getDataPacketHeader(PacketType);

		int startOfDataPacket = 0x02;

		int endOfDataPacket = 0x03;

		int eot = 0x04; // End of transmission

		for (int i = 0; i < actualData.length; i++) {
			a_data = new byte[] { (byte) header_start_first, (byte) header_start_second, (byte) packetIdentifier,
					length[0], length[1], (byte) packetLength, (byte) destAddressMsb, (byte) destAddressLsb,
					(byte) sourecAddressMsb, (byte) sourecAddressLsb, (byte) serialNumber, (byte) packetType,
					(byte) startOfDataPacket, (byte) actualData[i], (byte) endOfDataPacket, (byte) eot };
		}

		for (int j = 0; j < actualData.length; j++) {
			tempData = new byte[] { length[0], length[1], (byte) destAddressMsb, (byte) destAddressLsb,
					(byte) sourecAddressMsb, (byte) sourecAddressLsb, (byte) serialNumber, (byte) packetType,
					(byte) startOfDataPacket, (byte) actualData[j], (byte) endOfDataPacket };

		}

		// Calculate CRC16
		List<Byte> res1 = new ArrayList<Byte>();
		res1.add(length[0]);
		res1.add(length[1]);
		res1.add((byte) destAddressMsb);
		res1.add((byte) destAddressLsb);
		res1.add((byte) sourecAddressMsb);
		res1.add((byte) sourecAddressLsb);
		res1.add((byte) serialNumber);
		res1.add((byte) packetType);
		res1.add((byte) startOfDataPacket);
		
//		int des=((byte)destAddressLsb)&0xff;
//		System.out.println("destination address in byte"+(byte) destAddressLsb);
//		System.out.println("destination address in int"+des);

		for (int i = 0; i < actualData.length; i++) {
			res1.add((byte) actualData[i]);
		}

		res1.add((byte) endOfDataPacket);

		byte[] packetData1 = new byte[res1.size()];

		for (int i = 0; i < res1.size(); i++) {
			packetData1[i] = res1.get(i);
		}

		byte crcMsb = PacketUtil.crc16ccitt(packetData1, "MSB");

		byte crcLsb = PacketUtil.crc16ccitt(packetData1, "LSB");

		System.out.println("sourecAddressLsb before preparing packet data" + sourecAddressLsb);

		List<Byte> res = new ArrayList<Byte>();
		res.add((byte) header_start_first);
		res.add((byte) header_start_second);
		res.add((byte) packetIdentifier);
		res.add(length[0]);
		res.add(length[1]);
		res.add((byte) destAddressMsb);
		res.add((byte) destAddressLsb);
		res.add((byte) sourecAddressMsb);
		res.add((byte) sourecAddressLsb);
		res.add((byte) serialNumber);
		res.add((byte) packetType);
		res.add((byte) startOfDataPacket);

		for (int i = 0; i < actualData.length; i++) {
			res.add((byte) actualData[i]);
		}

		res.add((byte) endOfDataPacket);
		res.add(crcMsb);
		res.add(crcLsb);
		res.add((byte) eot);
		byte[] packetData = new byte[res.size()];

		for (int i = 0; i < res.size(); i++) {
			packetData[i] = res.get(i);

			System.out.println("packetData" + (i + 1) + "--------->" + res.get(i));

		}
		System.out.println();
		System.out.println();
		System.out.println("packet in hexa");
		for (int i = 0; i < res.size(); i++) {
			String s = dataUtil.decimalToHex1(res.get(i));

			System.out.println("byte no-" + (i + 1) + "----" + s);

		}

		return packetData;

	}

	public byte[] getCommandPacket(String dviceType, String PacketType, String ipAddress) {

		String[] ipArray = ipAddress.split("[, . ']+");

		int packetIdentifier = DataUtil.getDeviceHeader(dviceType);

		int packetLength = 0x08;

		byte[] length = new byte[2];

		short a = (short) (packetLength & 0Xff);
		short b = (short) ((packetLength >> 8) & 0xff);
//old
//		length[0] = (byte) (packetLength & 0Xff);
//		length[1] = (byte) ((packetLength >> 8) & 0xff);
//		
		length[0] = (byte) ((packetLength >> 8) & 0xff);
		length[1] = (byte) (packetLength & 0Xff);

		//int destAddressMsb = DataUtil.stringToInt(ipArray[2]);
		int destAddressMsb = DataUtil.stringToInt("3");

		
		//int destAddressLsb = DataUtil.stringToInt(ipArray[3]);
		int destAddressLsb = DataUtil.stringToInt("7");
		
		int sourecAddressMsb = dataUtil.getSAMSB();

		int sourecAddressLsb = dataUtil.getSALSB();

		int serialNumber = 0x00;

		int packetType = DataUtil.getCommandPacketHeader(PacketType);

		int eot = 0x04;

		byte[] a_data = new byte[] { (byte) header_start_first, (byte) header_start_second, (byte) packetIdentifier,
				(byte) packetLength, (byte) packetLength, (byte) destAddressMsb, (byte) destAddressLsb,
				(byte) sourecAddressMsb, (byte) sourecAddressLsb, (byte) serialNumber, (byte) packetType, (byte) eot };

		byte[] tempData = new byte[] { (byte) length[0],(byte) length[1], (byte) destAddressMsb, (byte) destAddressLsb,
				(byte) sourecAddressMsb, (byte) sourecAddressLsb, (byte) serialNumber, (byte) packetType };
		
		//byte[] tempData = new byte[] {  (byte) header_start_first, (byte) header_start_second, (byte) packetIdentifier,(byte) packetLength, (byte) destAddressMsb, (byte) destAddressLsb,
				//(byte) sourecAddressMsb, (byte) sourecAddressLsb, (byte) serialNumber, (byte) packetType };

		
		
		byte crcMsb = PacketUtil.crc16ccitt(tempData, "MSB");
		byte crcLsb = PacketUtil.crc16ccitt(tempData, "LSB");

		List<Byte> res = new ArrayList<Byte>();

		res.add((byte) header_start_first);

		res.add((byte) header_start_second);

		res.add((byte) packetIdentifier);

		res.add(length[0]);
		res.add(length[1]);

		res.add((byte) destAddressMsb);

		res.add((byte) destAddressLsb);

		res.add((byte) sourecAddressMsb);

		res.add((byte) sourecAddressLsb);

		res.add((byte) serialNumber);

		res.add((byte) packetType);

		res.add(crcMsb);

		res.add(crcLsb);

		res.add((byte) eot);

		byte[] commandData = new byte[] { (byte) header_start_first, (byte) header_start_second,
				(byte) packetIdentifier, length[0], length[1], (byte) destAddressMsb, (byte) destAddressLsb,
				(byte) sourecAddressMsb, (byte) sourecAddressLsb, (byte) serialNumber, (byte) packetType, crcMsb,
				crcLsb, (byte) eot };

		for (int i = 0; i < res.size(); i++) {
			commandData[i] = res.get(i);

			System.out.println("command packetData" + (i + 1) + "--------->" + res.get(i));
		}

		System.out.println("command packet in hexa");
		for (int i = 0; i < res.size(); i++) {
			String s = dataUtil.decimalToHex1(res.get(i));
			System.out.println("byte no-" + (i + 1) + "----" + s);

		}

		return commandData;

	}

	public byte[] getTrainColorCommandPacket(IvdScreenColorConfig ivdScreenColorConfig,
			List<TrainStatusColor> listOfTrainStatusColor, String ipAddress, int intensity, int displayTimeOut) {
		byte[] packetData = null;
		try {
			List<Byte> response = new ArrayList<>();
			response.add((byte) intensity);
			response.add((byte) displayTimeOut);
			response.add((byte) ivdScreenColorConfig.getHorizontalColor().getR());
			response.add((byte) ivdScreenColorConfig.getHorizontalColor().getG());
			response.add((byte) ivdScreenColorConfig.getHorizontalColor().getB());
			response.add((byte) ivdScreenColorConfig.getVerticalColor().getR());
			response.add((byte) ivdScreenColorConfig.getVerticalColor().getG());
			response.add((byte) ivdScreenColorConfig.getVerticalColor().getB());
			response.add((byte) ivdScreenColorConfig.getBackgroundColor().getR());
			response.add((byte) ivdScreenColorConfig.getBackgroundColor().getG());
			response.add((byte) ivdScreenColorConfig.getBackgroundColor().getB());
			response.add((byte) ivdScreenColorConfig.getMessageColor().getR());
			response.add((byte) ivdScreenColorConfig.getMessageColor().getG());
			response.add((byte) ivdScreenColorConfig.getMessageColor().getB());
			for (TrainStatusColor trainStatusColor : listOfTrainStatusColor) {
				response.add((byte) DataUtil.getCommandPacketHeaderForColorStatus(trainStatusColor.getStatus(),
						trainStatusColor.getArrDep()));
				response.add((byte) trainStatusColor.getTrainNoColor().getR());
				response.add((byte) trainStatusColor.getTrainNoColor().getG());
				response.add((byte) trainStatusColor.getTrainNoColor().getB());
				response.add((byte) trainStatusColor.getTrainNameColor().getR());
				response.add((byte) trainStatusColor.getTrainNameColor().getG());
				response.add((byte) trainStatusColor.getTrainNameColor().getB());
				response.add((byte) trainStatusColor.getTrainTimeColor().getR());
				response.add((byte) trainStatusColor.getTrainTimeColor().getG());
				response.add((byte) trainStatusColor.getTrainTimeColor().getB());
				response.add((byte) trainStatusColor.getTrainADColor().getR());
				response.add((byte) trainStatusColor.getTrainADColor().getG());
				response.add((byte) trainStatusColor.getTrainADColor().getB());
				response.add((byte) trainStatusColor.getTrainPfColor().getR());
				response.add((byte) trainStatusColor.getTrainPfColor().getG());
				response.add((byte) trainStatusColor.getTrainPfColor().getB());
				logger.info("response :{}", response);
			}
			packetData = new byte[response.size()];

			for (int i = 0; i < response.size(); i++) {
				packetData[i] = response.get(i);
				System.out.println("packetData" + (i + 1) + "--------->" + response.get(i));
			}
			logger.info("command packet in hexa");
			for (int i = 0; i < response.size(); i++) {
				String s = dataUtil.decimalToHex1(response.get(i));
				System.out.println("byte no-" + (i + 1) + "----" + s);

			}
			return packetData;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return packetData;
		}

	}

	public byte[] getDefaultMessageCommandPacket(short lColumn, short rColumn, short tRow, short bRow, String data,
			DeviceType boardType, String speedValue, String effect, String letterSizeValue, int gap) {
		byte[] packetData = null;
		try {
			List<Byte> response = new ArrayList<>();

			byte[] lc = new byte[2];
			lc[0] = (byte) (lColumn >> 8);
			lc[1] = (byte) lColumn;
			response.add(lc[0]);
			response.add(lc[1]);

			byte[] rc = new byte[2];
			rc[0] = (byte) (rColumn >> 8);
			rc[1] = (byte) rColumn;
			response.add(rc[0]);
			response.add(rc[1]);

			byte[] tr = new byte[2];
			tr[0] = (byte) (tRow >> 8);
			tr[1] = (byte) tRow;
			response.add(tr[0]);
			response.add(tr[1]);

			byte[] br = new byte[2];
			br[0] = (byte) (bRow >> 8);
			br[1] = (byte) bRow;
			response.add(br[0]);
			response.add(br[1]);

			// Speed

			int rev = 0;
			int speed = dataUtil.getSpeed(speedValue);

			byte speed8 = (byte) (((rev & 0X01) << 7) | (speed & 0x07));

			response.add(speed8);

			// effectcode

			int effectCode = dataUtil.getEffectCode(effect);
			byte effect8 = (byte) (effectCode & 0x0f);

			response.add(effect8);

			// letter Size

			int letterSize = dataUtil.getFont(letterSizeValue);
			int gapFinal = dataUtil.getGap(gap);

			byte letter8 = (byte) (((letterSize & 0x07) << 3) | (gapFinal & 0x07));

			response.add(letter8);

			int timeDelay = 0x00;

			response.add((byte) timeDelay);

			short add = (short) addressOf(data);
			// System.out.println("start addresss in decimal------------->"+add);

			byte[] sadd = new byte[2];
			sadd[0] = (byte) (add >> 8);
			sadd[1] = (byte) add;

			response.add(sadd[0]);
			response.add(sadd[1]);

			Charset charset = StandardCharsets.UTF_16;
			byte[] defaultMessageArray = data.getBytes(charset);
			for (int i = 0; i < defaultMessageArray.length; i++) {
				response.add((byte) defaultMessageArray[i]);
			}

			int terminationByte1 = 0xFF;

			int terminationByte2 = 0xFF;

			response.add((byte) terminationByte1);
			response.add((byte) terminationByte2);

//			response.add((byte) DataUtil.getSpeed(speed));
//			response.add((byte) DataUtil.getEffectCode(effect));
//			response.add((byte) DataUtil.getFont(letterSize));
//			response.add((byte) DataUtil.getGap(gap));
			packetData = new byte[response.size()];

			for (int i = 0; i < response.size(); i++) {
				packetData[i] = response.get(i);
				System.out.println("packetDatasendDefaultmessage" + (i + 1) + "--------->" + response.get(i));
			}
			logger.info("command packet in hexa");
			for (int i = 0; i < response.size(); i++) {
				String s = dataUtil.decimalToHex1(response.get(i));
				System.out.println("sendDefaultmessage byte no-" + (i + 1) + "----" + s);

			}
			return packetData;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return packetData;
		}

	}

}
