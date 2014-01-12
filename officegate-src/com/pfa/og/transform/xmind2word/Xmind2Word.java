package com.pfa.og.transform.xmind2word;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.pfa.og.from.xmind.Sheet;
import com.pfa.og.from.xmind.XmindReader;
import com.pfa.og.from.xmind.XmindToOutlineRaccourci;
import com.pfa.og.pivot.Outline;
import com.pfa.og.to.word.OfficeGateHtml4WordGenerator;

public class Xmind2Word {
	public static String[] generateDocs(File f) throws XPathExpressionException, ZipException, IOException, ParserConfigurationException, SAXException
	{
		List<String> rc =new ArrayList<String>();
		Sheet[] sh=new XmindReader().read(f);
		for(Sheet shi:sh)
		{
		Outline o=new XmindToOutlineRaccourci().convert(shi);
		String suffix = f.getName()+"."+shi.getLabel()+"."+shi.getChildren()[0].getLabel()+".doc";
		suffix = suffix.replace(":", " ");
		suffix = suffix.replace("/","_");
		suffix = suffix.replace("\\","_");
		File f1 = new File(f.getParentFile().getCanonicalPath()+File.separator+"generated-docs"+File.separator+suffix);
		f1.getParentFile().mkdirs();
		System.out.println("Generating file "+f1.getCanonicalPath());
		FileOutputStream f1s = new FileOutputStream(f1);
		PrintStream ps = new PrintStream(f1s);
		new OfficeGateHtml4WordGenerator(ps).generate(o);
		f1s.close();
		rc.add(f1.getCanonicalPath());
		}
		return rc.toArray(new String[]{});
	}
}
