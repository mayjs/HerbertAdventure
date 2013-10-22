package de.herbert.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


public class Inventory implements Serializable
{

	private static final long serialVersionUID = 1L;
	private List<Item> content;
	
	public Inventory()
	{
		content =  new LinkedList<Item>();
	}
	
	public Inventory(List<Item> pList)
	{
		content = pList;
	}
	
	public void add(Item pItem)
	{
		if(pItem!=null) content.add(pItem);
	}

	public Item get(int pIndex)
	{
		return content.get(pIndex);
	}
	
	public List<Item> getItems()
	{
		return content;
	}
	
	public int getIndex(Item pItem)
	{
		return content.lastIndexOf(pItem);
	}
	
	public boolean remove(Item pItem)
	{
		return content.remove(pItem);		
	}
	
	public boolean remove(int pIndex)
	{
		if(content.size()>pIndex)
		{
			return false;
		} 
		content.remove(pIndex);
		return true;			
	}
	
	public int countItems()
	{
		return content.size();
	}
	
	public boolean contains(Item pItem) {
		return !(getIndex(pItem)<0);
	}
  
}
 