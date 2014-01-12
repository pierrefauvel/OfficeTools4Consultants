package com.pfa.ideationmemories;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.ImageIcon;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.poi.hslf.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.xml.sax.SAXException;

import com.pfa.og.from.powerpoint.SlideShowToOutline;
import com.pfa.og.from.powerpoint.XSLFSlideShowToOutline;
import com.pfa.og.from.xmind.Sheet;
import com.pfa.og.from.xmind.XmindReader;
import com.pfa.og.from.xmind.XmindToOutline;
import com.pfa.og.pivot.Outline;

public class FileHierarchyAnalyser {

//	private Map<String,File> mapRoots=new HashMap<String,File>();
	
	public static class OutlineAndThumbnail
	{
		public Outline outline;
		public ImageIcon thumbnail;
	}
	
	public static InputStream resolveInputStream(File root, ExtendedPath ep) throws IOException
			{
				File fi = new File(root.getCanonicalPath()+ep.listOfPaths.get(0));
				if(ep.listOfPaths.size()==1)
					return new FileInputStream(fi);
				ZipFile zi = new ZipFile(fi);
				ZipEntry ze=null;
				for(int i=1;i<ep.listOfPaths.size();i++)
				{
					ze = zi.getEntry(ep.listOfPaths.get(i).toString().substring(1));
					if(ze==null)
						throw new ZipException("Could not find entry "+ep.listOfPaths.get(i).toString()+" in "+zi.getName()+" for "+ep);
					if(i==ep.listOfPaths.size()-1)
					{
						return zi.getInputStream(ze);
					}
					zi=new ZipFile(castToTempFile(zi,ze));
				}
				return zi.getInputStream(ze);
			}

