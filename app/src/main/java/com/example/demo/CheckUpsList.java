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
import com.example.demo.VaccinationListDetail;
import com.example.demo.CheckUps;
import com.example.demo.R;
import com.example.demo.CheckUps_pet;

import java.util.ArrayList;
import java.util.List;


public class CheckUpsList extends BaseAdapter{

    private Context context;
    private List<CheckUps_pet> list = new ArrayList<>(0);

    public CheckUpsList(Context context, List<CheckUps_pet> list) {
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


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.checkups_list,null);

            viewHolder.textView = (TextView) view.findViewById(R.id.tvName);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        CheckUps_pet pet = list.get(position);

        viewHolder.textView.setText(pet.getName());

        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("petid",pet.getId());
                bundle.putString("name",pet.getName());
                bundle.putString("recordUN",pet.getAccountname());
                Intent intent = new Intent(context, CheckUpsDetail.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        return view;
    }

    class ViewHolder{
        TextView textView;
        ImageView imageView;
    }
}
