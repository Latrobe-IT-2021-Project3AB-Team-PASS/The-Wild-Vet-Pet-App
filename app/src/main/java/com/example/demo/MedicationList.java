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

/**
 *Medication首页展示数据
 */
public class MedicationList extends BaseAdapter {

    //Medication里的动态列表 包含列表信息和转跳判定

    private Context context;
    private List<Medication_pet> list = new ArrayList<>(0);

    public MedicationList(Context context, List<Medication_pet> list) {
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
        // 适配 页面
        ViewHolder viewHolder;
        if (view == null){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.medication_list,null);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.tvImage);
            viewHolder.textView = (TextView) view.findViewById(R.id.tvName);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Medication_pet medi = list.get(position);

        viewHolder.textView.setText(medi.getName());
        //viewHolder.imageView.setImageResource(setImage(pet.getImage()));
        //disable image for right now due to image still not fixed yet.

        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("petid",medi.getId());
                bundle.putString("petname",medi.getName());
                //bundle.putString("age",pet.getAge().toString());
                //bundle.putString("type", pet.getType());
                //String sex = "1".equals(pet.getSex())?"男":"女";
                //bundle.putString("sex", sex);
                Intent intent = new Intent(context, MedicationDetail.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("petid",medi.getId());
                bundle.putString("petname",medi.getName());
                //bundle.putString("age",pet.getAge().toString());
                //bundle.putString("type", pet.getType());
                //String sex = "1".equals(pet.getSex())?"男":"女";
                //bundle.putString("sex", sex);
                Intent intent = new Intent(context, MedicationDetail.class);
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
