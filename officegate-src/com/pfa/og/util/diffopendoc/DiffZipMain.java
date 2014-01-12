package com.pfa.og.util.diffopendoc;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class DiffZipMain {

	//TODO a généraliser
	private static String useReorderingByAttribute( String fn, String path, String enp, String en)
	{
		if(fn.compareTo("[Content_Types].xml")==0 && path.compareTo("/")==0 && enp.compareTo("Types")==0 && en.compareTo("Override")==0)
		{
			return "PartName";
		}
		if(fn.compareTo("[Content_Types].xml")==0 && path.compareTo("/")==0 && enp.compareTo("Types")==0 && en.compareTo("Default")==0)
		{
			return "Extension";
		}
		if(fn.compareTo("ppt/_rels/presentation.xml.rels")==0 && path.compareTo("/")==0 && enp.compareTo("Relationships")==0 && en.compareTo("Relationship")==0)
		{
			return "Target";
		}
//		if(fn.compareTo("ppt/_rels/presentation.xml.rels")==0)
//			System.out.println("fn="+fn+",path="+path+",enp="+enp+",en="+en);
		return null;
	}
	
	private static void compareZip(String prevu, String produit) throws IOException, SAXException
	{
		ZipFile zf_prevu = new ZipFile(prevu);
		ZipFile zf_produit = new ZipFile(produit);

		Enumeration<? extends ZipEntry> e_prevu = zf_prevu.entries();
		while(e_prevu.hasMoreElements())
		{
			ZipEntry ze_prevu = (ZipEntry) e_prevu.nextElement();
			ZipEntry ze_produit = zf_produit.getEntry(ze_prevu.getName());
			if(ze_produit==null)
			{
				System.err.println("Prevu "+ze_prevu.getName()+" is missing in produit");
			}
			else if(ze_prevu.getName().endsWith(".jpeg"))
			{
				//skip
			}
			else
			{
				InputStream is_prevu = zf_prevu.getInputStream(ze_prevu);
				InputStream is_produit = zf_produit.getInputStream(ze_produit);
				compareXml(ze_prevu.getName(),is_prevu,is_produit);
			}
		}

		Enumeration<? extends ZipEntry> e_produit = zf_produit.entries();
		while(e_produit.hasMoreElements())
		{
			ZipEntry ze_produit = (ZipEntry) e_produit.nextElement();
			ZipEntry ze_prevu = zf_prevu.getEntry(ze_produit.getName());
			if(ze_prevu==null)
			{
				System.err.println("Produit "+ze_produit.getName()+" is missing in prevu");
			}
		}
		
	}

	static DocumentBuilderFactory dbf;
	static DocumentBuilder db;
	
	static {
		try
		{
		dbf= DocumentBuilderFactory.newInstance();
		db = dbf.newDocumentBuilder();
		}
		catch(Throwable t)
		{
			t.printStackTrace();
		}
	}
	
	private static void myNormalize(Document d)
	{
		myNormalize(d.getDocumentElement());
	}
	
	private static void myNormalize(Element e)
	{
		NodeList nl = e.getChildNodes();
		for(int i=0;i<nl.getLength();i++)
		{
			Node ni = nl.item(i);
			if(ni instanceof Element)
				myNormalize((Element)ni);
			else if(ni instanceof org.w3c.dom.Comment)
				e.removeChild(ni);
			else if(ni instanceof Text)
			{
				if(ni.getNodeValue().trim().length()==0)
					e.removeChild(ni);
			}
		}
	}
	
	
	private static void compareXml(String fn,InputStream isPrevu, InputStream isProduit) throws SAXException, IOException {
		try
		{
		Document dPrevu = db.parse(isPrevu);
		Document dProduit = db.parse(isProduit);
		dPrevu.normalizeDocument();
		myNormalize(dPrevu);
		dProduit.normalizeDocument();
		myNormalize(dProduit);
//		System.out.println("Comparing "+fn );
		compareDom(fn,"/","0",dPrevu.getDocumentElement(),dProduit.getDocumentElement());
		}
		catch(Throwable t)
		{
			System.err.println("File "+fn+":"+t.getMessage());
		}
	}

	private static void compareDom(String fn, String path, String pos, Element ePrevu, Element eProduit) {
		if(ePrevu.getNodeName().compareTo(eProduit.getNodeName())!=0)
		{
			System.err.println("File "+fn+":"+"Different element name at "+path+"/"+ePrevu.getNodeName()+"["+pos+"], prevu = "+ePrevu.getNodeName()+", produit = "+eProduit.getNodeName());
			return;
		}
		
		NamedNodeMap nnmPrevu = ePrevu.getAttributes();
		NamedNodeMap nnmProduit = eProduit.getAttributes();
		if(nnmPrevu.getLength()!=nnmProduit.getLength())
		{
			HashSet hsPrevu = new HashSet();
			HashSet hsProduit = new HashSet();
			for(int i=0;i<nnmPrevu.getLength();i++)
			{
				hsPrevu.add(nnmPrevu.item(i).getNodeName());
			}
			for(int i=0;i<nnmProduit.getLength();i++)
			{
				hsProduit.add(nnmProduit.item(i).getNodeName());
			}
			HashSet hs = new HashSet();
			HashSet hs1 = new HashSet();
			hs.addAll(hsPrevu);
			hs.removeAll(hsProduit);
			hs1.addAll(hsProduit);
			hs1.removeAll(hsPrevu);
			if(hs.size()>0 || hs1.size()>0)
				System.err.println("File "+fn+":"+"Different attribute count at "+path+"/"+ePrevu.getNodeName()+"["+pos+"], prevu but not produit : "+hs+", produit but not prevu : "+hs1);
		}
		boolean found=false;
		for(int i=0;i<nnmPrevu.getLength();i++)
		{
			String an = nnmPrevu.item(i).getNodeName();
			String avPrevu = nnmPrevu.getNamedItem(an).getNodeValue();
			String avProduit = nnmProduit.getNamedItem(an).getNodeValue();
			if(avPrevu.compareTo(avProduit)!=0)
			{
				System.err.println("File "+fn+":"+"Different attribute value at "+path+"/"+ePrevu.getNodeName()+"["+pos+"]/@"+an+", prevu = "+avPrevu+", produit = "+avProduit);
				found=true;
			}
		}
		if(found)
			return;
		
		NodeList nlPrevu = ePrevu.getChildNodes();
		NodeList nlProduit = eProduit.getChildNodes();
		if(nlPrevu.getLength()!=nlProduit.getLength())
		{
			List lstPrevu = new ArrayList();
			for(int i=0;i<nlPrevu.getLength();i++)
			{
				Node ni = nlPrevu.item(i);
				lstPrevu.add(ni.getNodeType()+":"+ni.getNodeName()+"/"+ni.getNodeValue());
			}
			List lstProduit = new ArrayList();
			for(int i=0;i<nlProduit.getLength();i++)
			{
				Node ni = nlProduit.item(i);
				lstProduit.add(ni.getNodeType()+":"+ni.getNodeName()+"/"+ni.getNodeValue());
			}
			System.err.println("File "+fn+":"+"Different child count at "+path+"/"+ePrevu.getNodeName()+"["+pos+"], prevu = "+nlPrevu.getLength()+lstPrevu+", produit = "+nlProduit.getLength()+lstProduit);
			return;
		}
		HashMap<String,Element> hmPrevuReordonne = new HashMap<String,Element>();
		HashMap<String,Element> hmProduitReordonne = new HashMap<String,Element>();
		for(int i=0;i<nlPrevu.getLength();i++)
		{
			Node nPrevu = nlPrevu.item(i);
			Node nProduit = nlProduit.item(i);
			if(nPrevu.getNodeType()!=nProduit.getNodeType())
			{
				System.err.println("File "+fn+":"+"Different child type at "+path+"/"+ePrevu.getNodeName()+"["+pos+"]["+i+"], prevu = "+nPrevu.getClass()+", produit = "+nProduit.getClass());
			}
			else
			{
				if(nPrevu instanceof Element)
				{
					String att=null;
					if((att=useReorderingByAttribute(fn, path, ePrevu.getNodeName(),nPrevu.getNodeName()))==null)
						compareDom(fn,path+ePrevu.getNodeName()+"["+pos+"]/",""+i,(Element)nPrevu,(Element)nProduit);
					else
					{
						String key = nPrevu.getNodeName()+"@"+att+"="+((Element)nPrevu).getAttribute(att);
						if(hmPrevuReordonne.containsKey(key))
						{
							System.err.println(fn+":Duplicated key in reorderring prevu "+att+" "+key);
							return;
						}
						hmPrevuReordonne.put(key,(Element)nPrevu);
					}
				}
				else if(nPrevu instanceof Text)
				{
					Text tPrevu = (Text)nPrevu;
					Text tProduit = (Text)nProduit;
					if(tPrevu.getNodeValue().compareTo(tProduit.getNodeValue())!=0)
					{
						System.err.println("File "+fn+":"+"Different child text at "+path+"["+pos+"]"+"/"+ePrevu.getNodeName()+"["+i+"], prevu = "+tPrevu.getNodeValue()+", produit = "+tProduit.getNodeValue());
					}
				}
				if(nProduit instanceof Element)
				{
					String att=null;
					if((att=useReorderingByAttribute(fn, path, eProduit.getNodeName(),nProduit.getNodeName()))!=null)
					{
						String key=nProduit.getNodeName()+"@"+att+"="+((Element)nProduit).getAttribute(att);
						if(hmProduitReordonne.containsKey(key))
						{
							System.err.println(fn+":Duplicated key in reorderring produit "+att+" "+key);
							return;
						}
						hmProduitReordonne.put(key,(Element)nProduit);
					}
					
				}
			}
		};
		if(hmProduitReordonne.size()>0 || hmPrevuReordonne.size()>0)
		{
			{
				HashSet<String> hs1 = new HashSet<String>();
				hs1.addAll(hmProduitReordonne.keySet());
				hs1.removeAll(hmPrevuReordonne.keySet());
				HashSet<String> hs2 = new HashSet<String>();
				hs2.addAll(hmPrevuReordonne.keySet());
				hs2.removeAll(hmProduitReordonne.keySet());
				if(hs1.size()>0 || hs2.size()>0)
				{
					System.err.println("File "+fn+":"+"Different entry count at "+path+"/"+ePrevu.getNodeName()+"["+pos+"], in prevu but not in produit = "+hs1+", in produit but not in prevu = "+hs2);
				}
				else
				{
					for(String k : hmPrevuReordonne.keySet())
					{
						compareDom(fn,path+ePrevu.getNodeName()+"["+pos+"]/",k,hmPrevuReordonne.get(k),hmProduitReordonne.get(k));
					}
				}
			}
		}
	}

	public static void main(String[] args) throws IOException, SAXException
	{
		String prevu = "demo.pptx";
		String produit = "Outline2Powerpoint.pptx";
		
		compareZip(prevu,produit);
		

	
	}
}
