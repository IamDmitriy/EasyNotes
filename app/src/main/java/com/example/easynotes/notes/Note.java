package com.example.easynotes.notes;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Note {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private String body;
    private boolean hasDeadline;
    private long deadline;
    private long lastChanges;

    @Ignore
    public Note(String title, String body) {
        this.title = title;
        this.body = body;
        hasDeadline = false;
        lastChanges = new Date().getTime();
    }

    @Ignore
    public Note(String title, String body, long deadline) {
        this.title = title;
        this.body = body;
        hasDeadline = true;
        this.deadline = deadline;
        lastChanges = new Date().getTime();
    }

    public Note(long id, String title, String body, boolean hasDeadline, long deadline, long lastChanges) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.hasDeadline = hasDeadline;
        this.deadline = deadline;
        this.lastChanges = lastChanges;
    }

    public long getLastChanges() {
        return lastChanges;
    }

    public void setLastChanges(long lastChanges) {
        this.lastChanges = lastChanges;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public void setHasDeadline(boolean hasDeadline) {
        this.hasDeadline = hasDeadline;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }


}
