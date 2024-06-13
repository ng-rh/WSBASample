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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Log implements Serializable
{
	private ArrayList entries = new ArrayList();
	
	public void addH1Entry(String entry)
	{
		entries.add("<H1>" + entry + "</H1>");
	}

	public void addH2Entry(String entry)
	{
		entries.add("<H2>" + entry + "</H2>");
	}
	
	public void addH3Entry(String entry)
	{
		entries.add("<H3>" + entry + "</H3>");
	}
	
	public void addH4Entry(String entry)
	{
		entries.add("<H4>" + entry + "</H4>");
	}
	
	public void addPEntry(String entry)
	{
		entries.add("<P>" + entry + "</P>");
	}
	
	public void addRaw(String entry)
	{
		entries.add(entry);
	}
	
	public void addHyperlinkEntry(String entry, String link)
	{
		entries.add("<P><a href=\"" + link + "\"  target=\"_blank\">" + entry + "</a><P>");
	}
	
	public List getEntries()
	{
		return entries;
	}
}
