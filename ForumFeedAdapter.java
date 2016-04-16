package com.androquad.shobujekattor.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.androquad.shobujekattor.model.ForumFeed;
import com.androquad.shobujekattor.R;

import java.util.ArrayList;

/**
 * Created by sujon on 07/04/2016.
 */
public class ForumFeedAdapter extends ArrayAdapter {
    ArrayList<ForumFeed> forumFeeds;
    Context context;

    public ForumFeedAdapter(Context context, ArrayList<ForumFeed> forumFeeds) {
        super(context, R.layout.market_row_view,forumFeeds);
        this.forumFeeds = forumFeeds;
        this.context = context;

    }



    static class ViewHolder {
        TextView nameTv;
        TextView mobileTv;
        TextView detailsTv;
        TextView dateTv;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.market_row_view, null);

            viewHolder.nameTv = (TextView) convertView.findViewById(R.id.nameTv);
            viewHolder.mobileTv = (TextView) convertView.findViewById(R.id.mobileTv);
            viewHolder.detailsTv = (TextView) convertView.findViewById(R.id.detailsTv);
            viewHolder.dateTv = (TextView) convertView.findViewById(R.id.dateTv);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.nameTv.setText(forumFeeds.get(position).getName());

        viewHolder.detailsTv.setText(forumFeeds.get(position).getMessage());
        viewHolder.dateTv.setText(forumFeeds.get(position).getDatetime());



        return convertView;
    }

}

