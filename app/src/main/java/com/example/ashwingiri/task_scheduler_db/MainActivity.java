package com.example.ashwingiri.task_scheduler_db;

import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ashwingiri.task_scheduler_db.Holder.Todo;
import com.example.ashwingiri.task_scheduler_db.db.MyDbHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Todo> lvTask=new ArrayList<>();
    ListView lvDetails;
    ToDoAdapter todoadapter;
    MyDbHelper myDbHelper;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvDetails = findViewById(R.id.lvTodoList);
        todoadapter = new ToDoAdapter(this, lvTask);
        lvDetails.setAdapter(todoadapter);
        myDbHelper = new MyDbHelper(this);
        refresh();
        fab = findViewById(R.id.fab);


        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog d = new Dialog(MainActivity.this);
                        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        d.setContentView(R.layout.add_dialogbox);
                        d.show();
                        Button btY = d.findViewById(R.id.btYesAdd);
                        Button btN = d.findViewById(R.id.btNoAdd);


                        btY.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        EditText etTask = d.findViewById(R.id.etTask);
                                        d.cancel();
                                        if (!d.equals("")) {
                                            myDbHelper.insertTodo(new Todo(etTask.getText().toString(), false));
                                            refresh();
                                            etTask.setText("");
                                            Toast.makeText(getApplicationContext(), "Your task is added successfully", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Enter the task", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                        );
                        btN.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        ((EditText) d.findViewById(R.id.etTask)).setText("");
                                        d.cancel();
                                    }
                                }
                        );

                    }
                }
        );
    }

    void refresh() {
        myDbHelper=new MyDbHelper(this);
        ArrayList<Todo> todos = myDbHelper.getAllTodos();
        lvTask.clear();
        lvTask.addAll(todos);
        todoadapter.notifyDataSetChanged();
    }


}