	public static OutlineAndThumbnail resolveOutline(File root, ExtendedPath ep, int pos) throws IOException, XPathExpressionException, ParserConfigurationException, SAXException
	{
		InputStream is = resolveInputStream(root,ep);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int c;
		while( (c=is.read())!=-1)
		{
			baos.write((byte)c);
		}
		byte[] b = baos.toByteArray();
		
		String ext = ep.listOfPaths.get(ep.listOfPaths.size()-1).toString();
		ext = ext.substring(ext.lastIndexOf(".")+1);
		ext=ext.toLowerCase();

		Outline rc = null;
		ImageIcon t = null;
		
		if(ext.compareTo("pptx")==0)
		{
			XMLSlideShow pptx = new XMLSlideShow(new ByteArrayInputStream(b));
			XSLFSlide s = pptx.getSlides()[pos];
			rc=XSLFSlideShowToOutline.toOutline(s);
		}
		else if(ext.compareTo("ppt")==0)
		{
			try
			{
			SlideShow ssh = new SlideShow(new ByteArrayInputStream(b));
			Slide s = ssh.getSlides()[pos];
			rc=SlideShowToOutline.toOutline(s);
			}
			catch(OfficeXmlFileException ex)
			{
				XMLSlideShow pptx = new XMLSlideShow(new ByteArrayInputStream(b));
				XSLFSlide s = pptx.getSlides()[pos];
				rc=XSLFSlideShowToOutline.toOutline(s);
			}
		}
		else if(ext.compareTo("xmind")==0)
		{
			File temp = File.createTempFile("analyseXmind", ".xmind");
			String cpfn = temp.getCanonicalPath();
			FileOutputStream fos = new FileOutputStream(temp);
			fos.write(b,0,b.length);
			fos.close();
			XmindReader xr=new XmindReader();
			XmindReader.Model m = xr.read(temp);
			rc=new XmindToOutline().convert(m.sheet[pos]);
			t=m.thumbnail;
		}
		else if(ext.compareTo("jpg")==0 || ext.compareTo("jpeg")==0)
		{
			rc=new Outline("Image");
			t=new ImageIcon(b);
		}
		else throw new RuntimeException("Unsupported extension "+ext);
//		System.out.println(rc.toString());
		
		OutlineAndThumbnail ot = new OutlineAndThumbnail();
		ot.outline = rc;
		ot.thumbnail = t;
		return ot;
	}
	
	
	public void analyseFileHierarchy(File root, /*String id,*/ IndexWriter w, AnalysisLog analysis) throws IOException, NoSuchAlgorithmException
	{
//		mapRoots.put(id,root);
		ExtendedPath ep = new ExtendedPath();
		ep.newPath();
		analyzeDir(root,/*id,*/ep,w,root,analysis);
	}
	public void analyzeDir(File dir, /*String id,*/ ExtendedPath ep, IndexWriter w,File root,AnalysisLog analysis) throws IOException, NoSuchAlgorithmException
	{
		ExtendedPath ep1 = ep.clone();
		
		for(File f : dir.listFiles())
		{
			if(f.isFile())
			{
				String fn = f.getName();
				int idx = fn.lastIndexOf(".");
				if(idx!=-1)
				{
					String ext = fn.substring(idx+1);
					if(ext.toLowerCase().compareTo("xmind")==0)
					{
						ExtendedPath ep2 = ep1.clone();
						ep2.addToken(fn);
						try
						{
							analyseXmind(new FileInputStream(f),/*id,*/ep2,w,root);
						}
						catch(Throwable t)
						{
							analysis.log(root,ep2,t);
							t.printStackTrace();
						}
					}
					else if(ext.toLowerCase().compareTo("ppt")==0)
					{
						ExtendedPath ep2 = ep1.clone();
						ep2.addToken(fn);
						try
						{
							analysePPT(new FileInputStream(f),/*id,*/ep2,w,root);
						}
						catch(Throwable t)
						{
							analysis.log(root,ep2,t);
						}
					}
					else if(ext.toLowerCase().compareTo("pptx")==0)
					{
						ExtendedPath ep2 = ep1.clone();
						ep2.addToken(fn);
						try
						{
							analysePPTX(new FileInputStream(f),/*id,*/ep2,w,root);
						}
						catch(Throwable t)
						{
							analysis.log(root,ep2,t);
						}
					}
					else if(ext.toLowerCase().compareTo("jpg")==0||ext.toLowerCase().compareTo("jpeg")==0)
					{
						ExtendedPath ep2 = ep1.clone();
						ep2.addToken(fn);
						try
						{
							analyseJPEG(new FileInputStream(f),/*id,*/ep2,w,root);
						}
						catch(Throwable t)
						{
							analysis.log(root,ep2,t);
						}
					}
					else if(ext.toLowerCase().compareTo("zip")==0)
					{
						ExtendedPath ep2 = ep1.clone();
						ep2.addToken(fn);
						ep2.newPath();
						try
						{
						analyseZIP(new ZipFile(f),/*id,*/ep2,w,root,analysis);
						}
						catch(Throwable ex)
						{
							analysis.log(root,ep2,ex);
						}
					}
					else
						//DO NOTHING
						;
				};
			}
			else
			{
				ExtendedPath ep2 = ep.clone();
				ep2.addToken(f.getName());
				if(drillDown(f))
					analyzeDir(f,/*id,*/ep2,w,root,analysis);
				else
					System.out.println("Skipping dir "+f.getCanonicalPath());
			}
		}
	}
private void analyseZIP(ZipFile zf,/* String id,*/ ExtendedPath ep, IndexWriter w,File root,AnalysisLog analysis) throws ZipException, IOException, NoSuchAlgorithmException {
	
	Enumeration e = zf.entries();
	while(e.hasMoreElements())
	{
		ZipEntry ze = (ZipEntry) e.nextElement();
		if(ze.isDirectory())
		{
			// do nothing
		}
		else
		{
			String zn = ze.getName();
			int idx = zn.lastIndexOf(".");
			if(idx!=-1)
			{
				String ext = zn.substring(idx+1);
				
				String[] znt = zn.split("/"); 
				ExtendedPath ep1=ep.clone();
				for(String znti:znt)
					ep1.addToken(znti);
				
				if(ext.toLowerCase().compareTo("ppt")==0)
				{
					try
					{
					analysePPT(zf.getInputStream(ze),/*id,*/ep1,w,root);
					}
					catch(Throwable t)
					{
						analysis.log(root,ep1,t);
					}
				}
				else if(ext.toLowerCase().compareTo("pptx")==0)
				{
					try
					{
					analysePPTX(zf.getInputStream(ze),/*id,*/ep1,w,root);
					}
					catch(Throwable t)
					{
						analysis.log(root,ep1,t);
					}
				}
				else if(ext.toLowerCase().compareTo("xmind")==0)
				{
					try
					{
					analyseXmind(zf.getInputStream(ze),/*id,*/ep1,w,root);
					}
					catch(Throwable t)
					{
						analysis.log(root,ep1,t);
					}
				}
				else if(ext.toLowerCase().compareTo("zip")==0)
				{
					try
					{
					File temp = castToTempFile(zf,ze);
					ep1.newPath();
					analyseZIP(new ZipFile(temp),/*id,*/ep1,w,root,analysis);
					}
					catch(Throwable t)
					{
						analysis.log(root,ep1,t);
					}
				}
				else if(ext.toLowerCase().compareTo("jpg")==0 || ext.toLowerCase().compareTo("jpeg")==0)
				{
					analyseJPEG(zf.getInputStream(ze),ep1,w,root);
				}
			}
		}
	}
	
	}

private static File castToTempFile(ZipFile zf, ZipEntry ze) throws IOException
{
	File temp = File.createTempFile(ze.getName().replace("/","_"), "temp");
	byte[] b = new byte[1024];
	int c=-1;
	InputStream is = zf.getInputStream(ze);
	OutputStream os = new FileOutputStream(temp);
	while ((c = is.read(b,0,1024))>0)
	{
		os.write(b,0,c);
	}
	os.close();
	is.close();
	return temp;
}

private void analyseJPEG(InputStream is, ExtendedPath ep2, IndexWriter w, File root) throws IOException, NoSuchAlgorithmException
{
//	System.out.println("ANALYSING JPEG");
	byte[] b = isNew(is,ep2);
	if(b!=null)
	{
		System.out.println("ADDING JPEG\n\t"+ep2+"\n\t"+ep2.toTokenizedString());
		addDoc(w,ep2,"",0,new Outline(""),root); 
	}
}

private void analysePPTX(InputStream is, /*String id,*/ ExtendedPath ep2, IndexWriter w,File root) throws NoSuchAlgorithmException, IOException {
	byte[] b = isNew(is,ep2);
	if(b!=null)
	{
		XMLSlideShow pptx = new XMLSlideShow(new ByteArrayInputStream(b));
		int k=0;
		for(XSLFSlide s:pptx.getSlides())
		{
			addDoc(w,ep2,s.getTitle(),k,XSLFSlideShowToOutline.toOutline(s),root); 
			k++;
		}
	}
}
private void analysePPT(InputStream is, /*String id,*/ ExtendedPath ep2, IndexWriter w,File root) throws NoSuchAlgorithmException, IOException {
	byte[] b = isNew(is,ep2);
	if(b!=null)
	{
		try
		{
			SlideShow ssh = new SlideShow(new ByteArrayInputStream(b));
			int k=0;
			for(Slide s:ssh.getSlides())
			{
				addDoc(w,ep2,s.getTitle(),k,SlideShowToOutline.toOutline(s),root);
				k++;
			}
		}
		catch(OfficeXmlFileException ex)
		{
			XMLSlideShow pptx = new XMLSlideShow(new ByteArrayInputStream(b));
			int k=0;
			for(XSLFSlide s:pptx.getSlides())
			{
				addDoc(w,ep2,s.getTitle(),k,XSLFSlideShowToOutline.toOutline(s),root); 
				k++;
			}
		}
	}
	}
private void analyseXmind(InputStream is, /*String id,*/ ExtendedPath ep2, IndexWriter w,File root) throws NoSuchAlgorithmException, IOException, XPathExpressionException, ParserConfigurationException, SAXException {
	byte[] b = isNew(is,ep2);
	if(b!=null)
	{
		File temp = File.createTempFile("analyseXmind", ".xmind");
		String cpfn = temp.getCanonicalPath();
		FileOutputStream fos = new FileOutputStream(temp);
		fos.write(b,0,b.length);
		fos.close();
		XmindReader xr=new XmindReader();
		XmindReader.Model m = xr.read(temp);
		int k=0;
		for(Sheet sheet : m.sheet)
		{
			String tt = sheet.getLabel();
			addDoc(w,ep2,tt,k,new XmindToOutline().convert(sheet),root);
			k++;
		}
		
	}
	}

