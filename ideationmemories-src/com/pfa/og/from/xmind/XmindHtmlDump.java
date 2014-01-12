package com.pfa.og.from.xmind;

import java.io.PrintStream;

public class XmindHtmlDump {

	private PrintStream pw;
	
	public XmindHtmlDump(PrintStream pw)
	{
		this.pw=pw;
	}
	
	public void dump(Sheet[] sh)
	{
		for (int i=0;i<sh.length;i++)
		{
			pw.println("<h4>"+sh[i].getLabel()+"</h4>");
			pw.println("<ul>");
			dump(sh[i].getChildren());
			pw.println("</ul>");
		}
	}

	private void dump(Topic[] tp)
	{
		for (int i=0;i<tp.length;i++)
		{
			pw.print("<li>");
			pw.println(tp[i].getLabel()+"<ul>");
			dump(tp[i].getChildren());
			pw.println("</ul>");
			pw.print("</li>");
		}
	}

}
