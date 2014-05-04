package model.service;

import java.io.IOException;
import java.io.InputStream;

import model.analyzer.UserHandler;
import model.beans.User;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import org.apache.log4j.Logger;

import constants.Constants;
import constants.Messages;
import exceptions.DaoException;
import exceptions.VerificationException;

public class UserXML {
	private Logger logger = Logger.getLogger(UserXML.class);
	private InputStream stream;
	
	public UserXML(InputStream stream) {
		super();
		this.stream = stream;
	}

	public User getUser(String email, String password) throws DaoException,VerificationException {
	    User user = null;
		
		try {
			
			XMLReader reader =  XMLReaderFactory.createXMLReader();
			UserHandler rh = new UserHandler(email,password);
			reader.setContentHandler(rh);
				
			if (rh != null) {
					
				reader.parse(new InputSource(stream));
				user = rh.getUser();
			}
			
			if (user == null) {
				throw new  VerificationException(Messages.ERROR_DATA);
			}
		
		} catch(SAXException e) {
			logger.error(Constants.SAX_EXCEPTION + e.getMessage());
			throw new  DaoException(Messages.ERROR_DATABASE_ACCESS);
		} catch (IOException e) {
			logger.error(Constants.IO_EXCEPTION + e.getMessage());
			throw new  DaoException(Messages.ERROR_DATABASE_ACCESS);
		}
			
		return user;		
	}
}

