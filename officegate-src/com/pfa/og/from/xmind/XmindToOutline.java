package com.pfa.og.from.xmind;

import java.io.PrintStream;

import com.pfa.og.pivot.Outline;

public class XmindToOutline {

	
	public Outline convert(Sheet sh)
	{
		Outline o = new Outline(sh.getLabel());
		convert(o,sh.getChildren());
		return o;
	}

	private void convert(Outline o,Topic[] tp)
	{
		for (int i=0;i<tp.length;i++)
		{
			Outline oi = new Outline(tp[i].getLabel());
			o.addChild(oi);
			convert(oi,tp[i].getChildren());
		}
	}
}
