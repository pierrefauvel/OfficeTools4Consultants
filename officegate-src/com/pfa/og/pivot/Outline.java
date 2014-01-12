package com.pfa.og.pivot;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Outline {
	private List<Outline> lst_children=new ArrayList<Outline>();
	private String txt;
	public Outline(String txt)
	{
		this.txt=txt;
	}
	public String getText(){
		return txt;
	}
	public void addChild(Outline o)
	{
		lst_children.add(o);
	}
	public List<Outline> getChildren()
	{
		return lst_children;
	}
	
	public static Document toXml(Outline o, String subtitle) throws ParserConfigurationException
	{
		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document d = db.newDocument();
		Element e = d.createElement("o");
		e.setAttribute("subtitle",subtitle);
		e.setAttribute("text",o.getText());
		e.setAttribute("level", "0");
		for(Outline oi : o.getChildren())
		{
			toXml(oi,d,e,1);
		}
		d.appendChild(e);
		return d;
	}
	private static void toXml(Outline o, Document d, Element parent, int level) {
		Element e = d.createElement("o");
		e.setAttribute("text", o.getText());
		e.setAttribute("level",""+level);
		for(Outline oi : o.getChildren())
		{
			toXml(oi,d,e,level+1);
		}
		parent.appendChild(e);
	}
}
