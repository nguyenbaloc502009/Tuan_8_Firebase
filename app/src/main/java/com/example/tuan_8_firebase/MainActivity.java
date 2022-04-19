package com.example.tuan_8_firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import  com.example.tuan_8_firebase.UserAdapter;
import com.example.tuan_8_firebase.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<User> userList;
    private com.example.tuan_8_firebase.UserAdapter userAdapter;
    private ListView listView;
    private com.example.tuan_8_firebase.UserDB userDB;
    private Button btnAdd, btnRemove, btnCancel;
    private EditText tpName;
    View oldViewSelected = null;
    int itemSelected = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        this.initUi();
        List<User> list = new ArrayList<User>();
        User user = new User("Nguyen Van A");
        User user1 = new User("Ho Cong Viet");
        User user2 = new User("Ho Cong B");
        userDB.getInstance(this).userDao().insertAll(user);
        userDB.getInstance(this).userDao().insertAll(user1);
        userDB.getInstance(this).userDao().insertAll(user2);
        userList = userDB.getInstance(this).userDao().getAll();
        userAdapter = new UserAdapter(
                this,
                R.layout.list_items,
                userList
        );
        listView.setAdapter(userAdapter);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setBackgroundColor(getResources().getColor(R.color.white));
                oldViewSelected = view;
                System.out.println(i);
                itemSelected = i;
            }

        });
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tpName.setText("");
            }
        });
    }
    private void addUser() {
        String name = tpName.getText().toString().trim();
        if(TextUtils.isEmpty(name)){
            return;
        }
        User user2 = new User(name);
        userDB.getInstance(this).userDao().insertAll(user2);
        tpName.setText("");
        userAdapter.addItem(user2);
    }
    private void deleteUser(){
        if(itemSelected == -1){
            Toast.makeText(this, "You must select a person to delete!", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = userList.get(itemSelected);
        System.out.print(user);
        userDB.getInstance(this).userDao().delete(user);
        userAdapter.removeItem(user);
        oldViewSelected.setBackgroundColor(0);
        oldViewSelected = null;
        itemSelected = -1;
        Toast.makeText(this, "Remove successfully!", Toast.LENGTH_SHORT).show();
    }
    public void initUi(){
        btnAdd = findViewById(R.id.btnAdd);
        btnRemove = findViewById(R.id.btnRemove);
        btnCancel = findViewById(R.id.btnCancel);
        tpName = findViewById(R.id.editText);
    }

}
