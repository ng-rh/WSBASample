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

package com.ibm.ws.wsba.sdo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.ibm.websphere.sdo.mediator.JDBCMediator;
import com.ibm.websphere.sdo.mediator.exception.MediatorException;
import com.ibm.websphere.sdo.mediator.jdbc.ConnectionWrapper;
import com.ibm.websphere.sdo.mediator.jdbc.ConnectionWrapperFactory;
import com.ibm.websphere.sdo.mediator.jdbc.JDBCMediatorFactory;
import com.ibm.websphere.sdo.mediator.jdbc.exception.InvalidMetadataException;
import com.ibm.websphere.sdo.mediator.jdbc.metadata.Column;
import com.ibm.websphere.sdo.mediator.jdbc.metadata.Filter;
import com.ibm.websphere.sdo.mediator.jdbc.metadata.FilterArgument;
import com.ibm.websphere.sdo.mediator.jdbc.metadata.Metadata;
import com.ibm.websphere.sdo.mediator.jdbc.metadata.MetadataFactory;
import com.ibm.websphere.sdo.mediator.jdbc.metadata.Table;
import com.ibm.ws.wsba.utils.Constants;
import com.ibm.ws.wsba.utils.Log;
import com.ibm.ws.wsba.utils.LogFactory;
import com.ibm.ws.wsba.utils.SystemException;
import commonj.sdo.DataObject;

public class CompensationDataHelper
{		
	public static Metadata getS1Metadata()
	{
		MetadataFactory mFactory = MetadataFactory.eINSTANCE;
		Metadata metadata = mFactory.createMetadata();
		Table table = metadata.addTable("CUSTOMERDATA");
				
		Column bookingId = table.addStringColumn("\"BOOKINGID\"");
		table.addStringColumn("\"CUSTOMERID\"");
		
		
		bookingId.setNullable(false);
		table.setPrimaryKey(bookingId);
		
		Filter filter = mFactory.createFilter();
		filter.setPredicate("\"BOOKINGID\" = ?");
				
		FilterArgument arg = mFactory.createFilterArgument();
		arg.setName("BOOKINGID");
		arg.setType(Column.STRING);
				
		filter.getFilterArguments().add(arg);
		table.setFilter(filter);
		
		metadata.setRootTable(table);
		
		return metadata;
	}
	
	public static DataObject getS1DataObject(String dataSourceLookup, Metadata metadata, String bookingId)
	{

		DataObject dataObject = null;
		
		try 
		{
			//Establish connection and connection wrapper
			InitialContext initialContext = new InitialContext();
			
			DataSource datasource = (DataSource) initialContext.lookup(dataSourceLookup);
			ConnectionWrapperFactory factory = ConnectionWrapperFactory.soleInstance;
			Connection conn = datasource.getConnection();
			
			ConnectionWrapper wrapper = factory.createPassiveConnectionWrapper(conn);

			//Create the mediator and perform the query based on the 
			//hwsystem metadata defined above.
			JDBCMediatorFactory mFactory = JDBCMediatorFactory.soleInstance;
			JDBCMediator med = mFactory.createMediator(metadata, wrapper);
					
			DataObject args = med.getParameterDataObject();
			args.setString("BOOKINGID", bookingId);
			
			dataObject = med.getGraph(args);
		
			//Close the connection
			conn.close();
		} 
		catch (NamingException ne) 
		{
			ne.printStackTrace();
			throw new SystemException(ne);
		} 
		catch (SQLException sqle) 
		{
			sqle.printStackTrace();
			throw new SystemException(sqle);
		} 
		catch (InvalidMetadataException ime)
		{
			ime.printStackTrace();
			throw new SystemException(ime);
		} 
		catch (MediatorException me) 
		{
			me.printStackTrace();
			throw new SystemException(me);
		}
		
		return dataObject;
	}

	public static DataObject getS1DataObject(String dataSourceLookup, String key)
	{
		Metadata metadata = CompensationDataHelper.getS1Metadata();
		
		return CompensationDataHelper.getS1DataObject(dataSourceLookup, metadata, key);
	}

	public static DataObject getCustomerCompensationData(String customerId, String bookingId)
	{
		DataObject dataObject = null;
		
		if (bookingId != null && customerId != null && !customerId.trim().equals(""))
		{
			dataObject = getS1DataObject(Constants.DB_JNDI_LOOKUP, bookingId);

			if (dataObject !=null && dataObject.getList(0).size() == 0)
			{
				//Connect to derby DB and add an entry to the DB table using a key of timestamp
			
				addCustomerData(customerId, bookingId);
				
				dataObject = getS1DataObject(Constants.DB_JNDI_LOOKUP, bookingId);
			}
		}	
		
		return dataObject;
	}
	
	public static void addCustomerData(String customerId, String bookingId) 
	{
		try
		{
			Statement theStatement = getStatement();
		
			String insertCustomerDataQuery = "INSERT INTO APP.CUSTOMERDATA VALUES('" + bookingId + "', '" + customerId + "')";
			
			theStatement.executeUpdate(insertCustomerDataQuery);
			
			Log log = LogFactory.getLog(bookingId);
			
			log.addPEntry("Added the following data to the customer information table without adding any compensation support:");
			
			log.addRaw("<TABLE border=\"1\" width=\"100%\"><TR><TD>" + bookingId + "</TD><TD>" + customerId + "</TD></TR></TABLE>");
			
			cleanUp(theStatement);
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace(System.out);
			
			throw new SystemException(sqle);
		}
		catch (NamingException ne)
		{
			ne.printStackTrace(System.out);
			
			throw new SystemException(ne);
		}
	}

	private static void cleanUp(Statement theStatement) throws SQLException 
	{
		Connection connection = theStatement.getConnection();
		theStatement.close();
		connection.close();
	}
	
	private static Statement getStatement() throws NamingException, SQLException 
	{
		InitialContext ctx = null;
		ctx = new InitialContext();

    	// lookup the datasource
    	DataSource theDataSource = (DataSource) ctx.lookup(Constants.DB_JNDI_LOOKUP);

    	// Get the connection
    	Connection theConnection = null;
    	
   	 	theConnection = theDataSource.getConnection();
    
    	// Create the SQL statement
    	Statement theStatement = theConnection.createStatement();
    	
    	return theStatement;
	}
}
