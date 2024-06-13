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
import java.sql.SQLException;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement; 
import javax.ejb.TransactionManagementType; 
import javax.ejb.TransactionAttribute; 
import javax.ejb.TransactionAttributeType; 
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;


import com.ibm.websphere.wsba.UserBusinessActivity;
import com.ibm.ws.wsba.utils.BookingHelper;
import com.ibm.ws.wsba.utils.BusinessException;
import com.ibm.ws.wsba.utils.Constants;
import com.ibm.ws.wsba.utils.Log;
import com.ibm.ws.wsba.utils.SystemException;
import commonj.sdo.DataObject;

/**
 * Bean implementation class for Enterprise Bean: FlightProviderA
 */
@Stateless(name="FlightProviderA")
@TransactionManagement(value=TransactionManagementType.CONTAINER) 
@TransactionAttribute(value=TransactionAttributeType.REQUIRES_NEW) 

public class FlightProviderABean implements FlightProviderALocal
{	
      @EJB
      CustomerDataHelperLocal customerDataHelperLocal;
      @EJB
	FlightProviderAEMailLocal flightProviderAEMailLocal;

	
	public void scenarioB(String id, boolean throwBusinessException, String bookingId, Log log) throws BusinessException
	{
		log.addPEntry("In the Flight Provider A stateless session bean.");
		
		UserBusinessActivity uba = null;
		
		try
		{
			InitialContext ctx = new InitialContext();
			
			uba = (UserBusinessActivity) ctx.lookup(Constants.WSBA_API_JNDI_NAME);
	
			// If the provider is set up to fail, throw a BusinessException straight away
			
			if (throwBusinessException)
			{
				throw new BusinessException();
			}
			
			String from = "WSBA Flight Provider A Person";
			String airline = "WSBA Flight Provider A";
						
			
			// The customer data which contains the bookingId for this booking is really all the
			// compensation data we need to undo our actions so we extract it as an SDO object from
			// the customer info table
			
			DataObject dataObject = customerDataHelperLocal.getCustomerCompensationData(id, bookingId);
			
			// Add a compensation handler that will be active only once the transaction has committed, as
			// the compensation action would be to undo the database updates performed within that tran
			
			uba.setCompensationDataAtCommit(dataObject);
			
			log.addPEntry("Added a Compensation Handler, active at commit.");

			// Perform the transactional work to the database by booking the Flight
			
			BookingHelper.confirmFlightBooking(bookingId, id, airline);
						
			// Finally we initiate the EMail process to let the customer know that the flight is booked
			
			flightProviderAEMailLocal.sendEMail(from, id, "Your flight booking with Flight Provider A", "Congratulations, your flight has been booked, we at WSBA Flight Provider A hope you enjoy it.", bookingId);
		} 
		catch (NamingException ne) 
		{
			ne.printStackTrace(System.out);			
			throw new SystemException(ne);
		} 
		catch (SQLException sqle) 
		{
			sqle.printStackTrace(System.out);			
			throw new SystemException(sqle);
		} 
		catch (SystemException se) 
		{
			se.printStackTrace(System.out);			
			throw new SystemException(se);
		} 
		catch (NotSerializableException nse) 
		{
			nse.printStackTrace(System.out);			
			throw new SystemException(nse);
		} 
		catch (IllegalStateException ise) 
		{
			ise.printStackTrace(System.out);			
			throw new SystemException(ise);
		}
	} 
}