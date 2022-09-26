/*
 * Decompiled with CFR 0.152.
 */
package zelix.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static String readFile(String path) {
        StringBuilder result = new StringBuilder();
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileInputStream fIn = new FileInputStream(file);
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fIn));){
                String str;
                while ((str = bufferedReader.readLine()) != null) {
                    result.append(str);
                    result.append(System.lineSeparator());
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static String readFile(File file) {
        StringBuilder result = new StringBuilder();
        try {
            FileInputStream fIn = new FileInputStream(file);
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fIn));){
                String str;
                while ((str = bufferedReader.readLine()) != null) {
                    result.append(str);
                    result.append(System.lineSeparator());
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static List<File> getFilesFromDir(String dir) {
        File[] files;
        ArrayList<File> file = new ArrayList<File>();
        File startFolder = new File(dir);
        for (File tempFile : files = startFolder.listFiles()) {
            if (startFolder.isDirectory()) {
                file.addAll(FileUtils.getFilesFromDir(tempFile.getAbsolutePath()));
                continue;
            }
            file.add(tempFile);
        }
        return file;
    }
}

