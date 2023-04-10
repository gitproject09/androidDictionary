package com.sopan.mydictionary.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sopan.mydictionary.R;

public class MeaningsViewHolder extends RecyclerView.ViewHolder {
    public TextView partsOfSpeech;
    public RecyclerView recycler_definitions;

    public MeaningsViewHolder(@NonNull View itemView) {
        super(itemView);

        recycler_definitions = itemView.findViewById(R.id.recycler_definitions);
        partsOfSpeech = itemView.findViewById(R.id.partsOfSpeech);
    }
}
