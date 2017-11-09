package com.example.rad.test.feature.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.rad.test.feature.data.Category;
import com.example.rad.test.feature.fragments.FilterFragment;
import com.example.rad.test.feature.fragments.SingleArticeleFragment;
import android.view.View;
import android.widget.ProgressBar;

import com.example.rad.test.feature.R;

/**
 * Created by Rad on 2017-11-06.
 */

public class ArticelsActivity extends AppCompatActivity implements FilterFragment.OnFragmentInteractionListener {

    private String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Intent intent = getIntent();
        category = intent.getStringExtra("category");
        Log.e("activity",category);
        loadCategoryArticle(category, true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filter_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void loadCategoryArticle(String id, boolean top){
        loadFragment(FilterFragment.newInstance(id, top));
    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_filter_main, fragment);
        transaction.commit();
    }
    @Override
    public void onFragmentInteraction(Category category) {

        loadCategoryArticle(category.key, true);
    }

    public void callBackCategory(String key ){
        loadCategoryArticle(key, false);
    }
    public void gotoProducts(String key ){
       // loadCategoryArticle(key, false);
    }

}
