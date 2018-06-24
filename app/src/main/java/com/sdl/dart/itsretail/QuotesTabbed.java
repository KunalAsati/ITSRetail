package com.sdl.dart.itsretail;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class QuotesTabbed extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    PagerAdapter adapter;
    int i=0, quoteCount;
    String RID;
    ArrayList<String> QID;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mQuotesCount=mRootRef.child("quotesCount");
    DatabaseReference mStatusRef=mRootRef.child("status");
    DatabaseReference mRIDRef=mStatusRef.child("RID1");
    DatabaseReference mQuotesRef;
    DatabaseReference mNewQuoteRef;

    String newQuoteList,commodity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes_tabbed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        commodity=getIntent().getStringExtra("commodity");
        mQuotesRef=mRIDRef.child(commodity);


        tabLayout =
                (TabLayout) findViewById(R.id.tab_layout);

       /* tabLayout.addTab(tabLayout.newTab().setIcon(
                android.R.drawable.ic_dialog_email));
        tabLayout.addTab(tabLayout.newTab().setIcon(
                android.R.drawable.ic_dialog_dialer));
        tabLayout.addTab(tabLayout.newTab().setIcon(
                android.R.drawable.ic_dialog_map));
        tabLayout.addTab(tabLayout.newTab().setIcon(
                android.R.drawable.ic_dialog_info));
*/

       viewPager =
                (ViewPager) findViewById(R.id.pager);
        mRIDRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                QID= new ArrayList<>(Arrays.asList(dataSnapshot.child(commodity).getValue().toString().split(",")));
                populate();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        tabLayout.addOnTabSelectedListener(new
                                                   TabLayout.OnTabSelectedListener() {
                                                       @Override
                                                       public void onTabSelected(TabLayout.Tab tab) {
                                                           viewPager.setCurrentItem(tab.getPosition());
                                                       }

                                                       @Override
                                                       public void onTabUnselected(TabLayout.Tab tab) {

                                                       }

                                                       @Override
                                                       public void onTabReselected(TabLayout.Tab tab) {

                                                       }

                                                   });
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tab_layout_demo, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void populate()
    {
        for(int j=0;j<QID.size();j++) {
            tabLayout.addTab(tabLayout.newTab().setText("Quote " + (i + 1)));
            i++;
        }

        adapter = new TabPagerAdapter
                (getSupportFragmentManager(),
                        tabLayout.getTabCount(),QID,commodity);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    protected void fabulous(View v){
        Log.d("xyzr22","this is fabulous");
        if(i==5)
        {
            Toast.makeText(getApplicationContext(), "Sorry! No more useless tabs for you.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        tabLayout.addTab(tabLayout.newTab().setText("Quote "+(i+1)));
        i++;
        createTab();


    }
    public void createTab()
    {
        /*QID.add("QID"+(quoteCount+1));
        Map<String,Float> newQuote=new HashMap<>();


        Quote quote=new Quote(0.0,0,0);

        mNewQuoteRef=mQuotesRef.child("QID"+(quoteCount+1));
        mNewQuoteRef.child("price").setValue(0.0);
        mNewQuoteRef.child("quantity").setValue(0);
        mNewQuoteRef.child("quality").setValue(0);

        mQuotesCount.setValue(new Integer(quoteCount+1));
        Log.d("xyzr22","Updated quoteCount = "+(quoteCount+1));
        newQuoteList=android.text.TextUtils.join(",", QID);
        mQuotesRef.setValue(newQuoteList);*/

        adapter = new TabPagerAdapter
                (getSupportFragmentManager(),
                        tabLayout.getTabCount(),commodity);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

}
