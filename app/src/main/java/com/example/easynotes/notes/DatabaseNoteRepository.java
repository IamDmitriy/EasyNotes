package com.example.easynotes.notes;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class DatabaseNoteRepository implements NoteRepository {
    private static final String LOG_TAG = "DBNoteRepositoryTag";
    private static final String DATABASE_NAME = "notes-database";

    private List<Note> notesCache;
    private AppDatabase db;
    private NoteDao noteDao;
    private NoteRepository.onDataChangedListener onDataChangedListener = null;

    public DatabaseNoteRepository(Context applicationContext) {
        db = Room.databaseBuilder(applicationContext, AppDatabase.class, DATABASE_NAME).build();
        noteDao = db.getNoteDao();
        notesCache = new ArrayList<>();

        //запускаем подгрузку данных из БД:
        noteDao.getAllNotes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Note>>() {
                    @Override
                    public void accept(List<Note> notes) {
                        notesCache.clear();
                        notesCache.addAll(notes);
                        Log.d(LOG_TAG, "Данные обновлены");
                        if (onDataChangedListener != null) {
                            Log.d(LOG_TAG, "Сработал интерфейс DataChanged");
                            onDataChangedListener.onDataChanged();
                        }
                    }
                });
    }

    @Override
    public List<Note> getNotes() {
        return notesCache;
    }

    @Override
    public int size() {
        return notesCache.size();
    }

    @Override
    public Note getNoteByPos(int position) {
        if (position < notesCache.size()) {
            return notesCache.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getNoteIdByPos(int position) {
        return getNoteByPos(position).getId();
    }

    @Override
    public Note getNoteById(long id) {
        for (Note note : notesCache) {
            if (note.getId() == id) return note;
        }
        return null;
    }

    @Override
    public void saveNote(final Note note) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                noteDao.insertAll(note);

            }
        }).start();

    }

    @Override
    public void setOnDataChangedListener(onDataChangedListener onDataChangedListener) {
        this.onDataChangedListener = onDataChangedListener;
    }

    @Override
    public void deleteById(final long id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                noteDao.deleteById(id);
            }
        }).start();

    }
}
