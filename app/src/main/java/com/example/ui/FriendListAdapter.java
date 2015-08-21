/*
 * Copyright (c) 2015. 添美科技
 */

package com.example.ui;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterGroup;
import org.jivesoftware.smack.roster.packet.RosterPacket;

import java.util.List;

/**
 * Created by hutao on 2015/8/20.
 */
public class FriendListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<RosterGroup> groupList;
    private List<List<RosterEntry>> friendList;

    public FriendListAdapter(Context context, List<RosterGroup> groupList,List<List<RosterEntry>> friendList) {
        this.context = context;
        this.groupList = groupList;
        this.friendList = friendList;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.list_friend_item, null);
            TextView textView = (TextView) convertView.findViewById(R.id.list_friend_item_username);
            RosterEntry rosterEntry = friendList.get(groupPosition).get(childPosition);
            textView.setText(rosterEntry.getName());

        }
        TextView textView = (TextView) convertView.findViewById(R.id.list_friend_item_username);
        RosterEntry rosterEntry = friendList.get(groupPosition).get(childPosition);
        textView.setText(rosterEntry.getName());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public RosterEntry getChild(int groupPosition, int childPosition) {
        return friendList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public RosterGroup getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return friendList.get(groupPosition).size();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.list_group_item, null);
            TextView textView = (TextView) convertView.findViewById(R.id.list_group_item_name);
            RosterGroup rosterGroup = groupList.get(groupPosition);
            textView.setText(rosterGroup.getName());
        }
        TextView textView = (TextView) convertView.findViewById(R.id.list_group_item_name);
        RosterGroup rosterGroup = groupList.get(groupPosition);
        textView.setText(rosterGroup.getName());
        return convertView;
    }
}
