package com.sopan.mydictionary.Bangla.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.sopan.mydictionary.Bangla.activity.BanglaDictionaryActivity;
import com.sopan.mydictionary.Bangla.db.DictionaryDB;
import com.sopan.mydictionary.Bangla.model.Word;
import com.sopan.mydictionary.Models.Adapters.DefinitionAdapter;
import com.sopan.mydictionary.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordsViewHolder> {

	private List<Word> wordList;
	private Activity context;
	private LayoutInflater mLayoutInflater;
	private DictionaryDB dictionaryDB;
	
	public WordListAdapter(Activity context, DictionaryDB dictionaryDB) {
		this.context = context;
		this.dictionaryDB = dictionaryDB;
		mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		wordList = new ArrayList<Word>();
	}

	/*public int getCount() {
		return wordList.size();
	}

	public Object getItem(int position) {
		return wordList.get(position);
	}*/

	@NonNull
	@Override
	public WordsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new WordsViewHolder(LayoutInflater.from(context).inflate(R.layout.word, parent, false));
	}

	@Override
	public void onBindViewHolder(@NonNull WordsViewHolder holder, int position) {
		final Word word = wordList.get(position);
		holder.bangla.setTypeface(Typeface.createFromAsset(context.getAssets(), BanglaDictionaryActivity.FONT));
		holder.english.setText(word.english);
		holder.bangla.setText(word.bangla);

		if(word.status != null && word.status.equals(DictionaryDB.BOOKMARKED)) {
			holder.bookmark.setImageResource(R.drawable.bookmarked);
		}
		else {
			holder.bookmark.setImageResource(R.drawable.not_bookmarked);
		}

		holder.bookmark.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				bookMarkWord(word, holder.bookmark);
			}
		});
	}


	public Object getItem(int position) {
		return wordList.get(position);
	}

	@Override
	public int getItemCount() {
		return wordList.size();
	}

	/*public View getView(int position, View convertView, ViewGroup parent) {
		final Word word = wordList.get(position);
		View view = convertView;
		if (view == null) {
			view = mLayoutInflater.inflate(R.layout.word, null);
		}
		TextView english = view.findViewById(R.id.english);
		TextView bangla = view.findViewById(R.id.bangla);
		final ImageButton bookmark = view.findViewById(R.id.bookmark);

		bangla.setTypeface(Typeface.createFromAsset(context.getAssets(), BanglaDictionaryActivity.FONT));
		english.setText(word.english);
		bangla.setText(word.bangla);
		
		if(word.status != null && word.status.equals(DictionaryDB.BOOKMARKED)) {
			bookmark.setImageResource(R.drawable.bookmarked);
		}
		else {
			bookmark.setImageResource(R.drawable.not_bookmarked);
		}
		
		bookmark.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				bookMarkWord(word, bookmark);
			}
		});
		
		return view;
	}*/

	private void bookMarkWord(final Word word, final ImageButton bookmark) {
		if (word.status != null && word.status.equals(DictionaryDB.BOOKMARKED)) {
			dictionaryDB.deleteBookmark(word.id);
			word.status = "";
			bookmark.setImageResource(R.drawable.not_bookmarked);
			Toast.makeText(context, "Bookmark Deleted", Toast.LENGTH_SHORT).show();
		}
		else {
			dictionaryDB.bookmark(word.id);
			word.status = DictionaryDB.BOOKMARKED;
			bookmark.setImageResource(R.drawable.bookmarked);
			Toast.makeText(context, "Bookmark Added", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void updateEntries(List<Word> wordList) {
		if (wordList == null) {
			AlertDialog dialog = new AlertDialog.Builder(context)
				.setTitle("Sorry!")
				.setMessage("Your phone doesn't support pre-built database")
				.setNeutralButton("Exit", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						context.finish();
					}
				})
				.create();
			dialog.show();
		} else {
			this.wordList = wordList;
			notifyDataSetChanged();
		}
	}

	static class WordsViewHolder extends RecyclerView.ViewHolder {
		TextView english;
		TextView bangla;
		final ImageButton bookmark;

		public WordsViewHolder(@NonNull View itemView) {
			super(itemView);

			english = itemView.findViewById(R.id.english);
			bangla = itemView.findViewById(R.id.bangla);
			bookmark = itemView.findViewById(R.id.bookmark);
		}
	}
}
