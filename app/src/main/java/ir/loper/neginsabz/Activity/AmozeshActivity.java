package ir.loper.neginsabz.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;
import java.util.List;

import ir.loper.neginsabz.Adapter.AmozeshRecyclerAdapter;
import ir.loper.neginsabz.Data.Amozesh;
import ir.loper.neginsabz.Model.AmozeshModel;
import ir.loper.neginsabz.R;
import ir.loper.neginsabz.Service.BackgroundSoundService;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AmozeshActivity extends AppCompatActivity  implements View.OnClickListener {


    private DrawerLayout mDrawerLayout;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab_amozesh, fab2_amozesh, fab3_amozesh;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;

    Intent svc;

    private List<AmozeshModel> itemList = new ArrayList<>();
    private RecyclerView rv_amozesh;
    private AmozeshRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amozesh);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        svc = new Intent(this, BackgroundSoundService.class);
        svc.putExtra(BackgroundSoundService.CMD_NAME, BackgroundSoundService.CMD_PLAY);
        startService(svc);


        fab_amozesh = (FloatingActionButton) findViewById(R.id.fab_amozesh);
        //fab1 = (FloatingActionButton)findViewById(R.id.fab1);
        fab2_amozesh = (FloatingActionButton) findViewById(R.id.fab2_amozesh);
        fab3_amozesh = (FloatingActionButton) findViewById(R.id.fab3_amozesh);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        fab_amozesh.setOnClickListener(this);
        //fab1.setOnClickListener(this);
        fab2_amozesh.setOnClickListener(this);
        fab3_amozesh.setOnClickListener(this);


        rv_amozesh = (RecyclerView) findViewById(R.id.rv_amozesh);
        adapter = new AmozeshRecyclerAdapter(AmozeshActivity.this, itemList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(AmozeshActivity.this);
        rv_amozesh.setLayoutManager(mLayoutManager);
        rv_amozesh.setItemAnimator(new DefaultItemAnimator());
        rv_amozesh.setAdapter(adapter);


        itemList.addAll(new Amozesh().getData());
        adapter.notifyDataSetChanged();


        rv_amozesh.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && fab_amozesh.isShown())
                    fab_amozesh.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    fab_amozesh.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });



    }




    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab_amozesh:

                animateFAB();
                break;
            case R.id.fab2_amozesh:

                fab2_amozesh.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_play));
                svc.putExtra(BackgroundSoundService.CMD_NAME, BackgroundSoundService.CMD_PAUSE);
                startService(svc);
                fab_amozesh.performClick();
                break;
            case R.id.fab3_amozesh:

                fab2_amozesh.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_pause));
                svc.putExtra(BackgroundSoundService.CMD_NAME, BackgroundSoundService.CMD_PLAY);
                startService(svc);
                fab_amozesh.performClick();
                break;
        }
    }


    public void animateFAB() {

        if (isFabOpen) {

            fab_amozesh.startAnimation(rotate_backward);
            //fab1.startAnimation(fab_close);
            fab2_amozesh.startAnimation(fab_close);
            fab3_amozesh.startAnimation(fab_close);
            //fab1.setClickable(false);
            fab2_amozesh.setClickable(false);
            fab3_amozesh.setClickable(false);
            isFabOpen = false;

        } else {

            fab_amozesh.startAnimation(rotate_forward);
            //fab1.startAnimation(fab_open);
            fab2_amozesh.startAnimation(fab_open);
            fab3_amozesh.startAnimation(fab_open);
            //fab1.setClickable(true);
            fab2_amozesh.setClickable(true);
            fab3_amozesh.setClickable(true);
            isFabOpen = true;

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        svc.putExtra(BackgroundSoundService.CMD_NAME, BackgroundSoundService.CMD_PAUSE);
        startService(svc);
        finish();
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



}
