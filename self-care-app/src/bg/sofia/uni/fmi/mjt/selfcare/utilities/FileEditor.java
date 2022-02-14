package bg.sofia.uni.fmi.mjt.selfcare.utilities;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileEditor {
    private final String CREDENTIALS = "./credentials.txt";
    private final String USERS_DIR = "./users/";
    private final String USER_FILE_POSTFIX = ".txt";

    public boolean isUsernameFree(String username) {
        try (Reader fileReader = new FileReader(CREDENTIALS);
             BufferedReader reader = new BufferedReader(fileReader)) {

            String usernameFile;
            String passwordFile;

            while ((usernameFile = reader.readLine()) != null && (passwordFile = reader.readLine()) != null) {
                if (usernameFile.equals(username)) {
                    return false;
                }
            }
        } catch (FileNotFoundException e) {
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    public boolean areCredentialsCorrect(String username, String password) {
        try (Reader fileReader = new FileReader(CREDENTIALS);
             BufferedReader reader = new BufferedReader(fileReader)) {

            String usernameFile;
            String passwordFile;

            while ((usernameFile = reader.readLine()) != null && (passwordFile = reader.readLine()) != null) {
                if (usernameFile.equals(username) && passwordFile.equals(password)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void addNewUser(String username, String password) {
        createFile(CREDENTIALS);

        try (Writer fileWriter = new FileWriter(CREDENTIALS, true);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {

            writer.write(username);
            writer.newLine();

            writer.write(password);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNewJournal(String username, Journal journal) {
        String pathToUserFile = USERS_DIR + username + USER_FILE_POSTFIX;
        createFile(pathToUserFile);

        try (Writer fileWriter = new FileWriter(pathToUserFile, true);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {

            writer.write(journal.getTitle());
            writer.newLine();

            writer.write(journal.getCreationDate().toString());
            writer.newLine();

            writer.write(journal.getContent());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Journal> getAllJournalsOfUser(String username) {
        String pathToUserFile = USERS_DIR + username + USER_FILE_POSTFIX;

        List<Journal> allJournals = new ArrayList<>();

        try (Reader fileReader = new FileReader(pathToUserFile);
             BufferedReader reader = new BufferedReader(fileReader)) {

            String title;
            String date;
            String content;

            while ((title = reader.readLine()) != null &&
                    (date = reader.readLine()) != null &&
                    (content = reader.readLine()) != null) {

                Journal journal = new Journal(title, LocalDate.parse(date), content);
                allJournals.add(journal);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allJournals;
    }

    private void createFile(String path) {
        try {
            Files.createFile(Path.of(path));
        } catch (IOException ignored) {
        }
    }
}
