package bg.sofia.uni.fmi.mjt.selfcare.utilities;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Journal implements Serializable {
    private final String title;
    private final LocalDateTime creationDate;
    private final String content;

    public Journal(String title, String content) {
        this.title= title;
        this.creationDate = LocalDateTime.now();
        this.content = content;
    }
}
