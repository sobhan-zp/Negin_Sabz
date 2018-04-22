package ir.loper.neginsabz.Data;


import java.util.ArrayList;

import ir.loper.neginsabz.Model.ForoshgahModel;
import ir.loper.neginsabz.R;


public class Foroshgah {

    ArrayList<ForoshgahModel> data = new ArrayList<>();

    String[] title = new String[]{
            "بوقلمون",

            "شتر مرغ",

            " مرغ ",

            "سایر محصولات"

    };

    String[] description = new String[]{

            "بوقلمون",

            "شتر مرغ",

            " مرغ ",

            "سایر محصولات"

    };

    int[] image = new int[]{
            R.drawable.turky,
            R.drawable.shotormorgh,
            R.drawable.chiken,
            R.drawable.other
    };

    String[] text = new String[]{


            "\n  بال بوقلمون " +
                    "\n" +
                    "\n  بازو بوقلمون" +
                    "\n" +
                    "\n  بال كبابي بوقلمون " +
                    "\n" +
                    "\n  ران بوقلمون با پوست و استخوان " +
                    "\n" +
                    "\n  ساق بوقلمون " +
                    "\n" +
                    "\n  ران و ساق بوقلمون " +
                    "\n" +
                    "\n  سينه بوقلمون با پوست و استخوان " +
                    "\n" +
                    "\n  سينه با بازو بوقلمون " +
                    "\n" +
                    "\n  گردن بوقلمون بدون پوست " +
                    "\n" +
                    "\n  بوقلمون كامل" +
                    "\n" +
                    "\n  آلايش بوقلمون ",


            "\n  گوشت ران شترمرغ  " +
                    "\n" +
                    "\n  جگر شترمرغ " +
                    "\n" +
                    "\n  دل شترمرغ " +
                    "\n" +
                    "\n  سنگدان شترمرغ " +
                    "\n" +
                    "\n  چربي شترمرغ " +
                    "\n" +
                    "\n  گردن شترمرغ " +
                    "\n" +
                    "\n  روغن شترمرغ " +
                    "\n" +
                    "\n  قلم شترمرغ " +
                    "\n" +
                    "\n  گوشت شتر ",


            "\n  ران و ساق مرغ با پوست " +
                    "\n" +
                    "\n  سينه مرغ " +
                    "\n" +
                    "\n  بال كبابي مرغ " +
                    "\n" +
                    "\n  مرغ كامل" +
                    "\n  گردن مرغ بدون پوست " +
                    "\n" +
                    "\n  گردن مرغ با پوست" +
                    "\n" +
                    "\n  جگر مرغ" +
                    "\n" +
                    "\n  سنگدان مرغ" +
                    "\n" +
                    "\n  دل مرغ" +
                    "\n" +
                    "\n  بازو مرغ" +
                    "\n" +
                    "\n  ساق مرغ" +
                    "\n" +
                    "\n  اسكلت مرغ " +
                    "\n" +
                    "\n  مرغ سايز" +
                    "\n" +
                    "\n  مرغ سبز كامل",




            "\n  گوشت شتر " +
                    "\n" +
            "\n  تخم بلدرچين 12 عددي " +
                    "\n" +
            "\n  تخم مرغ درشت " +
                    "\n" +
            "\n  تخم مرغ متوسط " +
                    "\n" +
            "\n  تخم مرغ محلي  " +
                    "\n" +
            "\n  تخم اردك محلي " +
                    "\n" +
            "\n  تخم بلدرچين فانتزي " +
                    "\n" +
            "\n  گوشت چرخ كرده 300 گرمي " +
                    "\n" +
            "\n  گوشت بلدرچين " +
                    "\n" +
            "\n  ذرت فله " +
                    "\n" +
            "\n  پنير پيتزا پروسس رنده اي " +
                    "\n" +
            "\n  گوجه گيلاسي يك در يك(درجه 1) " +
                    "\n" +
            "\n  گوجه گيلاسي يك در يك(لوكس) " +
                    "\n" +
            "\n  گوجه زيتوني خان عمو " ,


    };


    public Foroshgah() {
        setData();
    }

    private void setData() {

        for (int i = 0; i < title.length; i++) {
            ForoshgahModel model = new ForoshgahModel();
            model.setTitle(title[i]);
            model.setDescription(description[i]);
            model.setImage(image[i]);
            model.setText1(text[i]);
            data.add(model);
        }
    }

    public ArrayList<ForoshgahModel> getData() {
        return data;
    }

}
