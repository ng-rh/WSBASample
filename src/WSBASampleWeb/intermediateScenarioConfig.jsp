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

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="theme/Master.css" rel="stylesheet"
	type="text/css">
<TITLE>WebServices-BusinessActivity Sample</TITLE>
</HEAD>

<%@ page import="java.io.*,java.util.*" %>

<BODY>
<H1>Intermediate Scenario Configuration.</H1>
<P><A href="main.jsp">Back to the samples menu</A></P>
<P><A href="topologyB.jsp" target="_blank">View Topology</A></P>
<% 
	List configErrors = (List) request.getAttribute("configErrors");

	if (configErrors != null)
	{
		Iterator errorIterator = configErrors.iterator();
	
		while (errorIterator.hasNext())
		{
			String error = (String) errorIterator.next();
	
			try
			{
				out.println("<P><IMG border=\"0\" src=\"Images/Error.gif\" width=\"16\" height=\"16\">" + error + "</P>");
			}
			catch (IOException ioe)
			{
				ioe.printStackTrace();
			}
		}
	}
%>

<p>To begin the test, either click on one of the preconfigured test setups below, 
or configure your own scenario details using the further options listed below.</p>

<FORM action="/WSBASampleWeb/IntermediateScenarioServlet" method="post" target="_self">
<H3>Preconfigured setup A</H3>
<P>This test will complete sucessfully but Flight Provider A was unable
to service our request. We instead booked with Flight Provider B and
completed booking the holiday with Hotel Provider A. This excercises the
function which allows us to accept a minor failure in the activiy, but
try to find an alternative route of success rather than failing. An
atlernative option, depending on the reason for Flight Provider A's
failure, might be to retry it a number of times before marking it as
unusable. <BR>
<INPUT type="submit" name="setupA" value="Run Setup A">
<INPUT type="hidden" name="testKind" value="configA">
</P></FORM><p></p>

<FORM action="/WSBASampleWeb/IntermediateScenarioServlet" method="post" target="_self">
<h3>Preconfigured setup B</h3>
<p>This test will also complete sucessfully but Flight Provider A was again unable to service
our request. We instead booked with Flight Provider B. Whilst booking the hotel, Hotel Provider
A could not service our request either, however we still managed to complete the booking by
using Hotel Provider B. This test again shows us we can accept a minor failure in the activiy, 
even though we have completed an amount of work already. We can then try to find an alternative 
route of success so that all the work we have completed successfully now can remain completed 
rather than failing the activity and compensating for all the work we have done.<BR>
<INPUT type="submit" name="setupB" value="Run Setup B">
<INPUT type="hidden" name="testKind" value="configB">
</P></FORM><p></p>

<FORM action="/WSBASampleWeb/IntermediateScenarioServlet" method="post" target="_self">
<h3>Preconfigured setup C</h3>
<p>This test will not complete sucessfully. Flight Provider A was again unable to service
our request so we booked with Flight Provider B instead. Both Hotel Providers could not 
service our request, and without any further service providers, our request has no option
but to end in failure at this time. This test exercises the compensational model of failure.
When the underlying activity fails, all work performed will be ordered to compensate via the
Compensation Handers assigned to each piece of work that requires compensating actions. <BR>
<INPUT type="submit" name="setupC" value="Run Setup C">
<INPUT type="hidden" name="testKind" value="configC">
</P></FORM><p></p>

<FORM action="/WSBASampleWeb/IntermediateScenarioServlet" method="post" target="_self">
<h3>Preconfigured setup D</h3>
<p>This test will not complete sucessfully. Despite all the providers being able to service
the requests, the end user decides not to confirm the booking of the holiday and this results
in a BusinessException being thrown from the outer Travel Agent task. This test exercises the 
compensational model of failure also. When the underlying activity fails, all work performed 
will be ordered to compensate via the Compensation Handers assigned to each piece of work that 
requires compensating actions. <BR>
<INPUT type="submit" name="setupD" value="Run Setup D">
<INPUT type="hidden" name="testKind" value="configD">
</P></FORM><p></p>

<h3>Custom Configuration:</h3>
<p>The section below allows you to configure the state of all the service providers in the 
scenario. By selecting a service provider you state that you want it to be able to service
any requests sent to it. If the service provider is down (unselected) any requests sent to 
it will fail, and the next service provider in the list will be tried. Finally after all work
in the flight and hotel providers has been done, the person buying the holiday has the option 
to confirm the booking. If they do not want to confirm, a business exception will be thrown 
from within the outer Task. This is configurable by selecting the last check box. To run this 
scenario, complete the form below, and click the finish button.</p>

<FORM method="post" action="/WSBASampleWeb/IntermediateScenarioServlet"
	target="_self" >
<INPUT type="hidden" name="testKind" value="custom">
<FIELDSET style="border-color: black">
<LEGEND><B>Task 1: Booking a holiday</B></LEGEND><DIV align="center">
<BR>
Customer name:<BR>
<INPUT type="text" name="id" size="20" maxlength="40"> <BR>
<BR>

<FIELDSET style="border-color: gray;margin: 10">
<LEGEND><B>Task 2: Booking a flight</B></LEGEND><DIV align="center">
<BR>
<INPUT type="checkbox" name="flightA" value="true" checked> Flight Provider A open for e-business<BR>
<BR>
<INPUT type="checkbox" name="flightB" value="true" checked> Flight Provider B open for e-business
<BR>
<BR>
</FIELDSET>

<BR>
<FIELDSET style="border-color: gray;margin: 10">
<LEGEND><B>Task 1: Booking a hotel</B></LEGEND><DIV align="center">
<BR>
<INPUT type="checkbox" name="hotelA" value="true" checked> Hotel Provider A open for e-business<BR>
<BR>
<INPUT type="checkbox" name="hotelB" value="true" checked> Hotel Provider B open for e-business
<BR>
<BR>
</DIV>
</FIELDSET><BR>
<INPUT type="checkbox" name="compensate" value="true"> Throw business
exception in travel agent bean upon confirmation of bookings<BR>
<BR>
<INPUT type="submit" name="Finish" value="Finish"><BR>
<BR/></DIV></FIELDSET>

</FORM>
<P><a href="main.jsp">Back to the samples menu</a></P>
</BODY>
</HTML>
