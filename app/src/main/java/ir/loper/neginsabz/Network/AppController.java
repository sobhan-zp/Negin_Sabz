package ir.loper.neginsabz.Network;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import ir.loper.neginsabz.Model.Question;
import ir.loper.neginsabz.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
 * Created by Maziar on 3/23/2018.
 */

public class AppController extends Application {

    public static final String TAG = AppController.class
            .getSimpleName();


    public final static String URL = "http://loper.ir/neginsabz/app/index.php/api/";

    public final static String URL_LOGIN = URL + "auth/login";
    public final static String URL_SIGNUP = URL + "auth/signup";
    public final static String URL_QUESTION = URL + "question/all";
    public final static String URL_USER_ANSVER = URL + "question/adduseransver";


    public final static String SAVE_LOGIN = "SAVE_LOGIN";

    public final static String SAVE_USER_ID = "SAVE_USER_ID";
    public final static String SAVE_USER_FULLNAME = "SAVE_USER_FULLNAME";
    public final static String SAVE_USER_USERNAME = "SAVE_USER_USERNAME";
    public final static String SAVE_USER_EMAIL = "SAVE_USER_EMAIL";
    public final static String SAVE_USER_MOBILE = "SAVE_USER_MOBILE";
    public final static String SAVE_USER_AGE = "SAVE_USER_AGE";
    public final static String SAVE_USER_CITY = "SAVE_USER_CITY";
    public final static String SAVE_USER_ACTIVE = "SAVE_USER_ACTIVE";


    public final static String SAVE_LAST_ANSVER_ID = "SAVE_LAST_ANSVER_ID";

    public static boolean IS_LOAD_QUESTION = false;
    public static Question question = new Question();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());


            CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath("IRANSansWeb_Medium.ttf")
                    .setFontAttrId(R.attr.fontPath)
                    .build()
            );


        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    (ImageLoader.ImageCache) new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }


    public static void message(Context context ,String txt) {
        Toast.makeText(context, txt, Toast.LENGTH_SHORT).show();
    }

    public static void message(Context context ,String txt, int time) {
        Toast.makeText(context, txt, time).show();
    }

}