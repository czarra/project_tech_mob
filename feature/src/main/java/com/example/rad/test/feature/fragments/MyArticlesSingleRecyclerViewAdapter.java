package com.example.rad.test.feature.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rad.test.feature.R;
import com.example.rad.test.feature.data.Articles;
import com.example.rad.test.feature.data.Articles_picture_single;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Rad on 2017-11-06.
 */

public class MyArticlesSingleRecyclerViewAdapter extends RecyclerView.Adapter<MyArticlesSingleRecyclerViewAdapter.ViewHolder> {

    public final List<Articles_picture_single> articles;
    private final SingleArticeleFragment.OnFragmentInteractionListener listener;
    private Context context;

    public MyArticlesSingleRecyclerViewAdapter(List<Articles_picture_single> articles,
                                               SingleArticeleFragment.OnFragmentInteractionListener listener) {
        this.articles = articles;
        this.listener = listener;
    }

    @Override
    public MyArticlesSingleRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_article_picture, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        final String url_picture = articles.get(position).name;
        Picasso.with(context)
                .load(url_picture)
                .into(holder.viewImage);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Picasso.with(context)
                        .load(url_picture)
                        .into(articles.get(position).img);
                if (null != listener) {

                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                   // listener.onFragmentInteraction(articles.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        final ImageView viewImage;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            viewImage = (ImageView) view.findViewById(R.id.imageSmallArticle);
        }
    }
}
