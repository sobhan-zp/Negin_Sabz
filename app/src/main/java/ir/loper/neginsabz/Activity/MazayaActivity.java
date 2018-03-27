package ir.loper.neginsabz.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
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

import ir.loper.neginsabz.Adapter.MazayaRecyclerAdapter;
import ir.loper.neginsabz.Data.Mazaya;
import ir.loper.neginsabz.Model.MazayaModel;
import ir.loper.neginsabz.R;
import ir.loper.neginsabz.Service.BackgroundSoundService;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MazayaActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawerLayout mDrawerLayout;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab2, fab3;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;

    Intent svc;

    private List<MazayaModel> itemList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MazayaRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mazaya);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);*/

       // mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        /*NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
*/

        svc = new Intent(this, BackgroundSoundService.class);
        svc.putExtra(BackgroundSoundService.CMD_NAME, BackgroundSoundService.CMD_PLAY);
        startService(svc);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab1 = (FloatingActionButton)findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        fab.setOnClickListener(this);
        //fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_main);
        adapter = new MazayaRecyclerAdapter(MazayaActivity.this, itemList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(MazayaActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        itemList.addAll(new Mazaya().getData());
        adapter.notifyDataSetChanged();


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && fab.isShown())
                    fab.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    fab.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });


    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab:

                animateFAB();
                break;
            case R.id.fab2:

                fab2.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_play));
                svc.putExtra(BackgroundSoundService.CMD_NAME, BackgroundSoundService.CMD_PAUSE);
                startService(svc);
                fab.performClick();
                break;
            case R.id.fab3:

                fab2.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_pause));
                svc.putExtra(BackgroundSoundService.CMD_NAME, BackgroundSoundService.CMD_PLAY);
                startService(svc);
                fab.performClick();
                break;
        }
    }


    public void animateFAB() {

        if (isFabOpen) {

            fab.startAnimation(rotate_backward);
            //fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            //fab1.setClickable(false);
            fab2.setClickable(false);
            fab3.setClickable(false);
            isFabOpen = false;

        } else {

            fab.startAnimation(rotate_forward);
            //fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            //fab1.setClickable(true);
            fab2.setClickable(true);
            fab3.setClickable(true);
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
