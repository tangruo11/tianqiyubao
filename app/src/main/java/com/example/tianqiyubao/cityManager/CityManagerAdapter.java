package com.example.tianqiyubao.cityManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tianqiyubao.R;
import com.example.tianqiyubao.db.DataBaseBean;
import com.example.tianqiyubao.juhe.JuHeTempBean;
import com.google.gson.Gson;

import java.util.List;

public class CityManagerAdapter extends BaseAdapter {
    private List<DataBaseBean> list;
    private Context context;

    public CityManagerAdapter(List<DataBaseBean> list, Context context) {
        this.list = list;
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.item_city_manager,parent,false);
             viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        DataBaseBean bean = list.get(position);
        viewHolder.cityTV.setText(bean.getCity());
        JuHeTempBean tempBean = new Gson().fromJson(bean.getContent(), JuHeTempBean.class);
        JuHeTempBean.ResultBean.RealtimeBean realtimeBean = tempBean.getResult().getRealtime();
        viewHolder.conTv.setText(realtimeBean.getInfo());
        viewHolder.windTv.setText(realtimeBean.getDirect()+realtimeBean.getPower());
        viewHolder.currentTempTv.setText(realtimeBean.getTemperature());
        viewHolder.tempRangeTv.setText(tempBean.getResult().getFuture().get(0).getTemperature());


        return convertView;
    }
    class ViewHolder{
        TextView cityTV,conTv,currentTempTv,windTv,tempRangeTv;

        public ViewHolder(View view) {
            cityTV=view.findViewById(R.id.item_tv_city);
            conTv=view.findViewById(R.id.item_tv_condition);
            currentTempTv=view.findViewById(R.id.item_tv_temp);
            windTv=view.findViewById(R.id.item_tv_wind);
            tempRangeTv=view.findViewById(R.id.item_tv_temprange);
        }
    }
}
