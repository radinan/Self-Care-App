package bg.sofia.uni.fmi.mjt.selfcare;

import bg.sofia.uni.fmi.mjt.selfcare.utilities.Journal;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        try {
//            Files.createFile(Path.of("./credentials.txt"));
//        } catch (Exception ignored) {
//            //log?
//        }
//
//        //read every line and sout it
//        try (Reader fr = new FileReader(Path.of("./credentials.txt").toString());
//             BufferedReader reader = new BufferedReader(fr)) {
//
//            String username;
//            String password;
//            String currentLine;
//            while ((username = reader.readLine()) != null && (password = reader.readLine()) != null) {
//                if (username.equals("user5")) {
//                    System.out.println("Already exists");
//                    break;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
////        //append username and password
////        try (Writer fw = new FileWriter(Path.of("./credentials.txt").toString(), true);
////             BufferedWriter bw = new BufferedWriter(fw)) {
////
////            bw.write("user3");
////            bw.newLine();
////            bw.write("user5");
////            bw.newLine();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }

        try {
            Files.createFile(Path.of("./user1.txt"));
        } catch (Exception ignored) {
            //log?
        }
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("./user1.txt"))) {
            Journal journal = new Journal("hi", "hello");
            Journal journal1 = new Journal("zaglavie1", "1tova e moeto sydyrjanie1");
            Journal journal2 = new Journal("zaglavie2", "2tova e moeto sydyrjanie2");
            List<Journal> journals = new ArrayList<>();
            journals.add(journal);
            journals.add(journal1);
            journals.add(journal2);

            for (Journal j : journals) {
                os.writeObject(j);
            }

//            journals.stream().forEach(o -> {
//                try {
//                    os.writeObject(o);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
        } catch (Exception ignored) {
            System.out.printf("NE syshtesvuva");
        }

        List<Journal> j = new ArrayList<>();
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream("./user1.txt"))) {
            Journal curr;
            while ((curr = (Journal)is.readObject()) != null) {
                j.add(curr);
            }
        } catch (Exception ignored) {
            System.out.printf("NE syshtesvuva");
        }
        System.out.println();
    }
}
