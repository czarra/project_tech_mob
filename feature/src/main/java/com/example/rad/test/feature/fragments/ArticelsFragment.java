package com.example.rad.test.feature.fragments;

import android.content.Context;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rad.test.feature.R;
import com.example.rad.test.feature.activity.ArticleFilterActivity;
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
    private RetrieveArticlesTask retrieveArticleTask;
    RecyclerView recyclerView;
    private String key;
    private EditText priceFrom;
    private EditText priceTo;
    private CheckBox sale;
    private Button buttonBack;
    private Button buttonNext;
    private String selectedSpinner;
    private Context context;
    private Button runFilter;
    private ProgressBar progressBar1;
    private Boolean showToast = false;
    private int page=1;
    private int totalPages=0;

    public static ArticelsFragment newInstance(String key) {
        ArticelsFragment fragment = new ArticelsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.key = key;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_articles, container, false);
        context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        sale = (CheckBox)  view.findViewById(R.id.CheckBoxButton);
        priceFrom = (EditText) view.findViewById(R.id.priceprice1);
        priceTo  = (EditText) view.findViewById(R.id.priceprice2);
        runFilter = (Button) view.findViewById(R.id.buttonFilter);
        buttonNext = (Button) view.findViewById(R.id.buttonNext);
        buttonBack = (Button) view.findViewById(R.id.buttonBack);
        layoutManager.setSmoothScrollbarEnabled(true);
        listener = (OnFragmentInteractionListener) context;
        progressBar1 = (ProgressBar) view.findViewById(R.id.progressBar1);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

           @Override
           public void onItemSelected(AdapterView<?> parent, View view,
                                      int position, long id) {
               Log.e("position", position+"  aa");
               switch (position) {
                   case 0:
                       selectedSpinner = "";
                       break;
                   case 1:
                       selectedSpinner = "popularity";
                       break;
                   case 2:
                       selectedSpinner = "priceasc";
                       break;
                   case 3:
                       selectedSpinner = "pricedesc";
                       break;
                   case 4:
                       selectedSpinner = "Wyprzedaż";
                       break;

                   default:
                       selectedSpinner="";

               }

           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {
               // TODO Auto-generated method stub
               selectedSpinner="";
           }
       });
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("");
        categories.add("Popularność");//popularity
        categories.add("Cena rosnąco");//pricedesc
        categories.add("Cena malejąco");//priceasc
        categories.add("Wyprzedaż");//sale
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,R.layout.simple_spinner_item, categories);


        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


        adapter = new MyArticlesRecyclerViewAdapter(new ArrayList<Articles>(), listener);


        retrieveArticleTask = new RetrieveArticlesTask() {
            @Override
            protected void onPreExecute() {
                progressBar1.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                buttonBack.setVisibility(View.INVISIBLE);
                buttonNext.setVisibility(View.INVISIBLE);
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
                if(totalPages>page){
                    buttonNext.setVisibility(View.VISIBLE);
                }
            }
        };
        retrieveArticleTask.execute();
        recyclerView.setAdapter(adapter);

        buttonBack.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  page--;
                  retrieveArticleTask = forButtons();
                  retrieveArticleTask.execute();
                  recyclerView.scrollToPosition(0);
              }
          });
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page++;
                retrieveArticleTask = forButtons();
                retrieveArticleTask.execute();
                recyclerView.scrollToPosition(0);
            }
        });

        runFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                retrieveArticleTask = forButtons();
                retrieveArticleTask.execute();
                recyclerView.scrollToPosition(0);
            }

        });
        //recyclerView.setNestedScrollingEnabled(false);
        return view;
    }

    private RetrieveArticlesTask forButtons(){
        RetrieveArticlesTask local =  new RetrieveArticlesTask() {
            @Override
            protected void onPreExecute() {
                progressBar1.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                buttonBack.setVisibility(View.INVISIBLE);
                buttonNext.setVisibility(View.INVISIBLE);
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
                if(totalPages>page){
                    buttonNext.setVisibility(View.VISIBLE);
                }
                if(page>1){
                    buttonBack.setVisibility(View.VISIBLE);
                }
                if (showToast){
                    CharSequence text = "Proszę podać drugą kwotę!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    showToast = false;
                }
            }
        };

        return local;
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
            url += "?pageSize=50&page="+page;
            if(key != null && !key.trim().isEmpty()){
                url += "&category="+key;
            }
            if(selectedSpinner != null && !selectedSpinner.trim().isEmpty()){
                url += "&sort="+selectedSpinner;
            }
            if(sale.isChecked()){
                url += "&sale=sale";
            }
            if(!priceFrom.getText().toString().trim().isEmpty() && !priceTo.getText().toString().trim().isEmpty()){
                url += "&price="+priceFrom.getText()+"-"+priceTo.getText();
            } else if((!priceFrom.getText().toString().trim().isEmpty() && priceTo.getText().toString().trim().isEmpty()) ||
                    (priceFrom.getText().toString().trim().isEmpty() && !priceTo.getText().toString().trim().isEmpty())) {
                showToast = true;
            }

            try {
                Log.d("url",url);
                String jsonResponse = client.getURL(url, String.class);
                Log.d("jsonResponse",jsonResponse);
                JSONObject jsonObject = new JSONObject(jsonResponse);
                JSONArray articles = jsonObject.getJSONArray("content");
                Log.d("page",  jsonObject.getString("page"));
                page =  stringToInt(jsonObject.getString("page"));
                Log.d("size",  jsonObject.getString("size"));
                totalPages =  stringToInt(jsonObject.getString("totalPages"));
                Log.d("totalPages",  jsonObject.getString("totalPages"));

                for (int i = 0; i < articles.length(); i++) {
                    JSONObject articlesObject = articles.getJSONObject(i);
                    Articles item = Articles.fromJsonObject(articlesObject);

                    if(item!=null && item.available.equalsIgnoreCase("true")) {
                        list.add(item);
                    }
                }
                    return list;
            } catch (Exception exp) {
                Log.e("Articles error",exp.getMessage());
            }
            return new ArrayList<>();
        }

        public int stringToInt(String text){
            try {
                return Integer.parseInt(text);
            }
            catch (NumberFormatException  e){
                return 0;
            }
        }

    }

}
