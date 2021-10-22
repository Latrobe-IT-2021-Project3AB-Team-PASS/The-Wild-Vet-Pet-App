package com.example.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ParasitePreventionList extends BaseAdapter {

    private Context context;
    private List<ParasitePrevention_pet> list = new ArrayList<>(0);

    public ParasitePreventionList(Context context, List<ParasitePrevention_pet> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.parasite_prevention_list,null);
            viewHolder.textView = (TextView) view.findViewById(R.id.tvName);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        ParasitePrevention_pet PP = list.get(position);

        viewHolder.textView.setText(PP.getName());

        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("petid",PP.getId());
                bundle.putString("petname",PP.getName());
                bundle.putString("recordUN",PP.getAccountname());
                Intent intent = new Intent(context, ParasitePreventionDetail.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        return view;
    }

    public Integer setImage(String image){
        if (null==image || "".equals(image) || 0==image.length()){
            return null;
        }
        switch (image){
            case "1":
                return R.drawable.ic_animal_dog;
            case "2":
                return R.drawable.ic_animal_cat;
            default:
                return R.drawable.ic_animal_hedgehog;
        }
    }

    class ViewHolder{
        TextView textView;
        ImageView imageView;
    }


}
