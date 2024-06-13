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
import java.rmi.RemoteException;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement; 
import javax.ejb.TransactionManagementType; 
import javax.ejb.TransactionAttribute; 
import javax.ejb.TransactionAttributeType; 
import javax.ejb.EJB;
import javax.xml.ws.WebServiceRef; 
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.ibm.websphere.wsba.UserBusinessActivity;
import com.ibm.ws.wsba.utils.BusinessException;
import com.ibm.ws.wsba.utils.Constants;
import com.ibm.ws.wsba.utils.Log;
import com.ibm.ws.wsba.utils.LogFactory;
import com.ibm.ws.wsba.utils.SystemException;
import commonj.sdo.DataObject;

import com.ibm.websphere.sample.wsba.HotelProviderA;
import com.ibm.websphere.sample.wsba.HotelProviderAService;
import com.ibm.websphere.sample.wsba.HotelProviderB;
import com.ibm.websphere.sample.wsba.HotelProviderBService;

/**
 * Bean implementation class for Enterprise Bean: TravelAgent
 */
@Stateless(name="TravelAgent")
@TransactionManagement(value=TransactionManagementType.CONTAINER) 
@TransactionAttribute(value=TransactionAttributeType.NOT_SUPPORTED) 

public class TravelAgentBean implements TravelAgentLocal
{
      @EJB
      CustomerDataHelperLocal customerDataHelperLocal;
      @EJB
	MockEMailServerLocal mockEMailServerLocal;
      @EJB
	FlightProviderALocal flightProviderALocal;
      @EJB
	FlightProviderBLocal flightProviderBLocal;
      @WebServiceRef(wsdlLocation="http://localhost:9080/WSBASampleEJB/HotelProviderAService?wsdl") 
      HotelProviderAService  hotelProviderAService; 
      @WebServiceRef(wsdlLocation="http://localhost:9080/WSBASampleEJB/HotelProviderBService?wsdl") 
      HotelProviderBService  hotelProviderBService; 


	public void scenarioA(String id, boolean compensate, String bookingId)
	{
		UserBusinessActivity uba = null;
		
		String from = "WSBA Travel Agent Person";
		
		Log log = LogFactory.getLog(bookingId);
		
		try
		{
			log.addPEntry("In the Travel Agent stateless session bean.");
			
			InitialContext ctx = new InitialContext();
			
			uba = (UserBusinessActivity) ctx.lookup(Constants.WSBA_API_JNDI_NAME);
			
			// Get the compensation data we need from the customer info table
			// so we have all the info we need if we have to compensate later			
			DataObject dataObject = customerDataHelperLocal.getCustomerCompensationData(id, bookingId);
			
			// Create and add a CompensationHandler immediately
			uba.setCompensationDataImmediate(dataObject);
			
			log.addPEntry("Added a Compensation Handler, active Immediately.");
			
			// Perform the phase zero work, sending an e-mail			
			mockEMailServerLocal.sendEMail(from, id, "Your holiday booking", "Congratulations, your holiday has been booked. We at WSBA Travel Agency wish you a pleasant trip.", bookingId);
			
			// If we are configured to fail, throw a business exception
			if (compensate)
			{
				log.addPEntry("Throwing a business exception.");
				
				throw new BusinessException();
			}
		} 
		catch (NamingException ne) 
		{
			ne.printStackTrace(System.out);
			
			// The NamingException may have been from the lookup of the UserBusinessActivity
			// interface, in which case we just need to throw the SystemException out
			
			if (uba != null)
			{
				uba.setCompensateOnly();
			}
			
			throw new SystemException(ne);
		}
		catch (NotSerializableException nse) 
		{
			nse.printStackTrace(System.out);
			uba.setCompensateOnly();
			throw new SystemException(nse);
		}
		catch (IllegalStateException ise) 
		{
			ise.printStackTrace(System.out);
			uba.setCompensateOnly();
			throw new SystemException(ise);
		}
		catch (BusinessException be)
		{
			// A Business Exception has been thrown, so we choose to 
			// setCompensateOnly() on the BA forcing it to compensate.
			
			if (uba != null)
			{
				log.addPEntry("Caught a business exception; calling setCompensateOnly().");
				uba.setCompensateOnly();
			}
		}	
		
	}
	
