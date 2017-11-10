package com.example.rad.test.feature.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rad.test.feature.R;
import com.example.rad.test.feature.data.Category;

import java.util.List;

/**
 * Created by Rad on 2017-11-06.
 */

public class MyCategorySingleRecyclerViewAdapter extends RecyclerView.Adapter<MyCategorySingleRecyclerViewAdapter.ViewHolder> {

    public final List<Category> articles;
    private final CategoryFilterFragment.OnFragmentInteractionListener listener;
    private Context context;

    public MyCategorySingleRecyclerViewAdapter(List<Category> articles,
                                               CategoryFilterFragment.OnFragmentInteractionListener listener) {
        this.articles = articles;
        this.listener = listener;
    }

    @Override
    public MyCategorySingleRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_filter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.text.setText(articles.get(position).name);

        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("before","kkkk");
                if (null != listener) {
                    Log.e("after","kkkk");
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    listener.onFragmentInteraction(articles.get(position));
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
        final TextView text;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            text = (TextView) view.findViewById(R.id.categoryName);
        }
    }
}
