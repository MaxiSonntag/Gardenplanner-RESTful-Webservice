package service.resource.caching;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.xml.bind.DatatypeConverter;


public class ETagGenerator {
	
	public EntityTag generateEtag(String obj){
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
			byte[] hash = digest.digest(obj.getBytes(StandardCharsets.UTF_8));
	        String hex = DatatypeConverter.printHexBinary(hash);
	        EntityTag etag = new EntityTag(hex);
	        return etag;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new EntityTag("Error");
		}
	}
		
}
