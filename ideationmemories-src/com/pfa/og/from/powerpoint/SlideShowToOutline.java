package com.pfa.og.from.powerpoint;

import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextRun;

import com.pfa.og.pivot.Outline;

public class SlideShowToOutline {

	public static Outline toOutline(Slide s) {
		Outline o = new Outline(s.getTitle());
		for(TextRun tr : s.getTextRuns())
		{
			o.addChild(new Outline(tr.getText()));
		}
		return o;
	}

}
