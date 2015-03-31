package com.infmme.readilyapp.navigation;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.infmme.readilyapp.Constants;
import com.infmme.readilyapp.R;
import com.infmme.readilyapp.ReceiverActivity;
import com.infmme.readilyapp.readable.Storable;

/**
 * Created by Dylan on 3/10/2015.
 */
public class ExpandableTocAdapter extends BaseExpandableListAdapter implements ExpandableListView.OnChildClickListener {

    private Context context;
    private TableOfContents items;
    private AlertDialog parentView;

    /**
     * Constructor for custom adapter
     * @param context Application context
     * @param items Table of Contents to bind this adapter to
     * @param parent Parent Dialog containing the view this adapter is attached to
    */
    public ExpandableTocAdapter(Context context, TableOfContents items, AlertDialog parent) {
        this.context = context;
        this.items = items;
        this.parentView = parent;
    }

    @Override
    public int getGroupCount() {
        return items.getSections().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return items.getSections().get(groupPosition).getChapters().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return items.getSections().get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return items.getSections().get(groupPosition).getChapters().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        final Section section = (Section)getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            convertView = inflater.inflate(R.layout.toc_group_item, null);
        }

        TextView item = (TextView)convertView.findViewById(R.id.section);

        /*item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Section clicked!",Toast.LENGTH_LONG).show();
            }
        });*/
        item.setTypeface(null, Typeface.BOLD);
        item.setText(section.getTitle());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Chapter chapter = (Chapter)getChild(groupPosition,childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            convertView = inflater.inflate(R.layout.toc_child_item, null);
        }

        TextView item = (TextView) convertView.findViewById(R.id.chapter);
        item.setText("\u2022 " + chapter.getTitle());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        final Chapter chapter = (Chapter)getChild(groupPosition,childPosition);
        Bundle args = new Bundle();
        args.putInt(Constants.EXTRA_TYPE, Storable.TYPE_FILE);
        args.putString(Constants.EXTRA_PATH, items.getFilepath());
        args.putString(Constants.EXTRA_HEADER, items.getTitle());
        args.putInt(Constants.EXTRA_INDEX,items.getItemIndex(chapter.getHref()));
        args.putString(Constants.EXTRA_ID,chapter.getFragmentId());
        args.putSerializable(Constants.EXTRA_RESUME_STATUS,Constants.RESUME_STATUS.SEEK);
        ReceiverActivity.startReceiverActivity(context, args);
        parentView.dismiss();
        return false;
    }
}
