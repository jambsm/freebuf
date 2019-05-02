package com.yf.re.freebuf;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by lenovo on 2016/11/13.
 */

public class SubReadFragment extends Fragment {
    private  String [] sItem={"黑客","极客","特色"};
    private  String [][] sSubItem={{"漏洞","安全工具","Web安全","系统安全","网络安全","无线安全","设备/客户端安全","数据库安全","安全管理"},{"极有意思","周边"},{"头条","人物志","活动","视频","观点","招聘","报告"}};

    String sBasePath;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.subread,container,false);
        ExpandableListView listView=(ExpandableListView) view.findViewById(R.id.exp_list);
        listView.setAdapter(new myExpListAdapter());
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                FragmentTransaction transaction=getFragmentManager().beginTransaction();


                if(groupPosition==0){

                    switch (childPosition){
                        case 0:
                            sBasePath="https://www.freebuf.com/vuls";
                            break;
                        case 1:
                            sBasePath="https://www.freebuf.com/sectool";
                            break;
                        case 2:
                            sBasePath="https://www.freebuf.com/articles/web";
                            break;
                        case 3:
                            sBasePath="https://www.freebuf.com/articles/system";
                            break;
                        case 4:
                            sBasePath="https://www.freebuf.com/articles/network";
                            break;
                        case 5:
                            sBasePath="https://www.freebuf.com/articles/wireless";
                            break;
                        case 6:
                            sBasePath="https://www.freebuf.com/articles/terminal";
                            break;
                        case 7:
                            sBasePath="https://www.freebuf.com/articles/database";
                            break;
                        case 8:
                            sBasePath="https://www.freebuf.com/articles/security-management";
                            break;
                        default: break;
                    }

                }else if(groupPosition==1){

                    switch (childPosition){
                        case 0:
                            sBasePath="https://www.freebuf.com/geek";
                            break;
                        case 1:
                            sBasePath="https://www.freebuf.com/news/others";
                            break;
                        default:break;
                    }
                }else if(groupPosition==2){

                    switch (childPosition){
                        case 0:
                            sBasePath="https://www.freebuf.com/news/special";
                            break;
                        case 1:
                            sBasePath="https://www.freebuf.com/articles/people";
                                break;
                        case 2:
                            sBasePath="https://www.freebuf.com/fevents";
                            break;
                        case 3:
                            sBasePath="https://www.freebuf.com/video";
                            break;
                        case 4:
                            sBasePath="https://www.freebuf.com/articles/neopoints";
                            break;
                        case 5:
                            sBasePath="https://www.freebuf.com/jobs";
                            break;
                        case 6:
                            sBasePath="https://www.freebuf.com/paper";
                            break;
                        default: break;
                    }

                }

                Intent intent=new Intent();
                intent.setClass(getActivity(),SubReadItemActivity.class);
                intent.putExtra("path",sBasePath);
                startActivity(intent);

                return true;
            }
        });
        return view;
    }

    public class myExpListAdapter  implements ExpandableListAdapter{

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public int getGroupCount() {
            return sItem.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return sSubItem[groupPosition].length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return sItem[groupPosition];
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return sSubItem[groupPosition][childPosition];
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
            View v=LayoutInflater.from(getContext()).inflate(R.layout.expandlist_item,null,false);
            TextView itemText=(TextView)v.findViewById(R.id.exp_detail_text);
            itemText.setText(getGroup(groupPosition).toString());
            ImageView itemImage=(ImageView)v.findViewById(R.id.exp_detail_image);
            if(isExpanded){
                itemImage.setImageResource(R.drawable.arrowl);
            }else{
                itemImage.setImageResource(R.drawable.arrowr);
            }

            return v;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View v=LayoutInflater.from(getContext()).inflate(R.layout.expandlist_subitem,null,false);
            TextView subitemText=(TextView)v.findViewById(R.id.exp_sub_item);
            subitemText.setText(getChild(groupPosition, childPosition).toString());
            return v;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public void onGroupExpanded(int groupPosition) {

        }

        @Override
        public void onGroupCollapsed(int groupPosition) {

        }

        @Override
        public long getCombinedChildId(long groupId, long childId) {
            return 0;
        }

        @Override
        public long getCombinedGroupId(long groupId) {
            return 0;
        }
    }
}
