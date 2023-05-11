package com.javadeveloperzone;
import java.io.BufferedReader;
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

public class PostJsonToCouchDB {

    public static void main(String[] args) {
        // Set the URL of the CouchDB database
        String dbUrl = "http://localhost:5984/first/_bulk_docs";
        
        try {
            // Read JSON data from a file
            String jsonData = readJsonFromFile("E:\\kokila\\Cloud-Data\\mnt\\ext100\\tweet-files\\processed\\outfile1.json");
            
            String username = "kokila";
            String password = "kokila@2023";
            // Create an HTTP POST request to CouchDB
            
            CredentialsProvider provider = new BasicCredentialsProvider();
            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
            provider.setCredentials(AuthScope.ANY, credentials);

            
            HttpClient client = HttpClientBuilder.create()
                    .setDefaultCredentialsProvider(provider)
                    .build();
            
            HttpPost post = new HttpPost(dbUrl);
            
            // Set the request body as JSON data
            StringEntity entity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);
            post.setEntity(entity);
            
            // Send the request to CouchDB and print the response
            HttpEntity responseEntity = client.execute(post).getEntity();
            String response = responseEntity != null ? EntityUtils.toString(responseEntity) : "";
            System.out.println(response);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Read JSON data from a file
    private static String readJsonFromFile(String fileName) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }
}
