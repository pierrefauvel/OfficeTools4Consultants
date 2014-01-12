package com.pfa.og.transform.xmind2wordpress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.zip.ZipException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.pfa.og.from.xmind.IXMindInterface;
import com.pfa.og.from.xmind.Sheet;
import com.pfa.og.from.xmind.XmindReader;
import com.pfa.og.from.xmind.XmindToOutlineRaccourci;
import com.pfa.og.pivot.Outline;
import com.pfa.og.to.word.OfficeGateHtml4WordGenerator;

public class Xmind2WordPressMain implements IXMindInterface{
	public static void main(String[] args) throws XPathExpressionException, ZipException, IOException, ParserConfigurationException, SAXException
	{
		String[] directories = new String[]{
				"D:/Dropbox/En cours - PC Sqli/Softreves/N-S/Publiés",
				"D:/Dropbox/En cours - PC Sqli/Softreves/N-S/Non publié"
				};
		System.out.println("Starting...");
		for(String directory:directories)
		{
			System.out.println("In directory "+directory);
			if(!new File(directory).exists())
				System.err.println("Directory "+directory+" does not exist");
			new File(directory+File.separator+"generated-docs").mkdirs();
			for(File f: new File(directory).listFiles(XMIND_FILES))
			{
				Sheet[] sh=new XmindReader().read(f);
				for(Sheet shi:sh)
				{
				Outline o=new XmindToOutlineRaccourci().convert(shi);
				String suffix = f.getName()+"."+shi.getLabel()+"."+shi.getChildren()[0].getLabel()+".fragment.html";
				suffix = suffix.replace(":", " ");
				File f1 = new File(f.getParentFile().getCanonicalPath()+File.separator+"generated-docs"+File.separator+suffix);
				System.out.println("Generating file "+f1.getCanonicalPath());
				FileOutputStream f1s = new FileOutputStream(f1);
				PrintStream ps = new PrintStream(f1s);
				new OfficeGateHtml4WordpressGenerator(ps).generate(o);
				f1s.close();
				}
			};
		}
		System.out.println("Done.");
	}
}
