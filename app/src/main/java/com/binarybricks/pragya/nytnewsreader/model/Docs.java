package com.binarybricks.pragya.nytnewsreader.model;

import java.util.ArrayList;

/**
 * Created by PRAGYA on 10/22/2016.
 */
public class Docs {

    private Headline headline;

    private String pub_date;

    private String web_url;

    private String news_desk;

    private String snippet;

    private String type_of_material;

    private String section_name;

    private ArrayList<Multimedia> multimedia;

    private String _id;

    private String source;

    private String lead_paragraph;

    public Headline getHeadline() {
        return headline;
    }

    public void setHeadline(Headline headline) {
        this.headline = headline;
    }

    public String getPub_date() {
        return pub_date;
    }

    public void setPub_date(String pub_date) {
        this.pub_date = pub_date;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public String getNews_desk() {
        return news_desk;
    }

    public void setNews_desk(String news_desk) {
        this.news_desk = news_desk;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getType_of_material() {
        return type_of_material;
    }

    public void setType_of_material(String type_of_material) {
        this.type_of_material = type_of_material;
    }

    public String getSection_name() {
        return section_name;
    }

    public void setSection_name(String section_name) {
        this.section_name = section_name;
    }

    public ArrayList<Multimedia> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(ArrayList<Multimedia> multimedia) {
        this.multimedia = multimedia;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLead_paragraph() {
        return lead_paragraph;
    }

    public void setLead_paragraph(String lead_paragraph) {
        this.lead_paragraph = lead_paragraph;
    }
}
