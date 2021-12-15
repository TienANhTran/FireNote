package com.example.firenote.model;

public class NoteProperty {
    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public NoteProperty(String title, String content) {
        this.title = title;
        this.content = content;
    }
    public  NoteProperty()
    {
    }
}
