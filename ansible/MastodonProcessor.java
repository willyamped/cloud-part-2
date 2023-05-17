import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
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

public class MastodonProcessor {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		String filePath = args[0];
		String searchString =args[1];
		StringBuilder jsonString = new StringBuilder();
		boolean firstRecord = true;
		String tempString ="";

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;

			while ((line = reader.readLine()) != null) {
				jsonString.append(line);
				
				if( line.contains("\"visibility\":")) {
					jsonString.append("}");
					if(firstRecord) {
						if(filerJSONString(jsonString.toString(), searchString)) {
						uploadDataToCouchDB(jsonString.toString());
						}
						firstRecord = false;
					}else {
						tempString = jsonString.toString();
						if(tempString.startsWith("}{")) {
							tempString = jsonString.toString().substring(1);
						}else {
							tempString = jsonString.toString();
						}
						
						if( filerJSONString(tempString, searchString)) {
							uploadDataToCouchDB(tempString);
						}
					}
					
					jsonString = new StringBuilder();
					tempString = "";
				}
				
			}
		}
	}
	
private static boolean filerJSONString(String json, String searchString) {
		System.out.println("json::"+json);
		JSONObject object = (JSONObject) JSONValue.parse(json);

		Set<String> keySet = null;
		try {
			keySet = object.keySet();
		}catch(Exception e) {
			e.printStackTrace();
		}
		for (String key : keySet) {

			if (key.equals("content")) {
				Object value = object.get(key);
				if (StringUtils.containsIgnoreCase(value.toString(),searchString)) {
					return true;
				}
			}

		}
		
		return false;
	}

private static  void uploadDataToCouchDB(String json)
{
	String dbUrl = "http://172.26.134.11:80/harvester-ansible";
    
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
