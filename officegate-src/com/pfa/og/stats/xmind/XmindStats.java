package com.pfa.og.stats.xmind;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.pfa.og.from.xmind.Sheet;
import com.pfa.og.from.xmind.Topic;
import com.pfa.og.from.xmind.XmindReader;
import com.pfa.og.swing.viewer.csv.CSVModel;

public class XmindStats {
	
	private static void dump(Topic[] t, String path,Map<String,String> flagMap, CSVModel pw)
	{
		for(Topic ti : t)
		{
			
			String flag=null;
			for(String k:ti.getFlags())
			{
				if(flag!=null)
					System.err.println("Duplicated flag on "+path+"/"+ti.getLabel());
				else
				{
					flag=k;
				}
			}

			String label = ti.getLabel();
			Integer count = 1;
			int idx = label.indexOf(" ");
			if(idx!=-1)
			{
				String strCount = ti.getLabel().substring(0,idx);
				try
				{
					Integer tmp = Integer.parseInt(strCount);
					count=tmp;
					label = label.substring(idx+1);
				}
				catch(Throwable throwable)
				{
					
				}
			}
			if(flag!=null)
//				pw.println(path+";"+ti.getLabel()+";"+count+";"+flagMap.get(flag)+";"+(ti.getChildren().length>0));
				pw.println(path,ti.getLabel(),""+count,flagMap.get(flag),""+(ti.getChildren().length>0));
			else
				pw.println(path,ti.getLabel(),""+count,"",""+(ti.getChildren().length>0));
//				pw.println(path+";"+ti.getLabel()+";"+count+";;"+(ti.getChildren().length>0));
				
			dump(ti.getChildren(),path+"/"+label,flagMap,pw);
		}
	}
/*
	public static void old_main(String[] args) throws IOException, XPathExpressionException, ParserConfigurationException, SAXException
	{
		String path = "D:/SQLI/Missions/Valtech - Michelin/Agile@Michelin/20110614 transfo 3/CR 2011 06 14-2-postits retranscrits.xmind";
		XmindReader reader = new XmindReader();
		Sheet[] sheet = reader.read(new File(path));

		Map<String,String> flagMap=new HashMap<String,String>();
		flagMap.put("task-done", "Fait");
		flagMap.put("task-half", "En cours");
		flagMap.put("task-start", "A faire");

		FileWriter fw=new FileWriter("XmindPresentationManager_Backlog.csv");
		PrintWriter pw = new PrintWriter(fw);
		
	}
	
	public static void old_main_formation(String[] args) throws XPathExpressionException, ZipException, IOException, ParserConfigurationException, SAXException
	{
		String path = "C:/Documents and Settings/pfauvel/Bureau/Formation Agile SKILLS/Plan formation Agile SQLI le 20110609.xmind";
		XmindReader reader = new XmindReader();
		Sheet[] sheet = reader.read(new File(path));

		Map<String,String> flagMap=new HashMap<String,String>();
		
		Topic legende=null;
		for( Topic t : sheet[0].getDetachedChildren())
		{
			if(t.getLabel().compareTo("Légende")==0)
			{
				for(Topic tli : t.getChildren())
				{
					flagMap.put(tli.getFlags().iterator().next(),tli.getLabel());
				}
			}
		}
				
		FileWriter fw=new FileWriter("XmindPresentationManager_Formation.csv");
		PrintWriter pw = new PrintWriter(fw);
		
		System.out.println("Path;Name;Count;Flag");
		Topic[] t = sheet[0].getChildren()[0].getChildren();
		dump(t,"",flagMap);
	}
*/
	public static CSVModel[] generateDocs(File f) throws XPathExpressionException, ZipException, IOException, ParserConfigurationException, SAXException
	{
		XmindReader reader = new XmindReader();
		Sheet[] sheet = reader.read(f);

		Map<String,String> flagMap=new HashMap<String,String>();
		
		Topic legende=null;
		for( Topic t : sheet[0].getDetachedChildren())
		{
			if(t.getLabel().compareTo("Légende")==0)
			{
				for(Topic tli : t.getChildren())
				{
					flagMap.put(tli.getFlags().iterator().next(),tli.getLabel());
				}
			}
		}
				

//		List<File> lst_files=new ArrayList<File>();
		List<CSVModel> lst_files=new ArrayList<CSVModel>();
		
		for(Sheet sh : sheet)
		{
			Topic[] t = sh.getChildren()[0].getChildren();
			File fout = new File(f.getParentFile()+File.separator+"generated-docs"+File.separator+f.getName()+"."+sh.getLabel()+".csv");
//			lst_files.add(fout);
//			FileWriter fw=new FileWriter(fout);
			CSVModel pw = new CSVModel(fout,"Path","Name","Count","Flag","Empty");
//			PrintWriter pw = new PrintWriter(fw);
//			pw.println("Path;Name;Count;Flag");
			dump(t,"",flagMap,pw);
			pw.close();
			lst_files.add(pw);
		};
		
		return lst_files.toArray(new CSVModel[]{});
	}

}
