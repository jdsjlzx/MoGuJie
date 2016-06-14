package com.Imy.Fuli.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.Imy.Fuli.R;

/**
 * Created by user on 2015/11/23.
 */
public class OrderAdapter extends BaseAdapter {
    private String[] tab_names=  new String[]{"私搭","热门","星榜"};
    private Context mContext;
    private int itemWidth = 0;
    private int curOrderItem = 0;
    public OrderAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return tab_names.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        convertView = View.inflate(mContext, R.layout.item_order3, null);
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        itemWidth = width / tab_names.length ;
        if (itemWidth > 0) {
            convertView.findViewById(R.id.order_listview_container)
                    .getLayoutParams().width = itemWidth;
        }
        TextView txtName = (TextView) convertView
                .findViewById(R.id.order_listview_txtName);
        ImageView imgSplit = (ImageView) convertView
                .findViewById(R.id.order_listview_imgSplit);
        ImageView imgIcon = (ImageView) convertView
                .findViewById(R.id.order_listview_icon);
        View line = convertView.findViewById(R.id.line);
        txtName.setText(tab_names[position]);
        if (position == curOrderItem) {
            txtName.setTextColor(Color.parseColor("#FF6C00"));
            line.setVisibility(View.VISIBLE);

        } else {
            txtName.setTextColor(Color.parseColor("#666666"));
            line.setVisibility(View.INVISIBLE);
        }
        if (position < tab_names.length - 1) {
            imgSplit.setVisibility(View.VISIBLE);
        } else {
            imgSplit.setVisibility(View.GONE);
        }
        return convertView;

    }

    public void setCurOrderItem(int position) {
        this.curOrderItem = position;
        notifyDataSetChanged();
    }
}
