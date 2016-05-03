package com.server;
import java.util.*;

public class dynamic {
	
	List<String> Array;
	public dynamic()
	{
		Array = new ArrayList<String>();
	}
	
	public void aaa(String a,String b)
	{
		 String temp = a+"/" +b;
		 
		 Array.add(temp);
	}

}
