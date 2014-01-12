package com.pfa.og.transform.xmind2word;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.zip.ZipException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.pfa.og.from.xmind.IXMindInterface;
import com.pfa.og.from.xmind.Sheet;
import com.pfa.og.from.xmind.XmindHtmlDump;
import com.pfa.og.from.xmind.XmindReader;
import com.pfa.og.pivot.Outline;
import com.pfa.og.to.word.OfficeGateHtml4WordGenerator;

public class Xmind2WordMain4Directories implements IXMindInterface {

	
	public static void main(String[] args) throws XPathExpressionException, ZipException, IOException, ParserConfigurationException, SAXException
	{
		String[] directories = new String[]{
		//		"C:\\Documents and Settings\\pfauvel\\Mes documents\\SQLI\\CMMI Agile\\Atelier 1 - presentations agile\\",
		//		"C:\\Documents and Settings\\pfauvel\\Mes documents\\SQLI\\CMMI Agile\\Atelier 4 - pratiques agiles\\",
//				"C:\\Documents and Settings\\pfauvel\\Mes documents\\SQLI\\offre agile\\"
//				"C:\\Documents and Settings\\pfauvel\\Mes documents\\SQLI\\Avant Vente\\wafa\\"
//				"C:\\Documents and Settings\\pfauvel\\Mes documents\\SQLI\\Missions\\bioMerieux\\rex"
//				"C:\\Documents and Settings\\pfauvel\\Mes documents\\SQLI\\CMMI-Agile 2010\\Propal Agile"
//				"C:\\Documents and Settings\\pfauvel\\Mes documents\\SQLI\\Missions\\Wafa Assurances\\Réunions\\2010 06"
//				"C:\\Documents and Settings\\pfauvel\\Mes documents\\SQLI\\Missions\\Wafa Assurances\\Réunions\\2010 0630 0701"
//				"C:\\Documents and Settings\\pfauvel\\Mes documents\\SQLI\\Missions\\Wafa Assurances\\Sessions\\2010 0803 0804 - product backlogs & identification PO & specs Appli Blanche"
//				"C:\\Documents and Settings\\pfauvel\\Mes documents\\SQLI\\Missions\\Wafa Assurances\\Sessions\\2010 0817 0818 - Atelier PO & Backlog Appli réelle & Atelier SM"
				"C:\\Documents and Settings\\pfauvel\\Mes documents\\SQLI\\Externe - Avant vente\\EDF CC\\pfl",
				"C:\\Documents and Settings\\pfauvel\\Mes documents\\SQLI\\Externe - Avant vente\\CEGID P-AGI"
		};

		System.out.println("Starting...");
		for(String directory:directories)
		{
			System.out.println("In directory "+directory);
//			new File(directory+File.separator+"generated-docs").mkdirs();
			for(File f: new File(directory).listFiles(XMIND_FILES))
			{
				Xmind2Word.generateDocs(f);
			};
		}
		System.out.println("Done.");
	}
}
