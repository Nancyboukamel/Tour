
/*
 * Copyright (c) 2017 Selva.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package benkoreatech.me.tour.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import benkoreatech.me.tour.R;
import benkoreatech.me.tour.objects.categoryItem;

public class ThreeLevelListAdapter extends BaseExpandableListAdapter {

    List<categoryItem> parentHeaders;
    List<List<categoryItem>> secondLevel;
    private Context context;
    List<LinkedHashMap<categoryItem, List<categoryItem>>> data;

    /**
     * Constructor
     * @param context
     * @param parentHeader
     * @param secondLevel
     * @param data
     */
    public ThreeLevelListAdapter(Context context, List<categoryItem> parentHeader, List<List<categoryItem>> secondLevel, List<LinkedHashMap<categoryItem, List<categoryItem>>> data) {
        this.context = context;

        this.parentHeaders = parentHeader;

        this.secondLevel = secondLevel;

        this.data = data;
    }

    @Override
    public int getGroupCount() {
        return parentHeaders.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {


        // no idea why this code is working

        return 1;

    }

    @Override
    public Object getGroup(int groupPosition) {

        return groupPosition;
    }

    @Override
    public Object getChild(int group, int child) {


        return child;


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

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_group, null);
        TextView text = (TextView) convertView.findViewById(R.id.listTitle);
        text.setText(this.parentHeaders.get(groupPosition).getName());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final SecondLevelExpandableListView secondLevelELV = new SecondLevelExpandableListView(context);

        List<categoryItem> headers = secondLevel.get(groupPosition);


        List<List<categoryItem>> childData = new ArrayList<>();
        HashMap<categoryItem,List<categoryItem>> secondLevelData = data.get(groupPosition);

        for (categoryItem key : secondLevelData.keySet()) {


            childData.add(secondLevelData.get(key));

        }


        secondLevelELV.setAdapter(new SecondLevelAdapter(context, headers, childData));

        secondLevelELV.setGroupIndicator(null);


        secondLevelELV.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup)
                    secondLevelELV.collapseGroup(previousGroup);
                    previousGroup = groupPosition;
            }
        });


        return secondLevelELV;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}