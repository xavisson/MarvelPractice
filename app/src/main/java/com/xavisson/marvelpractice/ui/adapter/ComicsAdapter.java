package com.xavisson.marvelpractice.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xavisson.marvelpractice.R;
import com.xavisson.marvelpractice.model.Result;

import java.util.List;

/**
 * Created by javidelpalacio on 23/1/18.
 */

public class ComicsAdapter extends RecyclerView.Adapter<ComicsAdapter.MyViewHolder> {

    private static final String LOG_TAG = "ComicsAdapter";
    private Context context;
    private List<Result> comicsList;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public ComicsAdapter(Context context, List<Result> comicsList) {
        this.context = context;
        this.comicsList = comicsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comic_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Result comicItem = comicsList.get(position);
        String imageURL = comicItem.getThumbnail().getPath()
                + "." + comicItem.getThumbnail().getExtension();

        Picasso.with(context).load(imageURL)
                .resize(600,900)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (comicsList != null)
            size = comicsList.size();
        return size;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        private TextView title;

        public MyViewHolder(final View view) {
            super(view);
            image = view.findViewById(R.id.comic_item_thumbnail);
            title = view.findViewById(R.id.comic_item_title);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(view, position);
                        }
                    }
                }
            });
        }
    }
}
