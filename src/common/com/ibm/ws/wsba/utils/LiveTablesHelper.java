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
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class LiveTablesHelper 
{
	private static final String readCustomerDataQuery = "SELECT * FROM APP.CUSTOMERDATA";	
	private static final String readHotelBookingsQuery = "SELECT * FROM APP.HOTELBOOKINGS";
	private static final String readFlightBookingsQuery = "SELECT * FROM APP.FLIGHTBOOKINGS";
	private static final String deleteCustomerDataQuery = "DELETE FROM APP.CUSTOMERDATA";
	private static final String deleteEMailQuery = "DELETE FROM APP.EMAILS";
	private static final String deleteHotelBookingsQuery = "DELETE FROM APP.HOTELBOOKINGS";
	private static final String deleteFlightBookingsQuery = "DELETE FROM APP.FLIGHTBOOKINGS";
	
	public static void deleteAllData() throws NamingException, SQLException
	{
		Statement theStatement = getStatement();
		
		theStatement.executeUpdate(deleteCustomerDataQuery);
		theStatement.executeUpdate(deleteFlightBookingsQuery);
		theStatement.executeUpdate(deleteHotelBookingsQuery);
		
		cleanUp(theStatement);
	}
	
	public static List getCustomerData() throws NamingException, SQLException
	{
		Statement theStatement = getStatement();

    	//Execute the statement
    	ResultSet results = theStatement.executeQuery(readCustomerDataQuery);

    	ArrayList customers = new ArrayList();
    	if (results != null)
    	{
    		while (results.next())
    		{
    			String customerId = results.getString("CUSTOMERID");
    			String bookingId = results.getString("BOOKINGID");
    			
    			customers.add(new Customer(bookingId, customerId));
    		}
    	}

    	cleanUp(theStatement);
    	return customers;
	}
	
	public static List getFlightBookings() throws NamingException, SQLException
	{
		Statement theStatement = getStatement();

    	//Execute the statement
    	ResultSet results = theStatement.executeQuery(readFlightBookingsQuery);

    	ArrayList customers = new ArrayList();
    	if (results != null)
    	{
    		while (results.next())
    		{
    			String customerId = results.getString("CUSTOMERID");
    			String bookingId = results.getString("BOOKINGID");
    			String flight = results.getString("AIRLINE");
    			String status = results.getString("STATUS");
    			
    			customers.add(new FlightBooking(bookingId, customerId, flight, status));
    		}
    	}

    	cleanUp(theStatement);
    	return customers;
	}
	
	public static List getHotelBookings() throws NamingException, SQLException
	{
		Statement theStatement = getStatement();

    	//Execute the statement
    	ResultSet results = theStatement.executeQuery(readHotelBookingsQuery);

    	ArrayList customers = new ArrayList();
    	if (results != null)
    	{
    		while (results.next())
    		{
    			String customerId = results.getString("CUSTOMERID");
    			String bookingId = results.getString("BOOKINGID");
    			String hotel = results.getString("HOTEL");
    			String status = results.getString("STATUS");
    			
    			customers.add(new HotelBooking(bookingId, customerId, hotel, status));
    		}
    	}

    	cleanUp(theStatement);
    	return customers;
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
	
	private static void cleanUp(Statement theStatement) throws SQLException 
	{
		Connection connection = theStatement.getConnection();
		theStatement.close();
		connection.close();
	}

	public static void clearAll() throws NamingException, SQLException
	{
		Statement theStatement = getStatement();
		
		theStatement.executeUpdate(deleteCustomerDataQuery);
		theStatement.executeUpdate(deleteEMailQuery);
		theStatement.executeUpdate(deleteFlightBookingsQuery);
		theStatement.executeUpdate(deleteHotelBookingsQuery);
		
		cleanUp(theStatement);
	}

}