	public boolean drillDown ( File dir )
	{
		return true;
	}
	
	private Map<String,ExtendedPath> md5map=new HashMap<String,ExtendedPath>();
	
	public byte[] isNew ( InputStream is, ExtendedPath ep ) throws NoSuchAlgorithmException, IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] b = new byte[1024];
		int c=-1;
		while ((c=is.read(b))>0){
			baos.write(b,0,c);
		}
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] digest = md.digest(baos.toByteArray());
		String s = new String(digest);
		if(md5map.containsKey(s))
		{
			ExtendedPath epOld = md5map.get(s);
//			System.out.println(ep+" already exists, using "+epOld+" instead");
			return null;
		}
		md5map.put(s,ep);
		return baos.toByteArray();
	}
	
	// construire progressivement le SCRIPT : chemin vers le zip 1, chemin au sein de ce zip 1 vers un zip 2, ...
	
	// faire un cas de texst simple : toutes les extensions, y compris un zip avec des sous niveaux et un sous zip

private static void addDoc(IndexWriter w, ExtendedPath extendedPath, String pageTitle, Integer pagePosition, Outline outline,File root) throws IOException {
    Document doc = new Document();
    doc.add(new Field("extended-path",extendedPath.toString(),Field.Store.YES,Field.Index.NO));
    String ept=extendedPath.toTokenizedString();
    doc.add(new Field("extended-path-tokenized",ept,Field.Store.YES,Field.Index.ANALYZED));
    if(pageTitle==null)
    	pageTitle="<no title>";
    doc.add(new Field("title", pageTitle, Field.Store.YES, Field.Index.NO));
//    System.out.println(outline);
//    System.out.println(extendedPath+"=>"+outline);
    doc.add(new Field("content", outline.toString(), Field.Store.YES, Field.Index.ANALYZED));
    doc.add(new Field("position", ""+pagePosition,Field.Store.YES,Field.Index.NO));
    doc.add(new Field("root", root.getCanonicalPath(),Field.Store.YES,Field.Index.NO));
    w.addDocument(doc);
  }
}