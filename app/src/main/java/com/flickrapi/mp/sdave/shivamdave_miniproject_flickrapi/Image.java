package com.flickrapi.mp.sdave.shivamdave_miniproject_flickrapi;

// Will be the object that the JSON is parsed into
// Contains title, link, and image because those are the only
// variables needed (image is the main one we need, title will
// be used for testing purposes)

import java.io.Serializable;

public class Image implements Serializable{
    private static final long serialVersionUID = 1L;
    private String _title;
    private String _link;
    private String _image;

    public Image(String _title, String _link, String _image) {
        this._title = _title;
        this._link = _link;
        this._image = _image;
    }

    public String get_title() {
        return _title;
    }

    public String get_link() {
        return _link;
    }

    public String get_image() {
        return _image;
    }

    @Override
    public String toString() {
        return "Image{" +
                "_title='" + _title + '\'' +
                ", _link='" + _link + '\'' +
                ", _image='" + _image + '\'' +
                '}';
    }
}
