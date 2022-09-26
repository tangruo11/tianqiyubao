package com.example.tianqiyubao.cityManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tianqiyubao.R;
import com.example.tianqiyubao.db.DBManager;

import java.util.List;

public class DeleteCityAdapter extends BaseAdapter {
    List<String> list;
    Context context;
    List<String> deleteCities;

    public DeleteCityAdapter(List<String> list, Context context, List<String> deleteCities) {
        this.list = list;
        this.context = context;
        this.deleteCities = deleteCities;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.item_delete,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.tv.setText(list.get(position));
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               deleteCities.add(list.get(position));
                list.remove(position);
                notifyDataSetChanged();//提示adapter更新
            }
        });
        return convertView;
    }
    class ViewHolder{
        TextView tv;
        ImageView iv;

        public ViewHolder(View view) {
           tv=view.findViewById(R.id.item_delete_tv);
           iv=view.findViewById(R.id.item_delete_iv);
        }
    }
}
