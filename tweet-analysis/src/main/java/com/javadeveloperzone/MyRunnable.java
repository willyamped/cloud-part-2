package com.javadeveloperzone;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class MyRunnable implements Runnable {
	private String inputFileName;
	private String outputFileName;
	private String fileName;
	private String searchString;

	BufferedWriter bufferedWriter = null;

	public MyRunnable(String inputFileName, String searchString, String outputFileName, String fileName) {
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

		MyRunnable task1 = new MyRunnable("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file53.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile53.txt");
		MyRunnable task2 = new MyRunnable("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file54.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile54.txt");
		MyRunnable task3 = new MyRunnable("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file55.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile55.txt");
		MyRunnable task4 = new MyRunnable("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file56.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile56.txt");
		MyRunnable task5 = new MyRunnable("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file57.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile57.txt");
		MyRunnable task6 = new MyRunnable("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file58.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile58.txt");
		MyRunnable task7 = new MyRunnable("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file59.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile59.txt");
//		MyRunnable task8 = new MyRunnable("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file24.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile24.txt");
//		MyRunnable task9 = new MyRunnable("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file25.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile25.txt");
//		MyRunnable task10 = new MyRunnable("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file26.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile26.txt");
//		MyRunnable task11 = new MyRunnable("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file27.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile27.txt");
//		MyRunnable task12 = new MyRunnable("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file28.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile28.txt");
//		MyRunnable task13 = new MyRunnable("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file29.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile29.txt");
//		MyRunnable task14 = new MyRunnable("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file30.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile30.txt");
//		MyRunnable task15 = new MyRunnable("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file31.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile31.txt");
//		MyRunnable task16 = new MyRunnable("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file32.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile32.txt");
//		MyRunnable task17 = new MyRunnable("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file33.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile33.txt");
//		MyRunnable task18 = new MyRunnable("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file34.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile34.txt");
//		MyRunnable task19 = new MyRunnable("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file35.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile35.txt");
//		MyRunnable task20 = new MyRunnable("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file36.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile36.txt");
//		MyRunnable task21 = new MyRunnable("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file37.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile37.txt");
//		MyRunnable task22 = new MyRunnable("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file38.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile38.txt");
//		MyRunnable task23 = new MyRunnable("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file39.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile39.txt");
//		MyRunnable task24 = new MyRunnable("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file40.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile40.txt");
//		MyRunnable task25 = new MyRunnable("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file41.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile41.txt");
//		MyRunnable task26 = new MyRunnable("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file42.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile42.txt");
//		MyRunnable task27 = new MyRunnable("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file43.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile43.txt");
//		MyRunnable task28 = new MyRunnable("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file44.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile44.txt");
//		MyRunnable task29 = new MyRunnable("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file45.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile45.txt");
//		MyRunnable task30 = new MyRunnable("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file46.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile46.txt");
//		MyRunnable task31 = new MyRunnable("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file47.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile47.txt");
//		MyRunnable task32 = new MyRunnable("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file48.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile48.txt");
//		MyRunnable task33 = new MyRunnable("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file49.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile49.txt");
//		MyRunnable task34 = new MyRunnable("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file50.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile50.txt");
//		MyRunnable task35 = new MyRunnable("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file51.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile51.txt");
//		MyRunnable task36 = new MyRunnable("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file52.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile52.txt");

		executor.submit(task1);
		executor.submit(task2);
		executor.submit(task3);
		executor.submit(task4);
		executor.submit(task5);
		executor.submit(task6);
		executor.submit(task7);
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
}
