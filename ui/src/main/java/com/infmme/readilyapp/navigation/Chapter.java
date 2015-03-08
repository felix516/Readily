package com.infmme.readilyapp.navigation;

/**
 * Created by Dyan on 3/6/2015.
 * A chapter should have no children
 */
public class Chapter {
    //Which word the chapter starts on;
    private int byteStart;
    //Which word the chapter ends on;
    private int byteEnd;
    //Title of Chapter
    private String title;

    public Chapter(String title) {
        this.title = title;
    }

    public int getByteStart() {
        return byteStart;
    }

    public int getByteEnd() {
        return byteEnd;
    }

    public String getTitle() {
        return title;
    }
}
