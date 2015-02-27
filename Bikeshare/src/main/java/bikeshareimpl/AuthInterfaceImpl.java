package bikeshareimpl;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import resources.Login;
import resources.User;
import util.BikeShareUtil;
import DTO.LoginDTO;
import bikeshareinterfaces.AuthInterface;

public class AuthInterfaceImpl implements AuthInterface{

	LoginDAOImpl loginDaoImpl = new LoginDAOImpl();
	
	@Override
	public LoginDTO login(LoginDTO loginDTO) {
		Login logindao = new Login();
		try{
			BeanUtils.copyProperties(logindao, loginDTO);
			//check username and password
			Login login = loginDaoImpl.getObject(logindao.getUsername(), logindao.getPassword());
			if(login == null){
				loginDTO.setMessage("Invalid Username/Password");
			}else{
				//generate sessionId
				int sessionId = BikeShareUtil.getRandomInteger();
				login.setSessionId(Integer.toString(sessionId));
				loginDaoImpl.updateObject(login);
				//set session id in header
				loginDTO.setSessionId(Integer.toString(sessionId));
				loginDTO.setUser_id(login.getUser_id());
				loginDTO.setMessage("Login Sucessfully");
			}
		}catch(IllegalAccessException e){
			e.printStackTrace();
		}catch(InvocationTargetException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return loginDTO;
		
	}

}
