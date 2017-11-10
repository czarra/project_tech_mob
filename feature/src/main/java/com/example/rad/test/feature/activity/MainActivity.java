package com.example.rad.test.feature.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.example.rad.test.feature.R;
import com.example.rad.test.feature.data.Articles;
import com.example.rad.test.feature.data.Articles_picture_single;
import com.example.rad.test.feature.fragments.ArticelsFragment;
import com.example.rad.test.feature.fragments.SingleArticeleFragment;
import com.example.rad.test.feature.fragments.StartFragment;

public class MainActivity extends AppCompatActivity implements SingleArticeleFragment.OnFragmentInteractionListener{
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


        if (id == R.id.powitalna) {
            Intent mIntent = new Intent(MainActivity.this, CategoryActivity.class);
            mIntent.putExtra("category", "katalog");
            startActivity(mIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void loadStartFragment() {
        loadFragment(StartFragment.newInstance());
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, fragment);
        transaction.commit();
    }


    @Override
    public void onFragmentInteraction(Articles_picture_single articles) {

    }
}

