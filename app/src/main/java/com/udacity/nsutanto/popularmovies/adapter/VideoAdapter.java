package com.udacity.nsutanto.popularmovies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.udacity.nsutanto.popularmovies.R;
import com.udacity.nsutanto.popularmovies.listener.ITaskVideoListener;
import com.udacity.nsutanto.popularmovies.model.Video;

import java.util.ArrayList;
import java.util.List;


public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private List<Video> mVideos = new ArrayList<>();
    private static ITaskVideoListener mTaskListener;
    private Context context;

    public VideoAdapter(ITaskVideoListener taskListener) {

        mTaskListener = taskListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView iv_Thumbnail;

        public ViewHolder(View v) {
            super(v);
            iv_Thumbnail = v.findViewById(R.id.iv_video);
            iv_Thumbnail.setOnClickListener(this);
        }

        public void bind(Video video) {
            Picasso.get()
                    .load(video.getYouTubeThumbnailURL())
                    .into(iv_Thumbnail);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Video trailer = mVideos.get(position);
            mTaskListener.PlayVideo(trailer);
        }
    }


    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.video_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Video video = mVideos.get(position);
        holder.bind(video);
    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }

    public void setVideos(List<Video> videos) {
        mVideos = videos;
        notifyDataSetChanged();
    }
}