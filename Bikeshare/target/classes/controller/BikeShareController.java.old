package controller;

import org.hibernate.validator.constraints.Email;
import org.joda.time.DateTime;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.annotation.Configuration;

import interceptors.SessionValidatorInterceptor;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import resources.Bike;
import resources.LocationEntity;
import resources.LocationInventory;
import resources.Login;
import resources.User;
import resources.Transactions;
import util.BikeShareUtil;
import util.EmailNotification;
import util.SendSMSNotifications;

import java.util.*;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;

import javax.annotation.Resource;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Location;

import DTO.LoginDTO;
import DTO.UserDTO;
import DTO.BookingDTO;
import bikeshareimpl.AuthInterfaceImpl;
import bikeshareimpl.BikeOperationsImpl;
import bikeshareimpl.LocationEntityOperations;
import bikeshareimpl.LocationInvetoryOperations;
import bikeshareimpl.LoginDAOImpl;
import bikeshareimpl.TransactionsImpl;
import bikeshareimpl.UserDAOImpl;
import bikeshareimpl.UserOperationsImpl;
import bikeshareinterfaces.AuthInterface;
import bikeshareinterfaces.BikeOperationsInterface;
import bikeshareinterfaces.LocationEntityInterface;
import bikeshareinterfaces.LocationInventoryInterface;
import bikeshareinterfaces.TransactionsInterface;
import bikeshareinterfaces.UserOperationInterface;

@Component
@EnableAutoConfiguration
@RestController
@RequestMapping("/api/v1/*")
public class BikeShareController  extends WebMvcConfigurerAdapter{

	UserOperationInterface userOperationInterface = new UserOperationsImpl();
	AuthInterface authInterface = new AuthInterfaceImpl();
	LocationInventoryInterface LocationInventoryOps = new LocationInvetoryOperations();
	public static String globalReservationIndicator = "RESERVED";
	public static String globalCancellationIndicator = "CANCELLED";
	public static String globalAvailableIndicator = "AVAILABLE";
	private static int UserCreationCredit = 5;
	private static int userReservationCredit = 1;
	private static int freeRideCredit = 10;
	
	
	@Bean
	public FilterRegistrationBean shallowEtagHeaderFilter() {

		ShallowEtagHeaderFilter shallowEtagHeaderFilter = new ShallowEtagHeaderFilter();
		FilterRegistrationBean etagBean = new FilterRegistrationBean();
		etagBean.setFilter(shallowEtagHeaderFilter);
		ArrayList<String> urlPatterns = new ArrayList<String>();
		urlPatterns.add("/api/v1/users/*");
		etagBean.setUrlPatterns(urlPatterns);
		return etagBean;
	}
	
