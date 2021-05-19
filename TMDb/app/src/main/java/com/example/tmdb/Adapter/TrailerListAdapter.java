package com.example.tmdb.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tmdb.Model.VideoResultModel;
import com.example.tmdb.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TrailerListAdapter extends RecyclerView.Adapter<TrailerListAdapter.ViewHolder> {
    List<VideoResultModel> videoResultModels;
    Context context;

    public TrailerListAdapter(List<VideoResultModel> videoResultModels, Context context) {
        this.videoResultModels = videoResultModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.trailer_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VideoResultModel videoResultModel = videoResultModels.get(position);
        final Boolean[] check = {true};
        holder.textView.setText(videoResultModel.getName());
        holder.youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {

            @Override
            public void onVideoId(@NotNull YouTubePlayer youTubePlayer, @NotNull String videoId) {
                super.onVideoId(youTubePlayer, videoId);
                if (check[0]) {
                    youTubePlayer.cueVideo(videoResultModel.getKey(), 0);
                    check[0] = false;
                }
            }

            @Override
            public void onError(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlayerError error) {
                super.onError(youTubePlayer, error);
            }
        });


    }

    @Override
    public int getItemCount() {
        return videoResultModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public YouTubePlayerView youTubePlayerView;
        public TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            youTubePlayerView = itemView.findViewById(R.id.ypv_trailer_item);
            textView = itemView.findViewById(R.id.tv_video_title);
        }
    }
}
