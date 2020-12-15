package com.example.ListAdapterNotes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddEditNoteDialogFragment extends DialogFragment {
    public static final String NOTE_TEXT = "NOTE_TEXT";
    public interface OnSaveListener{
        void onSave(Note note);
    }
    private OnSaveListener onSaveListener;
    private Note note;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null){
            note = getArguments().getParcelable(NOTE_TEXT);
        }
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        if (note == null){
            builder.setTitle("Add");
        }else {
            builder.setTitle(getString(R.string.edit));
        }
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_edit_layout , (ViewGroup) getView(), false);
        EditText noteEditText = view .findViewById(R.id.edit_text_note);
        if (note != null){
            noteEditText.setText(note.getText());

        }
        view.findViewById(R.id.button_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEditNoteDialogFragment.this.dismiss();
            }
        });
        view.findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (onSaveListener != null){
                String noteText= noteEditText.getText().toString();
                if (!TextUtils.isEmpty(noteText)){
                    if (note == null){
                        note = new Note(System.currentTimeMillis() ,noteText);

                    }else {
                        note.setText(noteText);
                    }
                    onSaveListener.onSave(note);
                    AddEditNoteDialogFragment.this.dismiss();
                }else {
                    noteEditText.setError(getString(R.string.error_note_empty));
                }
            }
            }
        });
        builder.setView(view);
        return builder.create();
    }
    public void setOnSaveListener (OnSaveListener onSaveListener){
        this.onSaveListener = onSaveListener;
    }
}
