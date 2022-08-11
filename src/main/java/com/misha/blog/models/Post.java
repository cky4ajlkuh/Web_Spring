package com.misha.blog.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Setter
@Getter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title, text, anons;
    private int views;

    public Post() {
    }

    public Post(String title, String anons, String text) {
        this.title = title;
        this.anons = anons;
        this.text = text;
    }
}
