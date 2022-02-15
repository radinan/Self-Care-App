package bg.sofia.uni.fmi.mjt.selfcare;

import bg.sofia.uni.fmi.mjt.selfcare.command.CommandParser;
import bg.sofia.uni.fmi.mjt.selfcare.utilities.Journal;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        //Serialization is a bad idea. Appending is corrupting the file
  /*      try {
            Files.createFile(Path.of("./user1.txt"));
        } catch (Exception ignored) {
            //log?
        }
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("./user1.txt", true))) {
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
            os.reset();

//            journals.stream().forEach(o -> {
//                try {
//                    os.writeObject(o);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
        } catch (Exception ignored) {
            ignored.printStackTrace();
            System.out.printf("NE syshtesvuva");
        }

        List<Journal> j = new ArrayList<>();
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream("./user1.txt"))) {
            Journal curr;
            while ((curr = (Journal)is.readObject()) != null) {
                j.add(curr);
            }
        } catch (Exception ignored) {
            System.out.printf("tuk reve");
        }
        System.out.println();*/

        List<Journal> list = new ArrayList<>();
        list.add(new Journal("aabc", LocalDate.of(2020,12,1), "aa"));
        list.add(new Journal("aabb", LocalDate.of(2020, 12, 1) ,"aa bb aa"));
        list.add(new Journal("cc", LocalDate.of(2022, 12, 1), "of"));

////listAllJournalsTitle
//        String s = list.stream()
//                .map(Journal::getTitle)
//                .collect(Collectors.joining(", "));

//        //findByTitle
//        String title = "bb";
//        String s = list.stream()
//                .filter(o -> o.getTitle().equals(title))
//                .map(Journal::toString)
//                .collect(Collectors.joining(",\n"));

//        //findByKeywords
//        List<String> keywords = new ArrayList<>();
//        keywords.add("aa");
//        keywords.add("bb");
//        List<Map.Entry<Long, Journal>> journalPairs = new ArrayList<>();
//
//        for (Journal journal : list) {
//            String separatorRegex = "[\\p{IsPunctuation}\\p{IsWhite_Space}]+";
//
//            List<String> contentWords = Arrays.stream(journal.getContent().split(separatorRegex))
//                    .distinct()
//                    .toList();
//
//            Long matchCount = keywords.stream().filter(contentWords::contains).count();
//            if (matchCount > 0) {
//                journalPairs.add(new AbstractMap.SimpleEntry<>(matchCount, journal));
//            }
//        }
//
//        String delimiter = ",\n";
//        String s = journalPairs.stream()
//                .sorted(Map.Entry.<Long, Journal>comparingByKey().reversed())
//                .map(Map.Entry::getValue)
//                .map(Journal::toString)
//                .collect(Collectors.joining(delimiter));

//        //findByDate
//        String s = list.stream()
//                .filter(o -> o.getCreationDate().equals(LocalDate.of(2020,12,1)))
//                .map(Journal::toString)
//                .collect(Collectors.joining(",\n"));

//        //sortByTitle
//        String s = list.stream()
//                .sorted(Comparator.comparing(Journal::getTitle))
//                .map(Journal::toString)
//                .collect(Collectors.joining(",\n"));
//
//        Stream<Journal> stream = list.stream();
//        stream = stream.sorted(Comparator.comparing(Journal::getTitle));
//        String s1 = stream.map(Journal::toString)
//                .collect(Collectors.joining(",\n"));
//
//        System.out.println(s);
//        System.out.println();
//        System.out.println(s1);

//        //sortByDate
//        String s = list.stream()
//                .sorted((Comparator.comparing(Journal::getCreationDate)).reversed())
//                .map(Journal::toString)
//                .collect(Collectors.joining(",\n"));
//        System.out.println(s);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://quotes15.p.rapidapi.com/quotes/random/"))
                .header("x-rapidapi-host", "quotes15.p.rapidapi.com")
                .header("x-rapidapi-key", null)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(response.body());


//        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://catfact.ninja/fact")).build();
//        try {
//            System.out.println(HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()).body());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
