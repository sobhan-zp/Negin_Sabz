package ir.loper.neginsabz.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.crashlytics.android.Crashlytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import customfonts.MyEditText;
import io.fabric.sdk.android.Fabric;
import ir.loper.neginsabz.Network.AppController;
import ir.loper.neginsabz.Network.CustomRequest;
import ir.loper.neginsabz.Network.SavePref;
import ir.loper.neginsabz.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignIn extends AppCompatActivity {

    TextView txt_signup_singupin;
    MyEditText edt_email, edt_pass;
    CheckBox chk_remember;
    Button btn_login_singup;
    SavePref save;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        Fabric.with(this, new Crashlytics());
        save = new SavePref(this);
        dialog = new ProgressDialog(this);

        edt_email = (MyEditText) findViewById(R.id.edt_signin_email);
        edt_pass = (MyEditText) findViewById(R.id.edt_signin_pass);
        chk_remember = (CheckBox) findViewById(R.id.checkbocremember);
        txt_signup_singupin = (TextView) findViewById(R.id.txt_signup_singupin);
        btn_login_singup = (Button) findViewById(R.id.btn_login_singup);


        if(save.load(AppController.SAVE_LOGIN, "0").equals("1")){
            startActivity(new Intent(SignIn.this , MainActivity.class));
            finish();
        }else if(save.load(AppController.SAVE_USER_USERNAME, "").length() > 0){
            edt_email.setText(save.load(AppController.SAVE_USER_USERNAME, ""));
            edt_pass.requestFocus();
        }


        txt_signup_singupin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this , SignUp.class));
            }
        });


        btn_login_singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(! isNetworkAvailable()) {
                    AppController.message(SignIn.this ,"لطفا اتصال به اینترنت خود را برسی کنید");
                    return;
                }

                if(edt_email.getText().toString().length() <= 1){

                    AppController.message(SignIn.this, "لطفا نام کاربری را وارد کنید");
                    edt_email.requestFocus();
                    return;
                }else if(edt_pass.getText().toString().length() <= 1){

                    AppController.message(SignIn.this, "کلمه عبور را وارد کنید");
                    edt_pass.requestFocus();
                    return;
                }else {

                    save.save(AppController.SAVE_USER_USERNAME, edt_email.getText().toString());

                    dialog.setMessage("ورود...");
                    dialog.show();
                    signin(edt_email.getText().toString(), edt_pass.getText().toString());
                }
            }
        });

    }

    private void signin(String email, String pass) {
        Map<String, String> params = new HashMap<>();
        params.put("uname", email);
        params.put("pass", pass);

        CustomRequest jsonObjReq = new CustomRequest(Request.Method.POST, AppController.URL_LOGIN, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject resp = response;
                //Log.e("TAG--------OK", resp.toString());

                try {
                    if (resp.getString("status").equals("200")) {

                        save.save(AppController.SAVE_USER_ID, resp.getString("id"));
                        save.save(AppController.SAVE_USER_EMAIL, resp.getString("email"));
                        save.save(AppController.SAVE_USER_FULLNAME, resp.getString("fullname"));
                        save.save(AppController.SAVE_USER_MOBILE, resp.getString("mobile"));
                        save.save(AppController.SAVE_USER_AGE, resp.getString("age"));
                        save.save(AppController.SAVE_USER_CITY, resp.getString("city"));
                        save.save(AppController.SAVE_USER_ACTIVE, resp.getString("active"));

                        if(chk_remember.isChecked()) save.save(AppController.SAVE_LOGIN, "1");
                        startActivity(new Intent(SignIn.this , MainActivity.class));
                        finish();

                    } else if (resp.getString("status").equals("204")) {
                        AppController.message(SignIn.this, "کاربری با این نام کاربری یافت نشد");
                    } else if (resp.getString("status").equals("205")) {
                        AppController.message(SignIn.this, "کلمه عبور اشتباه است");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (dialog.isShowing()) dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG--------Error", "Error: " + error.getMessage());
                AppController.message(SignIn.this, "لطفا در زمان دیگری اقدام کنید");
                if (dialog.isShowing()) dialog.dismiss();
            }
        });
        jsonObjReq.setShouldCache(false);
        //myRequestQueue.getCache().clear();
        AppController.getInstance().addToRequestQueue(jsonObjReq, "LOGIN");

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
