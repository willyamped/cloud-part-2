package com.javadeveloperzone;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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

public class TweetCouchDBProcessor {

	public static void main(String[] args) throws IOException {
		FileReader fr = null;
		for(int i=1;i<=10;i++) {
			fr = new FileReader(new File("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\processed\\covid\\covid"+i+".json"));
			BufferedReader br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				if(line.lastIndexOf(",")!=-1) {
					line = line.substring(0,line.length()-1);
					uploadDataToCouchDB(line);
				}
			}
		}
		
	}
	
	private static  void uploadDataToCouchDB(String json)
	{
		String dbUrl = "http://172.26.134.11:80/tweets-covid";
	    
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
