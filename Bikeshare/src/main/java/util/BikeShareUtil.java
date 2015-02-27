package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.Cookie;

import DTO.UserDTO;
import resources.User;

public class BikeShareUtil {

	//need to change this random number logic - logic using current timestamp
	public static int getRandomInteger(){
		Random random = new Random();
		return random.nextInt(Integer.MAX_VALUE)  + 1 ;
	}
	
	public static String[] covertObjectArrayToStringArray(ArrayList<String> input){
		String output[] = new String[input.size()];
		if(input != null){
			for(int i = 0; i < input.size(); i++){
				output[i] = input.get(i);
			}
		}
		return output;
	}
	
	public static Map<String,String> getCookiesValue(Cookie[] cookies){
		Map<String,String> coockieMap = new HashMap<String, String>();
		if(cookies != null){
			for(Cookie cookie : cookies){
				coockieMap.put(cookie.getName(),cookie.getValue());
			}
		}
		return coockieMap;
	}
	
	public static String[] validateCreateUser(UserDTO userDTO){
		ArrayList<String> message = new ArrayList<String>();
		if(userDTO == null ) message.add("please provide user data");
		if(userDTO.getName() == null || userDTO.getName().trim() == "") message.add("name required");
		if(userDTO.getEmail() == null || userDTO.getEmail().trim() == "") message.add("email required");
		if(userDTO.getAddress() == null || userDTO.getAddress().trim() == "") message.add("address required");
		if(userDTO.getPhone() == null || userDTO.getPhone().trim() == "") message.add("phone required");
		if(userDTO.getZipcode() == null || userDTO.getZipcode().trim() == "") message.add("zipcode required");
		if(userDTO.getUsername() == null || userDTO.getUsername().trim() == "") message.add("username required");
		if(userDTO.getPassword() == null || userDTO.getPassword().trim() == "") message.add("password required");
		return covertObjectArrayToStringArray(message);
	}

	public static String generateRandomBikeId(){
		Random random = new Random();
		return "b-"+random.nextInt(Integer.MAX_VALUE)  + 1 ;
	}
	
	public static int generateTransactionID (int location_id, int fromHour, int toHour)
	
	{
		int transactionID = 0;
		
		int maximum = 10000;
		int mininmum = 1000;
		
		Random rn = new Random();
		int range = maximum - mininmum + 1;
		int randomNum =  rn.nextInt(range) + mininmum;
		
		transactionID = location_id + fromHour + toHour + randomNum;
		
		return transactionID;
	
	}
	
	public static String passwordEncrypter(String password)
	{
		String generatedDigest = null;
		
		try{
			MessageDigest md = MessageDigest.getInstance("MD5");

			md.update(password.getBytes());

			byte[] bytes = md.digest();

			StringBuilder sb = new StringBuilder();
			for(int i=0; i< bytes.length ;i++)
			{
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}

			generatedDigest = sb.toString();
		}catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		
		return generatedDigest;
	}
	


}
