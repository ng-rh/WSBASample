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

package com.ibm.ws.wsba.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.CreateException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.websphere.wsba.sample.TravelAgentLocal;
import com.ibm.ws.wsba.utils.Constants;
import com.ibm.ws.wsba.utils.Log;
import com.ibm.ws.wsba.utils.LogFactory;
import com.ibm.ws.wsba.utils.SystemException;


public class BasicScenarioServlet extends HttpServlet implements Servlet 
{
      @EJB
	TravelAgentLocal travelAgent;


	private static long bookingId = 0;
	
	public BasicScenarioServlet() 
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		service(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		service(request, response);
	}
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		if (request != null && response != null)
		{
			String throwBusinessException = (String) request.getParameter("compensate");

			boolean compensate = throwBusinessException == null?false:true;
			String emailTo = (String) request.getParameter("id");
			
			PrintWriter out = response.getWriter();
					
			
			out.println("<HTML>");
			out.println("<HEAD>");
			out.println("<META http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">");
			out.println("<META name=\"GENERATOR\" content=\"IBM Software Development Platform\">");
			out.println("<META http-equiv=\"Content-Style-Type\" content=\"text/css\">");
			out.println("<LINK href=\"theme/Master.css\" rel=\"stylesheet\" type=\"text/css\">");
			out.println("<TITLE>WebServices-BusinessActivity Sample</TITLE>");
			out.println("</HEAD>");
			out.println("<BODY>");
			
			String logKey = null;
			out.println("<H1>Results of the basic scenario.</H1>");
			
			out.println("<P><A href=\"topologyA.jsp\" target=\"_blank\">View Topology</A></P>");
			try 
			{
				
				List errors = validateParameters(emailTo);
				
				if (errors == null)
				{
					logKey = "BasicScenario " + bookingId++;
					
					travelAgent.scenarioA(emailTo, compensate, logKey);
				}
				else
				{
					request.setAttribute("configErrors", errors);
					request.getRequestDispatcher("basicScenarioConfig.jsp").forward(request, response);
				}
				
				printLog(out, logKey);
				
			} 
/*
			catch (NamingException e) 
			{
				e.printStackTrace(System.out);
				request.getRequestDispatcher("error.jsp").forward(request, response);
			} 
			catch (CreateException e) 
			{
				e.printStackTrace(System.out);
				request.getRequestDispatcher("error.jsp").forward(request, response);
			}
*/
			catch (javax.ejb.TransactionRolledbackLocalException trbe) 
			{				
				printLog(out, logKey);
				
				trbe.printStackTrace();
			}
			catch (javax.ejb.EJBException trbe) 
			{			
                        // Result of BusinessException	
				printLog(out, logKey);
				
				trbe.printStackTrace();
			}
			catch (SystemException se) 
			{
				se.printStackTrace(System.out);
				request.getRequestDispatcher("error.jsp").forward(request, response);
			}
                  catch (Exception e)
			{
				e.printStackTrace(System.out);
				request.getRequestDispatcher("error.jsp").forward(request, response);
			}
			finally
			{
				LogFactory.deleteLog(logKey);
			}
			
			out.println("<HR/>");
			out.println("<P><a href=\"basicScenarioConfig.jsp\">Replay</a> | <a href=\"main.jsp\">Back to samples</a></P>");
			
			out.println("</BODY></HTML>");
			
		}
		else
		{
			System.out.println("request or response was null, req: " + request + ", resp: " + response);
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	}

	private List validateParameters(String emailTo) 
	{
		ArrayList errors = new ArrayList();
		
		if (emailTo == null)
		{
			errors.add("The Customer Name cannot be null.");
		} 
		else if (emailTo.trim().equals(""))
		{
			errors.add("The Customer Name cannot be ''.");
		}
		
		if (errors.size() == 0)
		{
			errors = null;
		}
		
		return errors;
	}

	private void printLog(PrintWriter out, String logKey) 
	{
		Log basicLog = LogFactory.getLog(logKey);
		
		List entries = basicLog.getEntries();
		
		Iterator entriesIterator = entries.iterator();
		
		while (entriesIterator.hasNext())
		{
			String entry = (String) entriesIterator.next();
			
			out.println(entry);
		}
	}

}