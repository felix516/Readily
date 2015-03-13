package com.infmme.readilyapp.navigation;

import java.util.ArrayList;
import java.util.List;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.TOCReference;

/**
 * Created by Dylan on 3/6/2015.
 * Our Table of contents will be very basic, containing a list of start and stop points
 */
public class TableOfContents {

    private String title;
    private String filepath;
    private ArrayList<Section> sections = new ArrayList<Section>();
    private Book data;


    public TableOfContents(Book data , String filepath) {

        this.filepath = filepath;
        this.data = data;
        buildTOC();
    }

    public String getTitle() {
        return title;
    }

    public String getFilepath() {
        return filepath;
    }

    public ArrayList<Section> getSections() {
        return sections;
    }

    private void buildTOC() {
        this.title = data.getTitle();
        List<TOCReference> referenceList = data.getTableOfContents().getTocReferences();

        for (TOCReference r : referenceList) {
            sections.add(new Section(r));
        }
    }


    public  int getItemIndex(String href){

        List<Resource> resourceList = data.getContents();
        for (int i = 0; i < resourceList.size(); i++) {
            if (resourceList.get(i).getHref().equals(href)) {
                return i;
            }
        }
        return -1;
    }



}
