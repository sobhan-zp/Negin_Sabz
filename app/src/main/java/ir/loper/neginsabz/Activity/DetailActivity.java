package ir.loper.neginsabz.Activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.leonardoxh.customfont.FontText;

import java.lang.reflect.Field;

import ir.loper.neginsabz.Model.MazayaModel;
import ir.loper.neginsabz.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String EXTRA_DESC = "EXTRA_DESC";
    public static final String EXTRA_IMAGE = "EXTRA_IMAGE";
    public static final String EXTRA_TEXT1 = "EXTRA_TEXT1";

    MazayaModel myModel;

    private ImageView img_insta_detail, img_tlg_detail, img_web_detail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        myModel = new MazayaModel();
        Intent intent = getIntent();



        myModel.setTitle(intent.getStringExtra(EXTRA_TITLE));
        myModel.setDescription(intent.getStringExtra(EXTRA_DESC));
        myModel.setImage(intent.getIntExtra(EXTRA_IMAGE, 0));
        myModel.setText1(intent.getStringExtra(EXTRA_TEXT1));

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        makeCollapsingToolbarLayoutLooksGood(collapsingToolbar);
        collapsingToolbar.setTitle("   " + myModel.getTitle());
        loadBackdrop();


        FontText tv_text1 = (FontText) findViewById(R.id.tv_text1);
        tv_text1.setText(myModel.getText1());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_share);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, myModel.getTitle() );
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, myModel.getTitle() +"\n-----------\n"+ myModel.getText1());
                startActivity(Intent.createChooser(sharingIntent, "اشتراک گذاری در"));
            }
        });


        img_insta_detail = (ImageView) findViewById(R.id.img_insta_detail);
        img_insta_detail.setOnClickListener(this);

        img_tlg_detail = (ImageView) findViewById(R.id.img_tlg_detail);
        img_tlg_detail.setOnClickListener(this);

        img_web_detail = (ImageView) findViewById(R.id.img_web_detail);
        img_web_detail.setOnClickListener(this);



    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);

        Glide.with(this).load(myModel.getImage()).centerCrop().into(imageView);

    }


    private void makeCollapsingToolbarLayoutLooksGood(CollapsingToolbarLayout collapsingToolbarLayout) {
        try {
            final Field field = collapsingToolbarLayout.getClass().getDeclaredField("mCollapsingTextHelper");
            field.setAccessible(true);

            final Object object = field.get(collapsingToolbarLayout);
            final Field tpf = object.getClass().getDeclaredField("mTextPaint");
            tpf.setAccessible(true);

            ((TextPaint) tpf.get(object)).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/bsans.ttf"));
            ((TextPaint) tpf.get(object)).setColor(getResources().getColor(R.color.primary_material_light));
        } catch (Exception ignored) {
        }
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }



    public void onClick(View v) {

        switch (v.getId()) {



            case R.id.img_insta_detail:
                Uri uri = Uri.parse("https://www.instagram.com/neginsabzco/");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.instagram.com/neginsabzco/")));
                }
                break;

            case R.id.img_tlg_detail:
                Intent telegram = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/neginsabzco"));
                startActivity(telegram);
                break;

            case R.id.img_web_detail:
                String url = "http://www.neginsabzco.com/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;


        }
    }

}
