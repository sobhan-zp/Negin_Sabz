package ir.loper.neginsabz.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

import ir.loper.neginsabz.Network.AppController;
import ir.loper.neginsabz.Network.SavePref;
import ir.loper.neginsabz.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProfileActivity extends AppCompatActivity {

    TextView tv_profile_lname, tv_profile_username, tv_profile_email, tv_profile_mobile , tv_profile_age ,tv_profile_city ;
    SavePref save;

    ArrayList<String> city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_show);

        save = new SavePref(this);

        city = new ArrayList<>();
        city.add ("آستارا");
        city.add ("آستانه اشرفیه");
        city.add ("املش");
        city.add ("بندر انزلی");
        city.add ("تالش");
        city.add ("رشت");
        city.add ("رضوان‌شهر");
        city.add ("رودسر");
        city.add ("رودبار");
        city.add ("شفت");
        city.add ("صومعه‌سرا");
        city.add ("فومن");
        city.add ("کوچصفهان");
        city.add ("لاهیجان");
        city.add ("لنگرود");
        city.add ("ماسال");
        city.add ("آذربايجان شرقي");
        city.add ("آذربايجان غربي");
        city.add ("اردبيل");
        city.add ("اصفهان");
        city.add ("البرز");
        city.add ("ايلام");
        city.add ("بوشهر");
        city.add ("تهران");
        city.add ("چهارمحال بختياري");
        city.add ("خراسان جنوبي");
        city.add ("خراسان رضوي");
        city.add ("خراسان شمالي");
        city.add ("خوزستان");
        city.add ("زنجان");
        city.add ("سمنان");
        city.add ("سيستان و بلوچستان");
        city.add ("فارس");
        city.add ("قزوين");
        city.add ("قم");
        city.add ("كردستان");
        city.add ("كرمان");
        city.add ("كرمانشاه");
        city.add ("كهكيلويه و بويراحمد");
        city.add ("گلستان");
        city.add ("گيلان");
        city.add ("لرستان");
        city.add ("مازندران");
        city.add ("مركزي");
        city.add ("هرمزگان");
        city.add ("همدان");
        city.add ("يزد");

        tv_profile_lname = (TextView) findViewById(R.id.tv_profile_lname);
        tv_profile_username = (TextView) findViewById(R.id.tv_profile_username);
        tv_profile_email = (TextView) findViewById(R.id.tv_profile_email);
        tv_profile_mobile = (TextView) findViewById(R.id.tv_profile_mobile);
        tv_profile_age = (TextView) findViewById(R.id.tv_profile_age);
        tv_profile_city = (TextView) findViewById(R.id.tv_profile_city);

        tv_profile_lname.setText(" نام و نام خانوادگی: " + save.load(AppController.SAVE_USER_FULLNAME, ""));
        tv_profile_username.append("  " + save.load(AppController.SAVE_USER_USERNAME, ""));
        tv_profile_email.append("  " + save.load(AppController.SAVE_USER_EMAIL, ""));
        tv_profile_mobile.append("  " + save.load(AppController.SAVE_USER_MOBILE, ""));
        tv_profile_age.append("  " + save.load(AppController.SAVE_USER_AGE, ""));
        tv_profile_city.append(" " +
                " " + city.get(Integer.parseInt(save.load(AppController.SAVE_USER_CITY, "0"))));





    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
