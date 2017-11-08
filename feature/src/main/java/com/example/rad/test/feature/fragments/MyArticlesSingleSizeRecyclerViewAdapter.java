package com.example.rad.test.feature.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rad.test.feature.R;
import com.example.rad.test.feature.data.Articles_picture_single;
import com.example.rad.test.feature.data.Articles_size_single;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Rad on 2017-11-06.
 */

public class MyArticlesSingleSizeRecyclerViewAdapter extends RecyclerView.Adapter<MyArticlesSingleSizeRecyclerViewAdapter.ViewHolder> {

    public final List<Articles_size_single> articles;
    private final SingleArticeleFragment.OnFragmentInteractionListener listener;
    private Context context;

    public MyArticlesSingleSizeRecyclerViewAdapter(List<Articles_size_single> articles,
                                                   SingleArticeleFragment.OnFragmentInteractionListener listener) {
        this.articles = articles;
        this.listener = listener;
    }

    @Override
    public MyArticlesSingleSizeRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_article_size, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.textButton.setText(articles.get(position).size);

        holder.textButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                articles.get(position).text.setText(articles.get(position).price + "   Rozmiar:"+articles.get(position).size);
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
        final Button textButton;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            textButton = (Button) view.findViewById(R.id.buttonSize);
        }
    }
}
