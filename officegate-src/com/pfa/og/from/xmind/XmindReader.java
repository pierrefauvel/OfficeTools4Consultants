package com.pfa.og.from.xmind;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class XmindReader {
	
	private static XPathExpression XP_ROOT_2_SHEET;
	private static XPathExpression XP_SHEET_TITLE;
	private static XPathExpression XP_TOPIC_TITLE;
	private static XPathExpression XP_SHEET_2_TOPIC;
	private static XPathExpression XP_TOPIC_2_TOPIC;
	private static XPathExpression XP_TOPIC_MARKER;
	private static XPathExpression XP_TOPIC_STRUCTURE;
	private static XPathExpression XP_ALL_DETACHED;

	static
	{
		XPathFactory xpf=XPathFactory.newInstance();
		XPath xp=xpf.newXPath();
		try
		{
			XP_ROOT_2_SHEET=xp.compile("//sheet");
			XP_SHEET_TITLE=xp.compile("title");
			XP_TOPIC_TITLE=xp.compile("title");
			XP_TOPIC_MARKER=xp.compile("marker-refs/marker-ref/@marker-id");
			XP_SHEET_2_TOPIC=xp.compile("topic");
			XP_TOPIC_2_TOPIC=xp.compile("children/topics[@type='attached']/topic");
			XP_TOPIC_STRUCTURE=xp.compile("@structure-class");
			XP_ALL_DETACHED=xp.compile("//topics[@type='detached']/topic");
		}
		catch(Throwable t)
		{
			t.printStackTrace();
		}
	}

	private Topic[] readTopics(/*String indent, */NodeList nl_topic) throws XPathExpressionException
	{
		Topic[] t = new Topic[nl_topic.getLength()];
		for(int j=0;j<nl_topic.getLength();j++)
		{
			Node n_topic=nl_topic.item(j);
			NodeList nl_flags = (NodeList)(XP_TOPIC_MARKER.evaluate(n_topic,XPathConstants.NODESET));
			String structure = XP_TOPIC_STRUCTURE.evaluate(n_topic);
			t[j]= new Topic(XP_TOPIC_TITLE.evaluate(n_topic));
			if(structure.length()>0) t[j].setStructure(structure);
			for(int i=0;i<nl_flags.getLength();i++)
			{
				String flagName = nl_flags.item(i).getNodeValue();
				t[j].addFlag(flagName);
			}
			NodeList nl_topic2 = (NodeList)(XP_TOPIC_2_TOPIC.evaluate(n_topic,XPathConstants.NODESET));
			Topic[] ti=readTopics(/*indent+"\t",*/nl_topic2);
			for (int k=0;k<ti.length;k++)
				t[j].addChild(ti[k]);
		}
		return t;
	}

	public Sheet[] read(File f) throws XPathExpressionException, ZipException, IOException, ParserConfigurationException, SAXException
	{
		ZipFile zf = new ZipFile(f);
		ZipEntry ze = zf.getEntry("content.xml");
		InputStream is = zf.getInputStream(ze);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document d = db.parse(is);

		NodeList nl_sheet = (NodeList)(XP_ROOT_2_SHEET.evaluate(d,XPathConstants.NODESET));
		Sheet[] sh=new Sheet[nl_sheet.getLength()];
		for(int i=0;i<nl_sheet.getLength();i++)
		{
			Node n_sheet=nl_sheet.item(i);
			sh[i] = new Sheet(XP_SHEET_TITLE.evaluate(n_sheet));
			NodeList nl_topic = (NodeList)(XP_SHEET_2_TOPIC.evaluate(n_sheet,XPathConstants.NODESET));
			Topic[] t=readTopics(/*"\t",*/nl_topic);
			for (int j=0;j<t.length;j++)
				sh[i].addChild(t[j]);
			Topic[] dt = readTopics((NodeList)(XP_ALL_DETACHED.evaluate(n_sheet,XPathConstants.NODESET)));
			for(Topic dti:dt)
			{
				sh[i].addDetachedChild(dti);
			}
		};
		
		return sh;
	}
}
