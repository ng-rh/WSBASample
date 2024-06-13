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
import java.util.Date;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BookingHelper 
{
	private static final String confirmHotelBookingQuery = "INSERT INTO APP.HOTELBOOKINGS";
	
	public static void cancelHotelBooking(String bookingId) throws SQLException, NamingException
	{
		Statement theStatement = getStatement();

		SimpleDateFormat fmt = new SimpleDateFormat("dd MMMM yyyy, HH:mm:ss:SSS z");
		String timestamp = fmt.format(new Date());
		
		String cancelHotelBookingQuery = "UPDATE HOTELBOOKINGS set STATUS='CANCELED " + timestamp +
					"' WHERE BOOKINGID='" + bookingId + "'";
		
		theStatement.executeUpdate(cancelHotelBookingQuery);
		
		String logHotelBookingQuery = "SELECT * FROM HOTELBOOKINGS WHERE BOOKINGID='" + bookingId + "'";

		ResultSet resultSet = theStatement.executeQuery(logHotelBookingQuery);
		
		if (resultSet !=null && resultSet.next())
		{
			String customerId = resultSet.getString("CUSTOMERID");
			String airline = resultSet.getString("HOTEL");
			String status = resultSet.getString("STATUS");
			
			Log log = LogFactory.getLog(bookingId);
			log.addPEntry("Updated the following booking in the hotel reservations table:");
			log.addRaw("<TABLE border=\"1\" width=\"100%\"><TR><TD>" + bookingId + "</TD><TD>" + customerId + "</TD><TD>" + airline + "</TD><TD><B>" + status + "</B></TD></TR></TABLE>");
		}
		
		cleanUp(theStatement);
	}
	
	public static void cancelFlightBooking(String bookingId) throws SQLException, NamingException
	{
		Statement theStatement = getStatement();

		SimpleDateFormat fmt = new SimpleDateFormat("dd MMMM yyyy, HH:mm:ss:SSS z");
		String timestamp = fmt.format(new Date());
		
		String cancelFlightBookingQuery = "UPDATE FLIGHTBOOKINGS SET STATUS='CANCELED " + timestamp +
					"' WHERE BOOKINGID='" + bookingId + "'";
		
		theStatement.executeUpdate(cancelFlightBookingQuery);
		
		String logFlightBookingQuery = "SELECT * FROM FLIGHTBOOKINGS WHERE BOOKINGID='" + bookingId + "'";

		ResultSet resultSet = theStatement.executeQuery(logFlightBookingQuery);
		
		if (resultSet != null && resultSet.next())
		{
			String customerId = resultSet.getString("CUSTOMERID");
			String hotel = resultSet.getString("AIRLINE");
			String status = resultSet.getString("STATUS");
			
			Log log = LogFactory.getLog(bookingId);
			log.addPEntry("Updated the following booking in the flight bookings table:");
			log.addRaw("<TABLE border=\"1\" width=\"100%\"><TR><TD>" + bookingId + "</TD><TD>" + customerId + "</TD><TD>" + hotel + "</TD><TD><B>" + status + "</B></TD></TR></TABLE>");
		}
		
		cleanUp(theStatement);		
	}
	
	public static void confirmHotelBooking(String bookingId, String customerId, String hotel) throws SQLException, NamingException
	{
		Statement theStatement = getStatement();

		SimpleDateFormat fmt = new SimpleDateFormat("dd MMMM yyyy, HH:mm:ss:SSS z");
		String timestamp = fmt.format(new Date());
		
		String confirmHotelBooking = "INSERT INTO APP.HOTELBOOKINGS VALUES('"     + bookingId + 
																 "', '" + customerId+ 
																 "', '" + hotel +
																 "', 'BOOKED " + timestamp + "')";
		theStatement.executeUpdate(confirmHotelBooking);
		
		Log log = LogFactory.getLog(bookingId);
		
		log.addPEntry("Added the following booking to the hotel reservations table:");
		log.addRaw("<TABLE border=\"1\" width=\"100%\"><TR><TD>" + bookingId + "</TD><TD>" + customerId + "</TD><TD>" + hotel + "</TD><TD>BOOKED " + timestamp + "</TD></TR></TABLE>");
		
		cleanUp(theStatement);
	}
	
	public static void confirmFlightBooking(String bookingId, String customerId, String airline) throws SQLException, NamingException
	{
		Statement theStatement = getStatement();

		SimpleDateFormat fmt = new SimpleDateFormat("dd MMMM yyyy, HH:mm:ss:SSS z");
		String timestamp = fmt.format(new Date());
		
		String confirmFlightBookingQuery = "INSERT INTO APP.FLIGHTBOOKINGS VALUES('"     + bookingId + 
																 "', '" + customerId+
																 "', '" + airline +
																 "', 'BOOKED " + timestamp + "')";
		theStatement.executeUpdate(confirmFlightBookingQuery);

		Log log = LogFactory.getLog(bookingId);
		
		log.addPEntry("Added the following booking to the flight bookings table:");
		log.addRaw("<TABLE border=\"1\" width=\"100%\"><TR><TD>" + bookingId + "</TD><TD>" + customerId + "</TD><TD>" + airline + "</TD><TD>BOOKED " + timestamp + "</TD></TR></TABLE>");
		
		cleanUp(theStatement);
	}
	
	private static void cleanUp(Statement theStatement) throws SQLException 
	{
		Connection connection = theStatement.getConnection();
		theStatement.close();
		connection.close();
	}
	
	private static Statement getStatement() throws NamingException, SQLException 
	{
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
