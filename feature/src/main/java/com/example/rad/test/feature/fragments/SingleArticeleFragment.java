package com.example.rad.test.feature.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.rad.test.feature.R;
import com.example.rad.test.feature.api.ApiClient;
import com.example.rad.test.feature.data.Articles;
import com.example.rad.test.feature.data.Articles_picture_single;
import com.example.rad.test.feature.data.Articles_size_single;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rad on 2017-11-06.
 */

public class SingleArticeleFragment extends Fragment {

    private OnFragmentInteractionListener listener;
    private MyArticlesSingleRecyclerViewAdapter adapter;
    private MyArticlesSingleSizeRecyclerViewAdapter adapterSize;
    private String id_art;
    public static SingleArticeleFragment newInstance(String id) {
        SingleArticeleFragment fragment = new SingleArticeleFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.id_art = id;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_article_single, container, false);
        final TextView textNameArtcle = (TextView) view.findViewById(R.id.textNameArtcle);
        final Button buttonShop = (Button) view.findViewById(R.id.buttonShop);
        final TextView textPrice = (TextView) view.findViewById(R.id.textPrice);
        final ImageView image = (ImageView) view.findViewById(R.id.imageSingleArticle);
        final ImageView imageBrand = (ImageView) view.findViewById(R.id.imageViewBrand);
        final LinearLayout liner = (LinearLayout) view.findViewById(R.id.linerhide);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.listArticlesPicture);
        final RecyclerView recyclerViewSize = (RecyclerView) view.findViewById(R.id.listArticlesSize);
        final Context context = view.getContext();
        final ProgressBar progressBar1 = (ProgressBar) view.findViewById(R.id.progressBarShow);
        listener = (OnFragmentInteractionListener) context;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false);
        GridLayoutManager gridLayoutManagerSize = new GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false);
        recyclerViewSize.setLayoutManager(gridLayoutManagerSize);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new MyArticlesSingleRecyclerViewAdapter( new ArrayList<Articles_picture_single>(),listener);
        adapterSize = new MyArticlesSingleSizeRecyclerViewAdapter( new ArrayList<Articles_size_single>(),listener);
        RetrieveArticleSingleTask retrieveArticleSingleTask = new RetrieveArticleSingleTask() {
            @Override
            protected void onPreExecute() {
                liner.setVisibility(View.GONE);
                imageBrand.setVisibility(View.GONE);
                progressBar1.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(final Articles article) {
                String url_picture = null;
                String show_pricee = null;
                adapter.articles.clear();
                adapterSize.articles.clear();
                try {
                    for (int i = 0; i < article.url.length(); i++) {
                        JSONObject articlesObject = article.url.getJSONObject(i);
                        if(articlesObject.getString("mediumHdUrl")!=null){
                            Articles_picture_single item = Articles_picture_single.fromJsonObject(articlesObject, image);
                            adapter.articles.add(item);
                        }
                        if( articlesObject.getString("mediumHdUrl")!=null && url_picture==null) {
                            url_picture =  articlesObject.getString("mediumHdUrl");

                        }
                    }
                    for (int i = 0; i < article.units.length(); i++) {
                        JSONObject articlesObject = article.units.getJSONObject(i);
                        Articles_size_single item2 = Articles_size_single.fromJsonObject(articlesObject, textPrice);
                        adapterSize.articles.add(item2);
                        if(show_pricee == null) {
                            show_pricee = item2.price + "   Rozmiar:" + item2.size;

                        }
                    }
                }catch (Exception e){
                    Log.e("ArticleAdapter", e.getMessage());
                }
                textNameArtcle.setText(article.name );
                Picasso.with(context)
                        .load(url_picture)
                        .into(image);

                Picasso.with(context)
                        .load(article.logo)
                        .into(imageBrand);
                buttonShop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = article.shopUrl;
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                });
                textPrice.setText(show_pricee);
                adapter.notifyDataSetChanged();
                adapterSize.notifyDataSetChanged();
                progressBar1.setVisibility(View.GONE);
                liner.setVisibility(View.VISIBLE);
                imageBrand.setVisibility(View.VISIBLE);
                }

        };
        retrieveArticleSingleTask.execute();
        recyclerView.setAdapter(adapter);
        recyclerViewSize.setAdapter(adapterSize);
        return view;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Articles_picture_single item);
    }



    class RetrieveArticleSingleTask extends AsyncTask<String, String, Articles> {

        private final ApiClient client = ApiClient.getInstance();

        @Override
        protected Articles doInBackground(String... urls) {
            try {
                String responseStr = client.getURL("https://api.zalando.com/articles/"+id_art, String.class);
                JSONObject jsonObject = new JSONObject(responseStr);
                Articles item = Articles.fromJsonObject(jsonObject);
                Log.d("Single article",responseStr);
                return item;
            } catch (Exception exp) {
                Log.e("Articles error",exp.getMessage());
            }
            return new Articles();
        }
    }

}
