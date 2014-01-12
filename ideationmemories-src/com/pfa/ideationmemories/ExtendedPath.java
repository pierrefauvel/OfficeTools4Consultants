package com.pfa.ideationmemories;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExtendedPath implements Cloneable
{
	public static final String SEP = " _#SEP#_ ";
	public List<StringBuffer> listOfPaths=new ArrayList<StringBuffer>();
	
	public void addToken(String name)
	{
		listOfPaths.get(listOfPaths.size()-1).append(File.separator).append(name);
	}
	public void newPath()
	{
		listOfPaths.add(new StringBuffer());
	}
	public ExtendedPath clone()
	{
			return clone(this);
	}
	private static ExtendedPath clone (ExtendedPath source)
	{
		ExtendedPath rc = new ExtendedPath();
		for(StringBuffer sb:source.listOfPaths)
		{
			rc.listOfPaths.add(new StringBuffer(sb.toString()));
		}
		return rc;
	}
	public String toString()
	{
		StringBuffer sb=new StringBuffer();
		int k=0;
		for(StringBuffer sbi:listOfPaths)
		{
			if(k>0 && sbi.length()>0)sb.append(SEP);
			sb.append(sbi);
			k++;
		};
		return sb.toString();
	};
	public static ExtendedPath fromString(String str)
	{
		ExtendedPath ep = new ExtendedPath();
		String[] tok = str.split(SEP);
		for(String toki:tok)
		{
			ep.listOfPaths.add(new StringBuffer(toki));
		};
		return ep;
	}
	public String getExtension() {
		String end_file_name = this.listOfPaths.get(this.listOfPaths.size()-1).toString();
		int idx = end_file_name.lastIndexOf(".");
		return end_file_name.substring(idx+1);
	}
	public String toTokenizedString() {
		String string = toString();	
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<string.length();i++)
		{
			char c = string.charAt(i);
			if(Character.isLetter(c))
				sb.append(c);
			else if(Character.isDigit(c))
				sb.append(c);
			else
				sb.append(" ");
		};
		return sb.toString();
	}
}