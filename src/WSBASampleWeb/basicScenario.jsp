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
<BODY><H1>Basic Scenario.</H1>

<P><A href="basicScenarioConfig.jsp">Configure and run the scenario</A></P>
<H3>Scenario description.</H3>
<P>The BA Scope will be created from a Stateless Session Bean (SLSB) invocation. This SLSB, Travel Agent Bean, will perform zero phase work which will be simulated by the sending on an e-mail, then make an invocation to the WSBA API to add a compensation handler by calling addCompensationDataImmediate(). A business exception may occur which prompts the application to call setCompensateOnly() from the WSBA API from within the scope of the SLSB. The compensation handler added earlier will then be called to compensate and the action in the compensation handler is to send a compensation e-mail explaining that something went wrong during the processing.</P>

<IMG src="Images/scenarioA.JPG"/>
<H4><a href="basicScenarioConfig.jsp">Configure and run the scenario</a></H4>


<HR/>
<P><a href="main.jsp">Back to the samples menu</a></P>
</BODY>
</HTML>
