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

package com.ibm.ws.wsba.handlers;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.naming.NamingException;

import com.ibm.websphere.wsba.CompensationHandler;
import com.ibm.websphere.wsba.CompensationHandlerFailedException;
import com.ibm.websphere.wsba.RetryCompensationHandlerException;
import com.ibm.ws.wsba.utils.BookingHelper;
import com.ibm.ws.wsba.utils.Log;
import com.ibm.ws.wsba.utils.LogFactory;
import commonj.sdo.DataObject;

public class ScenarioBFlightProviderACompensationHandler implements CompensationHandler
{
	public void close(DataObject dataObject) throws RetryCompensationHandlerException, CompensationHandlerFailedException 
	{		
		List customers = dataObject.getList("CUSTOMERDATA");
		
		Iterator customerIterator = customers.iterator();
		
		if(customerIterator.hasNext())
		{
			DataObject customer = (DataObject) customerIterator.next();
			String bookingId = customer.getString("\"BOOKINGID\"");
			
			if (bookingId != null)
			{
				Log log = LogFactory.getLog(bookingId);
				
				log.addPEntry("Compensation Handler '" + this.getClass().getName() + "' called to close.");
			}
		}
	}

	public void compensate(DataObject dataObject) throws RetryCompensationHandlerException, CompensationHandlerFailedException 
	{		
		List customers = dataObject.getList("CUSTOMERDATA");
		
		Iterator customerIterator = customers.iterator();
		
		if(customerIterator.hasNext())
		{
			DataObject customer = (DataObject) customerIterator.next();
			String to = customer.getString("\"CUSTOMERID\"");
			String bookingId = customer.getString("\"BOOKINGID\"");
			
			if (bookingId != null)
			{
				Log log = LogFactory.getLog(bookingId);
				
				log.addPEntry("Compensation Handler '" + this.getClass().getName() + "' called to compensate.");
				
				try 
				{
					BookingHelper.cancelFlightBooking(bookingId);
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
					throw new RetryCompensationHandlerException();
				} 
				catch (NamingException e) 
				{
					e.printStackTrace();
					throw new RetryCompensationHandlerException();
				}
			}
		}
	}
}
