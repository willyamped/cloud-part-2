package com.javadeveloperzone;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpHeaders;

public class CouchDBExample {
    public static void main(String[] args) {

        String couchDBUrl = "http://localhost:5984/first/_all_docs";
        String username = "kokila";
        String password = "kokila@2023";
        // Create an HTTP POST request to CouchDB
        
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
        provider.setCredentials(AuthScope.ANY, credentials);

        
        HttpClient client = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(provider)
                .build();
        
        
        HttpGet httpGet = new HttpGet(couchDBUrl);
        httpGet.setHeader(HttpHeaders.ACCEPT, "application/json");

        try {
            HttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);

            // Process the response body as needed
            System.out.println(responseBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
