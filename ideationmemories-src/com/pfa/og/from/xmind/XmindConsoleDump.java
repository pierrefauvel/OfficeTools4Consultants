package com.pfa.og.from.xmind;

import java.io.PrintStream;
import java.util.Iterator;

public class XmindConsoleDump {

	private PrintStream pw;
	
	public XmindConsoleDump(PrintStream pw)
	{
		this.pw=pw;
	}
	
	public void dump(Sheet[] sh)
	{
		for (int i=0;i<sh.length;i++)
		{
	//		pw.println("Sheet:"+sh[i].getLabel());
			dump("\t",sh[i].getChildren());
		}
	}

	private void dump(String indent,Topic[] tp)
	{
		for (int i=0;i<tp.length;i++)
		{
			pw.println(indent+"Topic:"+tp[i].getLabel()+" flag:"+dump(tp[i].getFlags())+" structure:"+tp[i].getStructure());
			dump(indent+"\t",tp[i].getChildren());
		}
	}

	private static String dump(Iterable<String> it)
	{
		StringBuffer sb=new StringBuffer();
		boolean first=true;
		for(String k:it)
		{
			if(!first)sb.append(",");
			sb.append(k);
			first=false;
		};
		return sb.toString();
	}
}