    @Bean
    public SessionValidatorInterceptor sessionValidatorInterceptor() {
        return new SessionValidatorInterceptor();
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionValidatorInterceptor()).addPathPatterns(	"/api/v1/users/*");
        registry.addInterceptor(sessionValidatorInterceptor()).addPathPatterns(	"/api/v1/loggedin");
    }
    
    //check unique username
    @ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/checkUniqueUsername/{user_name}", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkUniqueUsername(@PathVariable String user_name) {
		return userOperationInterface.checkUniqueUsername(user_name);
	}
    
  //check unique email
    @ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/checkUniqueEmail/{email:.+}", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkUniqueEmail(@PathVariable String email) {
		return userOperationInterface.checkUniqueEmail(email);
	}
    
    
    
    //logged in
    
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/loggedin", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkLoggedIn(){
    	return true;
    }
         
   	// User Related Operations
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	@ResponseBody
	public UserDTO createUser(@Valid @RequestBody UserDTO user) {
		
		user.setPassword(BikeShareUtil.passwordEncrypter(user.getPassword()));
		user.setCredits_earned(UserCreationCredit);
		
				
		SendSMSNotifications.sendSMSOnSignUp(user.getPhone(), user.getName());
		try{
			EmailNotification.sendEmailonSignUp(user.getEmail(), user.getUsername());
		}catch(UnsupportedEncodingException e)
		{
			System.out.println(e.getMessage());
		}		
		return userOperationInterface.createUser(user);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "/users/{user_id}", method = RequestMethod.PUT)
	@ResponseBody
	public UserDTO updateUser(@PathVariable String user_id,
			@Valid @RequestBody UserDTO user) {
		return userOperationInterface.updateUser(user_id, user);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/users/{user_id}", method = RequestMethod.GET)
	@ResponseBody
	public UserDTO getUser(@PathVariable String user_id) {
		return userOperationInterface.getUser(user_id);
	}

	@RequestMapping("/login")
	@ResponseBody
	private LoginDTO login(@Valid @RequestBody LoginDTO loginDTO, HttpServletResponse response) {
		loginDTO = authInterface.login(loginDTO);
        response.addCookie(new Cookie("sessionid", loginDTO.getSessionId()));
        response.addCookie(new Cookie("username", loginDTO.getUsername()));
        response.addCookie(new Cookie("user_id", Integer.toString(loginDTO.getUser_id())));
        return loginDTO;
	}
	
	@RequestMapping("/logout")
	@ResponseBody
	private boolean logout(HttpServletResponse response) {
		
        response.addCookie(new Cookie("sessionid", ""));
        response.addCookie(new Cookie("username", ""));
        response.addCookie(new Cookie("user_id", ""));
        return true;
	}
	
	
	// Location Inventory Related Function - To be used for Checking Availability, Bike Reservation and Cancellation. 
	@RequestMapping("/availability")
	@ResponseBody
	private Bike[] checkAvailability(@RequestBody BookingDTO bookingDTO, HttpServletResponse response) {
		
		int location_id = bookingDTO.getLocation_id();
		int fromHour= bookingDTO.getFromHour();
		int toHour = bookingDTO.getToHour();
		
		Bike [] bikes;				
		
		bikes = LocationInventoryOps.getAvailableBikes(location_id, fromHour, toHour);
				
		
		if(bikes!=null && bikes.length > 0 && bikes[0]==null){
			return null;
		}
		
		return bikes;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping("/reserve")
	@ResponseBody
	private BookingDTO updateInvForReservation(@RequestBody BookingDTO bookingDTO, @CookieValue("user_id") int user_id) 
	{
		
		int location_id = bookingDTO.getLocation_id();
		int fromHour= bookingDTO.getFromHour();
		int toHour = bookingDTO.getToHour();
		String bikeID = bookingDTO.getBike_id();
		BookingDTO booking = new BookingDTO();
		
		BikeOperationsInterface bikeOps = new BikeOperationsImpl();
		Bike bike = new Bike();
		bike = bikeOps.getBike(bikeID);
		
				
		Transactions tx = new Transactions();
		LocationEntity loc = new LocationEntity();
		User user = new User();
		
		
		TransactionsInterface txOps = new TransactionsImpl();
		LocationEntityInterface locOps = new LocationEntityOperations();
		UserDAOImpl userOps = new UserDAOImpl();
		
		booking = LocationInventoryOps.updateInvForReservation(location_id, fromHour, toHour, bikeID);
		
		if(booking.isReserve_success())
		{
			
			bikeOps.updateBikeStatusToReserved(bikeID);			
			loc = locOps.getLocation(location_id);
			user = userOps.getObject(user_id+"");
			bike.setStatus(globalReservationIndicator);
			
			tx.setBooked_bike_id(bikeID);
			tx.setLocation_id(location_id);
			tx.setFrom_hour(fromHour);
			tx.setTo_hour(toHour);
			tx.setUser_id(user_id);
			tx.setTransaction_id(BikeShareUtil.generateTransactionID(location_id, fromHour, toHour));
			if(bike!=null)
			{	
			tx.setBikename(bike.getBikename());
			tx.setBiketype(bike.getType());
			tx.setBikeStatus(bike.getStatus());
			tx.setPrice(bike.getPrice());
			}
			if(loc!=null)
			{	
			tx.setCity(loc.getCity());
			tx.setLocation_name(loc.getLocation_name());
			tx.setZipcode(loc.getZipcode());
			tx.setState(loc.getState());
			}
			
			if(user!=null)
			{	
			
			if(user.getCredits_earned() >= freeRideCredit)	
			{
				tx.setFreeRide(true);
				booking.setFreeRide(true);
				tx.setCredit_used(true);
				user.setCredits_earned(user.getCredits_earned() - freeRideCredit); 
			}
			else
			{
				tx.setFreeRide(false);
				booking.setFreeRide(false);
				tx.setCredit_used(false);
				user.setCredits_earned(user.getCredits_earned() + userReservationCredit);
				tx.setCredited_amount(userReservationCredit);
			}	
			
			}
			
			tx.setLocation_name(loc.getLocation_name());
			tx.setZipcode(loc.getZipcode());
			tx.setState(loc.getState());
			
			userOps.updateObject(user);
			txOps.addTransaction(tx);
			
			booking.setTransaction_id(tx.getTransaction_id());
			booking.setUser_id(user_id);
			booking.setSuccessMessage(globalReservationIndicator);
			
			System.out.println("phone no "+user.getPhone());
								
			SendSMSNotifications.sendSMSOnReservation(user.getPhone(), user.getName(), tx.getLocation_name(), tx.getFrom_hour(), tx.getTo_hour(), tx.getBikename());
			try{
				EmailNotification.sendEmailonReservation(user.getEmail(), user.getName(), tx.getLocation_name(), fromHour, toHour, tx.getTransaction_id(), tx.getBikename(), ("E" + BikeShareUtil.getRandomInteger()));
			}catch(UnsupportedEncodingException e)
			{
				System.out.println(e.getMessage());
			}
		}	

		return booking;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping("/cancel")
	@ResponseBody
	private BookingDTO updateInvForCancellation(@RequestBody BookingDTO bookingDTO, @CookieValue("user_id") int user_id)
	{
		TransactionsInterface txOps = new TransactionsImpl();
		
		Transactions txn = new Transactions();
		User user = new User();
		
		txn = txOps.getTransaction(bookingDTO.getTransaction_id());
	
		if(txn.getBooking_status().equalsIgnoreCase(globalReservationIndicator))
		{	
		int location_id = txn.getLocation_id();
		int fromHour= txn.getFrom_hour();
		int toHour = txn.getTo_hour();
		String bikeID = txn.getBooked_bike_id();
		
		BookingDTO booking = new BookingDTO();
		BikeOperationsInterface bikeOps = new BikeOperationsImpl();
		UserDAOImpl userOps = new UserDAOImpl();
		
		booking = LocationInventoryOps.updateInvForCancellation(location_id, fromHour, toHour, bikeID);
	
		if(booking.isCancel_success())
		{
			user = userOps.getObject(user_id+"");
			
			if(txn.isFreeRide())
			{ 
				user.setCredits_earned(user.getCredits_earned() + freeRideCredit);
				txn.setCredited_amount(freeRideCredit);	
			}
			else
			{ 
				user.setCredits_earned(user.getCredits_earned() - userReservationCredit);	
				txn.setCredited_amount(userReservationCredit);
			}
						
			bikeOps.updateBikeStatusToAvailable(bikeID);
					
			txOps.updateTransaction(bookingDTO.getTransaction_id());
			userOps.updateObject(user);
			booking.setTransaction_id(bookingDTO.getTransaction_id());
			booking.setUser_id(user_id);
			booking.setSuccessMessage(globalCancellationIndicator);
			
			SendSMSNotifications.sendSMSOnCancellation(user.getPhone(), bookingDTO.getTransaction_id());
			try{
				EmailNotification.sendEmailonCancellation(user.getEmail(), user.getName(), txn.getLocation_name(), txn.getFrom_hour(), txn.getTo_hour(), txn.getTransaction_id());
			}catch(UnsupportedEncodingException e)
			{
				System.out.println(e.getMessage());
			}
		}
		return booking;
		}
		else {return null;}	
	}
	
	@RequestMapping(value = "/transactions", method = RequestMethod.GET )
	@ResponseBody
	private Transactions [] getTransactions(@CookieValue("user_id") int user_id) 
	{ 		
		List<Transactions> txList = new ArrayList<Transactions>();

		TransactionsInterface txOps = new TransactionsImpl();	

		DateTime now = DateTime.now();

		int hourOfTheDay = now.getHourOfDay();
		Transactions [] tx;
		txList =  txOps.getUserTransactions(user_id);

		int size = txList.size();

		tx = new Transactions[size];

		for(int i=0; i<size; i++)

		{ 

			if(txList.get(i).getDate_of_booking().getDayOfYear() >= now.getDayOfYear()) 
			{	
				if (txList.get(i).getFrom_hour() > hourOfTheDay)
				{
					txList.get(i).setActive_booking(true);
				}
			}
			else
			{
				txList.get(i).setActive_booking(false);
			}

			tx[i] = txList.get(i);
		}


		return tx;
	}
	
	
	@RequestMapping(value = "/locations", method = RequestMethod.GET )
	@ResponseBody
	private LocationEntity [] getLocations( )
	{ 		
		List<LocationEntity> locationList = new ArrayList<LocationEntity>();
		
		LocationEntityInterface locOps = new LocationEntityOperations();	

		LocationEntity [] locations;
		locationList =  locOps.getLocations();
						
		int size = locationList.size();
		
		locations = new LocationEntity[size];
		
		for(int i=0; i<size; i++)
		
		{ 
			locations[i] = locationList.get(i);
		}
		
		return locations;
	}
	
	
	@RequestMapping("/bikes")
	@ResponseBody
	private Bike [] getBikes( )
	{ 		
		List<Bike> bikeList = new ArrayList<Bike>();
		
		BikeOperationsInterface bikeOps = new BikeOperationsImpl();	

		Bike [] bikess;
		bikeList =  bikeOps.getAllBikes();
						
		int size = bikeList.size();
		
		bikess = new Bike[size];
		
		for(int i=0; i<size; i++)
		
		{ 
			bikess[i] = bikeList.get(i);
		}
		
		return bikess;
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/androidauth", method = RequestMethod.GET)
	@ResponseBody
	public boolean androidlogin(@RequestParam String uname, String upass) {

		String username = uname;
		String password= upass;
		boolean success = false;

		Login login = new Login();
		LoginDAOImpl loginOps = new LoginDAOImpl();

		login = loginOps.getUserBasedOnUsername(username);
				
		if(login==null)
		{
			System.out.println("Login came null !");
			return false;
		}
		else{
			System.out.println(login.getUsername());
			System.out.println(login.getPassword());
			System.out.println(BikeShareUtil.passwordEncrypter(password));
			if (login.getPassword().equals(BikeShareUtil.passwordEncrypter(password)))
			{
				System.out.println(login.getUsername());
				return true;
			//	return success;
			}
			else
			{
				System.out.println("Password did not match !");
				return false;
				//return success;
			}
			
		}
	
		
	}	


	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/android", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkAvailability(@RequestParam int location, int from, int to, String uname) {

		int location_id = location;
		int fromHour= from;
		int toHour = to;
		String username = uname;
		/*****************************************************************/	
		Login login = new Login();
		User user = new User();
		LoginDAOImpl loginOps = new LoginDAOImpl();
		UserDAOImpl userOps = new UserDAOImpl();

		login = loginOps.getUserBasedOnUsername(username);

		if(login==null)
		{
			System.out.println("Entered in user == null");
			user=null;
			return false;
		}
		else
		{
			
			user= userOps.getObject(login.getUser_id()+"");
			System.out.println("Entered else " +user.getName() );
		}


		/*****************************************************************/	


		Bike [] bikes;				

		bikes = LocationInventoryOps.getAvailableBikes(location_id, fromHour, toHour);


		if(bikes!=null && bikes.length > 0 && bikes[0]==null){
			return false;
		}
		else
		{	
			if(bikes==null)
			{
				
				return false;
			}
			else{
				
				
				BookingDTO booking = new BookingDTO();

				BikeOperationsInterface bikeOps = new BikeOperationsImpl();
				Bike bike = new Bike();

				bike = bikeOps.getBike(bikes[0].getBike_id());

				String bikeID = bikes[0].getBike_id();

				Transactions tx = new Transactions();
				LocationEntity loc = new LocationEntity();

				TransactionsInterface txOps = new TransactionsImpl();
				LocationEntityInterface locOps = new LocationEntityOperations();

				booking = LocationInventoryOps.updateInvForReservation(location, from, to, bikeID);

				if(booking.isReserve_success())
				{
					loc = locOps.getLocation(location_id);

					tx.setBooked_bike_id(bikeID);
					tx.setLocation_id(location_id);
					tx.setFrom_hour(fromHour);
					tx.setTo_hour(toHour);
					tx.setUsername(user.getName());
					tx.setTransaction_id(BikeShareUtil.generateTransactionID(location_id, fromHour, toHour));
					if(bike!=null)
					{	
						tx.setBikename(bike.getBikename());
						tx.setBiketype(bike.getType());
						tx.setBikeStatus(bike.getStatus());
						tx.setPrice(bike.getPrice());
					}
					if(loc!=null)
					{	
						tx.setCity(loc.getCity());
						tx.setLocation_name(loc.getLocation_name());
						tx.setZipcode(loc.getZipcode());
						tx.setState(loc.getState());
					}
					tx.setLocation_name(loc.getLocation_name());
					tx.setZipcode(loc.getZipcode());
					tx.setState(loc.getState());

					if(user.getCredits_earned() >= freeRideCredit)	
					{
						tx.setFreeRide(true);
						booking.setFreeRide(true);
						tx.setCredit_used(true);
						user.setCredits_earned(user.getCredits_earned() - freeRideCredit); 
					}
					else
					{
						tx.setFreeRide(false);
						booking.setFreeRide(false);
						tx.setCredit_used(false);
						user.setCredits_earned(user.getCredits_earned() + userReservationCredit);
						tx.setCredited_amount(userReservationCredit);
					}	

					userOps.updateObject(user);
					txOps.addTransaction(tx);

					SendSMSNotifications.sendSMSOnReservation(user.getPhone(), user.getName(), tx.getLocation_name(), tx.getFrom_hour(), tx.getTo_hour(), tx.getBikename());
					try{
						EmailNotification.sendEmailonReservation(user.getEmail(), user.getName(), tx.getLocation_name(), fromHour, toHour, tx.getTransaction_id(), tx.getBikename(), ("E" + BikeShareUtil.getRandomInteger()));
					}
					catch(UnsupportedEncodingException e)
					{
						System.out.println(e.getMessage());
					}

				}	
			}

			return true;	
		}

	}
}
	
	
	
	
	
	

	
		
		
/*General Purpose Function to Load Location DataBaste - Please don't remove
	
	@RequestMapping("/loadDatabase")
	@ResponseBody
	private void loadTable() {
		
		LocationEntity loc = new LocationEntity();
		LocationEntityInterface locOps = new LocationEntityOperations();
		int location_id = 95112;
		
		for(int i=0; i<10;i++)
		{
			loc.setLocation_id(location_id);
			loc.setCity("San Jose");
			loc.setZipcode(location_id);
			loc.setState("California");
			loc.setLatitude("Something");
			loc.setLongitude("Something");
			loc.setLocation_capacity(11);
						
			location_id += 3;
			
			locOps.addLocation(loc);
		}
	
	
	}
}


@RequestMapping("/onebike")
	@ResponseBody
	private Bike getBike( )
	{ 		
		List<Bike> bikeList = new ArrayList<Bike>();
		
		BikeOperationsInterface bikeOps = new BikeOperationsImpl();	

		Bike [] bikess;
		bikeList =  bikeOps.getAllBikes();
						
		int size = bikeList.size();
		
		bikess = new Bike[size];
		
		for(int i=0; i<size; i++)
		
		{ 
			bikess[i] = bikeList.get(i);
		}
		
		return bikess[0];
	}
	
		
	//General Purpose Function to Load Location DataBaste - Please don't remove
	@RequestMapping("/loadDatabase")
	@ResponseBody
	private void loadTable() {
		
		LocationInvetoryOperations.loadDatabase();
		
	}
	
	
	/*
	//General Purpose Function to test Location Inventory - Please don't remove
	@RequestMapping("/getLocation")
	@ResponseBody
	private String[] getLocaiton() {
	
		LocationInventoryInterface lop = new LocationInvetoryOperations();
		
			
	//	LocationInventory loc = new LocationInventory();
		String [] hour = null;
		
		try {
			hour = lop.getInvForAnHour(900, 5);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//LocationInvetoryOperations.loadDatabase();
	
		return hour;
	
	}
	
	
	//General Purpose Function to Load Bikes - Please don't remove
	@RequestMapping("/addBikes")
	@ResponseBody
	private void addBikes() {
	
		Bike bike = new Bike();
		BikeOperationsImpl impl = new BikeOperationsImpl();
		
		for(int i= 900; i<=910; i++)
		{
			bike.setBike_id(""+i);
			bike.setBikename("A-One");
			bike.setPrice(""+(i*10));
			bike.setStatus("Available");
			bike.setType("Classic");
			impl.addBike(bike);
*/

	
	
	


	