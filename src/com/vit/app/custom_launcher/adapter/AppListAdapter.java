package com.vit.app.custom_launcher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.vit.app.custom_launcher.R;
import com.vit.app.custom_launcher.appinfo.AppModel;

import java.util.ArrayList;

/**
 * Created by Alexander Belokopytov on 16.02.2015.
 *
 * it is a custom Adapter for GridView
 */
public class AppListAdapter extends ArrayAdapter<AppModel> {
    private final LayoutInflater mInflater;
    private ItemHolder mHolder;

    public AppListAdapter(Context context){
        super(context, R.layout.single_list_item_layout);
        mInflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<AppModel> data) {
        clear();
        if (data != null) {
            addAll(data);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            view = mInflater.inflate(R.layout.single_list_item_layout, parent, false);
        } else {
            view = convertView;
        }

        mHolder = new ItemHolder();

        mHolder.item = getItem(position);
        mHolder.icon = (ImageView)view.findViewById(R.id.icon);
        mHolder.text = (TextView)view.findViewById(R.id.text);

        setupItem(mHolder);
        return view;
    }

    private void setupItem(ItemHolder holder){
        holder.icon.setImageDrawable(holder.item.getIcon());
        holder.text.setText(holder.item.getLabel());
    }


    /**
     * this class hold views  for single item
     */
    private class ItemHolder{
        AppModel item;
        ImageView icon;
        TextView text;
    }
}
