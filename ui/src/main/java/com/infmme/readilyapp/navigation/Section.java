package com.infmme.readilyapp.navigation;

import java.util.ArrayList;

import nl.siegmann.epublib.domain.TOCReference;

/**
 * Created by Dylan on 3/6/2015.
 * A section of a book
 * generally contains child chapters
 * may contain subSections with their own chapters
 */
public class Section {
    //Same as first chapter start?
    private int byteStart;
    //Same as last chapter end?
    private int byteEnd;
    private String title;
    private ArrayList<Chapter> chapters = new ArrayList<Chapter>();
    private ArrayList<Section> subSections = new ArrayList<Section>();

    public Section(TOCReference reference) {
        this.title = reference.getTitle();
        FillSection(reference);
    }

    private void FillSection(TOCReference reference) {

        for (TOCReference r : reference.getChildren()) {
            //If the reference has children then create a subsection
            if (r.getChildren().size() > 1) {
                subSections.add(new Section(r));
            }
            //If the item only has one child, assume chapter # + title
            else if (r.getChildren().size() == 1) {
                chapters.add(new Chapter(r.getTitle() + " - " + r.getChildren().get(0).getTitle(),
                        r.getCompleteHref(),r.getFragmentId()));
            }
            //Otherwise create a Chapter
            else {
                chapters.add(new Chapter(r.getTitle(),r.getCompleteHref(),r.getFragmentId()));
            }
        }
    }

    public int getByteStart() {
        return byteStart;
    }

    public int getByteEnd() {
        return byteEnd;
    }

    public ArrayList<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(ArrayList<Chapter> chapters) {
        this.chapters = chapters;
    }

    public ArrayList<Section> getSubSections() {
        return subSections;
    }

    public void setSubSections(ArrayList<Section> subSections) {
        this.subSections = subSections;
    }
}
