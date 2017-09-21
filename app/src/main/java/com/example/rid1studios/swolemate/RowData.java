package com.example.rid1studios.swolemate;

/**
 * Created by rid on 9/20/17.
 */

public class RowData {

    private String title;
    private String subtitle;

    public RowData(String titleIn, String subtitleIn){
        title = titleIn;
        subtitle = subtitleIn;
    }

    public String getSubtitle() {
        return subtitle;
    }
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

}
