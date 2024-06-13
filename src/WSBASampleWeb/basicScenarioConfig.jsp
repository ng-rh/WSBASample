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
<H1>Basic Scenario Configuration.</H1>
<P><A href="topologyA.jsp" target="_blank">View Topology</A></P>
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
<P>To run the scenario, enter a customer name into the text box, which will be used to track the e-mails sent by the scenario. If you wish to throw a BusinessException within the Travel Agent Task, select the check box also. This will drive the compensation model. Click the Finish button once you have configured the scenario to begin.</P>

<FORM method="post" action="/WSBASampleWeb/BasicScenarioServlet"
	target="_self" >
<FIELDSET style="border-color: black">
<LEGEND><B>Task 1: Booking a holiday</B></LEGEND><DIV align="center">
<BR>
Customer name:<BR>
<INPUT type="text" name="id" size="20" maxlength="40"> <BR>
<BR>
<INPUT type="checkbox" name="compensate" value="true"> Throw business
exception in travel agent bean<BR>
<BR>
<INPUT type="submit" name="Finish" value="Finish"><BR/></DIV>
<BR>
</FIELDSET>
</FORM>
<P><a href="main.jsp">Back to the samples menu</a></P>
</BODY>
</HTML>
