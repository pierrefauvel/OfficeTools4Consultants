package com.pfa.og.swing.viewer.csv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class CSVModel {
	private PrintWriter _pw;
	private String[] _h;
	private List<String[]> _o;
	private File _f;
	
	public CSVModel(File f,String... h) throws IOException
	{
		_h=h;
		setFile(f);
	}
	
	public void println( String... o)
	{
		for(String s : o)
		{
			if(s!=null)
			{
				if(s.indexOf(";")!=-1)
					_pw.print('"'+s+'"');
				_pw.print(s);
			};
			_pw.print(";");
		};
		_pw.println();
		_o.add(o);
		if(o.length!=_h.length)
			throw new RuntimeException("Expected "+_h.length+", got only "+o.length);
	}
	
	public void close()
	{
		_pw.close();
	}

	public void setFile(File f) throws IOException {
		f.getParentFile().mkdirs();
		_pw=new PrintWriter(new FileWriter(f));
		_o=new ArrayList<String[]>();
		for(String s : _h)
		{
			if(s.indexOf(";")!=-1)
				_pw.print('"'+s+'"');
			_pw.print(s);
			_pw.print(";");
		};
		_f=f;
	}

	public File getFile()
	{
		return _f;
	}
	
	public JComponent toSwingComponent()
	{
		JTable tbl = new JTable(_o.toArray(new String[][]{}),_h);
		return new JScrollPane(tbl);
	}
}
