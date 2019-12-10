package com.Lab1.searchinfile;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Search {
    private static ArrayList<String> listfiles = new ArrayList<>();

    public static void main(String[] args) {
        String dir = "C:\\Games";
        String text = "[1205/123756.540:WARNING:media_session.cc(334)] Duplicate id found. Reassigning from 104 to 127";
        files(new File(dir));
        Date time = new Date();
        long a = time.getTime();
//        System.out.println(time.getTime());
        series(text);
        time = new Date();
        System.out.println("Series: " + (time.getTime() - a));
        a = time.getTime();
        thread(text);
        time = new Date();
        System.out.println("Threads: " + (time.getTime() - a));
    }

    public static void files(File dir) {
        File[] folder = dir.listFiles();
        assert folder != null;
        for (File entry : folder) {
            if (entry.isDirectory()) {
                files(entry);
                continue;
            }
            listfiles.add(entry.getAbsolutePath());
        }
    }

    public static void series(String text) {
        //System.out.println("enter");
        for (String name_file : listfiles) {
            Search_in_File file = new Search_in_File(name_file, text);
            file.search_text_in_file();
        }
    }

    public static void thread(String text) {
        for (String i : listfiles) {
            //           System.out.println(i);
            Search_in_File file = new Search_in_File(i, text);
            Thread thread = new Thread(file);
            thread.start();

        }
    }

    static class Search_in_File implements Runnable {
        private String file_name, text;

        public Search_in_File(String file_name, String text) {
            this.file_name = file_name;
            this.text = text;
        }

        public void search_text_in_file() {
            try {

                Scanner in = new Scanner(new File(this.file_name));
                while (in.hasNextLine()) {
                    if (in.nextLine().contains(this.text)) {
                        System.out.println("Find in file:");
                        System.out.println(this.file_name);
                        break;
                    }

                }

            } catch (Exception e) {
                System.out.println("file not found");
            }
        }
        @Override
        public void run() {
            this.search_text_in_file();
        }
    }
}