	public void scenarioB(String customerName, boolean flightProviderA, boolean flightProviderB, boolean hotelProviderA, boolean hotelProviderB, boolean throwBusinessException, String bookingId)
	{
		Log log = LogFactory.getLog(bookingId);
		
		UserBusinessActivity uba = null;
		
		try
		{
			log.addPEntry("In the Travel Agent stateless session bean.");
			
			InitialContext ctx = new InitialContext();

			uba = (UserBusinessActivity) ctx.lookup(Constants.WSBA_API_JNDI_NAME);
			
			// Add the customer data to the customer table. We don't want this to be removed
			// if the booking fails as we want to keep it for future reference, so we simply 
			// perform it in an EJB that isn't enabled to perform work in the BA, so no
			// compensation handlers will be added.
			
			customerDataHelperLocal.addCustomerData(customerName, bookingId);
			
			// Book the flight with either flightProviderA or flightProviderB 
			bookFlight(customerName, flightProviderA, flightProviderB, bookingId, log);
			
			// Book the hotel with either hotelProviderA or hotelProviderB
			bookHotel(customerName, hotelProviderA, hotelProviderB, bookingId, log);
			
			// If the user has chosen not to confirm their holiday at the end of the entire
			// booking process then we need to compensate the work and so we throw a BusinessException
			
			if (throwBusinessException)
			{
				throw new BusinessException();
			}
		}
		catch (BusinessException be)
		{
			// A Business Exception has been thrown, so we choose to 
			// setCompensateOnly() on the BA forcing it to compensate.
		
			log.addPEntry("Caught a business exception; calling setCompensateOnly().");
			uba.setCompensateOnly();
		} 
		catch (NamingException ne) 
		{
			ne.printStackTrace();
			
			// The NamingException may have been from the lookup of the UserBusinessActivity
			// interface, in which case we just need to throw the SystemException out
			
			if (uba != null)
			{
				uba.setCompensateOnly();
			}
			
			throw new SystemException(ne);
		} 
	}
	
	private void bookFlight(String id, boolean flightProviderA, boolean flightProviderB, String bookingId, Log log)  throws BusinessException
	{		
		try
		{						
			InitialContext ctx = new InitialContext();
			
		
			try
			{
				// Call the Flight Provider A EJB to try and book a flight with them
				flightProviderALocal.scenarioB(id, flightProviderA, bookingId, log);
				
				log.addPEntry("Sucessfully booked a flight with Flight Provider A.");
			} 
			catch (BusinessException be)
			{
				log.addPEntry("Caught a BusinessException trying to book with Flight Provider A, trying Flight Provider B.");
							
				try
				{
					// The booking failed so we call the Flight Provider B EJB to see if we can
					// book a flight with them instead
					
					flightProviderBLocal.scenarioB(id, flightProviderB, bookingId, log);
				
					log.addPEntry("Sucessfully booked a flight with Flight Provider B.");
				} 
				catch (BusinessException be2)
				{
					// We get here if neither of the two flight providers can service our request
					// and book us our flight, we simply write to the log and rethrow the exception
					
					log.addPEntry("Caught a BusinessException trying to book with Flight Provider B, no more Flight Providers available to try.");
					
					throw be2;
				}
			}
		}
		catch (NamingException ne)
		{
			ne.printStackTrace();
			throw new SystemException(ne);
		} 
	}
	
	private void bookHotel(String id, boolean hotelProviderA, boolean hotelProviderB, String bookingId, Log log) throws BusinessException 
	{
		try
		{						
			InitialContext ctx = new InitialContext();

			try
			{
				// Call the Hotel Provider A Web Service to try and book our hotel
                        HotelProviderA portA = hotelProviderAService.getHotelProviderA();
                        portA.scenarioB(id, hotelProviderA, bookingId); 
				
				log.addPEntry("Sucessfully booked a room with Hotel Provider A.");
			} 
			catch (com.ibm.websphere.sample.wsba.BusinessException be)
			{
				log.addPEntry("Caught a BusinessException trying to book with Hotel Provider A, trying Hotel Provider B.");
			
				try
				{
					// The booking failed so we call the Hotel Provider B Web Service to see if we 
					// can book a hotel with them instead
                              HotelProviderB portB = hotelProviderBService.getHotelProviderB();
                              portB.scenarioB(id, hotelProviderB, bookingId); 
					
					log.addPEntry("Sucessfully booked a room with Hotel Provider B.");
				} 
				catch (com.ibm.websphere.sample.wsba.BusinessException be2)
				{					
					// We get here if neither of the two hotel providers can service our request
					// and book us our hotel, we simply write to the log and rethrow the exception
					
					log.addPEntry("Caught a BusinessException trying to book with Hotel Provider B, no more Hotel Providers available to try.");
					
					throw new BusinessException();
				}
			}
		}
		catch (NamingException ne)
		{
			ne.printStackTrace();
			throw new SystemException(ne);
		} 
	}
	
}