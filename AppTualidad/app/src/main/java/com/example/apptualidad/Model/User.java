package com.example.apptualidad.Model;

import java.util.Arrays;

public class User {

    private String id;
    private String name;
    private String picture;
    private String email;
    private String role;
    private String favs[];
    private String noticias[];

    public User(String id, String name, String picture, String email, String role, String[] favs, String[] noticias) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.email = email;
        this.role = role;
        this.favs = favs;
        this.noticias = noticias;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String[] getFavs() {
        return favs;
    }

    public void setFavs(String[] favs) {
        this.favs = favs;
    }

    public String[] getNoticias() {
        return noticias;
    }

    public void setNoticias(String[] noticias) {
        this.noticias = noticias;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", favs=" + Arrays.toString(favs) +
                ", noticias=" + Arrays.toString(noticias) +
                '}';
    }
}

