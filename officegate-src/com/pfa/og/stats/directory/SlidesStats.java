package com.pfa.og.stats.directory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.xml.sax.SAXException;

import com.pfa.og.from.xmind.Sheet;
import com.pfa.og.from.xmind.XmindReader;
import com.pfa.og.swing.viewer.csv.CSVModel;

public class SlidesStats {
	
	private static int countSlides(File f) throws FileNotFoundException, IOException
	{
		if(f.getName().endsWith(".pptx"))
		{
			XMLSlideShow pptx = new XMLSlideShow(new FileInputStream(f));
			return pptx.getSlides().length;
		}
		else if(f.getName().endsWith(".ppt"))
		{
			SlideShow ssh = new SlideShow(new FileInputStream(f));
			return ssh.getSlides().length;
		}
		return -1;
	}
	/*
	public static void main(String[] args) throws FileNotFoundException, IOException, XPathExpressionException, ParserConfigurationException, SAXException
	{
		File f = new File("D:/Dropbox/En cours - Pro Alcyonix/Formation/SIG/contenu");
	}
	*/
	public static CSVModel countSlidesInDirector(File f) throws FileNotFoundException, IOException, XPathExpressionException, ParserConfigurationException, SAXException {
		File fout = new File(f.getParentFile()+File.separator+"generated-docs"+File.separator+f.getName()+".csv");
		CSVModel pw = new CSVModel(fout,"Item","Count");
		pw.setFile(fout);
		fout.getParentFile().mkdirs();
//		PrintWriter pw = new PrintWriter(new FileWriter(fout));
		for(File fi : f.listFiles())
		{
			if(fi.getName().endsWith(".pptx"))
			{
				int c = countSlides(fi);
				pw.println(fi.getName(),""+c);
			}
			if(fi.getName().endsWith(".xmind"))
			{
				Sheet[] sh=new XmindReader().read(fi);
				int s=0;
				for(Sheet shi:sh)
				{
					s+=shi.getChildren()[0].getChildren().length;
				}
				pw.println(fi.getName(),""+s);
			}
		};
		pw.close();
		return pw;
	}
}
