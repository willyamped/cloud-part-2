package com.javadeveloperzone;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ReadWriteJSONFile {

	public static void main(String[] args) throws IOException {
		String filePath = "E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\";
		Path path = Paths.get(filePath + "dest.txt");
		BufferedReader bufferedReader = Files.newBufferedReader(path);
		BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(filePath + "/dest0.txt"));
		Stream<String> lines = bufferedReader.lines();
//	    AtomicLong totalCount = new AtomicLong(0);
		bufferedWriter.append("{\"docs\":[");
		Integer i = 0;
		int filecount = 0;
		int splitcount = 1000;
		int tempcnt =0;
		for (String s : lines.toList()) {
			if (i == splitcount) {
				bufferedWriter.append("]}");
				bufferedWriter.close();
				i = 0;
				filecount++;
				bufferedWriter = Files.newBufferedWriter(Paths.get(filePath + "/dest" + filecount + ".txt"));
				bufferedWriter.append("{\"docs\":[");
				bufferedWriter.append(s);
				bufferedWriter.newLine();
				i++;
				tempcnt++;
			} else {

				if (i == splitcount - 1) {
					s = s.substring(0, s.length() - 1);
					bufferedWriter.append(s);

				} else {
					bufferedWriter.append(s);
				}
				bufferedWriter.newLine();
				i++;
				tempcnt++;
			}

		}
		bufferedWriter.close();
		System.out.println(tempcnt);
	}

}
