package com.binarybricks.pragya.nytnewsreader.model;

/**
 * Created by PRAGYA on 10/22/2016.
 */

public class ArticleSearchResponse
{
    private Response response;

    private String status;

    private String copyright;

    public Response getResponse ()
    {
        return response;
    }

    public void setResponse (Response response)
    {
        this.response = response;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getCopyright ()
    {
        return copyright;
    }

    public void setCopyright (String copyright)
    {
        this.copyright = copyright;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [response = "+response+", status = "+status+", copyright = "+copyright+"]";
    }
}

