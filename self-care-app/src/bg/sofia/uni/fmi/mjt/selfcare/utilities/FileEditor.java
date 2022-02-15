package bg.sofia.uni.fmi.mjt.selfcare.utilities;

import bg.sofia.uni.fmi.mjt.selfcare.exceptions.FileEditorException;

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

    public boolean isUsernameFree(String username) throws FileEditorException {
        try (Reader fileReader = new FileReader(CREDENTIALS);
             BufferedReader reader = new BufferedReader(fileReader)) {

            String usernameFile;
            String passwordFile;

            while ((usernameFile = reader.readLine()) != null && (passwordFile = reader.readLine()) != null) {
                if (usernameFile.equals(username)) {
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            throw new FileEditorException(e.getMessage());
        }
    }

    public boolean areCredentialsCorrect(String username, String password) throws FileEditorException {
        try (Reader fileReader = new FileReader(CREDENTIALS);
             BufferedReader reader = new BufferedReader(fileReader)) {

            String usernameFile;
            String passwordFile;

            while ((usernameFile = reader.readLine()) != null && (passwordFile = reader.readLine()) != null) {
                if (usernameFile.equals(username) && passwordFile.equals(password)) {
                    return true;
                }
            }

            return false;
        } catch (Exception e) {
            throw new FileEditorException(e.getMessage());
        }
    }

    public void addNewUser(String username, String password) throws FileEditorException {
        createFile(CREDENTIALS);

        try (Writer fileWriter = new FileWriter(CREDENTIALS, true);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {

            writer.write(username);
            writer.newLine();

            writer.write(password);
            writer.newLine();
        } catch (Exception e) {
            throw new FileEditorException(e.getMessage());
        }
    }

    public void addNewJournal(String username, Journal journal) throws FileEditorException {
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
        } catch (Exception e) {
            throw new FileEditorException(e.getMessage());
        }
    }

    public List<Journal> getAllJournalsOfUser(String username) throws FileEditorException {
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

            return allJournals;
        } catch (Exception e) {
            throw new FileEditorException(e.getMessage());
        }
    }

    private void createFile(String path) {
        try {
            Files.createFile(Path.of(path));
        } catch (Exception ignored) {
        }
    }
}
