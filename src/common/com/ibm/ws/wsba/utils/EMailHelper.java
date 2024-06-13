/*
 * COPYRIGHT LICENSE: This information contains sample code provided in 
 * source code form. You may copy, modify, and distribute these sample 
 * programs in any form without payment to IBM for the purposes of 
 * developing, using, marketing or distributing application programs 
 * conforming to the application programming interface for the operating 
 * platform for which the sample code is written. Notwithstanding anything 
 * to the contrary, IBM PROVIDES THE SAMPLE SOURCE CODE ON AN "AS IS" BASIS 
 * AND IBM DISCLAIMS ALL WARRANTIES, EXPRESS OR IMPLIED, INCLUDING, BUT NOT 
 * LIMITED TO, ANY IMPLIED WARRANTIES OR CONDITIONS OF MERCHANTABILITY, 
 * SATISFACTORY QUALITY, FITNESS FOR A PARTICULAR PURPOSE, TITLE, AND ANY 
 * WARRANTY OR CONDITION OF NON-INFRINGEMENT. IBM SHALL NOT BE LIABLE FOR 
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL OR CONSEQUENTIAL DAMAGES ARISING 
 * OUT OF THE USE OR OPERATION OF THE SAMPLE SOURCE CODE. IBM HAS NO 
 * OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS OR 
 * MODIFICATIONS TO THE SAMPLE SOURCE CODE.
 * 
 * (C) Copyright IBM Corp. 2005, 2011.
 * All Rights Reserved. Licensed Materials - Property of IBM.
 */

package com.ibm.ws.wsba.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class EMailHelper 
{
	private static final String readInboxQuery = "SELECT * FROM APP.EMAILS";	
	private static final String deleteInboxQuery = "DELETE FROM APP.EMAILS";
	
	public static void sendEMail(String from, String to, String subject, String body, String bookingId) throws NamingException, SQLException
	{
		//Connect to derby DB and add an entry to the DB table using a key of timestamp
		SimpleDateFormat fmt = new SimpleDateFormat("dd MMMM yyyy, HH:mm:ss:SSS z");
		String timestamp = fmt.format(new Date());
		
		Statement theStatement = getStatement();

		String insertEmailQuery = "INSERT INTO APP.EMAILS VALUES('"     + bookingId + 
																 "', '" + timestamp + 
																 "', '" + from + 
																 "', '" + to + 
																 "', '" + subject +
																 "', '" + "REF: "+ bookingId + ". <BR/>" + body + "')";
		theStatement.executeUpdate(insertEmailQuery);
		
		LogFactory.getLog(bookingId).addHyperlinkEntry("Sent an e-mail (click to view)", "email.jsp?id=" + timestamp);
		
		cleanUp(theStatement);
	}
	
	private static void cleanUp(Statement theStatement) throws SQLException 
	{
		Connection connection = theStatement.getConnection();
		theStatement.close();
		connection.close();
	}

	public static EMail readEMail(String id) throws NamingException, SQLException
	{
		//Connect to derby database to get the e-mail record with the key  of ID (timestamp)
		//return an EMail class containing all the info that the JSP page can extract
		
		EMail emailResult = null;
		
		String readEMailQuery = "SELECT * FROM APP.EMAILS WHERE \"TIMEDATE\"='" + id + "'";
		
		List email = getEmails(readEMailQuery);
		
		if (email != null)
		{
			Iterator emailIterator = email.iterator();
			
			if (emailIterator.hasNext())
			{
				emailResult = (EMail) emailIterator.next(); 
			}
		}
		
		return emailResult;
	}
	
	public static List readInbox() throws NamingException, SQLException
	{
		//Connect to derby database to get all the e-mail records 
		//return a List EMail class containing all the info that the JSP page can extract
		
		return getEmails(readInboxQuery);
	}
	
	public static void deleteInbox() throws NamingException, SQLException
	{
		Statement theStatement = getStatement();
		
		theStatement.executeUpdate(deleteInboxQuery);
		
		cleanUp(theStatement);
	}
	
	public static List getEmails(String query) throws NamingException, SQLException
	{
		Statement theStatement = getStatement();

    	//Execute the statement
    	ResultSet results = theStatement.executeQuery(query);

    	ArrayList emails = new ArrayList();
    	if (results != null)
    	{
    		while (results.next())
    		{
    			String timestamp = results.getString("TIMEDATE");
    			String from = results.getString("FROM");
    			String to = results.getString("TO");
    			String subject = results.getString("SUBJECT");
    			String body = results.getString("BODY");
    			String bookingId = results.getString("BOOKINGID");
    			
    			emails.add(new EMail(timestamp, from, to, subject, body, bookingId));
    		}
    	}    	

    	cleanUp(theStatement);
    	return emails;
	}

	private static Statement getStatement() throws NamingException, SQLException {
		InitialContext ctx = null;
		ctx = new InitialContext();

		
    	// lookup the datasource
    	DataSource theDataSource = (DataSource) ctx.lookup(Constants.DB_JNDI_LOOKUP);

    	// Get the connection
    	Connection theConnection = null;
    	
   	 	theConnection = theDataSource.getConnection();
    
    	// Create the SQL statement
    	Statement theStatement = theConnection.createStatement();

    	
    	return theStatement;
	}
}
