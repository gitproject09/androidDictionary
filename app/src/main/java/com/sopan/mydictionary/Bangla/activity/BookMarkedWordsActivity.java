package com.sopan.mydictionary.Bangla.activity;

import java.util.List;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sopan.mydictionary.Bangla.adapter.WordListAdapter;
import com.sopan.mydictionary.Bangla.db.DatabaseInitializer;
import com.sopan.mydictionary.Bangla.db.DictionaryDB;
import com.sopan.mydictionary.Bangla.model.Word;
import com.sopan.mydictionary.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BookMarkedWordsActivity extends AppCompatActivity {
    private TextView empty;
    private DictionaryDB dictionaryDB;
    private WordListAdapter adapter;
    private RecyclerView rvBookmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmarked);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvBookmark = findViewById(R.id.rvBookmark);
        empty = findViewById(android.R.id.empty);

        DatabaseInitializer initializer = new DatabaseInitializer(getBaseContext());
        dictionaryDB = new DictionaryDB(initializer);
        rvBookmark.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WordListAdapter(this, dictionaryDB);
        rvBookmark.setAdapter(adapter);

        List<Word> bookmarkedWords = dictionaryDB.getBookmarkedWords();
        if (bookmarkedWords.size() == 0) {
            empty.setText("No Bookmarked Word");
        } else {
            empty.setVisibility(View.GONE);
        }
        adapter.updateEntries(bookmarkedWords);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
