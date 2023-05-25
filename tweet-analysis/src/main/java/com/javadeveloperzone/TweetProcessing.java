package com.javadeveloperzone;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpEntity;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class TweetProcessing implements Runnable {
	private String inputFileName;
	private String outputFileName;
	private String fileName;
	private String searchString;

	BufferedWriter bufferedWriter = null;

	public TweetProcessing(String inputFileName, String searchString, String outputFileName, String fileName) {
		this.inputFileName = inputFileName;
		this.outputFileName = outputFileName;
		this.searchString = searchString;
		this.fileName = fileName;
	}

	public void run() {
		try {
			FileReader fr = new FileReader(new File(inputFileName));
			bufferedWriter = Files.newBufferedWriter(Paths.get(outputFileName + File.separator + fileName));
			BufferedReader br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				try {
					line = line.substring(0, line.length() - 1);
					if (filerJSONString(line, searchString)) {
						uploadDataToCouchDB(line);
//						bufferedWriter.append(line).append(",");
//						bufferedWriter.newLine();
					}
				} catch (Exception e) {
					System.out.println(line);
					e.printStackTrace();
				}
			}
			bufferedWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static boolean filerJSONString(String json, String searchString) {
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

	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(4);

//		TweetProcessing task1 = new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file1.txt","covid","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed\\covid","covid1.json");
//		TweetProcessing task2 = new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file2.txt","covid","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed\\covid","covid2.json");
//		TweetProcessing task3 = new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file3.txt","covid","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed\\covid","covid3.json");
//		TweetProcessing task4 = new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file4.txt","covid","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed\\covid","covid4.json");
		for(int i =40;i<50 ;i++) {
			executor.submit(new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file"+i+".txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed\\covid","covid"+i+".json"));
		}
//		TweetProcessing task5 = new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file57.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile57.txt");
//		TweetProcessing task6 = new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file58.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile58.txt");
//		TweetProcessing task7 = new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file59.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile59.txt");
//		TweetProcessing task8 = new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file24.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile24.txt");
//		TweetProcessing task9 = new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file25.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile25.txt");
//		TweetProcessing task10 = new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file26.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile26.txt");
//		TweetProcessing task11 = new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file27.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile27.txt");
//		TweetProcessing task12 = new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file28.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile28.txt");
//		TweetProcessing task13 = new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file29.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile29.txt");
//		TweetProcessing task14 = new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file30.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile30.txt");
//		TweetProcessing task15 = new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file31.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile31.txt");
//		TweetProcessing task16 = new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file32.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile32.txt");
//		TweetProcessing task17 = new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file33.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile33.txt");
//		TweetProcessing task18 = new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file34.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile34.txt");
//		TweetProcessing task19 = new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file35.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile35.txt");
//		TweetProcessing task20 = new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file36.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile36.txt");
//		TweetProcessing task21 = new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file37.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile37.txt");
//		TweetProcessing task22 = new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file38.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile38.txt");
//		TweetProcessing task23 = new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file39.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile39.txt");
//		TweetProcessing task24 = new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file40.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile40.txt");
//		TweetProcessing task25 = new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file41.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile41.txt");
//		TweetProcessing task26 = new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file42.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile42.txt");
//		TweetProcessing task27 = new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file43.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile43.txt");
//		TweetProcessing task28 = new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file44.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile44.txt");
//		TweetProcessing task29 = new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file45.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile45.txt");
//		TweetProcessing task30 = new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file46.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile46.txt");
//		TweetProcessing task31 = new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file47.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile47.txt");
//		TweetProcessing task32 = new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file48.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile48.txt");
//		TweetProcessing task33 = new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file49.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile49.txt");
//		TweetProcessing task34 = new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file50.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile50.txt");
//		TweetProcessing task35 = new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file51.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile51.txt");
//		TweetProcessing task36 = new TweetProcessing("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file52.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile52.txt");

//		executor.submit(task1);
//		executor.submit(task2);
//		executor.submit(task3);
//		executor.submit(task4);
//		executor.submit(task5);
//		executor.submit(task6);
//		executor.submit(task7);
//		executor.submit(task8);
//		executor.submit(task9);
//		executor.submit(task10);
//		executor.submit(task11);
//		executor.submit(task12);
//		executor.submit(task13);
//		executor.submit(task14);
//		executor.submit(task15);
//		executor.submit(task16);
//		executor.submit(task17);
//		executor.submit(task18);
//		executor.submit(task19);
//		executor.submit(task20);
//		executor.submit(task21);
//		executor.submit(task22);
//		executor.submit(task23);
//		executor.submit(task24);
//		executor.submit(task25);
//		executor.submit(task26);
//		executor.submit(task27);
//		executor.submit(task28);
//		executor.submit(task29);
//		executor.submit(task30);
//		executor.submit(task31);
//		executor.submit(task32);
//		executor.submit(task33);
//		executor.submit(task34);
//		executor.submit(task35);
//		executor.submit(task36);

		
		
		executor.shutdown();
	}
	
	private static  void uploadDataToCouchDB(String json)
	{
		String dbUrl = "http://172.26.134.11:80/tweets-lockdown";
	    
	    try {
	      
	    	String username = "grp77";
	        String password = "grp77@2023";
	        // Create an HTTP POST request to CouchDB
	        
	        CredentialsProvider provider = new BasicCredentialsProvider();
	        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
	        provider.setCredentials(AuthScope.ANY, credentials);

	        
	        HttpClient client = HttpClientBuilder.create()
	                .setDefaultCredentialsProvider(provider)
	                .build();
	        
	        HttpPost post = new HttpPost(dbUrl);
	        
	        // Set the request body as JSON data
	        StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
	        post.setEntity(entity);
	        
	        // Send the request to CouchDB and print the response
	        HttpEntity responseEntity = client.execute(post).getEntity();
	        String response = responseEntity != null ? EntityUtils.toString(responseEntity) : "";
	        System.out.println(response);
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
