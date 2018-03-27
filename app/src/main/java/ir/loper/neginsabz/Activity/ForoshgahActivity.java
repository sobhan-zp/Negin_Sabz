package ir.loper.neginsabz.Activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.loper.neginsabz.Adapter.CustomGridViewAdapter;
import ir.loper.neginsabz.Model.ItemGrid;
import ir.loper.neginsabz.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ForoshgahActivity extends AppCompatActivity {


    GridView gv_item_foroshgah;
    ArrayList<ItemGrid> gridArray = new ArrayList<ItemGrid>();
    CustomGridViewAdapter customGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foroshgah);

//set grid view item
        Bitmap morgh = BitmapFactory.decodeResource(this.getResources(), R.drawable.morgh);
        Bitmap shotormorgh = BitmapFactory.decodeResource(this.getResources(), R.drawable.shotormorgh);
        Bitmap boghalamon = BitmapFactory.decodeResource(this.getResources(), R.drawable.boghalamon);

        gridArray.add(new ItemGrid(morgh,"مرغ"));
        gridArray.add(new ItemGrid(shotormorgh,"شتر مرغ"));
        gridArray.add(new ItemGrid(boghalamon,"بوقلمون"));



        gv_item_foroshgah = (GridView) findViewById(R.id.gv_item_foroshgah);
        customGridAdapter = new CustomGridViewAdapter(this, R.layout.gridview_item_foroshgah, gridArray);
        gv_item_foroshgah.setAdapter(customGridAdapter);

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}