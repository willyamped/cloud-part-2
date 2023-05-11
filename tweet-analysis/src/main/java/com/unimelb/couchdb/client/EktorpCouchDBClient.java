package com.unimelb.couchdb.client;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;

public class EktorpCouchDBClient {

	public static void main(String[] args) {
		HttpClient httpClient = new StdHttpClient.Builder().host("localhost").port(5984).username("kokila")
				.password("kokila@2023").build();
		CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
		CouchDbConnector db = new StdCouchDbConnector("first", dbInstance);
		List<String> allDocIds = db.getAllDocIds();
		System.out.println(allDocIds);
	}

}
