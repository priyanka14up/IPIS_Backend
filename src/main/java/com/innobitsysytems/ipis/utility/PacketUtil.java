/**
 * Name: Amit Yadav
 * Copyright: Innobit Systems, 2021
 * Purpose: Packet Util
 */
package com.innobitsysytems.ipis.utility;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class PacketUtil {

    public byte[] ItoBA4(int value) {       // integer to bytes function (return byte array of 4 bytes)
        return new byte[]{
            (byte) (value >>> 24),
            (byte) (value >>> 16),
            (byte) (value >>> 8),
            (byte) value};
    }

    public byte[] ItoBA2(int value) {   // integer to bytes function (return byte array of 2 bytes)
        return new byte[]{
            (byte) (value >>> 8),
            (byte) value};
    }

    public byte[] ItoBA1(int value) {   // integer to bytes function (return byte array of 2 bytes)
        return new byte[]{
            (byte) value};
    }

    public byte getDSN() // return one byte random number
    {
        char[] chars = "1234567890".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 1; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        byte output = Byte.valueOf(sb.toString());
        return output;
    }

    public byte getCRC(byte[] packet) //  required CRC function (return byte)
    {
        try {
            if (packet == null) {
                //Logger.Error("empty packet received");
                return (byte) 0;
            }

            byte XORCheckSum = 0;
            byte zeroCount = 0;
            byte FFCount = 0;

            for (int i = 0; i < packet.length; i++) {
                XORCheckSum ^= packet[i];
                if (packet[i] == (byte) 0) {
                    zeroCount++;
                    continue;
                }
                if (packet[i] == (byte) 255) {
                    FFCount++;
                    continue;
                }
            }

            XORCheckSum ^= zeroCount;
            XORCheckSum ^= FFCount;
            return XORCheckSum;
        } catch (Exception ex) {
            //Logger.Error(ex);
            return (byte) 0;
        }
    }

    public byte[] concatBytes(byte[]... arrays) //  concatenate byte arrays
    {
        // Determine the length of the result array
        int totalLength = 0;
        for (int i = 0; i < arrays.length; i++) {
            totalLength += arrays[i].length;
        }

        // create the result array
        byte[] result = new byte[totalLength];

        // copy the source arrays into the result array
        int currentIndex = 0;
        for (int i = 0; i < arrays.length; i++) {
            System.arraycopy(arrays[i], 0, result, currentIndex, arrays[i].length);
            currentIndex += arrays[i].length;
        }

        return result;
    }

    public byte[] combineBytes(byte[] arr1, byte[] arr2) //  concatenate byte arrays
    {

        // create the result array
        byte[] result = new byte[arr1.length + arr2.length];
        int index = 0;
        
        System.arraycopy(arr1, 0, result, 0, arr1.length);
        System.arraycopy(arr2, 0, result, index, arr2.length);
    
        return result;
    }

    public int getDateTime() {
        int dateInSec = (int) (System.currentTimeMillis() / 1000);
        return dateInSec;

    }

    public static byte crc16ccitt(byte[] bytes, String resultType) {
    	
        int crc = 0xffff;          // initial value
        final int polynomial = 0x1021;   // 0001 0000 0010 0001  (0, 5, 12)
        byte res = 0;

        for (byte b : bytes) {
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b >> (7 - i) & 1) == 1);
                boolean c15 = ((crc >> 15 & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit) {
                    crc ^= polynomial;
                   
                }
            }
        }
        System.out.println("crc"+crc);
        crc &= 0xffff; // 16 bits only
        final byte[] ret = new byte[2];
        ret[0] = (byte) (crc & 0xff);
        ret[1] = (byte) (crc >> 8 & 0xff); // little endian
        if (resultType.equals("MSB")) {
            res = ret[1];
        } else if (resultType.equals("LSB")) {
            res = ret[0];
        }

        return res;
    }

}
