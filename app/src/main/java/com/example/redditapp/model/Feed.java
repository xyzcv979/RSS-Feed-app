/*
*
*
*
 */

package com.example.redditapp.model;

import androidx.annotation.NonNull;
import com.example.redditapp.model.entry.Entry;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

@Root(name = "feed", strict = false) // This tells retrofit library it doesn't need all tags
public class Feed implements Serializable {

    @Element(name = "icon") // Needed to reference xml element tag directly
    private String icon;

    @Element(name = "id")
    private String id;

    @Element(name = "logo")
    private String logo;

    @Element(name = "title")
    private String title;

    @Element(name = "updated")
    private String updated;

    @ElementList(inline = true, name = "entry") // Need ElementList for lists
    private List<Entry> entries;


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    // override toString because output returns memory location
    @NonNull
    @Override
    public String toString() {
        return "Feed: \n [Entries: \n" + entries + "]";
    }
}
