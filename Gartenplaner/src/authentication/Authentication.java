package authentication;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import model.Constants;
import requests.APIRequest;

public class Authentication {
	
	private String createForgeryStateToken(HttpServletRequest request){
		  String state = new BigInteger(130, new SecureRandom()).toString(32);
		  //request.session().attribute("state", state);
		  request.getSession().setAttribute("state", state);
		  
		  
		  return state;
		  
	}
	
	public String getRequestToGoole(HttpServletRequest request){
		String[] paramNames = {"client_id", "response_type", "scope", "redirect_uri",
				"state", "login_hint", "nonce", "hd"};
		String nonce = UUID.randomUUID().toString();
		String[] paramValues = {Constants.CLIENT_ID, "code", "openid%20email", Constants.CALLBACK_URL, createForgeryStateToken(request), "A @googlemail address", nonce, "googlemail.de"};
		String[][] params = {paramNames, paramValues};
		String resp = new APIRequest().loadDataFromApi(Constants.AUTHENTICATION_API, params);
		return resp;
	}

}