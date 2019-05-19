package com.example.fanjin.word__bar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by FanJin on 2016/9/15.
 */
public class MyListAdapter extends BaseAdapter {
    // 填充数据的list
    private ArrayList<String> list;
    // 用来控制CheckBox的选中状况
    private static HashMap<Integer, Boolean> isSelected;
    // 上下文
    private Context context;
    // 用来导入布局
    private LayoutInflater inflater = null;
    // 构造器
    public MyListAdapter(ArrayList<String> list, Context context) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
        isSelected = new HashMap<Integer, Boolean>();
        // 初始化数据
        initDate();
    }
    // 初始化isSelected的数据
    private void initDate() {
        for (int i = 0; i < list.size(); i++) {
            getIsSelected().put(i, false);
        }
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
        ViewHolder holder = null;
        if (convertView == null) {
            // 获得ViewHolder对象
            holder = new ViewHolder();
            // 导入布局并赋值给convertview
            convertView = inflater.inflate(R.layout.listviewitem, null);
            holder.tv = (TextView) convertView.findViewById(R.id.item_tv);
            holder.cb = (CheckBox) convertView.findViewById(R.id.item_cb);
            // 为view设置标签
            convertView.setTag(holder);
        } else {
            // 取出holder
            holder = (ViewHolder) convertView.getTag();
        }
        // 设置list中TextView的显示
        holder.tv.setText(list.get(position));
        // 根据isSelected来设置checkbox的选中状况
        holder.cb.setOnCheckedChangeListener(null);
        holder.cb.setChecked(getIsSelected().get(position));
        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //点击操作
                getIsSelected().put(position, isChecked);
                Log.e("eer","------------0----------------");
                if (isChecked){
                    wordInList.checkNum++;
                    Log.e("eer","------------1----------------"+wordInList.checkNum);
                }else {
                    wordInList.checkNum--;
                    Log.e("eer","-------------2---------------"+wordInList.checkNum);
                }
                wordInList.tv_show.setText(" 已选中" + wordInList.checkNum + "项");
                wordInList.fff[position]=-wordInList.fff[position];
            }

        });
        //holder.cb.setChecked(getIsSelected().get(position));
        return convertView;
    }


    public static HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }
    public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        MyListAdapter.isSelected = isSelected;
    }
    public static class ViewHolder {
        TextView tv;
        CheckBox cb;
    }
}
