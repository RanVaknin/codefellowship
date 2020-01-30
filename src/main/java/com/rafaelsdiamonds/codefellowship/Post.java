package com.rafaelsdiamonds.codefellowship;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String body;
    private String timeStamp;


    @ManyToOne
    private ApplicationUser applicationUser;

    public Post(String body, String timeStamp,ApplicationUser applicationUser) {
        this.body = body;
        this.timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
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
