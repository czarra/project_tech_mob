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
import com.example.rad.test.feature.activity.MainActivity;
import com.example.rad.test.feature.data.Articles;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Rad on 2017-11-06.
 */

public class MyArticlesRecyclerViewAdapter extends RecyclerView.Adapter<MyArticlesRecyclerViewAdapter.ViewHolder> {

    public final List<Articles> articles;
    private final ArticelsFragment.OnFragmentInteractionListener listener;
    private Context context;

    public MyArticlesRecyclerViewAdapter(List<Articles> articles,
         ArticelsFragment.OnFragmentInteractionListener listener) {
        this.articles = articles;
        this.listener = listener;
    }

    @Override
    public MyArticlesRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.articles = articles.get(position);
        holder.viewName.setText(articles.get(position).name );

       String url_picture = null;
       try {
           for (int i = 0; i < articles.get(position).url.length(); i++) {
               JSONObject articlesObject = articles.get(position).url.getJSONObject(i);
               if( articlesObject.getString("thumbnailHdUrl")!=null && url_picture==null) {
                   url_picture =  articlesObject.getString("thumbnailHdUrl");
               }
           }
       }catch (Exception e){
           Log.e("ArticleAdapter", e.getMessage());
       }
        Picasso.with(context)
                .load(url_picture)
                .into(holder.viewImage);
        Picasso.with(context)
                .load(articles.get(position).logo)
                .into(holder.viewImageBrand);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("listiii", articles.get(position).toString());
                if (null != listener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    Log.e("list22", articles.get(position).toString());
                    listener.onFragmentInteraction(articles.get(position));
                }
            }
        });
        //Log.e("color", ""+articles.get(position).color);
       // holder.viewImage.setImageURI(url_picture);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        final TextView viewName;
        final ImageView viewImage;
        //final TextView realcolor;
        public Articles articles;
        final ImageView viewImageBrand;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            viewName = (TextView) view.findViewById(R.id.ArticleName);
            viewImage = (ImageView) view.findViewById(R.id.imageView2);
            viewImageBrand = (ImageView) view.findViewById(R.id.imagebrand);

           // realcolor = (TextView) view.findViewById(R.id.realcolor);
        }
    }
}
