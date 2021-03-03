package com.example.tmdb.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.example.tmdb.Activity.GenreActivity;
import com.example.tmdb.Model.GenreModel;
import com.example.tmdb.R;

import java.util.List;

public class GenreListAdapter extends BaseAdapter {
    private List<GenreModel> genreModels;
    private Context context;

    public GenreListAdapter(List<GenreModel> genreModels, Context context) {
        this.genreModels = genreModels;
        this.context = context;
    }

    @Override
    public int getCount() {
        return genreModels.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.genre_item, null);

        Button btnGenre = view.findViewById(R.id.btn_genre);
        btnGenre.setText(genreModels.get(position).getName());

        btnGenre.setOnClickListener(v -> {
            Intent intent = new Intent(parent.getContext(), GenreActivity.class);
            GenreModel genreModel = new GenreModel();
            genreModel.setId(genreModels.get(position).getId());
            genreModel.setName(genreModels.get(position).getName());

            intent.putExtra(GenreActivity.EXTRA_GENRE, genreModel);
            parent.getContext().startActivity(intent);
        });

        return view;
    }
}
