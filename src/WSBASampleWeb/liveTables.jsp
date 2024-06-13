<!-- 

  COPYRIGHT LICENSE: This information contains sample code provided in 
  source code form. You may copy, modify, and distribute these sample 
  programs in any form without payment to IBM for the purposes of 
  developing, using, marketing or distributing application programs 
  conforming to the application programming interface for the operating 
  platform for which the sample code is written. Notwithstanding anything 
  to the contrary, IBM PROVIDES THE SAMPLE SOURCE CODE ON AN "AS IS" BASIS 
  AND IBM DISCLAIMS ALL WARRANTIES, EXPRESS OR IMPLIED, INCLUDING, BUT NOT 
  LIMITED TO, ANY IMPLIED WARRANTIES OR CONDITIONS OF MERCHANTABILITY, 
  SATISFACTORY QUALITY, FITNESS FOR A PARTICULAR PURPOSE, TITLE, AND ANY 
  WARRANTY OR CONDITION OF NON-INFRINGEMENT. IBM SHALL NOT BE LIABLE FOR 
  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL OR CONSEQUENTIAL DAMAGES ARISING 
  OUT OF THE USE OR OPERATION OF THE SAMPLE SOURCE CODE. IBM HAS NO 
  OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS OR 
  MODIFICATIONS TO THE SAMPLE SOURCE CODE.
 
  (C) Copyright IBM Corp. 2005, 2011.
  All Rights Reserved. Licensed Materials - Property of IBM.

 -->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@taglib uri="http://www.ibm.com/siteedit/sitelib" prefix="siteedit"%>
<HTML>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="theme/Master.css" rel="stylesheet"
	type="text/css">
<TITLE>WebServices-BusinessActivity Sample</TITLE>
</HEAD>

<%@ page import="java.util.*, com.ibm.ws.wsba.utils.*" %>

<%! 
	private static boolean _init = false; 
%>	

<BODY>
<P align="center">live tables | <a href=emails.jsp>e-mails</a></P>
<P align="center"><a href=liveTables.jsp>Refresh</a> | <a href=clearData.jsp>Clear All Data</a></P>

<h2>Customer Information table</h2>

<TABLE border="1" width="100%">
	<THEAD>
		<TR>
			<TD>Booking ID</TD>
			<TD>Customer ID</TD>
		</TR>
	</THEAD>
	<TBODY>
<%

	if (_init)
	{
		try
		{
			List customers = LiveTablesHelper.getCustomerData();
		
			Iterator customerIterator = customers.iterator();
		
			while (customerIterator.hasNext())
			{
				Customer customer = (Customer) customerIterator.next();
	
				String bookingId = customer.getBookingId();
				String customerId = customer.getCustomerId();
	
				out.println("<TR>");
					out.println("<TD>" + bookingId + "</TD>");
					out.println("<TD>" + customerId + "</TD>");
				out.println("</TR>");
			}
	
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

%>


	</TBODY>
</TABLE>

<h2>Flight Bookings table</h2>

<TABLE border="1" width="100%">
	<THEAD>
		<TR>
			<TD>Booking ID</TD>
			<TD>Customer ID</TD>
			<TD>Airline Carrier</TD>
			<TD>Booking Status</TD>
		</TR>
	</THEAD>
	<TBODY>
<%

	if (_init)
	{
		try
		{
			List flights = LiveTablesHelper.getFlightBookings();
		
			Iterator flightIterator = flights.iterator();
		
			while (flightIterator.hasNext())
			{
				FlightBooking flight = (FlightBooking) flightIterator.next();
	
				String bookingId = flight.getBookingId();
				String customerId = flight.getCustomerId();
				String airline = flight.getAirline();
				String status = flight.getState();
	
				out.println("<TR>");
					out.println("<TD>" + bookingId + "</TD>");
					out.println("<TD>" + customerId + "</TD>");
					out.println("<TD>" + airline + "</TD>");
					out.println("<TD>" + status + "</TD>");
				out.println("</TR>");
			}
	
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

%>


	</TBODY>
</TABLE>

<h2>Hotel Reservations table</h2>

<TABLE border="1" width="100%">
	<THEAD>
		<TR>
			<TD>Booking ID</TD>
			<TD>Customer ID</TD>
			<TD>Hotel</TD>
			<TD>Booking Status</TD>
		</TR>
	</THEAD>
	<TBODY>
<%

	if (_init)
	{
		try
		{
			List hotels = LiveTablesHelper.getHotelBookings();
		
			Iterator hotelsIterator = hotels.iterator();
		
			while (hotelsIterator.hasNext())
			{
				HotelBooking hotel = (HotelBooking) hotelsIterator.next();
	
				String bookingId = hotel.getBookingId();
				String customerId = hotel.getCustomerId();
				String hotelCompany = hotel.getHotel();
				String status = hotel.getState();
	
				out.println("<TR>");
					out.println("<TD>" + bookingId + "</TD>");
					out.println("<TD>" + customerId + "</TD>");
					out.println("<TD>" + hotelCompany + "</TD>");
					out.println("<TD>" + status + "</TD>");
				out.println("</TR>");
			}
	
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	else
	{
		_init = true;
	}

%>
	</TBODY>
</TABLE>

<BR>

<P align="center"><a href=liveTables.jsp>Refresh</a> | <a href=clearData.jsp>Clear All Data</a></P>

</BODY>
</HTML>
