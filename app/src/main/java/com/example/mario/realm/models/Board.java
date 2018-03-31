package com.example.mario.realm.models;

import com.example.mario.realm.MyApplication;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by mario on 24/03/2018.
 */

public class Board extends RealmObject {

    @PrimaryKey
    private int id;

    @Required
    private String title;

    @Required
    private Date createdAT;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreatedAT() {
        return createdAT;
    }

    public void setCreatedAT(Date createdAT) {
        this.createdAT = createdAT;
    }

    public Board(){



    }

    public Board(String title){

    this.id = MyApplication.BoardId.incrementAndGet();
    this.title = title;
    this.createdAT = new Date();


    }



}
