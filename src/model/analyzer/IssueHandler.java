package model.analyzer;

import java.util.ArrayList;
import java.util.List;
import model.beans.Issue;
import constants.Constants;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class IssueHandler extends DefaultHandler{
	
	private List<Issue> issues = new ArrayList<Issue>();
	private Issue issue;

	
	public List<Issue> getIssues(){
		return issues;
	}
	
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attrs){
		
		final int indexAttrId       = 0;
		final int indexAttrPriority = 1;
		final int indexAttrAssignee = 2;
		final int indexAttrType     = 3;
		final int indexAttrStatus   = 4;
		final int indexAttrSummary  = 5;
	
		if (Constants.ISSUE.equals(qName)) {	

			issue = new Issue();
			issue.setId(attrs.getValue(indexAttrId));
			issue.setPriority(attrs.getValue(indexAttrPriority));
			issue.setAssignee(attrs.getValue(indexAttrAssignee));
			issue.setType(attrs.getValue(indexAttrType));
			issue.setStatus(attrs.getValue(indexAttrStatus));
			issue.setSummary(attrs.getValue(indexAttrSummary));
			issues.add(issue);	
			
		}
	}	

}
	