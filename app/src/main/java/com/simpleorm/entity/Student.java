package com.simpleorm.entity;

import com.liteorm.anno.ColumnName;
import com.liteorm.anno.ID;
import com.liteorm.anno.TableName;

import java.io.Serializable;

/**
 * Created by guofeng
 * on 2017/6/21.
 */
@TableName(name = "Student")
public class Student implements Serializable {
    @ID
    private int id;
    @ColumnName(name = "teacher")
    private Teacher teacher;
    @ColumnName(name = "name")
    private String name;
    @ColumnName(name = "age")
    private int age;

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "\nStudent{" +
                "id=" + id +
                ", teacher=" + teacher +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
