package com.example.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import com.example.demo.MainActivity;
import com.example.demo.CheckUpsDetailPlus;
import com.example.demo.CheckUps;
import com.example.demo.R;
import com.example.demo.CheckUps_petCheckUpDetail;

import java.util.ArrayList;
import java.util.List;


public class CheckUpsListDetail extends BaseAdapter {

    //for show more detail about the selected pets checkups
    //CheckUpsDetail里的动态列表 包含列表信息和转跳判定

    private Context context;
    private List<CheckUps_petCheckUpDetail> list = new ArrayList<>(0);

    public CheckUpsListDetail(Context context, List<CheckUps_petCheckUpDetail> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {
        // 适配 页面
        ViewHolder viewHolder;
        if (view == null){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.checkups_list_detail,null);
            //viewHolder.imageView = (ImageView) view.findViewById(R.id.tvImage);
            viewHolder.textView = (TextView) view.findViewById(R.id.tvLefttest);
            viewHolder.textView1 = (TextView) view.findViewById(R.id.tvRighttest);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        CheckUps_petCheckUpDetail checku = list.get(position);
        viewHolder.textView.setText(checku.getCheckupstype());
        viewHolder.textView1.setText(checku.getCheckupsdate().toString());

        //viewHolder.imageView.setImageResource(setImage(pet.getImage()));
        //disable image for right now due to image still not fixed yet.

        //disable clickListener due to no need

        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                //Pet_id = String, Vet_fullname = String, Checkups_type = String, Checkups_date = Date, Checkups_notes = String
                bundle.putString("vetfullname",checku.getVetfullname());
                bundle.putString("checkupstype",checku.getCheckupstype());
                bundle.putString("checkupsdate",checku.getCheckupsdate().toString());
                bundle.putString("checkupsnotes",checku.getCheckupsnotes());

                Intent intent = new Intent(context, CheckUpsDetailPlus.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        viewHolder.textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                //Pet_id = String, Vet_fullname = String, Checkups_type = String, Checkups_date = Date, Checkups_notes = String
                bundle.putString("vetfullname",checku.getVetfullname());
                bundle.putString("checkupstype",checku.getCheckupstype());
                bundle.putString("checkupsdate",checku.getCheckupsdate().toString());
                bundle.putString("checkupsnotes",checku.getCheckupsnotes());

                Intent intent = new Intent(context, CheckUpsDetailPlus.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        return view;
    }

    class ViewHolder{
        TextView textView;
        TextView textView1;
    }
}
