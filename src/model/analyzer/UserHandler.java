package model.analyzer;

import model.beans.User;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import constants.Constants;

public class UserHandler  extends DefaultHandler{

	private String role;
	private User user;
	private UserEnum currentEnum;
	private String email;
	private String password;
	
	private static enum UserEnum{ 
		ROLE, EMPLOYEE;
	}

	public UserHandler(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	public User getUser() {
		return user;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attrs){
	
		final int indexAttrLastName  = 0;
		final int indexAttrFirstName = 1;
		final int indexAttrEmail     = 2;
		final int indexAttrPassword  = 3;
		
		if (!Constants.USERS.equals(qName) && !Constants.USER.equals(qName) && 
				!Constants.EMPLOYEES.equals(qName)) {	
			
			currentEnum = UserEnum.valueOf(qName.toUpperCase());
			switch (currentEnum) {
				case EMPLOYEE:{
					if (attrs.getValue(indexAttrEmail).equals(email) && attrs.getValue(indexAttrPassword).equals(password)){
						user = new User();
						user.setRole(role);
						user.setLastName(attrs.getValue(indexAttrLastName));
						user.setFirstName(attrs.getValue(indexAttrFirstName));
						user.setEmail(attrs.getValue(indexAttrEmail));
						user.setPassword(attrs.getValue(indexAttrPassword));
					}
					currentEnum = null;
					break;
				}
				default:
					break;
			}
		}
	}
	
	public void characters(char[] ch, int start, int length) {
		String temp = new String (ch, start, length).trim();
	
		if (currentEnum == null) {
			return;
		}
		switch (currentEnum) {
			case ROLE: {
				role = temp;
				break;
			}
			default:
				break;
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName){
		currentEnum = null;
	}
}
	

