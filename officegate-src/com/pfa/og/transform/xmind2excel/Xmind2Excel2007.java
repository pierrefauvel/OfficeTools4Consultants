package com.pfa.og.transform.xmind2excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.xml.sax.SAXException;

import com.pfa.og.from.xmind.Sheet;
import com.pfa.og.from.xmind.XmindReader;
import com.pfa.og.from.xmind.XmindToOutline;
import com.pfa.og.pivot.Outline;
import com.pfa.og.to.excel2007.Outline2Excel2007;

public class Xmind2Excel2007 {
	public static String toCSV(File f) throws XPathExpressionException, ZipException, IOException, ParserConfigurationException, SAXException
	{
		File fout = new File(f.getParentFile()+File.separator+"generated-docs"+File.separator+f.getName()+".xlsx");
		fout.getParentFile().mkdirs();
		Sheet[] sh = new XmindReader().read(f);
		Outline2Excel2007 o2e = new Outline2Excel2007(fout);
		for(Sheet shi : sh)
		{
			Outline i = new XmindToOutline().convert(shi);
			o2e.add(i);
		}
		o2e.flush();
		return fout.getCanonicalPath();
	}
	
//	public static void main(String[] args) throws XPathExpressionException, ZipException, IOException, ParserConfigurationException, SAXException
//	{
//		Xmind2Excel2007.toCSV(new File("F:/Michelin/[Transverse]/sprint 6/Training Contexte/Plan v10.xmind"), new File("out.xlsx"));
//	}
}
