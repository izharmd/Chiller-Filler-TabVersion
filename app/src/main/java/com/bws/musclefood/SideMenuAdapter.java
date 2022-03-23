/*
package com.bws.musclefood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SideMenuAdapter extends BaseAdapter {
    Context context;
    List<Item> items;

    public SideMenuAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_view_item_type2, null);
        ImageView icon = v.findViewById(R.id.icon);
        TextView text = v.findViewById(R.id.text);
        TextView textCount = v.findViewById(R.id.textCount);

        icon.setImageResource(items.get(i).getImage());
        text.setText(items.get(i).getName());
        String ount = items.get(i).getCont();

        if (!ount.equalsIgnoreCase("")) {
            textCount.setText(items.get(i).getCont());
        } else {
            textCount.setVisibility(View.INVISIBLE);
        }

        return v;
    }
}

*/
