package com.notmind.easynotes.notes;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.notmind.easynotes.App;
import com.notmind.easynotes.notification.DeadlineWorker;
import com.notmind.easynotes.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class NoteActivity extends AppCompatActivity {
    private static final String DATE_FORMAT = "dd.MM.yyyy";
    private static final String TIME_FORMAT = "HH:mm";
    private static final String KEY_IS_NOTE_SAVE = "isNoteSaved";
    private static final String KEY_NOTE_ID = "noteId";

    private Calendar dateAndTime;
    private TextView txtDate;
    private TextView txtTime;
    private ImageButton btnSetDate;
    private ImageButton btnSetTime;
    private EditText edtTitle;
    private EditText edtBody;
    private CheckBox chbHasDeadline;
    private Note noteForEdit;

    private DatePickerDialog.OnDateSetListener onDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    dateAndTime.set(Calendar.YEAR, year);
                    dateAndTime.set(Calendar.MONTH, monthOfYear);
                    dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    setOutputDateTime(dateAndTime);
                }
            };

    private TimePickerDialog.OnTimeSetListener onTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    dateAndTime = Calendar.getInstance();
                    dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    dateAndTime.set(Calendar.MINUTE, minute);

                    setOutputDateTime(dateAndTime);

                }
            };
    private NoteRepository noteRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        noteRepository = App.getNoteRepository();

        long noteId = getIntent().getLongExtra(KEY_NOTE_ID, -1);

        if (noteId != -1) {
            noteForEdit = noteRepository.getNoteById(noteId);
        } else {
            noteForEdit = null;
        }

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                if (noteForEdit == null) {
                    saveNewNote();
                } else {
                    saveEditedNote();
                }
                Toast.makeText(NoteActivity.this, getString(R.string.note_saved_successfully),
                        Toast.LENGTH_SHORT).show();
                return true;
        }

        return false;
    }

    private void init() {

        edtTitle = findViewById(R.id.edtTitle);
        edtBody = findViewById(R.id.edtBody);
        chbHasDeadline = findViewById(R.id.chbHasDeadline);

        txtDate = findViewById(R.id.txtDate);
        txtTime = findViewById(R.id.txtTime);
        btnSetDate = findViewById(R.id.btnSetDate);
        btnSetTime = findViewById(R.id.btnSetTime);

        dateAndTime = Calendar.getInstance();

        chbHasDeadline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setEnabledInputDateTime(isChecked);

                if (isChecked) {
                    setOutputDateTime(dateAndTime);
                }
            }
        });

        if (noteForEdit != null) {
            initNoteForEdit();
        } else {
            setEnabledInputDateTime(false);
        }

    }

    private void initNoteForEdit() {
        String title = noteForEdit.getTitle();
        String body = noteForEdit.getBody();
        boolean hasDeadline = noteForEdit.hasDeadline();

        edtTitle.setText(title);
        edtBody.setText(body);
        chbHasDeadline.setChecked(hasDeadline);
        setEnabledInputDateTime(hasDeadline);

        if (hasDeadline) {
            dateAndTime.setTimeInMillis(noteForEdit.getDeadline());

            setOutputDateTime(dateAndTime);
        }


    }

    private void setEnabledInputDateTime(boolean enabled) {
        txtDate.setEnabled(enabled);
        txtTime.setEnabled(enabled);
        btnSetDate.setEnabled(enabled);
        btnSetTime.setEnabled(enabled);
    }

    private void setOutputDateTime(Calendar dateAndTime) {
        DateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);
        String deadlineTime = timeFormat.format(dateAndTime.getTime());
        txtTime.setText(deadlineTime);

        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String deadlineDate = dateFormat.format(dateAndTime.getTime());
        txtDate.setText(deadlineDate);
    }

    public void setDate(View v) {
        new DatePickerDialog(NoteActivity.this, onDateSetListener,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    public void setTime(View v) {
        new TimePickerDialog(NoteActivity.this, onTimeSetListener,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }

    private void saveEditedNote() {
        String title = edtTitle.getText().toString();
        String body = edtBody.getText().toString();
        boolean hasDeadline = chbHasDeadline.isChecked();

        noteForEdit.setTitle(title);
        noteForEdit.setBody(body);
        noteForEdit.setHasDeadline(hasDeadline);

        if (hasDeadline) {
            noteForEdit.setDeadline(dateAndTime.getTimeInMillis());
        }

        noteForEdit.setLastChanges(new Date().getTime());


        finishSaveNote(noteForEdit);
    }

    private void saveNewNote() {
        Note newNote;

        String title = edtTitle.getText().toString();
        String body = edtBody.getText().toString();
        boolean hasDeadline = chbHasDeadline.isChecked();

        if (hasDeadline) {
            newNote = new Note(title, body, dateAndTime.getTimeInMillis());

        } else {
            newNote = new Note(title, body);
        }

        finishSaveNote(newNote); //TODO внимание, тут id пустой
    }

    private void finishSaveNote(Note noteForSave) {
        dispatchScheduleReminder(noteForSave); //Хорошо бы через возврат всё сделать
        noteRepository.saveNote(noteForSave);

        Intent intent = new Intent();
        intent.putExtra(KEY_IS_NOTE_SAVE, true);

        setResult(RESULT_OK, intent);

        finish();
    }

    private void dispatchScheduleReminder(Note note) {
        if (note.hasWorkId() && !note.hasDeadline()) { //существующее напоминание удалили
            cancelScheduleReminder(note);
            return;
        }

        if (note.hasWorkId() && note.hasDeadline()) { //существующее напоминание изменили
            updateScheduleReminder(note);
            return;
        }

        if (!note.hasWorkId() && note.hasDeadline()) { // создают новое напоминание
            createScheduleReminder(note);
            return;
        }

        if (!note.hasWorkId() && !note.hasDeadline()) { // напоминание не установлено
            return;
        }

    }

    private void cancelScheduleReminder(Note note) {
        note.setHasWorkId(false);
        WorkManager.getInstance().cancelWorkById(UUID.fromString(note.getWorkId()));
    }

    private void updateScheduleReminder(Note note) {
        cancelScheduleReminder(note);
        createScheduleReminder(note);
    }

    private void createScheduleReminder(Note note) {
        long delay = note.getDeadline() - System.currentTimeMillis();
        if (delay < 0) {
            note.setHasWorkId(false);
            return;
        } // Напоминание установлено в прошлом - задача не треубется

        Data data = new Data.Builder()
                .putLong(DeadlineWorker.NOTE_ID_KEY, note.getId())
                .putString(DeadlineWorker.CONTENT_TITLE_KEY, note.getTitle())
                .putString(DeadlineWorker.CONTENT_TEXT_KEY, note.getBody())
                .build();

        OneTimeWorkRequest scheduleReminderRequest = new OneTimeWorkRequest.Builder(DeadlineWorker.class)
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .setInputData(data)
                .build();

        WorkManager.getInstance().enqueue(scheduleReminderRequest);

        note.setHasWorkId(true);
        note.setWorkId(scheduleReminderRequest.getId().toString());
    }

    @Override
    public void onBackPressed() {
        if (noteForEdit == null) {
            saveNewNote();
        } else {
            saveEditedNote();
        }

        Intent intent = new Intent();
        intent.putExtra(KEY_IS_NOTE_SAVE, true);

        setResult(RESULT_OK, intent);

        finish();
    }
}
