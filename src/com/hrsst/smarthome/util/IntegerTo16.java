package com.hrsst.smarthome.util;

public class IntegerTo16 {

	 public byte algorismToHEXString(int algorism) {
        String result = "";
        result = Integer.toHexString(algorism);

        if (result.length() % 2 == 1) {
            result = "0" + result;

        }
        result = result.toUpperCase();
        
        return (byte) Integer.parseInt(result, 16);
    }
	 
	 public byte str16ToByte(String result){
		 
		 if (result.length() % 2 == 1) {
	            result = "0" + result;
	        }
        result = result.toUpperCase();
        return (byte) Integer.parseInt(result, 16);
	 }
}
