package com.example.rad.test.feature.fragments;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.rad.test.feature.R;
import com.example.rad.test.feature.api.ApiClient;
import com.example.rad.test.feature.data.Articles;
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
    private MyArticlesRecyclerViewAdapter adapter;
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
        final TextView textFirstNameSpeaker = (TextView) view.findViewById(R.id.textFirstNameSpeaker);
        final TextView textFunctionCompanyName = (TextView) view.findViewById(R.id.textFunctionCompanyName);
        final TextView textDescriptionSpeaker = (TextView) view.findViewById(R.id.textDescriptionSpeaker);
        final ImageView image = (ImageView) view.findViewById(R.id.imageViewSpeakerDesc);
        final TextView textReadMoreSpeaker = (TextView) view.findViewById(R.id.textReadMoreSpeaker);
        final LinearLayout liner = (LinearLayout) view.findViewById(R.id.linerhide);

        final Context context = view.getContext();
        final ProgressBar progressBar1 = (ProgressBar) view.findViewById(R.id.progressBarShow);


        RetrieveArticleSingleTask retrieveArticleSingleTask = new RetrieveArticleSingleTask() {
            @Override
            protected void onPreExecute() {
                liner.setVisibility(View.GONE);
                progressBar1.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(Articles article) {
                String url_picture = null;
                try {
                    for (int i = 0; i < article.url.length(); i++) {
                        JSONObject articlesObject = article.url.getJSONObject(i);
                        if( articlesObject.getString("thumbnailHdUrl")!=null && url_picture==null) {
                            url_picture =  articlesObject.getString("thumbnailHdUrl");
                        }
                    }
                }catch (Exception e){
                    Log.e("ArticleAdapter", e.getMessage());
                }
                textFirstNameSpeaker.setText(article.name );
                Picasso.with(context)
                        .load(url_picture)
                        .into(image);
                progressBar1.setVisibility(View.GONE);
                liner.setVisibility(View.VISIBLE);
                }

        };
        retrieveArticleSingleTask.execute();
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
        void onFragmentInteraction(Articles item);
    }



    class RetrieveArticleSingleTask extends AsyncTask<String, String, Articles> {

        private final ApiClient client = ApiClient.getInstance();

        @Override
        protected Articles doInBackground(String... urls) {
            try {
                String responseStr = client.getURL("https://api.zalando.com/articles/"+id_art, String.class);
                JSONObject jsonObject = new JSONObject(responseStr);
                Articles item = Articles.fromJsonObject(jsonObject);
                return item;
            } catch (Exception exp) {
                Log.e("Articles error",exp.getMessage());
            }
            return new Articles();
        }
    }

}
