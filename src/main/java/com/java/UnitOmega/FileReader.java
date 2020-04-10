package com.java.UnitOmega;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static com.java.UnitOmega.Executor.execute;

public class FileReader {

    public static void readFile(){
        File resultFile = new File("result.txt");
        Scanner scanner = new Scanner(System.in);
        if (resultFile.delete()){
            System.out.println("Last result deleted.");
        }
        try {
            System.out.println("Input file name:");
            File file = new File(scanner.next());
            java.io.FileReader fileReader = new java.io.FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                execute(line);
                line = bufferedReader.readLine();
            }
            fileReader.close();
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
