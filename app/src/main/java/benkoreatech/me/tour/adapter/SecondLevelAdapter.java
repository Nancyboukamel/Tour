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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import benkoreatech.me.tour.R;
import benkoreatech.me.tour.objects.categoryItem;


public class SecondLevelAdapter extends BaseExpandableListAdapter {
    private Context context;


    List<List<categoryItem>> data;

    List<categoryItem> headers;

    ImageView ivGroupIndicator;


    public SecondLevelAdapter(Context context, List<categoryItem> headers, List<List<categoryItem>> data) {
        this.context = context;
        this.data = data;
        this.headers = headers;

    }

    @Override
    public Object getGroup(int groupPosition) {

        return headers.get(groupPosition);
    }

    @Override
    public int getGroupCount() {

        return headers.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_item, null);
        TextView text = (TextView) convertView.findViewById(R.id.expandedListItem);
        categoryItem groupText = (categoryItem) getGroup(groupPosition);
        text.setText(groupText.getName());
        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        List<categoryItem> childData;

        childData = data.get(groupPosition);


        return childData.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.third_row, null);

        TextView textView = (TextView) convertView.findViewById(R.id.thirditem);

        List<categoryItem> childArray = data.get(groupPosition);

        categoryItem text = childArray.get(childPosition);

        textView.setText(text.getName());

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<categoryItem> children = data.get(groupPosition);


        return children.size();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
