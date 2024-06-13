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
<BODY>
<%@ page import="java.util.*, com.ibm.ws.wsba.utils.*" %>

<P align="center"><a href=liveTables.jsp>live tables</a> | e-mails<BR>
<A href="emails.jsp"></A>
<BR>
<A href="emails.jsp">Refresh</A> | <A href="clearEmails.jsp">Clear Inbox</A></P>
<TABLE border="1" width="100%">
	<THEAD>
		<TR>
			<TD>Booking ID</TD>
			<TD>DateTime</TD>
			<TD>From</TD>
			<TD>To</TD>
			<TD>Subject</TD>
		</TR>
	</THEAD>
	<TBODY>
<%

	try
	{
		List emails = EMailHelper.readInbox();
	
		Iterator emailIterator = emails.iterator();
	
		while (emailIterator.hasNext())
		{
			EMail email = (EMail) emailIterator.next();

			String id = email.getId();

			out.println("<TR>");
				out.println("<TD>" + email.getBookingId() + "</TD>");
				out.println("<TD><a href=\"email.jsp?id="+id+"\" target=\"_blank\">" + id + "<a></TD>");
				out.println("<TD>" + email.getFrom() + "</TD>");
				out.println("<TD>" + email.getTo() + "</TD>");
				out.println("<TD>" + email.getSubject() + "</TD>");
			out.println("</TR>");
		}

	} catch (Exception e)
	{
		e.printStackTrace();
	}
%>
	</TBODY>
</TABLE>
<BR>
<P align="center"><a href=emails.jsp>Refresh</a> | <a href=clearEmails.jsp>Clear Inbox</a></P>
</BODY>
</HTML>
