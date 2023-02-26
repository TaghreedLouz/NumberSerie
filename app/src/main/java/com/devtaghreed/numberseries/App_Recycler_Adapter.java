package com.devtaghreed.numberseries;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devtaghreed.numberseries.databinding.RecyclerViewBinding;

import java.util.ArrayList;

public class App_Recycler_Adapter extends RecyclerView.Adapter<App_Recycler_Adapter.App_ViewHolder> {
    ArrayList<Game> games;
    Context context;

    public App_Recycler_Adapter(ArrayList<Game> games, Context context) {
        this.games = games;
        this.context = context;
    }

    @NonNull
    @Override
    public App_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerViewBinding binding = RecyclerViewBinding.inflate(LayoutInflater.from(context));
        return new App_ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull App_ViewHolder holder, int position) {
        Game game = games.get(position);
        holder.binding.tvNameRv.setText(game.getUsername());
        holder.binding.tvScoreRv.setText(String.valueOf(game.getScore()));
        holder.binding.tvDateRv.setText(game.getDate());
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    class App_ViewHolder extends RecyclerView.ViewHolder {
        RecyclerViewBinding binding;
        public App_ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RecyclerViewBinding.bind(itemView);

        }
    }
}
