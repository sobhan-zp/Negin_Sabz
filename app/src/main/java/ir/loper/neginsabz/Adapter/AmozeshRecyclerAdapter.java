package ir.loper.neginsabz.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.leonardoxh.customfont.FontText;

import java.util.List;

import ir.loper.neginsabz.Activity.DetailActivity;
import ir.loper.neginsabz.Model.AmozeshModel;
import ir.loper.neginsabz.Model.MazayaModel;
import ir.loper.neginsabz.R;


public class AmozeshRecyclerAdapter extends RecyclerView.Adapter<AmozeshRecyclerAdapter.MyViewHolder> {

    private List<AmozeshModel> itemList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public FontText tv_title_amozesh;
        public CardView ll_bg_amozesh;
        public ImageView img_avatar_amozesh;

        public MyViewHolder(View view) {
            super(view);

            this.tv_title_amozesh = (FontText) view.findViewById(R.id.tv_title_amozesh);
            this.img_avatar_amozesh = (ImageView) view.findViewById(R.id.img_avatar_amozesh);
            this.ll_bg_amozesh = (CardView) view.findViewById(R.id.ll_bg_amozesh);

        }
    }


    public AmozeshRecyclerAdapter(Context context, List<AmozeshModel> itemList) {
        this.itemList = itemList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_amozesh, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final AmozeshModel Item = itemList.get(position);

        holder.tv_title_amozesh.setText(Item.getTitle());

        Glide.with(holder.img_avatar_amozesh.getContext())
                .load(Item.getImage())
                .fitCenter()
                .into(holder.img_avatar_amozesh);

        holder.ll_bg_amozesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_TITLE, Item.getTitle());
                intent.putExtra(DetailActivity.EXTRA_DESC, Item.getDescription());
                intent.putExtra(DetailActivity.EXTRA_IMAGE, Item.getImage());
                intent.putExtra(DetailActivity.EXTRA_TEXT1, Item.getText1());
                mContext.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public void onViewDetachedFromWindow(MyViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }


}
