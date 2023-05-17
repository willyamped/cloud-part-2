package com.example.camel;

import org.apache.commons.lang3.StringUtils;

public class Test {

	public static void main(String[] args) {
		String s ="}{  \"account\": {   ";
		System.out.println(s.substring(1));
		
		String value ="COVI cases are more in this area";
		String searchString ="covid";
		
		if(StringUtils.containsIgnoreCase(value.toString(),searchString))
		{
			System.out.println(true);
		}else {
			System.out.println(false);
		}
	}

}
