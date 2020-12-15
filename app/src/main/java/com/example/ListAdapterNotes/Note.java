package com.example.ListAdapterNotes;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {
    private long id;
    private String text;
    private boolean Checked;
    private boolean isImportant;

    public Note(long id, String text) {
        this.id = id;
        this.text = text;

    }

    public Note(long id, String text,boolean isImportant) {
        this.id = id;
        this.text = text;
        this.isImportant=isImportant;

    }


    protected Note(Parcel in) {
        id = in.readLong();
        text = in.readString();
        Checked = in.readByte() != 0;
        isImportant = in.readByte() != 0;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setChecked(boolean checked) {
        Checked = checked;
    }

    public boolean isChecked() {
        return Checked;
    }

    public void setImportant(boolean important) {
        isImportant = important;
    }

    public boolean isImportant() {
        return isImportant;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(id);
        dest.writeString(text);
        dest.writeByte((byte) (Checked ? 1 : 0));
        dest.writeByte((byte) (isImportant ? 1 : 0));
    }
}

