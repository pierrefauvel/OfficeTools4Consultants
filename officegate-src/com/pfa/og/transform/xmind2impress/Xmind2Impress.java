package com.pfa.og.transform.xmind2impress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.pfa.og.from.xmind.Sheet;
import com.pfa.og.from.xmind.XmindReader;
import com.pfa.og.from.xmind.XmindToOutlineRaccourci;
import com.pfa.og.pivot.Outline;
import com.pfa.og.to.impress32.Outline2Impress32;
import com.pfa.og.to.word.OfficeGateHtml4WordGenerator;

public class Xmind2Impress {

		public static String[] generateDocs(File f) throws XPathExpressionException, ZipException, IOException, ParserConfigurationException, SAXException, TransformerException
		{
			List<String> rc =new ArrayList<String>();
			Sheet[] sh=new XmindReader().read(f);
			for(Sheet shi:sh)
			{
			Outline o=new XmindToOutlineRaccourci().convert(shi);
			String suffix = f.getName()+"."+shi.getLabel()+"."+shi.getChildren()[0].getLabel()+".odp";
			suffix = suffix.replace(":", " ");
			suffix = suffix.replace("/","_");
			suffix = suffix.replace("\\","_");
			File f1 = new File(f.getParentFile().getCanonicalPath()+File.separator+"generated-docs"+File.separator+suffix);
			f1.getParentFile().mkdirs();
			System.out.println("Generating file "+f1.getCanonicalPath());
			Outline2Impress32.convert(o, f1, f.getCanonicalPath()+" "+new SimpleDateFormat().format(new Date()));
			rc.add(f1.getCanonicalPath());
			}
			return rc.toArray(new String[]{});
		}

}
