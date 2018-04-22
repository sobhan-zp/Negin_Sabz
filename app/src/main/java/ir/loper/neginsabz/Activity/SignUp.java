package ir.loper.neginsabz.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.crashlytics.android.Crashlytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.fabric.sdk.android.Fabric;
import ir.loper.neginsabz.Network.AppController;
import ir.loper.neginsabz.Network.CustomRequest;
import ir.loper.neginsabz.Network.SavePref;
import ir.loper.neginsabz.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignUp extends AppCompatActivity {

    ArrayList<String> city;
    ArrayAdapter adapter;
    TextView txt_signin_singup;
    EditText name_singup, user_singup, email_singup, mobile_singup, age_singup, pass_singup;
    Button btn_create_singup;
    Spinner spCity;
    ProgressDialog dialog;
    SavePref save;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        Fabric.with(this, new Crashlytics());
        dialog = new ProgressDialog(this);
        save = new SavePref(this);

        name_singup = (EditText) findViewById(R.id.name_singup);
        user_singup = (EditText) findViewById(R.id.user_singup);
        email_singup = (EditText) findViewById(R.id.email_singup);
        mobile_singup = (EditText) findViewById(R.id.mobile_singup);
        age_singup = (EditText) findViewById(R.id.age_singup);
        pass_singup = (EditText) findViewById(R.id.pass_singup);

        btn_create_singup = (Button) findViewById(R.id.btn_create_singup);

        /*txt_signin_singup = (TextView) findViewById(R.id.txt_signin_singup);
        txt_signin_singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SignUp.this, SignIn.class);
                startActivity(it);
            }
        });*/


        btn_create_singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isNetworkAvailable()) {
                    AppController.message(SignUp.this, "لطفا اتصال به اینترنت خود را برسی کنید");
                    return;
                }


                if (name_singup.getText().toString().length() <= 1) {

                    AppController.message(SignUp.this, "لطفا نام خود را وارد کنید");
                    name_singup.requestFocus();
                    return;

                } else if (user_singup.getText().toString().length() <= 1) {

                    AppController.message(SignUp.this, "لطفا نام کاربری را وارد کنید");
                    user_singup.requestFocus();
                    return;
                } else if (email_singup.getText().toString().length() > 0  && !AppController.isValidEmail(email_singup.getText().toString())) {


                        AppController.message(SignUp.this, "لطفا ایمیل را درست وارد کنید");
                        email_singup.requestFocus();
                        return;



                } else if (!mobile_singup.getText().toString().matches("(\\+98|0)?9\\d{9}")) {


                    AppController.message(SignUp.this, "لطفا شماره موبایل را درست وارد کنید");
                    mobile_singup.requestFocus();
                    return;
                } else if (age_singup.getText().toString().length() <= 1) {

                    AppController.message(SignUp.this, "لطفا سن خود را وارد کنید");
                    age_singup.requestFocus();
                    return;
                } else if (pass_singup.getText().toString().length() <= 1) {

                    AppController.message(SignUp.this, "لطفا رمزعبور را وارد کنید");
                    pass_singup.requestFocus();
                    return;
                } else {

                    save.save(AppController.SAVE_USER_FULLNAME, name_singup.getText().toString());
                    save.save(AppController.SAVE_USER_EMAIL, email_singup.getText().toString());
                    save.save(AppController.SAVE_USER_USERNAME, user_singup.getText().toString());
                    save.save(AppController.SAVE_USER_MOBILE, mobile_singup.getText().toString());
                    save.save(AppController.SAVE_USER_AGE, age_singup.getText().toString());
                    save.save(AppController.SAVE_USER_CITY, String.valueOf(spCity.getSelectedItemPosition()));

                    dialog.setMessage("ورود...");
                    dialog.show();
                    signup(
                            name_singup.getText().toString(),
                            user_singup.getText().toString(),
                            email_singup.getText().toString(),
                            mobile_singup.getText().toString(),
                            age_singup.getText().toString(),
                            pass_singup.getText().toString(),
                            String.valueOf(spCity.getSelectedItemPosition())
                    );
                }
            }
        });


        fillcity();
    }

    private void signup(String fullname, String uname, String email, String mobile, String age, String pass, String city) {

        Map<String, String> params = new HashMap<>();
        params.put("fullname", fullname);
        params.put("uname", uname);
        params.put("email", email);
        params.put("mobile", mobile);
        params.put("age", age);
        params.put("pass", pass);
        params.put("city", city);

        CustomRequest jsonObjReq = new CustomRequest(Request.Method.POST, AppController.URL_SIGNUP, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject resp = response;
                //Log.d("TAG--------OK", resp.toString());

                try {
                    if (resp.getString("status").equals("200")) {

                        save.save(AppController.SAVE_LOGIN, "1");
                        save.save(AppController.SAVE_USER_ID, resp.getString("id"));

                        AppController.message(SignUp.this, "ثبت نام انجام شد");
                        startActivity(new Intent(SignUp.this, MainActivity.class));
                        finish();

                    } else if (resp.getString("status").equals("204")) {
                        AppController.message(SignUp.this, "کاربری تکراری می باشد");
                    } else if (resp.getString("status").equals("206")) {
                        AppController.message(SignUp.this, "خطا در سرور لطفا بعدا سعی کنید");
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
                AppController.message(SignUp.this, "لطفا در زمان دیگری اقدام کنید");
                if (dialog.isShowing()) dialog.dismiss();
            }
        });
        jsonObjReq.setShouldCache(false);
        //myRequestQueue.getCache().clear();
        AppController.getInstance().addToRequestQueue(jsonObjReq, "REGISTER");

    }


    private void fillcity() {
        city = new ArrayList<>();

        spCity = (Spinner) findViewById(R.id.sp_signup_city);
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, city);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCity.setAdapter(adapter);

        city.add("آستارا");
        city.add("آستانه اشرفیه");
        city.add("املش");
        city.add("بندر انزلی");
        city.add("تالش");
        city.add("رشت");
        city.add("رضوان‌شهر");
        city.add("رودسر");
        city.add("رودبار");
        city.add("شفت");
        city.add("صومعه‌سرا");
        city.add("فومن");
        city.add("کوچصفهان");
        city.add("لاهیجان");
        city.add("لنگرود");
        city.add("ماسال");
        city.add("آذربايجان شرقي");
        city.add("آذربايجان غربي");
        city.add("اردبيل");
        city.add("اصفهان");
        city.add("البرز");
        city.add("ايلام");
        city.add("بوشهر");
        city.add("تهران");
        city.add("چهارمحال بختياري");
        city.add("خراسان جنوبي");
        city.add("خراسان رضوي");
        city.add("خراسان شمالي");
        city.add("خوزستان");
        city.add("زنجان");
        city.add("سمنان");
        city.add("سيستان و بلوچستان");
        city.add("فارس");
        city.add("قزوين");
        city.add("قم");
        city.add("كردستان");
        city.add("كرمان");
        city.add("كرمانشاه");
        city.add("كهكيلويه و بويراحمد");
        city.add("گلستان");
        city.add("گيلان");
        city.add("لرستان");
        city.add("مازندران");
        city.add("مركزي");
        city.add("هرمزگان");
        city.add("همدان");
        city.add("يزد");

        adapter.notifyDataSetChanged();
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
