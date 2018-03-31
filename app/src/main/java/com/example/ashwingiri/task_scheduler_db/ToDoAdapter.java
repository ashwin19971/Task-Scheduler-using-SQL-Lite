package com.example.ashwingiri.task_scheduler_db;

import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashwingiri.task_scheduler_db.Holder.Todo;
import com.example.ashwingiri.task_scheduler_db.db.MyDbHelper;

import java.util.ArrayList;

import static com.example.ashwingiri.task_scheduler_db.R.*;

/**
 * Created by Ashwin Giri on 3/29/2018.
 */

class ToDoAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<Todo> task_list;
    MyDbHelper myDbHelper;

    ToDoAdapter(Context context, ArrayList<Todo> task_list) {
        mContext=context;
        this.task_list=task_list;
    }

    @Override
    public int getCount() {
        return task_list.size();
    }

    @Override
    public Todo getItem(int i) {
        return task_list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {
        myDbHelper=new MyDbHelper(mContext);

        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(layout.task_list, parent, false);
        }

        ((TextView)(convertView.findViewById(R.id.tvTask))).setText(task_list.get(i).getTask());
        ((CheckBox)(convertView.findViewById(R.id.cbToDo))).setChecked(task_list.get(i).isChecked());
        ((CheckBox)(convertView.findViewById(R.id.cbToDo))).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDbHelper.updateTask(task_list.get(i).getTask(),!task_list.get(i).isChecked());
                        refresh();
                    }
                }
        );
        ((ImageButton)(convertView.findViewById(R.id.btDel))).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog d=new Dialog(mContext);
                        d.setContentView(layout.remove_dialogbox);
                        d.setTitle("Delete the task?");
                        d.show();
                        d.findViewById(R.id.btYesRemove).setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        myDbHelper.removeTodo(task_list.get(i).getTask());
                                        refresh();
                                        d.cancel();
                                        Toast.makeText(mContext,"Your task is deleted successfully",Toast.LENGTH_SHORT).show();
                                    }
                                }
                        );
                        d.findViewById(id.btNoRemove).setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        d.cancel();
                                    }
                                }
                        );
                    }
                }
        );
        return convertView;
    }

    void refresh() {
        myDbHelper=new MyDbHelper(mContext);
        task_list.clear();
        task_list.addAll(myDbHelper.getAllTodos());
        notifyDataSetChanged();
    }
}
