package com.pfa.og.to.word;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import com.pfa.og.pivot.Outline;

public class OfficeGateHtml4WordGenerator {

	private PrintStream ps;
	
	public OfficeGateHtml4WordGenerator(PrintStream ps)
	{
		this.ps=ps;
	}
	
	public void generate(Outline o) throws IOException
	{
//		writeFromFile("src/com/pfa/word/Header.xml");
		writeFromFile(OfficeGateHtml4WordGenerator.class.getResourceAsStream("Header.xml"));
		generateFromOutline(o);
//		writeFromFile("src/com/pfa/word/Footer.xml");
		writeFromFile(OfficeGateHtml4WordGenerator.class.getResourceAsStream("Footer.xml"));
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
		ps.println("<h"+level+">"+o.getText()+"</h"+level+">");
		for (Outline oi : o.getChildren())
		{
			generateFromOutline(level+1,oi);
		}
	}
	private void writeFromFile(InputStream is) throws IOException
	{
//		InputStream is = new FileInputStream(path);
		int c;
		while ((c=is.read())!=-1)
		{
			ps.write(c);
		}
	}
}
