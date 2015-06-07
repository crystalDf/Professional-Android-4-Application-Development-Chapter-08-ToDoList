package com.star.todolist;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class ToDoListActivity extends AppCompatActivity {

    public static final int LOADER = 0;

    private ArrayList<ToDoItem> toDoItems;
    private ToDoItemAdapter toDoItemAdapter;

    private NewItemFragment.OnNewItemAddedListener listener = new NewItemFragment.OnNewItemAddedListener() {
        @Override
        public void onNewItemAdded(String newItem) {

            ContentResolver contentResolver = getContentResolver();

            ContentValues values = new ContentValues();

            values.put(ToDoContentProvider.KEY_TASK, newItem);

            contentResolver.insert(ToDoContentProvider.CONTENT_URI, values);

            getSupportLoaderManager().restartLoader(LOADER, null, loaderCallbacks);
        }
    };

    private android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks =
            new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
            CursorLoader cursorLoader = new CursorLoader(ToDoListActivity.this,
                    ToDoContentProvider.CONTENT_URI, null, null, null, null);

            return cursorLoader;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            int keyTaskIndex = cursor.getColumnIndexOrThrow(ToDoContentProvider.KEY_TASK);

            toDoItems.clear();

            while (cursor.moveToNext()) {
                ToDoItem toDoItem = new ToDoItem(cursor.getString(keyTaskIndex));
                toDoItems.add(toDoItem);
            }

            toDoItemAdapter.notifyDataSetChanged();
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        FragmentManager fragmentManager = getSupportFragmentManager();
        ToDoListFragment toDoListFragment =
                (ToDoListFragment) fragmentManager.findFragmentById(R.id.ToDoListFragment);

        toDoItems = new ArrayList<>();

        toDoItemAdapter = new ToDoItemAdapter(this, R.layout.todolist_item, toDoItems);

        toDoListFragment.setListAdapter(toDoItemAdapter);

        getSupportLoaderManager().initLoader(LOADER, null, loaderCallbacks);
    }

    @Override
    protected void onResume() {
        super.onResume();

        getSupportLoaderManager().restartLoader(LOADER, null, loaderCallbacks);
    }

    public NewItemFragment.OnNewItemAddedListener getListener() {
        return listener;
    }
}
