package com.unimelb.sudo.regional;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Set;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SudoHospitalDataProcessing {
	
	@GetMapping("/hospitaldata/push")
	public String triggerDataPush() {
		String response  = "";
		String hostpath ="/container/data/";
		try {
			FileReader fr = new FileReader(new File(hostpath+"my_hospital_beds-4841806019900112000.json"));
			BufferedReader br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				JSONObject object = (JSONObject) JSONValue.parse(line);
				Set keySet = object.keySet();
				System.out.println(keySet);
				for (Object key : keySet) {
					if(key.toString().equals("features")) {
						List<JSONObject> list = (List<JSONObject>) object.get(key);
						list.forEach(jsonobj ->  uploadDataToCouchDB(jsonobj.toString()));
//						JSONObject json = list.get(0);
//						uploadDataToCouchDB(json.toString());
					}
				}
			}

			response = "Data Pushed Successfully to Couch DB";
		}catch(Exception e) {
			e.printStackTrace();
			response = "Data Push Failed to Couch DB";
		}
		
		return response;
	}
	
	private  void uploadDataToCouchDB(String json)
	{
		String dbUrl = "http://172.26.134.11:80/hospital_beds";
        
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
