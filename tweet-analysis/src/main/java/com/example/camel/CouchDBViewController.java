package com.example.camel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CouchDBViewController {
	
	@GetMapping("/language")
	public String getLanguage() {
		 String result  = "";
		 // Set the CouchDB server URL and view path
        String serverUrl = "http://172.26.134.11:80";
        String databaseName = "mastodon-others";
        String viewPath = "_design/myview/_view/view1";

        // Create an HttpClient instance
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // Create the URL for the view request
        String viewUrl = serverUrl + "/" + databaseName + "/" + viewPath;

        try {
            // Create a GET request to the view URL
            HttpGet httpGet = new HttpGet(viewUrl);

            // Execute the request and get the response
            HttpResponse response = httpClient.execute(httpGet);

            // Get the response entity
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                // Convert the response entity to a string
                String responseBody = EntityUtils.toString(entity);
                JSONObject object = (JSONObject) JSONValue.parse(responseBody);
                Set<String> keySet = object.keySet();
                List<JSONObject> list = (List<JSONObject>) object.get("rows");
                Map<String,Integer> languageMap = new HashMap<>();
                list.forEach(e->{
                	if(e.get("value") != null) {
                		if(languageMap.get(e.get("value")) != null) {
                    		languageMap.put(e.get("value").toString(), languageMap.get(e.get("value"))+1);
                    	}else {
                    		languageMap.put(e.get("value").toString(), 1);
                    	}
                	}
                
                	
                });
                

                String resJson = "";
                StringBuilder sb = new StringBuilder();
                sb.append("{\"rows\":[");
                for(String keys: languageMap.keySet())
                {
                sb.append("{");
                sb.append("\"key\"").append(":").append("\""+keys+"\"").append(",");
                sb.append("\"value\"").append(":").append(languageMap.get(keys));
                sb.append("},");
                }
                result = sb.subSequence(0, sb.length()-1).toString();
                result =result+"]}";
                System.out.println(result);
                // Process the response data as needed
//                System.out.println("response body::"+responseBody);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the HttpClient
                httpClient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
	}
	
}
