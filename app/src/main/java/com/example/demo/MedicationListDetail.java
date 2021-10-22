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
import com.example.demo.Medication_petMediDetail;


import java.util.ArrayList;
import java.util.List;

public class MedicationListDetail extends BaseAdapter{

    private Context context;
    private List<Medication_petMediDetail> list = new ArrayList<>(0);

    public MedicationListDetail(Context context, List<Medication_petMediDetail> list) {
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
        ViewHolder viewHolder;
        if (view == null){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.medication_list_detail,null);
            viewHolder.textView = (TextView) view.findViewById(R.id.tvLefttest);
            viewHolder.textView1 = (TextView) view.findViewById(R.id.tvRighttest);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Medication_petMediDetail medi = list.get(position);
        viewHolder.textView.setText(medi.getMediproduct());
        viewHolder.textView1.setText(medi.getMedipurchasedate().toString());
        return view;
    }
    class ViewHolder{
        TextView textView;
        TextView textView1;
    }
}
