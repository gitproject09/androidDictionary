package com.sopan.mydictionary.Bangla.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sopan.mydictionary.Bangla.adapter.WordListAdapter;
import com.sopan.mydictionary.Bangla.db.DataLoader;
import com.sopan.mydictionary.Bangla.db.DatabaseInitializer;
import com.sopan.mydictionary.Bangla.db.DictionaryDB;
import com.sopan.mydictionary.R;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BanglaDictionaryActivity extends AppCompatActivity {
    private EditText input;
    private TextView empty;
    private RecyclerView recyclerView;

    private DictionaryDB dictionaryDB;
    private WordListAdapter adapter;

    public static final String FONT = "SolaimanLipi.ttf";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bn_dictionary);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        DatabaseInitializer initializer = new DatabaseInitializer(getBaseContext());
        initializer.initializeDataBase();
        dictionaryDB = new DictionaryDB(initializer);

        input = findViewById(R.id.input);
        empty = findViewById(android.R.id.empty);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.d("Dictionary Data", dictionaryDB.getWords("fox").toString());

        adapter = new WordListAdapter(this, dictionaryDB);
        recyclerView.setAdapter(adapter);
        input.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadData(input.getText().toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void loadData(String word) {
        DataLoader loader = new DataLoader(dictionaryDB, adapter);
        loader.execute(word);
        if (empty != null) {
            if (word.equals(""))
                empty.setText("");
            else
                empty.setText("No match found");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (input != null) {
            loadData(input.getText().toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.bookmarked_words) {
            Intent intent = new Intent(this, BookMarkedWordsActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.add_new) {
            showInputDialog();
        } else if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showInputDialog() {
        LayoutInflater factory = LayoutInflater.from(this);

        final View addNew = factory.inflate(R.layout.add_new, null);

        final EditText english = (EditText) addNew.findViewById(R.id.english_input);
        final EditText bangla = (EditText) addNew.findViewById(R.id.Bangla_input);

        bangla.setTypeface(Typeface.createFromAsset(getAssets(), BanglaDictionaryActivity.FONT));

        final AlertDialog.Builder newWordInputDialog = new AlertDialog.Builder(this);
        newWordInputDialog
                .setTitle("Add a new word")
                .setView(addNew)
                .setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {

                                addNewWord(english, bangla);
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                            }
                        });
        newWordInputDialog.show();
    }

    private void addNewWord(final EditText english, final EditText bangla) {
        String englishWord = english.getText().toString();
        String banglaWord = bangla.getText().toString();
        if ((englishWord.equals("") || banglaWord.equals("")))
            Toast.makeText(this, "Field can't be blank",
                    Toast.LENGTH_SHORT).show();
        else {
            dictionaryDB.addWord(englishWord, banglaWord);

            Toast.makeText(this, "Word Added to the Dictionary",
                    Toast.LENGTH_SHORT).show();
        }
    }

}