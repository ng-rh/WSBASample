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

public class Constants 
{
	public static final String WSBA_API_JNDI_NAME = "java:comp/websphere/UserBusinessActivity";
	
	public static final String FLIGHT_PROVIDER_A_JNDI_NAME = "java:comp/env/ejb/FlightProviderA";
	public static final String FLIGHT_PROVIDER_B_JNDI_NAME = "java:comp/env/ejb/FlightProviderB";

	public static final String FLIGHT_PROVIDER_A_EMAIL_JNDI_NAME = "java:comp/env/ejb/FlightProviderAEMail";
	public static final String FLIGHT_PROVIDER_B_EMAIL_JNDI_NAME = "java:comp/env/ejb/FlightProviderBEMail";
	public static final String HOTEL_PROVIDER_A_EMAIL_JNDI_NAME = "java:comp/env/ejb/HotelProviderAEMail";
	public static final String HOTEL_PROVIDER_B_EMAIL_JNDI_NAME = "java:comp/env/ejb/HotelProviderBEMail";
	
	public static final String CUSTOMER_DATA_HELPER_JNDI_NAME = "java:comp/env/ejb/CustomerDataHelper";
	public static final String TRAVEL_AGENT_JNDI_NAME = "java:comp/env/ejb/TravelAgent";
	public static final String EMAIL_SERVER_JNDI_NAME = "java:comp/env/ejb/MockEMailServer";
	
	public static final String DB_JNDI_LOOKUP = "java:comp/env/WSBASampleDB";
	
	public static final String NAMESPACE = "http://www.ibm.com/websphere/sample/wsba";
	public static final String HOTEL_A_PORT = "HotelProviderA";
	public static final String HOTEL_A_SERVICE_NAME = "HotelProviderAService";
	public static final String HOTEL_A_ADDRESS = "http://localhost:9080/WSBASampleEJB/HotelProviderAService";
	public static final String HOTEL_B_PORT = "HotelProviderB";
	public static final String HOTEL_B_SERVICE_NAME = "HotelProviderBService";
	public static final String HOTEL_B_ADDRESS = "http://localhost:9080/WSBASampleEJB/HotelProviderBService";
	public static final String WS_OPERATION = "scenarioB";

}
