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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rad.test.feature.R;
import com.example.rad.test.feature.activity.CategoryActivity;
import com.example.rad.test.feature.api.ApiClient;
import com.example.rad.test.feature.data.Category;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rad on 2017-11-06.
 */

public class CategoryFilterFragment extends Fragment {

    private OnFragmentInteractionListener listener;
    private MyCategorySingleRecyclerViewAdapter adapter;
    private String newCategory;
    private String newCategoryName;
    private Boolean top;
    public static CategoryFilterFragment newInstance(String newCategory, Boolean top, String newCategoryName) {
        CategoryFilterFragment fragment = new CategoryFilterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.newCategory = newCategory;
        fragment.top = top;
        fragment.newCategoryName = newCategoryName;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_filters, container, false);
        Context context = view.getContext();
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.listFilter);
        final TextView categoryNameGeneral = (TextView) view.findViewById(R.id.categoryNameGeneral);
        final Button buttonBack = (Button) view.findViewById(R.id.buttonBack);
        final Button buttonProducts = (Button) view.findViewById(R.id.buttonProducts);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setSmoothScrollbarEnabled(true);
        final ProgressBar progressBar1 = (ProgressBar) view.findViewById(R.id.progressBar1);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (getActivity() != null) {
                        ((CategoryActivity)getActivity()).callBackCategory(newCategory, newCategoryName);
                    }
                } catch (Exception e) {
                   Log.e("activite Filter", e.getMessage());
                }
            }
        });
        buttonProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (getActivity() != null) {
                        ((CategoryActivity)getActivity()).gotoProducts(newCategory);
                    }
                } catch (Exception e) {
                    Log.e("activite Filter", e.getMessage());
                }
            }
        });
        listener = (OnFragmentInteractionListener) context;
        adapter = new MyCategorySingleRecyclerViewAdapter(new ArrayList<Category>(),listener);
        RetrieveArticlesTask retrieveCategoryTask = new RetrieveArticlesTask() {
            @Override
            protected void onPreExecute() {
                progressBar1.setVisibility(View.VISIBLE);
                categoryNameGeneral.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                buttonBack.setVisibility(View.GONE);
                buttonProducts.setVisibility(View.GONE);
            }

            @Override
            protected void onPostExecute(List<Category> list) {
                adapter.articles.clear();
                for (int i = 0; i < list.size(); i++) {
                    adapter.articles.add(list.get(i));
                }
                categoryNameGeneral.setText(newCategoryName.replace("-"," ").toUpperCase());
                adapter.notifyDataSetChanged();
                progressBar1.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                categoryNameGeneral.setVisibility(View.VISIBLE);
                if(!(newCategory.equalsIgnoreCase("katalog"))){
                    buttonBack.setVisibility(View.VISIBLE);
                }
                buttonProducts.setVisibility(View.VISIBLE);
            }
        };
        retrieveCategoryTask.execute();
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
        void onFragmentInteraction(Category item);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    class RetrieveArticlesTask extends AsyncTask<String, String, List<Category>> {

        private final ApiClient client = ApiClient.getInstance();
        List<Category> list = new ArrayList<>();
        @Override
        protected List<Category> doInBackground(String... urls) {
            String url = "https://api.zalando.com/categories";
            if(top) {
                url += "?key=" + newCategory;
            } else {
                url += "?childKey=" + newCategory;
            }
            try {

                String jsonResponse = client.getURL(url, String.class);
                Log.d("jsonResponse", jsonResponse);
                JSONObject jsonObject = new JSONObject(jsonResponse);
                JSONArray articles = jsonObject.getJSONArray("content");
                for (int i = 0; i < articles.length(); i++) {
                    JSONObject articlesObject = articles.getJSONObject(i);
                    JSONArray category = articlesObject.getJSONArray("childKeys");
                    if(!top) {
                        newCategory = articlesObject.getString("key");
                        newCategoryName = articlesObject.getString("name");
                    }
                    for (int j = 0; j < category.length(); j++) {
                        if(!category.getString(j).isEmpty()) {
                            String url2 = "https://api.zalando.com/categories?key=" + category.getString(j);
                            String jsonResponse2 = client.getURL(url2, String.class);
                            JSONObject jsonObject2 = new JSONObject(jsonResponse2);
                            JSONArray articles2 = jsonObject2.getJSONArray("content");
                            for (int k = 0; k < articles2.length(); k++) {
                                JSONObject articlesObject2 = articles2.getJSONObject(i);
                                Category item = Category.fromJsonObject(articlesObject2);
                                if (item != null && !articlesObject2.getString("hidden").equalsIgnoreCase("true")) {
                                    list.add(item);
                                }
                            }

                        }
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
