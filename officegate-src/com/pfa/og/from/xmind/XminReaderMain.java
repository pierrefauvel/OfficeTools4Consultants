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
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XminReaderMain {

	public static void main(String[] args) throws ZipException, IOException, ParserConfigurationException, SAXException, XPathExpressionException
	{
//		String directory = "C:/Documents and Settings/pfauvel/Mes documents/Perso2009";
//		String file = "Curriculum_Vitae.xmind";
//		String file = "Vie pro.xmind";
		String directory = "C:\\Documents and Settings\\pfauvel\\Mes documents\\SQLI\\pratiques agiles";
		String file = "CMMI_Agile_Atelier_4_TRD.xmind";
		Sheet[] sh=new XmindReader().read(new File(directory+"\\"+file));
//		new XmindConsoleDump(System.out).dump(sh);
		new XmindHtmlDump(System.out).dump(sh);
	}

}
