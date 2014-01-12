package com.pfa.og.to.powerpoint2007;

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

public class Outline2Powerpoint2007 {
	
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
		
		convert(o, new File("Outline2Powerpoint.pptx"),"Mon sous-titre, le "+new Date());
	}

	
	public static void convert( Outline o, File r, String subtitle) throws IOException, ParserConfigurationException, TransformerException
	{
		InputStream is = Outline2Powerpoint2007.class.getResourceAsStream("demo.pptx");
		File f = File.createTempFile("Outline2Powerpoint", "ppt");
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
	      
	  	  TransformerFactory tf = TransformerFactory.newInstance();
		
	  	  System.out.println("Loading slide-front.xslt");
	  	  StreamSource transformerSourceFront = new StreamSource(Outline2Powerpoint2007.class.getResourceAsStream("slide-front.xslt"));
	  	  Transformer t_front = tf.newTransformer(transformerSourceFront);
		  
	  	  System.out.println("Loading slide-current.xslt");
	  	  StreamSource transformerSourceCurrent = new StreamSource(Outline2Powerpoint2007.class.getResourceAsStream("slide-current.xslt"));
	  	  Transformer t_current = tf.newTransformer(transformerSourceCurrent);

	  	  System.out.println("Loading app.xslt");
	  	  StreamSource transformerSourceApp = new StreamSource(Outline2Powerpoint2007.class.getResourceAsStream("app.xslt"));
	  	  Transformer t_app = tf.newTransformer(transformerSourceApp);

	  	  System.out.println("Loading content-type.xslt");
	  	  StreamSource transformerSourceContentType = new StreamSource(Outline2Powerpoint2007.class.getResourceAsStream("content-type.xslt"));
	  	  Transformer t_ct = tf.newTransformer(transformerSourceContentType);

	  	  System.out.println("Loading presentation-rels.xslt");
	  	  StreamSource transformerSourcePresentationRels = new StreamSource(Outline2Powerpoint2007.class.getResourceAsStream("presentation-rels.xslt"));
	  	  Transformer t_pr = tf.newTransformer(transformerSourcePresentationRels);

	  	  System.out.println("Loading presentation.xslt");
	  	  StreamSource transformerSourcePresentation = new StreamSource(Outline2Powerpoint2007.class.getResourceAsStream("presentation.xslt"));
	  	  Transformer t_pres = tf.newTransformer(transformerSourcePresentation);

	  	  Enumeration<? extends ZipEntry> e = z_in.entries();
		while(e.hasMoreElements())
		{
			ZipEntry ze = e.nextElement();
//			System.out.println("Handling "+ze.getName());
		      
		      if(ze.getName().startsWith("ppt/slides/slide"))
		      {
		    	  System.out.println("Skipping "+ze.getName());
		      }
		      else if(ze.getName().startsWith("ppt/slides/_rels/"))
		      {
		    	  System.out.println("Skipping "+ze.getName());
		      }
		      else if(ze.getName().startsWith("docProps/app.xml"))
		      {
		    	  System.out.println("Skipping "+ze.getName());
			      zos.putNextEntry(new ZipEntry("docProps/app.xml"));
			  	  StreamResult resulta = new StreamResult(zos);
			  	  DOMSource sourcea = new DOMSource(Outline.toXml(o,subtitle));
			  	  t_app.transform(sourcea,resulta);
			  	  System.out.println("Transforming docProps/app.xml");
		      }
		      else if(ze.getName().startsWith("[Content_Types].xml"))
		      {
		    	  System.out.println("Skipping "+ze.getName());
			      zos.putNextEntry(new ZipEntry("[Content_Types].xml"));
			  	  StreamResult resulta = new StreamResult(zos);
			  	  DOMSource sourcect = new DOMSource(Outline.toXml(o,subtitle));
			  	  t_ct.transform(sourcect,resulta);
			  	  System.out.println("Transforming [Content_Types].xml");
		      }
		      else if(ze.getName().startsWith("ppt/presentation.xml"))
		      {
		    	  System.out.println("Skipping "+ze.getName());
			      zos.putNextEntry(new ZipEntry("ppt/presentation.xml"));
			  	  StreamResult resultpr = new StreamResult(zos);
			  	  DOMSource sourcect = new DOMSource(Outline.toXml(o,subtitle));
			  	  t_pres.transform(sourcect,resultpr);
			  	  System.out.println("Transforming ppt/presentation.xml");
		      }
		      else if(ze.getName().startsWith("ppt/_rels/presentation.xml.rels"))
		      {
		    	  System.out.println("Skipping "+ze.getName());
			      zos.putNextEntry(new ZipEntry("ppt/_rels/presentation.xml.rels"));
			  	  StreamResult resultpr = new StreamResult(zos);
			  	  DOMSource sourcect = new DOMSource(Outline.toXml(o,subtitle));
			  	  t_pr.transform(sourcect,resultpr);
			  	  System.out.println("Transforming ppt/_rels/presentation.xml.rels");
		      }
		      else
		      {
		    	  System.out.println("Copying "+ze.getName());
			      zos.putNextEntry(new ZipEntry(ze.getName()));
					InputStream z_is = z_in.getInputStream(ze);
			      int c;
			      while ((c = z_is.read()) !=-1){
			    	  zos.write(c);
			      }
			      zos.closeEntry();
			      z_is.close();
		      }
		}
		


  	  zos.putNextEntry(new ZipEntry("ppt/slides/slide1.xml"));
  	  StreamResult result = new StreamResult(zos);
  	  DOMSource source = new DOMSource(Outline.toXml(o,subtitle));
  	  t_front.transform(source,result);
  	  System.out.println("Transforming ppt/slides/slide1.xml");
  	  {
	      zos.putNextEntry(new ZipEntry("ppt/slides/_rels/slide1.xml.rels"));
			InputStream z_is = Outline2Powerpoint2007.class.getResourceAsStream("slide-rel-front.xml");
	    int c;
	    while ((c = z_is.read()) !=-1){
	  	  zos.write(c);
	    }
	    zos.closeEntry();
	    z_is.close();
	    System.out.println("Copying ppt/slides/_rels/slide1.xml.rels");
  	  }
    int k=1;
  	  for(Outline oi : o.getChildren())
  	  {
  		  k++;
  	      zos.putNextEntry(new ZipEntry("ppt/slides/slide"+k+".xml"));
  	  	  StreamResult resulti = new StreamResult(zos);
  	  	  DOMSource sourcei = new DOMSource(Outline.toXml(oi,oi.getText()));
  	  	  t_current.transform(sourcei,resulti);

	      zos.putNextEntry(new ZipEntry("ppt/slides/_rels/slide"+k+".xml.rels"));
			InputStream z_is = Outline2Powerpoint2007.class.getResourceAsStream("slide-rel-current.xml");
	    int c;
	    while ((c = z_is.read()) !=-1){
	  	  zos.write(c);
	    }
	    zos.closeEntry();
	    z_is.close();

	    System.out.println("Copying ppt/slides/slide"+k+".xml");
	    System.out.println("Copying ppt/slides/_rels/slide"+k+".xml.rels");
  	  }
  	  
  	  // docProps/app.xml
  	  // [Content_Types].xml
		
	    zos.close();
	    System.out.println("Generated file "+r.getCanonicalPath());
	}
}
