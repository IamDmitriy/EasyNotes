package com.example.easynotes.notes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MemoryNoteRepository implements NoteRepository {
    private static int MAX_ID = 0;

    private Map<Integer, Note> notes;

    public MemoryNoteRepository() {
        notes = new TreeMap<>(); //TODO реализовать компаратор и пополнить контент для демонстрацию.

        generateContent();
    }

    private void generateContent() {
        Note[] content = new Note[]{
                new Note(MAX_ID++, "Заметка с заголовков", "Короткое тело",
                        false, 0),
                new Note(MAX_ID++, null, "Заметка без заголовка", false,
                        0),
                new Note(MAX_ID++, "Заметка без тела с длинным заголовком", null,
                        false, 0),
                new Note(MAX_ID++, "Заметка с длинным телом", "1111111111111111111111" +
                        "22222222222222222222222222222222222222222111111111111111111111111111111" +
                        "11111111111111111111111111111111111111111", false, 0),
                new Note(MAX_ID++, null, "Заметка с просроченным дедлайном",
                        true, 749_701_100_138L),
                new Note(MAX_ID++, null, "Заметка с будующим дедлайном",
                        true, 1_949_701_100_138L),

                new Note(MAX_ID++, "Заметка с заголовков", "Короткое тело",
                        false, 0),
                new Note(MAX_ID++, null, "Заметка без заголовка", false,
                        0),
                new Note(MAX_ID++, "Заметка без тела с длинным заголовком", null,
                        false, 0),
                new Note(MAX_ID++, "Заметка с длинным телом", "1111111111111111111111" +
                        "22222222222222222222222222222222222222222111111111111111111111111111111" +
                        "11111111111111111111111111111111111111111", false, 0),
                new Note(MAX_ID++, null, "Заметка с просроченным дедлайном",
                        true, 749_701_100_138L),
                new Note(MAX_ID++, null, "Заметка с будующим дедлайном",
                        true, 1_949_701_100_138L),

                new Note(MAX_ID++, "Заметка с заголовков", "Короткое тело",
                        false, 0),
                new Note(MAX_ID++, null, "Заметка без заголовка", false,
                        0),
                new Note(MAX_ID++, "Заметка без тела с длинным заголовком", null,
                        false, 0),
                new Note(MAX_ID++, "Заметка с длинным телом", "1111111111111111111111" +
                        "22222222222222222222222222222222222222222111111111111111111111111111111" +
                        "11111111111111111111111111111111111111111", false, 0),
                new Note(MAX_ID++, null, "Заметка с просроченным дедлайном",
                        true, 749_701_100_138L),
                new Note(MAX_ID++, null, "Заметка с будующим дедлайном",
                        true, 1_949_701_100_138L),
        };


        for (Note curNote : content) {
            notes.put(curNote.getId(), curNote);
        }


    }

    @Override
    public Note getNoteById(String id) {
        return notes.get(Integer.valueOf(id));
    }

    @Override
    public List<Note> getNotes() {

        List<Note> outputList = new ArrayList<>();
        Iterator<Note> iterator = notes.values().iterator();
        for (int i = 0; i < notes.size(); i++) {

            outputList.add(iterator.next());
        }

        return outputList;
    }

    @Override
    public void saveNote(Note note) {
        notes.put(MAX_ID++, note);
    }

    @Override
    public void deleteById(String id) {
        notes.remove(Integer.valueOf(id));
    }
}
