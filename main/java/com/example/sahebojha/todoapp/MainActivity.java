package com.example.sahebojha.todoapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Dbhelper dbhelper;
    ArrayAdapter<String> mAdapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        dbhelper = new Dbhelper(this);
        loadTask();
    }
    // dialog box for exit
    @Override
    public void onBackPressed() {
        AlertDialog.Builder abd = new AlertDialog.Builder(MainActivity.this);
        abd.setMessage("Do u really want to Exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setTitle("Wait");
        abd.show();
    }

    //add icon to bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);

    }
    //load all task
    private  void loadTask(){
        ArrayList<String> tasklist = dbhelper.getTaskList();
        if (mAdapter== null){
            mAdapter = new ArrayAdapter<String>(this,R.layout.row,R.id.text_title,tasklist);
            listView.setAdapter(mAdapter);
        }else{
            mAdapter.clear();
            mAdapter.addAll(tasklist);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.addTask:
                final EditText editText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add new task")
                        .setTitle("What's your Task")
                        .setView(editText)
                        .setPositiveButton("add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(editText.getText());
                                dbhelper.insertNewTask(task);

                                //load all task
                                loadTask();

                            }
                        })
                        .setNegativeButton("CANCEL",null)
                        .create();
                dialog.show();
                return true;
        }
        return super.onContextItemSelected(item);
    }


    public void deleteTask(View view){
        try{
            int index = listView.getPositionForView(view);
            String task = mAdapter.getItem(index++);
            dbhelper.deleteTask(task);
            loadTask();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage().toString(),Toast.LENGTH_SHORT).show();
        }

    }




}
