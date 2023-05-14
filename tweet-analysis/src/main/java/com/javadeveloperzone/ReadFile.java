package com.javadeveloperzone;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class ReadFile {
	Integer totalCount = 0;
	  Integer matchedCount= 0;
	public static void main(String[] args) throws IOException {
		
		  String filePath = "E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\";
		  

		    Path path = Paths.get(filePath+"twitter-huge.json");

		    BufferedReader bufferedReader = Files.newBufferedReader(path);

		    Stream<String> lines = bufferedReader.lines();
		    AtomicLong totalCount = new AtomicLong(0);
		    AtomicLong matchedCount = new AtomicLong(0);

		    BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(filePath+"/dest.txt"));
		    
		    lines.forEach(txt -> {
		    	totalCount.getAndIncrement();
		        try {
		        	System.out.println("txt"+txt);
		        	txt = txt.substring(0,txt.length()-1);
//		        	Thread.sleep(10000);
		        	if(filerJSONString(txt,"lockdown"))
		        	{
		        	matchedCount.getAndIncrement();
		            bufferedWriter.append(txt).append(",");
		            bufferedWriter.newLine();
		        	}
//		        	System.out.println(txt);
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    });
		    
		    bufferedWriter.append(totalCount+"").append(""+matchedCount);
	}
	
	private static boolean filerJSONString(String json,String searchString) {
		JSONObject object = (JSONObject) JSONValue.parse(json);

		Set<String> keySet = object.keySet();
		for (String key : keySet) {
			Object value = object.get(key);
			if (key.equals("value")) {
				JSONObject valueObj = (JSONObject) JSONValue.parse(value.toString());
				Set<String> keySet1 = valueObj.keySet();
				for (String key1 : keySet1) {
					Object value1 = valueObj.get(key1);

//					System.out.printf("%s=%s (%s)\n", key1, value1, value1.getClass().getSimpleName());
					if (key1.equals("tokens") && value1.toString().contains(searchString)) {
						return true;
					} else {
						return false;
					}

				}
			}

		}
		return false;
	}
}
