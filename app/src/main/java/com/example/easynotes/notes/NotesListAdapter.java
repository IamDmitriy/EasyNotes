package com.example.easynotes.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.easynotes.App;
import com.example.easynotes.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NotesListAdapter extends BaseAdapter {
    private static final String DATE_FORMAT = "dd.MM.yyyy HH:mm";

    private List<Note> notes;

    private LayoutInflater inflater;

    private Context context;

    NotesListAdapter(Context context) {
        this.context = context;

        notes = App.getNoteRepository().getNotes();

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    void deleteNote(int position) {
        notes.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int position) {
        if (position < notes.size()) {
            return notes.get(position);
        } else {
            return null;
        }

    }

    @Override
    public long getItemId(int position) {
        return notes.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.notes_list_item, parent, false);
        }

        Note note = notes.get(position);

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
