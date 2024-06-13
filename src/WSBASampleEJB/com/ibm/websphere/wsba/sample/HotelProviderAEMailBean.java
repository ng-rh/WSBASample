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

package com.ibm.websphere.wsba.sample;

import java.io.NotSerializableException;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement; 
import javax.ejb.TransactionManagementType; 
import javax.ejb.TransactionAttribute; 
import javax.ejb.TransactionAttributeType; 
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.ibm.websphere.wsba.UserBusinessActivity;
import com.ibm.ws.wsba.utils.Constants;
import com.ibm.ws.wsba.utils.Log;
import com.ibm.ws.wsba.utils.LogFactory;
import com.ibm.ws.wsba.utils.SystemException;
import commonj.sdo.DataObject;

/**
 * Bean implementation class for Enterprise Bean: HotelProviderAEMail
 */
@Stateless(name="HotelProviderAEMail")
@TransactionManagement(value=TransactionManagementType.CONTAINER) 
@TransactionAttribute(value=TransactionAttributeType.NOT_SUPPORTED) 

public class HotelProviderAEMailBean implements HotelProviderAEMailLocal
{
      @EJB
	CustomerDataHelperLocal customerDataHelperLocal;
      @EJB
	MockEMailServerLocal mockEMailServerLocal;
	
	public void sendEMail(String from, String to, String subject, String body, String bookingId)
	{
		try
		{
			Log log = LogFactory.getLog(bookingId);
			
			log.addPEntry("In the Hotel Provider A EMail stateless session bean.");
			
			InitialContext ctx = new InitialContext();
			
			UserBusinessActivity uba = (UserBusinessActivity) ctx.lookup(Constants.WSBA_API_JNDI_NAME);
			
			// Get the customer data from the database as an SDO object that will be used as our
			// compensation data when we add our compensation hander
			
			DataObject dataObject = customerDataHelperLocal.getCustomerCompensationData(to, bookingId);

			// Add a compensation handler active immediately. regardless of the completion direction 
			// the compensation handler will always be driven
			
			uba.setCompensationDataImmediate(dataObject);
			
			log.addPEntry("Added a Compensation Handler, active Immediately.");
			
			// Send an e-mail. In this Sample we are using a temp store for the emails and are doing
			// this outside of any global tran. This piece of work will act as a 0 phase resource
			
			mockEMailServerLocal.sendEMail(from, to, subject, body, bookingId);
		}
		catch (NotSerializableException nse) 
		{
			nse.printStackTrace(System.out);			
			throw new SystemException(nse);
		} 
		catch (NamingException ne)
		{
			ne.printStackTrace(System.out);
			
			throw new SystemException(ne);
		} 
	}
}