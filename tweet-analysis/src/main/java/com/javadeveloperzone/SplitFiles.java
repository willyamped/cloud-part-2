package com.javadeveloperzone;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;


public class SplitFiles {

private static final String INPUT_FILE_PATH = "E:\\kokila\\Cloud-Data\\mnt\\ext100\\twitter-huge.json";
private static final String TEMP_DIRECTORY = "E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files";

public static void main(String[] args) throws IOException {

    File input = new File(INPUT_FILE_PATH);
    File outPut = fileJoin2(splitFile(input, 59));

    try (InputStream in = Files.newInputStream(input.toPath()); InputStream out = Files.newInputStream(outPut.toPath())) {
        System.out.println(IOUtils.contentEquals(in, out));
    }

}

public static List<File> splitFile(File largeFile, int noOfFiles) throws IOException {
    return splitBySize(largeFile, getSizeInBytes(largeFile.length(), noOfFiles));
}

public static List<File> splitBySize(File largeFile, int maxChunkSize) throws IOException {
    List<File> list = new ArrayList<>();
    int numberOfFiles = 0;
    try (InputStream in = Files.newInputStream(largeFile.toPath())) {
        final byte[] buffer = new byte[maxChunkSize];
        int dataRead = in.read(buffer);
        while (dataRead > -1) {
            list.add(stageLocally(buffer, dataRead));
            numberOfFiles++;
            dataRead = in.read(buffer);
        }
    }
    System.out.println("Number of files generated: " + numberOfFiles);
    return list;
}

private static int getSizeInBytes(long totalBytes, int numberOfFiles) {
    if (totalBytes % numberOfFiles != 0) {
        totalBytes = ((totalBytes / numberOfFiles) + 1)*numberOfFiles;
    }
    long x = totalBytes / numberOfFiles;
    if (x > Integer.MAX_VALUE){
        throw new NumberFormatException("Byte chunk too large");

    }
    return (int) x;
}




private static File stageLocally(byte[] buffer, int length) throws IOException {
    File outPutFile = File.createTempFile("temp-", "split", new File(TEMP_DIRECTORY));
    try(FileOutputStream fos = new FileOutputStream(outPutFile)) {
        fos.write(buffer, 0, length);
    }
    return outPutFile;
}


public static File fileJoin2(List<File> list) throws IOException {
    File outPutFile = File.createTempFile("temp-", "unsplit", new File(TEMP_DIRECTORY));
    FileOutputStream fos = new FileOutputStream(outPutFile);
    for (File file : list) {
        Files.copy(file.toPath(), fos);
    }
    fos.close();
    return outPutFile;
}}