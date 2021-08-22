package com.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;
import com.example.dummy.CardDummyContent;
import com.example.dummy.OnItemClickListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private String TAG = "CardAdapter";
    public final List<CardDummyContent.DummyItem> mValues;
//    private final OnListFragmentInteractionListener mListener;
    private final Context mContext;
    public OnItemClickListener mOnItemClickListener;//声明接口
    public OnItemClickListener mOnButtonClickListener;//声明选取操作接口

        public CardAdapter(List<CardDummyContent.DummyItem> items, Context context) {
        mValues = items;
//        mListener = listener;
        mContext = context;

    }

    public  void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        mOnItemClickListener=onItemClickListener;
    }

    public  void setOnButtonClickListener(OnItemClickListener onItemClickListener ){
        mOnButtonClickListener=onItemClickListener;
    }

    public void remove(int position) {
        mValues.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_result_vertical, parent, false);
            return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        int column = mValues.size();
        if((position > column) || (position == column)){
            return;
        }

        holder.mItem = mValues.get(position);
        holder.mQuestionView.setText(mValues.get(position).title);
        holder.mAnswerView.setText(mValues.get(position).answer);

        if(position % 5 == 1){
            holder.mPersonalcard_layoutView.setBackgroundResource(R.drawable.blue_bg);
            holder.mCertTypeLayoutView.setBackgroundResource(R.drawable.blue_circle);
            holder.mIssueView.setImageResource(R.drawable.blue_issue);
            holder.mIssueTextView.setTextColor(Color.parseColor("#1ea6dd"));
        }else if(position % 5 == 2){
            holder.mPersonalcard_layoutView.setBackgroundResource(R.drawable.green_bg);
            holder.mCertTypeLayoutView.setBackgroundResource(R.drawable.green_circle);
            holder.mIssueView.setImageResource(R.drawable.green_issue);
            holder.mIssueTextView.setTextColor(Color.parseColor("#32ae5b"));
        }else if(position % 5 == 3){
            holder.mPersonalcard_layoutView.setBackgroundResource(R.drawable.red_bg);
            holder.mCertTypeLayoutView.setBackgroundResource(R.drawable.red_circle);
            holder.mIssueView.setImageResource(R.drawable.red_issue);
            holder.mIssueTextView.setTextColor(Color.parseColor("#eb5937"));
        }else if(position % 5 == 0){
            holder.mPersonalcard_layoutView.setBackgroundResource(R.drawable.purple_bg);
            holder.mCertTypeLayoutView.setBackgroundResource(R.drawable.purple_circle);
            holder.mIssueView.setImageResource(R.drawable.purple_issue);
            holder.mIssueTextView.setTextColor(Color.parseColor("#a87bf9"));
        }else if(position % 5 == 4){
            holder.mPersonalcard_layoutView.setBackgroundResource(R.drawable.yellow_bg);
            holder.mCertTypeLayoutView.setBackgroundResource(R.drawable.yellow_circle);
//                holder.mIssueView.setImageResource(R.drawable.yellow_issue);
            holder.mIssueTextView.setTextColor(Color.parseColor("#ffb600"));
        }

        if (mOnItemClickListener != null) {
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.mView,position);
                }
            });
        }
        if (mOnButtonClickListener != null) {
            holder.certIssueBtnView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnButtonClickListener.onItemClick(holder.mView,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
            int size = mValues.size();
            return size;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mTypeImg;
        public final TextView mQuestionView;
        public final TextView mAnswerView;
        public final RelativeLayout certIssueBtnView;
        public final ImageView mIssueView;
        public final TextView mIssueTextView;
        public final LinearLayout mCertTypeLayoutView;

        public final RelativeLayout mPersonalcard_layoutView;

        public CardDummyContent.DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTypeImg = (ImageView) view.findViewById(R.id.certTypeImg);
            mQuestionView = (TextView) view.findViewById(R.id.tv_question);
            mAnswerView = (TextView) view.findViewById(R.id.tv_answer);
            certIssueBtnView = (RelativeLayout)view.findViewById(R.id.certIssueBtn);
            mIssueView = (ImageView)view.findViewById(R.id.certIssueImg);
            mIssueTextView = (TextView)view.findViewById(R.id.tv_certIssue);

            mCertTypeLayoutView = (LinearLayout)view.findViewById(R.id.certType_layout);
            mPersonalcard_layoutView = (RelativeLayout)view.findViewById(R.id.leftCardInfo_layout);

        }

    }

}
