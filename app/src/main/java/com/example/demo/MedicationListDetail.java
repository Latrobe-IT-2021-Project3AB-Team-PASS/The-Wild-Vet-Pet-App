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

    //for show more detail about the selected pets medication
    //MedicationDetail里的动态列表 包含列表信息和转跳判定

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
        // 适配 页面
        ViewHolder viewHolder;
        if (view == null){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.medication_list_detail,null);
            //viewHolder.imageView = (ImageView) view.findViewById(R.id.tvImage);
            viewHolder.textView = (TextView) view.findViewById(R.id.tvLefttest);
            viewHolder.textView1 = (TextView) view.findViewById(R.id.tvRighttest);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Medication_petMediDetail medi = list.get(position);
        viewHolder.textView.setText(medi.getMediproduct());
        viewHolder.textView1.setText(medi.getMedipurchasedate().toString());

        //viewHolder.imageView.setImageResource(setImage(pet.getImage()));
        //disable image for right now due to image still not fixed yet.

        //disable clickListener due to no need
        /*
        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                // Vacc_type = String, Vacc_date = Date, Vacc_productname = String, Vacc_name = String, Vacc_dueday = DATE
                bundle.putString("vacctype",pet.getVacctype());
                bundle.putString("Vaccdate",pet.getVaccdate().toString());
                bundle.putString("vaccproductname",pet.getVaccproductname());
                bundle.putString("vaccname",pet.getVaccname());
                bundle.putString("dueday",pet.getVaccdueday().toString());
                //bundle.putString("age",pet.getAge().toString());
                //bundle.putString("type", pet.getType());
                //String sex = "1".equals(pet.getSex())?"男":"女";
                //bundle.putString("sex", sex);
                Intent intent = new Intent(context, VaccinationDetailPlus.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                // Vacc_type = String, Vacc_date = Date, Vacc_productname = String, Vacc_name = String, Vacc_dueday = DATE
                bundle.putString("vacctype",pet.getVacctype());
                bundle.putString("Vaccdate",pet.getVaccdate().toString());
                bundle.putString("vaccproductname",pet.getVaccproductname());
                bundle.putString("vaccname",pet.getVaccname());
                bundle.putString("dueday",pet.getVaccdueday().toString());
                //bundle.putString("sex", sex);
                Intent intent = new Intent(context, VaccinationDetailPlus.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });*/

        return view;
    }
    class ViewHolder{
        TextView textView;
        TextView textView1;
    }
}
