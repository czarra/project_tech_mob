package com.example.rad.test.feature.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.example.rad.test.feature.R;
import com.example.rad.test.feature.fragments.CategoryFragment;
import com.example.rad.test.feature.fragments.StartFragment;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loadStartFragment();
       // progressBar= (ProgressBar) findViewById(R.id.progressBar);
        //progressBar.setVisibility(View.VISIBLE);

       // FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
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
        loadFragment(CategoryFragment.newInstance());
    }
    private void loadStartFragment() {
        loadFragment(StartFragment.newInstance());
    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, fragment);
        transaction.commit();
       /* if(titleResourceId==R.string.menu_contact_label){
            CategoryFragment fragmentcontact= (CategoryFragment)fragment;
            fragmentcontact.addUs();
        }
*/

      //  toolbar_title.setText(getResources().getString(titleResourceId));
        //@mipmap/align_eft_3x
    }
}

