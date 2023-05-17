package com.unimelb.mastadon.process;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReadJSONFromFile {
	
	public static List<String> readJSONFromFile(String filePath) throws IOException {
	    List<String> jsonStrings = new ArrayList<>();
	    List<String> brackets = new ArrayList<>();
	    StringBuilder jsonString = new StringBuilder();
	    BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get("E:\\kokila\\Cloud-Data\\mastadon\\Mastodon data\\outfile2.json"));
	    boolean firstRecord = true;
	    String tempString = null;

	    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	        String line;
	        
	       
	        while ((line = reader.readLine()) != null) {
	            jsonString.append(line);
	            
	            if(line.contains("\"visibility\":")) {
	            	jsonString.append("}");
//	            	jsonStrings.add(jsonString.toString());
	            	
	            	System.out.println(jsonString);
	            	
	            	if(firstRecord) {
	            		bufferedWriter.append(jsonString.toString()).append(",");
		            	bufferedWriter.newLine();
		            	firstRecord = false;
	            	}else {	
	            		tempString = "";
	            		tempString = jsonString.toString().substring(1);
	            		bufferedWriter.append(tempString).append(",");
		            	bufferedWriter.newLine();
	            	}
	            	jsonString = new StringBuilder();
	            	
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return jsonStrings;
	}


	public static void main(String[] args) throws IOException {
		String filePath = "E:\\kokila\\Cloud-Data\\mastadon\\Mastodon data\\data2.json";
		List<String> jsonStrings = readJSONFromFile(filePath);
		int count = 0;

		// Iterate over the JSON strings
		for (String jsonString : jsonStrings) {
		    System.out.println(jsonString);
		    count++;
		}
		
		System.out.println(count);
	}

}
