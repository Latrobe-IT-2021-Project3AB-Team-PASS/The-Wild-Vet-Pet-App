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
import com.example.demo.R;
import com.example.demo.ParasitePrevention_petPPDetail;

import java.util.ArrayList;
import java.util.List;


public class ParasitePreventionListDetail extends BaseAdapter {

    //for show more detail about the selected pets ParasitePrevention
    //ParasitePreventionDetail里的动态列表 包含列表信息和转跳判定

    private Context context;
    private List<ParasitePrevention_petPPDetail> list = new ArrayList<>(0);

    public ParasitePreventionListDetail(Context context, List<ParasitePrevention_petPPDetail> list) {
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

    public View getView(int position, View view, ViewGroup parent){
        // 适配 页面
        ViewHolder viewHolder;
        if (view == null){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.parasite_prevention_list_detail,null);
            //viewHolder.imageView = (ImageView) view.findViewById(R.id.tvImage);
            viewHolder.textView = (TextView) view.findViewById(R.id.tvLefttest);
            viewHolder.textView1 = (TextView) view.findViewById(R.id.tvMiddletest);
            viewHolder.textView3 = (TextView) view.findViewById(R.id.tvRighttest);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        ParasitePrevention_petPPDetail PP = list.get(position);
        viewHolder.textView.setText(PP.getPpdate().toString());
        viewHolder.textView1.setText(PP.getPpproduct());
        viewHolder.textView3.setText(PP.getPpfuequency());

        return view;
    }




    class ViewHolder{
        TextView textView;
        TextView textView1;
        TextView textView3;
    }
}
