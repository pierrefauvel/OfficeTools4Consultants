package com.pfa.og.from.xmind;

import java.util.ArrayList;
import java.util.List;

public class Sheet {
	private String label;
	private List<Topic> children = new ArrayList<Topic>();
	private List<Topic> detachedChildren = new ArrayList<Topic>();
	public Sheet(String label)
	{
		this.label=label;
	}
	public String getLabel()
	{
		return label;
	}
	public void addChild(Topic t)
	{
		children.add(t);
	}
	public void addDetachedChild(Topic t)
	{
		detachedChildren.add(t);
	}
	public Topic[] getChildren()
	{
		return children.toArray(new Topic[]{});
	}
	public Topic[] getDetachedChildren()
	{
		return detachedChildren.toArray(new Topic[]{});
	}

}
