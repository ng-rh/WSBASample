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

<%@ page import="com.ibm.ws.wsba.utils.*" %>
<%! 
	private static boolean _init = false; 
%>	

<BODY>

<%
	//Reset the tables when the application is loaded for the first time

	if (!_init)
	{	
		try
		{
			LiveTablesHelper.clearAll();
			_init = true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

%>

<H1>Web Services-BusinessActivity (WS-BA) Sample.</H1>
<P>Welcome to the WS-BA Sample. From this page you will be able to link to
three WS-BA use cases that are designed to educate different levels of 
the WS-BA functionality. Click on the <a href="basicScenario.jsp">Basic</a> or <a href="intermediateScenario.jsp">Intermediate</a> scenario links to begin.</P>

<H3><a href="basicScenario.jsp">Basic Scenario</a></H3>
<P>The basic scenario will involve a configuration of one BusinessActivity (BA) Scope that can compensate zero phase work, i.e. work that requires a compensation handler to be active immediately.</P>
<H3><a href="intermediateScenario.jsp">Intermediate Scenario</a></H3>
<P>The Intermediate example introduces the concept of two activities performing work independently, yet retaining a common outcome. It will also show how if a provider cannot complete successfully for whatever reason how the caller can choose to try another provider without having to undo all previous steps. This scenario also shows Web Service and EJB components mixing within the same BA. The last concept it explains is how the root component can direct the entire activity even though the work performed has already been completed in other units of work (UOWs) simply by marking the BA as setCompensateOnly.</P>

</BODY>
</HTML>