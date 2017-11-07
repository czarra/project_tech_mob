package com.example.rad.test.feature.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.example.rad.test.feature.R;
import com.example.rad.test.feature.data.Articles;
import com.example.rad.test.feature.fragments.ArticelsFragment;
import com.example.rad.test.feature.fragments.SingleArticeleFragment;
import com.example.rad.test.feature.fragments.StartFragment;

public class MainActivity extends AppCompatActivity implements ArticelsFragment.OnFragmentInteractionListener{
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loadStartFragment();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.category) {
            //progressBar.setVisibility(View.VISIBLE);
            loadAboutFragment();
            return true;
        }
        if (id == R.id.brands) {
            //progressBar.setVisibility(View.GONE);
            loadStartFragment();
            return true;
        }
        if (id == R.id.powitalna) {
            //progressBar.setVisibility(View.GONE);
            loadStartFragment();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadAboutFragment() {
        loadFragment(ArticelsFragment.newInstance());
    }
    private void loadStartFragment() {
        loadFragment(StartFragment.newInstance());
    }
    private void loadSingleArticle(String id){
        loadFragment(SingleArticeleFragment.newInstance(id));
    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, fragment);
        transaction.commit();
       /* if(titleResourceId==R.string.menu_contact_label){
            ArticelsFragment fragmentcontact= (ArticelsFragment)fragment;
            fragmentcontact.addUs();
        }
*/

      //  toolbar_title.setText(getResources().getString(titleResourceId));
        //@mipmap/align_eft_3x
    }

    @Override
    public void onFragmentInteraction(Articles articles) {
        loadSingleArticle(articles.id);
    }
}

