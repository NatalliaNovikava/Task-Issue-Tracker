package model.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import model.analyzer.IssueHandler;
import model.beans.Issue;
import model.beans.User;
import constants.Constants;
import constants.Messages;

import org.apache.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import exceptions.DaoException;

public class IssueXML {
	
	private List<Issue> allIssues;
	private final static int NUMBER_ISSUES = 10;
	private Logger logger = Logger.getLogger(IssueXML.class);
	private InputStream stream;
	
	public IssueXML(InputStream stream) {
		super();
		this.stream = stream;
	}

	private List<Issue> getAllIssues() throws DaoException {
		
		allIssues = new ArrayList<Issue>();
		
		try {
			
			XMLReader reader =  XMLReaderFactory.createXMLReader();
			IssueHandler ih = new IssueHandler();
			reader.setContentHandler(ih);
				if(ih != null){
					reader.parse(new InputSource(stream));
					allIssues = ih.getIssues();
				}
			
					
		} catch(SAXException e) {
			logger.error(Constants.SAX_EXCEPTION + e.getMessage());
			throw new  DaoException(Messages.ERROR_DATABASE_ACCESS);
		} catch (IOException e) {
			logger.error(Constants.IO_EXCEPTION + e.getMessage());
			throw new  DaoException(Messages.ERROR_DATABASE_ACCESS);
		}
		
		return allIssues;
	}

	public List<Issue> getLastIssues() throws DaoException {
		
		allIssues = getAllIssues();
		List<Issue> lastIssues = new ArrayList<Issue>();
		int fromIndex = allIssues.size() - NUMBER_ISSUES;
		int toIndex = allIssues.size() ;
		lastIssues = allIssues.subList(fromIndex, toIndex);
		
		return lastIssues;
	}

	public List<Issue> getAssigneeIssues(User user) throws DaoException {
		
		allIssues = getAllIssues();
		List<Issue> assigneeIssues = new ArrayList<Issue>();
		ListIterator<Issue> iterator = allIssues.listIterator(allIssues.size());
		Issue issue = null;
		
		while (iterator.hasPrevious()) {
			issue = iterator.previous();
			
			if (issue.getAssignee().equals(user.getEmail()) && (assigneeIssues.size() < NUMBER_ISSUES)) {
				assigneeIssues.add(issue);
			}
		}
		
		return assigneeIssues;
	}
}
	
	

