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

    private String href;
    private String fragmentId;


    public Chapter(String title, String href, String fragmentId) {
        this.title = title;
        this.href = href;

        this.fragmentId = fragmentId;
    }


    public String getHref() {
        return href;
    }

    public String getFragmentId() {
        return fragmentId;
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
