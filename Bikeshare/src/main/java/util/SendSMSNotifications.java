package util;


import java.util.HashMap;
import java.util.Map;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.TwilioRestResponse;
import com.twilio.sdk.resource.factory.SmsFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Sms;
/**
 * Created by gaurav on 12/2/14.
 */
public class SendSMSNotifications {
    public static final String ACCOUNTSID = "ACbc8a775065e604105d99a98574922532";
    public static final String AUTHTOKEN = "8ae53b90322991f812a198f959b266fd";
    public static final String twilioNumber = "650-900-8329";

    public static void sendSMSOnReservation(String toPhoneNumber,String Receiver, String location_name, int from_hour, int to_hour, String bike_name){

        String toNumber = toPhoneNumber;

        //build map of post parameters
        Map<String,String> params = new HashMap<String,String>();
        params.put("From", twilioNumber);
        params.put("To", toNumber);
        params.put("Body", "Bike Reserved.\n" +
        		"Location for bike pickup: "+location_name+" \n" +
        		"Bike "+bike_name+" reserved from "+from_hour+" to "+to_hour+"."+
                "\nHave a safe ride!");

        sendSMSUsingTwilio(params);
    }
    
    public static void sendSMSOnCancellation(String toPhoneNumber,int booking_id){

        String toNumber = toPhoneNumber;

        //build map of post parameters
        Map<String,String> params = new HashMap<String,String>();
        params.put("From", twilioNumber);
        params.put("To", toNumber);
        params.put("Body", "Your bike reservation with booking id: "+booking_id+" has been cancelled!");


        sendSMSUsingTwilio(params);
    }
    

    public static void sendSMSOnSignUp(String toPhoneNumber,String Receiver){

        String toNumber = toPhoneNumber;

        //build map of post parameters
        Map<String,String> params = new HashMap<String,String>();
        params.put("From", twilioNumber);
        params.put("To", toNumber);
        params.put("Body", "Hi "+Receiver+"! Welcome to Spartan Bike Share. Book bikes at very cheap rates!");

        sendSMSUsingTwilio(params);
    }

    public static void sendSMSUsingTwilio(Map<String,String> params){
        try {
            TwilioRestClient client = new TwilioRestClient(ACCOUNTSID, AUTHTOKEN);
            Account acct = client.getAccount();
            SmsFactory smsFactory = acct.getSmsFactory();

            Sms sms = smsFactory.create(params);
            //System.out.println("Success sending SMS: " + sms.getSid());
        }
        catch (TwilioRestException e) {
            e.printStackTrace();
        }
    }


}
