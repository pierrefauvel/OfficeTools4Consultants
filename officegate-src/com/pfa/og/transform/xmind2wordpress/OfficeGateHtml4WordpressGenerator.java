package com.pfa.og.transform.xmind2wordpress;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import com.pfa.og.pivot.Outline;

public class OfficeGateHtml4WordpressGenerator {

	private PrintStream ps;
	
	public OfficeGateHtml4WordpressGenerator(PrintStream ps)
	{
		this.ps=ps;
	}
	
	public void generate(Outline o) throws IOException
	{
		generateFromOutline(o);
		writeFromFile("src/com/pfa/og/transform/xmind2wordpress/FooterCC.html");
	}
	private void writeFromFile(String path) throws IOException
	{
		InputStream is = new FileInputStream(path);
		int c;
		while ((c=is.read())!=-1)
		{
			ps.write(c);
		}
	}
	
	private void generateFromOutline(Outline o)
	{
		if(o.getText()!=null)
			generateFromOutline(1,o);
		else
		for (Outline oi:o.getChildren())
		{
			generateFromOutline(1,oi);
		}
	}
	private void generateFromOutline(int level,Outline o)
	{
//		int size = 120 - 12 * level;
//		int size = 140 - 10 * level;
		int size = 120 - 6 * level;
		
//		String[] vhex = new String[]{"20", "40", "80", "C0", "FF"};
//		String[] vhex = new String[]{"20", "38", "50", "68", "80"};
		String[] vhex = new String[]{"20", "3B", "58", "6F", "8C"};
//		String[] vbhex = new String[]{"E0", "C0", "A0", "70", "5	0"};
//		String[] vbhex = new String[]{"E0", "D0", "C0", "B0", "A0"};
		String[] vbhex = new String[]{ "FF", "FF","FF","FF","FF"};
		String hex_col = vhex[0];
		if(level-1<vhex.length)
			hex_col=vhex[level-1];
		String hex_bkg = vbhex[0];
		if(level-1<vhex.length)
			hex_bkg=vbhex[level-1];
		String color = "#"+hex_col+hex_col+hex_col;
		String background = "#"+hex_bkg+hex_bkg+hex_bkg;
		ps.println("<span style=\"color:"+color+";background-color:"+background+";font-family:Verdana;font-size:"+size+"%\">"+o.getText()+"</span><ul>");
		for (Outline oi : o.getChildren())
		{
			ps.println("<li>");
			generateFromOutline(level+1,oi);
			ps.println("</li>");
					}
		ps.println("</ul>");
	}
}
