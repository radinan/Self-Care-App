package bg.sofia.uni.fmi.mjt.selfcare.utilities;

import java.time.LocalDate;

public class Journal {
    private final String title;
    private final LocalDate creationDate;
    private final String content;

    public Journal(String title, String content) {
        this.title= title;
        this.creationDate = LocalDate.now();
        this.content = content;
    }

    public Journal(String title, LocalDate creationDate, String content) {
        this.title= title;
        this.creationDate = creationDate;
        this.content = content;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Journal{" +
                "title='" + title + '\'' +
                ", creationDate=" + creationDate +
                ", content='" + content + '\'' +
                '}';
    }
}
