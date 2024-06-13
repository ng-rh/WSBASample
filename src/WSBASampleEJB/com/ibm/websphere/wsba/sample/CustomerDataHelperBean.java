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

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement; 
import javax.ejb.TransactionManagementType; 
import javax.ejb.TransactionAttribute; 
import javax.ejb.TransactionAttributeType; 

import com.ibm.ws.wsba.sdo.CompensationDataHelper;

import commonj.sdo.DataObject;

/**
 * Bean implementation class for Enterprise Bean: CustomerDataHelper
 */

@Stateless(name="CustomerDataHelper")
@TransactionManagement(value=TransactionManagementType.CONTAINER) 
@TransactionAttribute(value=TransactionAttributeType.REQUIRES_NEW) 

public class CustomerDataHelperBean implements CustomerDataHelperLocal
{
	
	public DataObject getCustomerCompensationData(String customerId, String bookingId)
	{
		return CompensationDataHelper.getCustomerCompensationData(customerId, bookingId);
	}
	
	public void addCustomerData(String customerId, String bookingId) 
	{
		CompensationDataHelper.addCustomerData(customerId, bookingId);
	}

}