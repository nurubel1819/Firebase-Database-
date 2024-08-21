package com.example.firebasedatabase;

public class image_path_db {
    String image_url;
    String name;

    public image_path_db(String image_url, String image_name) {
        this.image_url = image_url;
        this.name = image_name;
    }
    public image_path_db()
    {

    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
