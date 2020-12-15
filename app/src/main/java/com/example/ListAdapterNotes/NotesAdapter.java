package com.example.ListAdapterNotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends BaseAdapter {

    private static final int TYPE_IMPORTANT = 0;
    private static final int TYPE_NORMAL = 1;

    public interface OnEditDeleteListener {
        void onEdit(Note note);

        void onDelete(Note note);
    }

    private OnEditDeleteListener onEditDeleteListener;
    private NoteFilter noteFilter;

    private List<Note> originalNotesList;
    private List<Note> filteredNotesList;

    public NotesAdapter(List<Note> originalNotesList) {
        this.originalNotesList = originalNotesList;
        this.filteredNotesList = new ArrayList<>();
        this.filteredNotesList.addAll(originalNotesList);
        noteFilter = new NoteFilter();
    }

    @Override
    public int getCount() {
        return filteredNotesList.size();
    }

    @Override
    public Note getItem(int position) {
        return filteredNotesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return filteredNotesList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NormalViewHolder normalViewHolder;
        ImportantViewHolder importantViewHolder;
        if (convertView == null) {
            // Create new view
            normalViewHolder = new NormalViewHolder();
            importantViewHolder = new ImportantViewHolder();
            if (getItemViewType(position) == TYPE_NORMAL) {
                // Inflate layout for normal note
                // Use normal holder and save it using unique tag key
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
                normalViewHolder.noteTextView = convertView.findViewById(R.id.text_view_note);
                normalViewHolder.editImageButton = convertView.findViewById(R.id.image_button_edit);
                normalViewHolder.deleteImageButton = convertView.findViewById(R.id.image_button_delete);
                normalViewHolder.doneCheckBox = convertView.findViewById(R.id.check_box_done);
                normalViewHolder.editImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onEditDeleteListener != null) {
                            onEditDeleteListener.onEdit((Note) v.getTag());
                        }
                    }
                });
                normalViewHolder.deleteImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onEditDeleteListener != null) {
                            onEditDeleteListener.onDelete((Note) v.getTag());
                        }
                    }
                });
                normalViewHolder.doneCheckBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Note note = (Note) v.getTag();
                        CheckBox checkBox = (CheckBox) v;
                        note.setChecked(checkBox.isChecked());
                    }
                });
                convertView.setTag(R.layout.item_layout, normalViewHolder);
            } else {
                // Inflate layout for important note
                // Use important holder and save it using unique tag key
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_important, parent, false);
                importantViewHolder.noteTextView = convertView.findViewById(R.id.text_view_note);
                convertView.setTag(R.layout.item_layout_important, importantViewHolder);
            }
        } else {
            // Get recycled view holder based on the view tag
            normalViewHolder = (NormalViewHolder) convertView.getTag(R.layout.item_layout);
            importantViewHolder = (ImportantViewHolder) convertView.getTag(R.layout.item_layout_important);
        }

        Note note = filteredNotesList.get(position);
        if (getItemViewType(position) == TYPE_NORMAL) {
            // Show normal note
            normalViewHolder.noteTextView.setText(note.getText());
            normalViewHolder.doneCheckBox.setChecked(note.isChecked());
            normalViewHolder.editImageButton.setTag(note);
            normalViewHolder.deleteImageButton.setTag(note);
            normalViewHolder.doneCheckBox.setTag(note);
        } else {
            // Show important note
            importantViewHolder.noteTextView.setText(note.getText());
        }
        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        Note note = getItem(position);
        if (note.isImportant()) {
            return TYPE_IMPORTANT;
        } else {
            return TYPE_NORMAL;
        }
    }

    public void setOnEditDeleteListener(OnEditDeleteListener onEditDeleteListener) {
        this.onEditDeleteListener = onEditDeleteListener;
    }

    public Filter getFilter() {
        return noteFilter;
    }

    public void delete(Note note) {
        originalNotesList.remove(note);
        filteredNotesList.remove(note);
        notifyDataSetChanged();
    }

    public void add(String searchQuery, Note note) {
        originalNotesList.add(0, note);
        if (searchQuery == null || note.getText().toLowerCase().contains(searchQuery.toLowerCase())) {
            filteredNotesList.add(0, note);
        }
        notifyDataSetChanged();
    }


    static class NormalViewHolder {
        TextView noteTextView;
        ImageButton editImageButton;
        ImageButton deleteImageButton;
        CheckBox doneCheckBox;
    }

    static class ImportantViewHolder {
        TextView noteTextView;
    }


    class NoteFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults filterResults = new FilterResults();

            List<Note> tempList = new ArrayList<>();
            String searchQuery = (String) constraint;
            for (int i = 0; i < originalNotesList.size(); i++) {
                Note note = originalNotesList.get(i);
                if (note.getText().toLowerCase().contains(searchQuery.toLowerCase())) {
                    tempList.add(note);
                }
            }

            filterResults.values = tempList;
            filterResults.count = tempList.size();

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
//            filteredNotesList = (List<Note>) results.values;
//            notifyDataSetChanged();
        }

    }

}