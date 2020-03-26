package com.example.easynotes.notes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MemoryNoteRepository implements NoteRepository {
    private static int MAX_ID = 0;

    private List<Note> notes;

    public MemoryNoteRepository() {
        notes = new ArrayList<>();

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
                new Note(MAX_ID++, null, "Заметка с будущим дедлайном",
                        true, 1_949_701_100_138L),

        };

        notes.addAll(Arrays.asList(content));

    }

    @Override
    public Note getNoteById(String id) {

        for (int i = 0; i < notes.size(); i++) {
            String curId = String.valueOf(notes.get(i).getId());
            if (curId.equals(id)) return notes.get(i);
        }

        return null;
    }

    @Override
    public List<Note> getNotes() {
        return notes;
    }

    @Override
    public void saveNote(Note note) {
        note.setId(MAX_ID++);
        notes.add(note);
    }

    @Override
    public void deleteById(String id) {

        for (int i = 0; i < notes.size(); i++) {
            String curId = String.valueOf(notes.get(i).getId());
            if (curId.equals(id)) notes.remove(Integer.valueOf(id));
        }

    }
}
