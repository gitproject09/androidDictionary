package com.sopan.mydictionary.Bangla.activity;

import android.os.Bundle;
import android.view.MenuItem;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

/**
  This is just a place holder
*/
public class AboutActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
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
