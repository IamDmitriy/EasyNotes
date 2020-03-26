package com.example.easynotes.notes;

public class Note {
    private int id;
    private String title;
    private String body;
    private boolean hasDeadline;
    private long deadline;

    public Note(String title, String body) {
        this.title = title;
        this.body = body;
        hasDeadline = false;
    }

    public Note(String title, String body, long deadline) {
        this.title = title;
        this.body = body;
        hasDeadline = true;
        this.deadline = deadline;
    }

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

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean hasDeadline() {
        return hasDeadline;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public void setHasDeadline(boolean hasDeadline) {
        this.hasDeadline = hasDeadline;
    }
}
