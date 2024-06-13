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
<H1>Intermediate Scenario.</H1>
<H4><A href="intermediateScenarioConfig.jsp">Configure and run the
scenario</A></H4>

<H3>Scenario description.</H3>
<P>The outer BA is created by the first SLSB whose UOW is a Local Transaction Containment (LTC). The job of this bean is to coordinate the work performed by the flight and hotel providers. The Travel Agent SLSB makes an invocation to a Flight Provider SLSB which will create a new Java Transaction API (JTA) transaction and BA, BA2. It will try to book a flight with Flight Provider A first. The flight provider SLSB will perform both transactional and non-transactional work by writing to a two phase capable resource and sending an e-mail. It will add two compensation handlers using the addCompensationDataAtCommit() and addCompensationDataImmediate() methods for the DB and e-mail work respectively. If this is successful it will complete BA2 and the Travel Agent Bean will make a second invocation, this time to a Web Service hotel provider. It will try hotel Provider A first. This Web Service will run under a new JTA transaction and BA, BA3. It will perform similar work to the flight providers, but to a different table and send slightly different e-mail content. It will also add two compensation handlers similar to the flight provider. Finally the Travel Agent bean will be able to review the updates made and perform a confirmation step which will confirm the updates should go ahead (close all compensation handlers) or undo all the changes that have been made under the JTA transactions which have already committed (compensate all compensation handlers).</P>

<IMG src="Images/scenarioB.JPG"/>

<H4><a href="intermediateScenarioConfig.jsp">Configure and run the scenario</a></H4>

<HR/>
<P><a href="main.jsp">Back to the samples menu</a></P>
</BODY>
</HTML>
