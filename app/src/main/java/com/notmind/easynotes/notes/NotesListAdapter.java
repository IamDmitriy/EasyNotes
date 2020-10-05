package com.notmind.easynotes.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.notmind.easynotes.App;
import com.notmind.easynotes.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NotesListAdapter extends BaseAdapter {
    private static final String DATE_FORMAT = "dd.MM.yyyy HH:mm";

    private LayoutInflater inflater;
    private Context context;
    private NoteRepository noteRepository;


    NotesListAdapter(Context context) {
        this.context = context;

        noteRepository = App.getNoteRepository();

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        noteRepository.setOnDataChangedListener(new NoteRepository.onDataChangedListener() {
            @Override
            public void onDataChanged() {
                notifyDataSetChanged();
            }
        });
    }

    void deleteNote(int position) {
        noteRepository.deleteById(noteRepository.getNoteByPos(position).getId());
    }

    @Override
    public int getCount() {
        return noteRepository.size();
    }

    @Override
    public Object getItem(int position) {
        return noteRepository.getNoteByPos(position);
    }

    @Override
    public long getItemId(int position) {
        return noteRepository.getNoteIdByPos(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.notes_list_item, parent, false);
        }

        Note note = noteRepository.getNoteByPos(position);

        TextView txtTitle = view.findViewById(R.id.title);
        TextView txtBody = view.findViewById(R.id.body);
        TextView txtDeadline = view.findViewById(R.id.deadline);

        String title = note.getTitle();
        String body = note.getBody();
        boolean hasDeadline = note.hasDeadline();

        if (title != null && !title.isEmpty()) {
            txtTitle.setText(title);
            txtTitle.setVisibility(View.VISIBLE);
        } else {
            txtTitle.setVisibility(View.GONE);
        }

        if (body != null && !body.isEmpty()) {
            txtBody.setText(body);
            txtBody.setVisibility(View.VISIBLE);
        } else {
            txtBody.setVisibility(View.GONE);
        }

        if (hasDeadline) {
            Date deadlineDate = new Date(note.getDeadline());

            if (deadlineDate.compareTo(new Date()) < 0) {
                setCardBackgroundColor(R.color.colorOverdueDeadline, view);

            } else {
                setCardBackgroundColor(R.color.colorNormalCardBackground, view);
            }

            DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            String deadlineOutput = dateFormat.format(deadlineDate);
            txtDeadline.setText(deadlineOutput);

            txtDeadline.setVisibility(View.VISIBLE);
        } else {
            txtDeadline.setVisibility(View.GONE);
            setCardBackgroundColor(R.color.colorNormalCardBackground, view);
        }

        return view;
    }

    private void setCardBackgroundColor(int colorCardBackground, View curItemView) {
        CardView cardView = curItemView.findViewById(R.id.cardView);
        cardView.setCardBackgroundColor(context.getResources().
                getColor(colorCardBackground));
    }

}
