package com.example.ListAdapterNotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.app.DownloadManager;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;



import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class BooksListsActivity extends AppCompatActivity {
    private List<Note> notes;
    private NotesAdapter notesAdapter;
    private String searchQuery;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_lists);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        notes = new ArrayList<>();
        notes.add(new Note(1, "Example 1", true));
        notes.add(new Note(2, "Example 2",false));
        notes.add(new Note(3, "Example 3",true));
        notes.add(new Note(4, "Example 4",true));
        ListView listView = findViewById(R.id.listView);
        notesAdapter = new NotesAdapter(notes);
        notesAdapter.setOnEditDeleteListener(new NotesAdapter.OnEditDeleteListener() {
            @Override
            public void onEdit(final Note note) {
                AddEditNoteDialogFragment addEditNoteDialogFragment = new AddEditNoteDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable(AddEditNoteDialogFragment.NOTE_TEXT, note);
                addEditNoteDialogFragment.setArguments(bundle);
                addEditNoteDialogFragment.setOnSaveListener(new AddEditNoteDialogFragment.OnSaveListener() {
                    @Override
                    public void onSave(Note note) {
                        notesAdapter.add(searchQuery, note);
                    }
                });
                addEditNoteDialogFragment.show(getSupportFragmentManager(), addEditNoteDialogFragment.getTag());
            }

            @Override
            public void onDelete(final Note note) {
                notesAdapter.delete(note);
            }
        });
        listView.setAdapter(notesAdapter);

//        SearchView searchView = findViewById(R.id.search_view);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                // لو بدي اياها تبحث من دون مضغط علها بحط الجملتين الللي تحت السطر هاد في الأون كويري تكست تشينج
//                searchQuery = query;
//                notesAdapter.getFilter().filter(searchQuery);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
//            @Override
//            public boolean onClose() {
//                searchQuery = "";
//                notesAdapter.getFilter().filter(searchQuery);
//                return false;
//            }
//        });

        Intent intent = getIntent();
        if (intent != null){
            handleSearchIntent(intent);
        }
      }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleSearchIntent(intent);
    }

    private  void handleSearchIntent(Intent intent){
        if (Intent.ACTION_SEARCH.equals(intent.getAction()));
          searchQuery = intent.getStringExtra(SearchManager.QUERY);
          notesAdapter.getFilter().filter(searchQuery);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_list_menu , menu);
        MenuItem menuItem =menu.findItem(R.id.action_search);
        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                System.out.println("Expand");
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                System.out.println("Collapse");
                searchQuery = "";
                notesAdapter.getFilter().filter(searchQuery);
                return true;
            }
        });
        searchView = (SearchView) menuItem.getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // لو بدي اياها تبحث من دون مضغط علها بحط الجملتين الللي تحت السطر هاد في الأون كويري تكست تشينج
                searchQuery = query;
                notesAdapter.getFilter().filter(searchQuery);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchQuery = "";
                notesAdapter.getFilter().filter(searchQuery);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                AddEditNoteDialogFragment addEditNoteDialogFragment = new AddEditNoteDialogFragment();
                addEditNoteDialogFragment.setOnSaveListener(new AddEditNoteDialogFragment.OnSaveListener() {
                    @Override
                    public void onSave(Note note) {
                        notesAdapter.add(searchQuery , note);

                    }
                });
                addEditNoteDialogFragment.show(getSupportFragmentManager() ,addEditNoteDialogFragment.getTag());
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}