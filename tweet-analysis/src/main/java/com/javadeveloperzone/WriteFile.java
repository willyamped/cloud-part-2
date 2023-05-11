package com.javadeveloperzone;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteFile {

	public static void main(String[] args) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("‪E:\\kokila\\Cloud-Data\\mnt\\ext100\\testfiles\\outfile1.txt"));
		bufferedWriter.append("text");
        bufferedWriter.newLine();
        bufferedWriter.close();
	}

}
