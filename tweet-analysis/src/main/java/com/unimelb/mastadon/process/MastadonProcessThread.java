package com.unimelb.mastadon.process;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class MastadonProcessThread implements Runnable {
	private String inputFileName;
	private String outputFileName;
	private String fileName;
	private String searchString;

	BufferedWriter bufferedWriter = null;

	public MastadonProcessThread(String inputFileName, String searchString, String outputFileName, String fileName) {
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
						bufferedWriter.append(line).append(",");
						bufferedWriter.newLine();
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

	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(4);

		MastadonProcessThread task1 = new MastadonProcessThread("E:\\kokila\\Cloud-Data\\mastadon\\Mastodon data\\outfile2.json","lockdown","E:\\kokila\\Cloud-Data\\mastadon\\Mastodon data\\","lockdown1.json");
		
		executor.submit(task1);
	

		
		
		executor.shutdown();
	}
}
