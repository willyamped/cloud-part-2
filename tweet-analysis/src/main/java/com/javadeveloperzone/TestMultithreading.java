package com.javadeveloperzone;

import java.io.IOException;

public class TestMultithreading {

    public static void main(String[] args) {
    	
    	
    	

        final FileReading fr = new FileReading();

        Thread t1 = new Thread() {
            public synchronized void run() {
                try {
                	fr.readFile("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file1.txt","lockdown","E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\processed","outfile1.txt");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        };

        Thread t2 = new Thread() {
            public synchronized void run() {
                try {
					fr.readFile("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file2.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile2.txt");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        };

        Thread t3 = new Thread() {
            public synchronized void run() {
                try {
					fr.readFile("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file3.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile3.txt");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        };
        
        Thread t4 = new Thread() {
            public synchronized void run() {
                try {
					fr.readFile("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file4.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile4.txt");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        };
        
        Thread t5 = new Thread() {
            public synchronized void run() {
                try {
					fr.readFile("E:\\kokila\\Cloud-Data\\mnt\\ext100\\split-files\\file5.txt","lockdown","E:\\\\kokila\\\\Cloud-Data\\\\mnt\\\\ext100\\\\split-files\\\\processed","outfile5.txt");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        };

        t1.start();
        t2.start();
        t3.start();
        t4.start();
//        t5.start();
    }
}