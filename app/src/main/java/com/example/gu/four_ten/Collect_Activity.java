package com.example.gu.four_ten;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.AndroidCharacter;
import android.text.style.ReplacementSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gu.four_ten.common.CollectInfo;
import com.example.gu.four_ten.common.CollectSqlite;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by GU on 2016/10/7.
 */
public class Collect_Activity extends ListActivity {

    private ArrayList<String> lists = new ArrayList<String>();
    private CollectSqlite sqlite = new CollectSqlite(this);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection_layout);

        final List<String> dataList = getData();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,dataList);
        setListAdapter(adapter);

//        lvCollect = (ListView)findViewById(R.id.lv_collect);
//        CollectSqlite sqlite = new CollectSqlite(this);
//        List<CollectInfo> lists = sqlite.findAll();
//        if (lists.size()>0){
//            lvCollect.setText(lists.get(0).getTitle());
//        }
        ListView lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CollectInfo info = sqlite.find(dataList.get(position));
                Uri uri = Uri.parse(info.getUrl());
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
//                Toast.makeText(Collect_Activity.this,info.getUrl(),Toast.LENGTH_LONG).show();
            }
        });
        ListView lvLong = getListView();
        lvLong.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Collect_Activity.this);
                builder.setTitle("提示");
                builder.setMessage("确定删除？");

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CollectInfo info = sqlite.find(dataList.get(position));
                        sqlite.delete(info.getTitle());
                        finish();
                        Intent intent = new Intent(Collect_Activity.this,Collect_Activity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
                return true;
            }
        });

    }
    public List<String> getData(){

        List<CollectInfo> list = sqlite.findAll();
        for (CollectInfo coloumn:list
             ) {
            lists.add(coloumn.getTitle());
        }
        return lists;
    }

}
