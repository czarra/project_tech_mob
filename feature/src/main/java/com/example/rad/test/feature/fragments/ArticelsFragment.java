package com.example.rad.test.feature.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.rad.test.feature.R;
import com.example.rad.test.feature.api.ApiClient;
import com.example.rad.test.feature.data.Articles;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rad on 2017-11-06.
 */

public class ArticelsFragment extends Fragment {

    private OnFragmentInteractionListener listener;
    private MyArticlesRecyclerViewAdapter adapter;

    public static ArticelsFragment newInstance() {
        ArticelsFragment fragment = new ArticelsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_articles, container, false);
        Context context = view.getContext();
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setSmoothScrollbarEnabled(true);
        listener = (OnFragmentInteractionListener) context;
        final ProgressBar progressBar1 = (ProgressBar) view.findViewById(R.id.progressBar1);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new MyArticlesRecyclerViewAdapter(new ArrayList<Articles>(), listener);
        RetrieveArticlesTask retrieveSpeakerTask = new RetrieveArticlesTask() {
            @Override
            protected void onPreExecute() {
                progressBar1.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }

            @Override
            protected void onPostExecute(List<Articles> list) {
                adapter.articles.clear();
                for (int i = 0; i < list.size(); i++) {
                    adapter.articles.add(list.get(i));
                }
                adapter.notifyDataSetChanged();
                progressBar1.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        };
        retrieveSpeakerTask.execute();
        recyclerView.setAdapter(adapter);
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (OnFragmentInteractionListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    class RetrieveArticlesTask extends AsyncTask<String, String, List<Articles>> {

        private final ApiClient client = ApiClient.getInstance();
        List<Articles> list = new ArrayList<>();
        @Override
        protected List<Articles> doInBackground(String... urls) {
            String url = "https://api.zalando.com/articles";
            url += "?pageSize=100";
            try {
                String jsonResponse = client.getURL(url, String.class);

                JSONObject jsonObject = new JSONObject(jsonResponse);
                JSONArray articles = jsonObject.getJSONArray("content");
                for (int i = 0; i < articles.length(); i++) {
                    JSONObject articlesObject = articles.getJSONObject(i);
                    Articles item = Articles.fromJsonObject(articlesObject);
                    if(item!=null) {
                        Log.d("sriiir",item.toString());
                        list.add(item);
                    }
                }
                    return list;
            } catch (Exception exp) {
                Log.e("Articles error",exp.getMessage());
            }
            return new ArrayList<>();
        }
    }

}
