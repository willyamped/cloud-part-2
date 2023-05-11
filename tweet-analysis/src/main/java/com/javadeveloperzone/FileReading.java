package com.javadeveloperzone;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

class FileReading {
    public synchronized void readFile(String inputFileName,String searchString,String outputFileName,String fileName) throws IOException {
    	BufferedWriter bufferedWriter = null;
    	long startTime = System.nanoTime();
        try {
        	
        	System.out.println("inputFileName"+inputFileName);
        	System.out.println("outputFileName"+outputFileName);
            FileReader fr = new FileReader(new File(inputFileName));
            bufferedWriter = Files.newBufferedWriter(Paths.get(outputFileName+File.separator+fileName));
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
            	try {
            	line = line.substring(0,line.length()-1);
            	if(filerJSONString(line, searchString)) {
            		bufferedWriter.append(line);
		            bufferedWriter.newLine();
            	}
            	}catch(Exception e) {
            		System.out.println(line);
            		e.printStackTrace();
            	}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        bufferedWriter.close();
        long endTime = System.nanoTime();
        
        long elapsedTime = endTime - startTime; // Calculate elapsed time in nanoseconds
        double elapsedTimeInSeconds = (double) elapsedTime / 1_000_000_000.0; // Convert to seconds

        System.out.println("Elapsed time: " + elapsedTimeInSeconds + " seconds");
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