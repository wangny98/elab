package com.device.business.docManager.element;

import java.util.Date;

import javax.ws.rs.FormParam;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.device.util.DateFormat;

public class ScienceElement {

    private String id;

    @FormParam("name")
    private String name;

    @FormParam("author_1")
    private String author_1;

    @FormParam("author_2")
    private String author_2;

    @FormParam("author_3")
    private String author_3;

    @FormParam("org_name")
    private String org_name;

    @FormParam("publication")
    private String publication;

    @FormParam("publication_time")
    @DateFormat("yyyy-MM-dd")
    private Date publication_time;

    @FormParam("key_word")
    private String key_word;

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor_1() {
        return author_1;
    }

    public void setAuthor_1(String author_1) {
        this.author_1 = author_1;
    }

    public String getAuthor_2() {
        return author_2;
    }

    public void setAuthor_2(String author_2) {
        this.author_2 = author_2;
    }

    public String getAuthor_3() {
        return author_3;
    }

    public void setAuthor_3(String author_3) {
        this.author_3 = author_3;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public Date getPublication_time() {
        return publication_time;
    }

    public void setPublication_time(Date publication_time) {
        this.publication_time = publication_time;
    }

    public String getKey_word() {
        return key_word;
    }

    public void setKey_word(String key_word) {
        this.key_word = key_word;
    }
}
