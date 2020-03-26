package com.example.easynotes.notes;

public class Note {
    private int id;
    private String title;
    private String body;
    private boolean hasDeadline;
    private long deadline;

    public Note(int id, String title, String body, boolean hasDeadline, long deadline) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.hasDeadline = hasDeadline;

        if (hasDeadline) {
            this.deadline = deadline;
        }

    }


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public boolean hasDeadline() {
        return hasDeadline;
    }

    public long getDeadline() {
        return deadline;
    }
}
