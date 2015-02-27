package interceptors;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import resources.Login;
import resources.User;
import util.BikeShareUtil;
import bikeshareimpl.LoginDAOImpl;

public class SessionValidatorInterceptor implements HandlerInterceptor{

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res,
			Object arg2) throws Exception {
		//retrieve cookie here
		boolean loggedIn = false;
		Cookie[] cookies = req.getCookies();
		if(cookies != null){
			Map<String,String> coockieMap = BikeShareUtil.getCookiesValue(cookies);
			String username = coockieMap.get("username");
			String user_id = coockieMap.get("user_id");
			String sessionid = coockieMap.get("sessionid");
			System.out.println("username "+username);
			System.out.println("sessionid "+sessionid);
			LoginDAOImpl loginDaoImpl = new LoginDAOImpl();
			Login login = loginDaoImpl.getObjectOnSession(username, sessionid);
			if(login != null) loggedIn = true;
			System.out.println("loggedin "+loggedIn);
		}
		return loggedIn;
	}

}
