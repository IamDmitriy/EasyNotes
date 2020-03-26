package com.example.easynotes.notes;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.easynotes.R;

public class NotesListActivity extends AppCompatActivity {
    private static final String LOG_TAG = "ListNotesActivityTag";
    private NotesListAdapter notesListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        main();
    }

    private void main() {
        ListView listViewNotes = findViewById(R.id.listViewNotes);
        notesListAdapter = new NotesListAdapter(this);
        listViewNotes.setAdapter(notesListAdapter);

        listViewNotes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO реализовать диалоговое окно
                notesListAdapter.deleteNote(position);
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Log.d(LOG_TAG, "Выбран пункт меню Настройки");
                return true;
        }

        return false;
    }
}
