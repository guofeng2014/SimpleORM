package com.simpleorm;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.liteorm.DbConfig;
import com.liteorm.DbFactory;
import com.liteorm.sqlite.SqliteUtil;
import com.simpleorm.entity.Student;
import com.simpleorm.entity.Teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<Student> studentList = new ArrayList<>();
        final TextView tvContent = (TextView) findViewById(R.id.tv_content);

        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 4; i++) {
                    Student s = new Student();
                    s.setAge(10);
                    s.setName("学生");
                    Teacher t = new Teacher();
                    t.setName("张老师");
                    t.setAge(40);
                    t.setAddress("北京市");
                    s.setTeacher(t);
                    studentList.add(s);
                }
                DbFactory.getInstance().save(studentList);

            }
        });
        findViewById(R.id.btn_query).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                List<Student> studentList = DbFactory.getInstance().query(Student.class);
                tvContent.setText(studentList.toString());
                tvContent.setMovementMethod(new ScrollingMovementMethod());
            }
        });

        findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DbFactory.getInstance().delete(studentList);
            }
        });
    }
}
