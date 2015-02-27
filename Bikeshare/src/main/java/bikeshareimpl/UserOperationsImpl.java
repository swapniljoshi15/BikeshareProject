package bikeshareimpl;

import java.lang.reflect.InvocationTargetException;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;

import DTO.UserDTO;
import bikeshareinterfaces.UserOperationInterface;
import resources.Login;
import resources.User;
import util.BikeShareUtil;

@Component
@EnableAutoConfiguration
public class UserOperationsImpl implements UserOperationInterface{

	
	UserDAOImpl userDaoImpl = new UserDAOImpl();
	LoginDAOImpl loginDaoImpl = new LoginDAOImpl();
	
	@Override
	public UserDTO createUser(UserDTO userDTO) {
		// TODO Auto-generated method stub
		User userdao = new User();
		Login logindao = new Login();
		try{
		//validate 
		String[] messages = BikeShareUtil.validateCreateUser(userDTO);
		if(messages.length > 0){
			userDTO.setMessage(messages);
			return userDTO;
		}
		int user_id = BikeShareUtil.getRandomInteger();
		System.out.println("random no "+user_id);
		userDTO.setUser_id(user_id);
		BeanUtils.copyProperties(userdao, userDTO);
		BeanUtils.copyProperties(logindao, userDTO);
		userDaoImpl.saveObject(userdao);
		loginDaoImpl.saveObject(logindao);
		}catch(IllegalAccessException e){
			e.printStackTrace();
		}catch(InvocationTargetException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return userDTO;
	}
	
	@Override
	public UserDTO updateUser(String user_id, UserDTO userDTO) {
		// TODO Auto-generated method stub
		User userdao = new User();
		Login logindao = new Login();
		try{
			userDTO.setUser_id(Integer.parseInt(user_id));
			BeanUtils.copyProperties(userdao, userDTO);
			BeanUtils.copyProperties(logindao, userDTO);
			userDaoImpl.updateObject(userdao);
			loginDaoImpl.updateObject(logindao);
		}catch(IllegalAccessException e){
			e.printStackTrace();
		}catch(InvocationTargetException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return userDTO;
	}

	@Override
	public UserDTO getUser(String user_id) {
		UserDTO userDTO = new UserDTO();
		try{
		User userdao = userDaoImpl.getObject(user_id);
		Login logindao = loginDaoImpl.getObject(user_id);
		BeanUtils.copyProperties(userDTO, userdao);
		userDTO.setUsername(logindao.getUsername());
		}catch(IllegalAccessException e){
			e.printStackTrace();
		}catch(InvocationTargetException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return userDTO;
	}

	@Override
	public boolean checkUniqueUsername(String user_name) {
		boolean isValidUsername = false;
		try{
			Login logindao = loginDaoImpl.getUserBasedOnUsername(user_name);
			System.out.println(logindao);
			if(logindao == null){
				isValidUsername = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return isValidUsername;
	}
	
	@Override
	public boolean checkUniqueEmail(String email) {
		boolean isValidUseremail = false;
		try{
			User userdao = userDaoImpl.getUserBasedOnEmail(email);
			System.out.println(email+" email "+userdao);
			if(userdao == null){
				isValidUseremail = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return isValidUseremail;
	}
	
}
