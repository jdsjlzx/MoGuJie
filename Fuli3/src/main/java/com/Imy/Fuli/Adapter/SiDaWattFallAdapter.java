package com.Imy.Fuli.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Imy.Fuli.R;
import com.Imy.Fuli.tools.LogUtils;
import com.Imy.Fuli.tools.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2015/11/23.
 */
public class SiDaWattFallAdapter extends RecyclerView.Adapter {
    private List<String> mDatas;
    private MyViewHolder holder;

    public SiDaWattFallAdapter(ArrayList<String>    datas) {
        this.mDatas =datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
         holder = new MyViewHolder(LayoutInflater.from(
               UiUtils.getContext()).inflate(R.layout.sida_list_item, viewGroup,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        LogUtils.w("mDatas.get(position)="+position+"内容是="+mDatas.get(position));

        holder.tv.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.sida_item_tv);
        }
    }
}
