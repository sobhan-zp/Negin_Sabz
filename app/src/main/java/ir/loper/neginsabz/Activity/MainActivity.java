package ir.loper.neginsabz.Activity;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.fabric.sdk.android.Fabric;
import ir.loper.neginsabz.Network.AppController;
import ir.loper.neginsabz.Network.CustomRequest;
import ir.loper.neginsabz.Network.SavePref;
import ir.loper.neginsabz.Adapter.SliderViewPager;
import ir.loper.neginsabz.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class
MainActivity extends AppCompatActivity implements View.OnClickListener {


    private SpaceNavigationView spaceNavigationView;
    LinearLayout home, user;
    private Button btn_amozesh_main, btn_mahsol_main, btn_mazaya_main;
    private ImageView img_insta_profile, img_tlg_profile, img_web_profile;
    LinearLayout li_about_profil, li_us_profile, li_detail_profile, li_site_profil, li_exit_profil;

    //view pager
    ViewPager viewPager;
    LinearLayout sliderDotspanel;

    SavePref save;

    Dialog myDialog;
    ProgressDialog dialog;

    private int dotscount;
    private ImageView[] dots;

    GlideDrawableImageViewTarget imageViewTarget2;


    boolean show = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fabric.with(this, new Crashlytics());
        myDialog = new Dialog(this);
        save = new SavePref(this);
        dialog = new ProgressDialog(this);



        findView();
        ansver();
        logUser();

        loadQuestions();

        //gif
        ImageView img_gif_main = (ImageView) findViewById(R.id.img_gif_main);
        ImageView img_gif2_main = (ImageView) findViewById(R.id.img_gif2_main);

        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(img_gif_main);
        imageViewTarget2 = new GlideDrawableImageViewTarget(img_gif2_main);
        Glide.with(this).load(R.raw.down2).into(imageViewTarget);
        Glide.with(this).load(R.raw.gifquiz2).into(imageViewTarget2);


        //item bottom bar
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("HOME", R.drawable.home));
        spaceNavigationView.addSpaceItem(new SpaceItem("ACCOUNT", R.drawable.account));


        spaceNavigationView.shouldShowFullBadgeText(true);
        spaceNavigationView.setCentreButtonIconColorFilterEnabled(false);

        //SetOnClick Center Button
        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                Log.d("onCentreButtonClick ", "onCentreButtonClick");
                spaceNavigationView.shouldShowFullBadgeText(true);
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                Log.d("onItemClick ", "" + itemIndex + " " + itemName);
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                Log.d("onItemReselected ", "" + itemIndex + " " + itemName);
            }
        });

        //SetOnClick item menu
        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {

                if(save.load(AppController.SAVE_ENABLE_COMPETENTION,"0").equals("0")){
                    Intent telegram = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/neginsabzco"));
                    startActivity(telegram);
                    return;
                }

                Glide.with(MainActivity.this).load(R.raw.gifquiz1).into(imageViewTarget2);

                if (!AppController.IS_LOAD_QUESTION) {
                    Toast.makeText(MainActivity.this, "زمان مسابقه به پایان رسید", Toast.LENGTH_SHORT).show();
                    return;
                }

                TextView txtclose, dialog_txt;
                final Button btnFollow, btn_1, btn_2, btn_3;
                final TextView tv_1, tv_2, tv_3;
                myDialog.setContentView(R.layout.custompopup);
                txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
                dialog_txt = (TextView) myDialog.findViewById(R.id.dialog_txt);
                txtclose.setText("x");
                btn_1 = (Button) myDialog.findViewById(R.id.btn_q_1);
                btn_2 = (Button) myDialog.findViewById(R.id.btn_q_2);
                btn_3 = (Button) myDialog.findViewById(R.id.btn_q_3);

                tv_1 = (TextView) myDialog.findViewById(R.id.txt_dialog_meat);
                tv_2 = (TextView) myDialog.findViewById(R.id.txt_dialog_chiken);
                tv_3 = (TextView) myDialog.findViewById(R.id.txt_dialog_turky);

                btnFollow = (Button) myDialog.findViewById(R.id.btnfollow);
                txtclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                setBackgroundButton(tv_1, btn_1, AppController.question.getQuestion_a());
                setBackgroundButton(tv_2, btn_2, AppController.question.getQuestion_b());
                setBackgroundButton(tv_3, btn_3, AppController.question.getQuestion_c());

                dialog_txt.setText(AppController.question.getQuestion_txt());

                myDialog.show();


                final String[] ansever = {"-1"};
                btn_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ansever[0] = "A";

                        btn_1.setBackgroundResource(R.drawable.ic_check);
                        changeBackground(btn_2, AppController.question.getQuestion_b());
                        changeBackground(btn_3, AppController.question.getQuestion_c());

                    }
                });
                btn_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ansever[0] = "B";


                        btn_2.setBackgroundResource(R.drawable.ic_check);
                        changeBackground(btn_1, AppController.question.getQuestion_a());
                        changeBackground(btn_3, AppController.question.getQuestion_c());
                    }
                });
                btn_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ansever[0] = "C";

                        btn_3.setBackgroundResource(R.drawable.ic_check);
                        changeBackground(btn_1, AppController.question.getQuestion_a());
                        changeBackground(btn_2, AppController.question.getQuestion_b());

                    }
                });

                btnFollow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!isNetworkAvailable()) {
                            AppController.message(MainActivity.this, "لطفا اتصال به اینترنت خود را برسی کنید");
                            return;
                        }

                        dialog.setMessage("ارسال...");
                        dialog.setCancelable(false);
                        dialog.show();
                        SendUserAnsver(
                                save.load(AppController.SAVE_USER_ID, "-1"),
                                AppController.question.getId(),
                                ansever[0]
                        );
                    }
                });


            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                //Toast.makeText(MainActivity.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();

                if (itemIndex == 1) {
                    //Toast.makeText(MainActivity.this,  " 11111" , Toast.LENGTH_SHORT).show();
                    //home.setVisibility(View.GONE);
                    animateViewVisibility(home, View.GONE);
                    animateViewVisibility(user, View.VISIBLE);
                    //user.setVisibility(View.VISIBLE);

                } else if (itemIndex == 0) {
                    //Toast.makeText(MainActivity.this,  " 22222" , Toast.LENGTH_SHORT).show();
                    /*home.setVisibility(View.VISIBLE);
                    user.setVisibility(View.GONE);*/
                    animateViewVisibility(home, View.VISIBLE);
                    animateViewVisibility(user, View.GONE);
                }

            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                //Toast.makeText(MainActivity.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();
            }
        });


        //Linewar Home

        //viewpager image Top
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        SliderViewPager viewPagerAdapter = new SliderViewPager(this);
        viewPager.setAdapter(viewPagerAdapter);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            sliderDotspanel.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        spaceNavigationView.showIconOnly();

    }

    private void changeBackground(Button btn, String item) {
        switch (Integer.parseInt(item)) {
            case 1:
                btn.setBackgroundResource(R.drawable.meat);
                break;
            case 2:
                btn.setBackgroundResource(R.drawable.chiken);
                break;
            case 3:
                btn.setBackgroundResource(R.drawable.turky);
                break;
        }
    }


    private void setBackgroundButton(TextView tv1, Button btn, String item) {
        switch (Integer.parseInt(item)) {
            case 1:
                btn.setBackgroundResource(R.drawable.meat);
                tv1.setText("گوشت");
                break;
            case 2:
                btn.setBackgroundResource(R.drawable.chiken);
                tv1.setText("مرغ");
                break;
            case 3:
                btn.setBackgroundResource(R.drawable.turky);
                tv1.setText("بوقلمون");
                break;
        }
    }


    private void ansver() {
        // TODO: Use your own attributes to track content views in your app
        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName("NeginSabz")
                .putContentType("data")
                .putContentId("1234")
                .putCustomAttribute("Favorites Count", 20)
                .putCustomAttribute("Screen Orientation", "portrate"));

    }


    public void findView() {
        //findview
        home = (LinearLayout) findViewById(R.id.home);
        user = (LinearLayout) findViewById(R.id.user);
        spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);

        btn_amozesh_main = (Button) findViewById(R.id.btn_amozesh_main);
        btn_amozesh_main.setOnClickListener(this);

        btn_mahsol_main = (Button) findViewById(R.id.btn_mahsol_main);
        btn_mahsol_main.setOnClickListener(this);

        btn_mazaya_main = (Button) findViewById(R.id.btn_mazaya_main);
        btn_mazaya_main.setOnClickListener(this);


        //User
        img_insta_profile = (ImageView) findViewById(R.id.img_insta_profile);
        img_insta_profile.setOnClickListener(this);

        img_tlg_profile = (ImageView) findViewById(R.id.img_tlg_profile);
        img_tlg_profile.setOnClickListener(this);

        img_web_profile = (ImageView) findViewById(R.id.img_web_profile);
        img_web_profile.setOnClickListener(this);


        //profile
        li_detail_profile = (LinearLayout) findViewById(R.id.li_detail_profile);
        li_detail_profile.setOnClickListener(this);
        li_us_profile = (LinearLayout) findViewById(R.id.li_us_profile);
        li_us_profile.setOnClickListener(this);
        li_about_profil = (LinearLayout) findViewById(R.id.li_about_profil);
        li_about_profil.setOnClickListener(this);
        li_exit_profil = (LinearLayout) findViewById(R.id.li_exit_profil);
        li_exit_profil.setOnClickListener(this);
        li_site_profil = (LinearLayout) findViewById(R.id.li_site_profil);
        li_site_profil.setOnClickListener(this);

    }





    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_amozesh_main:
                Intent intent = new Intent(MainActivity.this, AmozeshActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_mahsol_main:
                startActivity(new Intent(MainActivity.this, ForoshgahActivity.class));
                break;

            case R.id.btn_mazaya_main:
                Intent intent3 = new Intent(MainActivity.this, MazayaActivity.class);
                startActivity(intent3);
                break;


            case R.id.img_insta_profile:
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

            case R.id.img_tlg_profile:
                Intent telegram = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/neginsabzco"));
                startActivity(telegram);
                break;

            case R.id.img_web_profile:
                String url = "http://www.neginsabzco.com/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;

            case R.id.li_detail_profile:
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                break;


            case R.id.li_us_profile:
                startActivity(new Intent(MainActivity.this, ContactUsActivity.class));
                break;


            case R.id.li_about_profil:
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                break;

            case R.id.li_site_profil:
                startActivity(new Intent(MainActivity.this, WebActivity.class));
                break;

            case R.id.li_exit_profil:
                save.save(AppController.SAVE_LOGIN, "0");
                startActivity(new Intent(MainActivity.this, SignIn.class));
                finish();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        spaceNavigationView.onSaveInstanceState(outState);
    }

    private void logUser() {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
        Crashlytics.setUserIdentifier("12345");
        Crashlytics.setUserEmail("user@fabric.io");
        Crashlytics.setUserName("Test User");
    }


    public static void animateViewVisibility(final View view, final int visibility) {
        view.animate().cancel();
        view.animate().setListener(null);

        if (visibility == View.VISIBLE) {
            view.animate().alpha(1f).start();
            view.setVisibility(View.VISIBLE);
        } else {
            view.animate().setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(visibility);
                }
            }).alpha(0f).setDuration(200).start();
        }
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private void SendUserAnsver(String id_user, String id_question, String ansver) {

        Map<String, String> params = new HashMap<>();
        params.put("id_user", id_user);
        params.put("id_question", id_question);
        params.put("ansver", ansver);

        CustomRequest jsonObjReq = new CustomRequest(Request.Method.POST, AppController.URL_USER_ANSVER, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject resp = response;
                //Log.e("TAG--------OK", resp.toString());

                try {
                    if (resp.getString("status").equals("200")) {

                        save.save(AppController.SAVE_LAST_ANSVER_ID, resp.getString("id"));
                        AppController.message(MainActivity.this, "انتخاب شما ارسال شد ", Toast.LENGTH_LONG);

                    } else if (resp.getString("status").equals("205")) {
                        AppController.message(MainActivity.this, "شما قبلا در مسابقه شرکت کرده اید");
                    } else {
                        AppController.message(MainActivity.this, "لطفا مجدد سعی کنید");
                    }

                    myDialog.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (dialog.isShowing()) dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG--------Error", "Error: " + error.getMessage());
                AppController.message(MainActivity.this, "لطفا در زمان دیگری اقدام کنید");
                if (dialog.isShowing()) dialog.dismiss();
            }
        });
        jsonObjReq.setShouldCache(false);
        //myRequestQueue.getCache().clear();
        AppController.getInstance().addToRequestQueue(jsonObjReq, "USER_ANSVERَ");
    }

    private void loadQuestions() {

        JsonArrayRequest req = new JsonArrayRequest(AppController.URL_QUESTION,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Log.d("TAG---------OK", response.toString());

                        try {
                            JSONObject object = response.getJSONObject(0);

                            AppController.question.setId(object.getString("id"));
                            AppController.question.setQuestion_txt(object.getString("question_txt"));
                            AppController.question.setQuestion_a(object.getString("question_a"));
                            AppController.question.setQuestion_b(object.getString("question_b"));
                            AppController.question.setQuestion_c(object.getString("question_c"));
                            AppController.question.setAnsver(object.getString("ansver"));
                            save.save(AppController.SAVE_ENABLE_COMPETENTION, object.getString("enable"));

                            AppController.IS_LOAD_QUESTION = true;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG------------Error", "Error: " + error.getMessage());

            }
        });
        req.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(req, "LIST_QUESTIOS");
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}