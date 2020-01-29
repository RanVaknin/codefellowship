package com.rafaelsdiamonds.codefellowship;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String body;
    private String timeStamp;


    @ManyToOne
    private ApplicationUser applicationUser;

    public Post(String body, String timeStamp,ApplicationUser applicationUser) {
        this.body = body;
        this.timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        this.applicationUser = applicationUser;
    }

    public Post() {
    }


    public Long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

}
