package com.pfa.og.from.powerpoint;

import java.util.Iterator;

import org.apache.poi.hslf.model.TextRun;
import org.apache.poi.xslf.usermodel.XSLFAutoShape;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;

import com.pfa.og.pivot.Outline;

public class XSLFSlideShowToOutline {

	public static Outline toOutline(XSLFSlide s) {
		Outline o = new Outline(s.getTitle());
		XSLFShape sh;
		Iterator<XSLFShape> it = s.iterator();
		while (it.hasNext())
		{
			XSLFShape shi = it.next();
			if(shi instanceof XSLFAutoShape)
			{
				XSLFAutoShape as = (XSLFAutoShape)shi;
				for(XSLFTextParagraph p: as.getTextParagraphs())
				{
					o.addChild(new Outline(p.getLevel()+" "+p.getText()));
				}
			}
			else
			{
				o.addChild(new Outline(shi.toString()));
			}
		}
		return o;
	}

}
