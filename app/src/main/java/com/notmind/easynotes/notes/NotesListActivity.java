package com.notmind.easynotes.notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.notmind.easynotes.R;
import com.notmind.easynotes.SettingsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NotesListActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_NOTE = 1;
    private static final String KEY_IS_NOTE_SAVE = "isNoteSaved";
    private static final String KEY_NOTE_ID = "noteId";

    private NotesListAdapter notesListAdapter;
    private SwipeRefreshLayout swipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        init();
    }

    private void init() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotesListActivity.this, NoteActivity.class);
                startActivityForResult(intent, REQUEST_CODE_NOTE);

            }
        });

        swipeLayout = findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                notesListAdapter.notifyDataSetChanged();
                swipeLayout.setRefreshing(false);
            }
        });


        ListView listViewNotes = findViewById(R.id.listViewNotes);
        notesListAdapter = new NotesListAdapter(NotesListActivity.this);
        listViewNotes.setAdapter(notesListAdapter);

        listViewNotes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteDialog(position);
                return true;
            }
        });

        listViewNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NotesListActivity.this, NoteActivity.class);
                Note curNote = (Note) notesListAdapter.getItem(position);
                intent.putExtra(KEY_NOTE_ID, curNote.getId());
                startActivityForResult(intent, REQUEST_CODE_NOTE);
            }
        });
    }

    private void showDeleteDialog(final int positionForDelete) {
        AlertDialog.Builder builder = new AlertDialog.Builder(NotesListActivity.this);
        builder.setMessage(R.string.confirmation_deletion);
        builder.setIcon(R.drawable.ic_delete_black_24dp);
        builder.setTitle(R.string.attention);

        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                notesListAdapter.deleteNote(positionForDelete);
                Toast.makeText(NotesListActivity.this,
                        R.string.note_deleted_successfully, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
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
                startActivity(new Intent(NotesListActivity.this, SettingsActivity.class));
                return true;
        }

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_NOTE && data != null && resultCode == RESULT_OK) {
            if (data.getBooleanExtra(KEY_IS_NOTE_SAVE, false)) {
                notesListAdapter.notifyDataSetChanged();
                //TODO По сути необходимоти т.к.адптер сам будет обновляться , если я реализую метод
                // озавершении операции

            }
        }

    }
}
