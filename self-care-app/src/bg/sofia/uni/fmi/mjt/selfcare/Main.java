package bg.sofia.uni.fmi.mjt.selfcare;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        try {
            Files.createFile(Path.of("./credentials.txt"));
        } catch (Exception ignored) {
            //log?
        }

        //read every line and sout it
        try (Reader fr = new FileReader(Path.of("./credentials.txt").toString());
             BufferedReader reader = new BufferedReader(fr)) {

            String username;
            String password;
            String currentLine;
            while ((username = reader.readLine()) != null && (password = reader.readLine()) != null) {
                if (username.equals("user5")) {
                    System.out.println("Already exists");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        //append username and password
//        try (Writer fw = new FileWriter(Path.of("./credentials.txt").toString(), true);
//             BufferedWriter bw = new BufferedWriter(fw)) {
//
//            bw.write("user3");
//            bw.newLine();
//            bw.write("user5");
//            bw.newLine();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
