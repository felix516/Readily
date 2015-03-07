package com.infmme.readilyapp.navigation;

import java.util.ArrayList;

/**
 * Created by Dylan on 3/6/2015.
 * A section of a book
 * generally contains child chapters
 */
public class Section {
    //Same as first chapter start?
    private int byteStart;
    //Same as last chapter end?
    private int byteEnd;
    private ArrayList<Chapter> chapters;
}
