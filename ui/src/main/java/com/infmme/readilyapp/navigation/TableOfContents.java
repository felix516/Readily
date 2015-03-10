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

    private ArrayList<Section> sections = new ArrayList<Section>();

    public TableOfContents(Book data) {
        buildTOC(data);
    }

    private void buildTOC(Book data) {
        List<TOCReference> referenceList = data.getTableOfContents().getTocReferences();

        for (TOCReference r : referenceList) {
            sections.add(new Section(r));
        }
    }

    private void buildTOC() {

    }

    public static int getItemIndex(List<Resource> resources, String href){

        for (int i = 0; i < resources.size(); i++) {
            if (resources.get(i).getHref().equals(href)) {
                return i;
            }
        }
        return -1;
    }



}
