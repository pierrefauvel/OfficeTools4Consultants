package com.pfa.og.to.impress32;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.pfa.og.pivot.Outline;

public class Outline2Impress32 {
	
	public static void main(String[] args) throws IOException, ParserConfigurationException, TransformerException
	{
		Outline o1211 = new Outline("o1211");
		Outline o1212 = new Outline("o1212");

		Outline o121 = new Outline("o121");
		Outline o122 = new Outline("o122");
		
		Outline o11 = new Outline("o11");
		Outline o12 = new Outline("o12");
		Outline o13 = new Outline("o13");
		
		o121.addChild(o1211);
		o121.addChild(o1212);
		
		o12.addChild(o121);
		o12.addChild(o122);
		
		Outline o1 = new Outline("slide-1");
		o1.addChild(o11);
		o1.addChild(o12);
		o1.addChild(o13);

		Outline o2 = new Outline("slide-2");
		
		Outline o = new Outline("outline2ppt");
		o.addChild(o1);
		o.addChild(o2);
		
		convert(o, new File("Outline2Odp.odp"),"Mon sous-titre, le "+new Date());
	}

	
	public static void convert( Outline o, File r, String subtitle) throws IOException, ParserConfigurationException, TransformerException
	{
		InputStream is = Outline2Impress32.class.getResourceAsStream("demo.odp");
		File f = File.createTempFile("Outline2Odp", "odp");
		{
			FileOutputStream fos = new FileOutputStream(f);
			int c;
			while( (c = is.read())!=-1)
			{
				fos.write(c);
			}
			fos.close();
			System.out.println("Generated file "+f.getCanonicalPath());
		};
		ZipFile z_in = new ZipFile(f);
		FileOutputStream fos = new FileOutputStream(r);
		ZipOutputStream zos = new ZipOutputStream(fos);
		zos.setLevel(Deflater.DEFAULT_COMPRESSION);
	      
		Enumeration<? extends ZipEntry> e = z_in.entries();
		while(e.hasMoreElements())
		{
			ZipEntry ze = e.nextElement();
			System.out.println(ze.getName());
		      zos.putNextEntry(new ZipEntry(ze.getName()));
		      
		      if(ze.getName().compareTo("content.xml")==0)
		      {
		    	  TransformerFactory tf = TransformerFactory.newInstance();
		    	  StreamSource transformerSource = new StreamSource(Outline2Impress32.class.getResourceAsStream("content.xslt"));
		    	  StreamResult result = new StreamResult(zos);
		    	  DOMSource source = new DOMSource(Outline.toXml(o,subtitle));
		    	  Transformer t = tf.newTransformer(transformerSource);
		    	  t.transform(source,result);
		    	  
		    	  Transformer tidl = tf.newTransformer();
		    	  StreamResult ridl = new StreamResult("temp_outline.xml");
		    	  StreamResult rid2 = new StreamResult("temp_result.xml");
		    	  tidl.transform(source, ridl);
		    	  t.transform(source,rid2);

		      }
		      else
		      {
					InputStream z_is = z_in.getInputStream(ze);
			      int c;
			      while ((c = z_is.read()) !=-1){
			    	  zos.write(c);
			      }
			      zos.closeEntry();
			      z_is.close();
		      }
		}
	    zos.close();
	    System.out.println("Generated file "+r.getCanonicalPath());
	}
}
