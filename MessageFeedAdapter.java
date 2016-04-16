package com.androquad.shobujekattor.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.androquad.shobujekattor.model.MessageFeed;
import com.androquad.shobujekattor.R;

import java.util.ArrayList;

/**
 * Created by sujon on 06/04/2016.
 */
public class MessageFeedAdapter extends ArrayAdapter {

    ArrayList<MessageFeed> messageFeeds;
    Context context;

    public MessageFeedAdapter(Context context, ArrayList<MessageFeed> messageFeeds) {
        super(context, R.layout.update_feed,messageFeeds);
        this.messageFeeds = messageFeeds;
        this.context = context;

    }



    static class ViewHolder {
        TextView nameTv;
        TextView phoneTv;
        TextView messageTv;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.update_feed, null);

            viewHolder.nameTv = (TextView) convertView.findViewById(R.id.nameTv);
            viewHolder.phoneTv = (TextView) convertView.findViewById(R.id.phoneTv);
            viewHolder.messageTv = (TextView) convertView.findViewById(R.id.messageTv);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.nameTv.setText(messageFeeds.get(position).getName());
        viewHolder.phoneTv.setText(messageFeeds.get(position).getPhone());
        viewHolder.messageTv.setText(messageFeeds.get(position).getMessage());



        return convertView;
    }

}

