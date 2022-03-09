package com.eulerity.hackathon.imagefinder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ImageFinderModel {

    public String[] doSearch(String url)
            throws IOException {
        ArrayList<String> list2 = new ArrayList<>();
        list2 = fetch(url);

        return list2.toArray(new String[list2.size()]);
    }

    public ArrayList<String> fetch(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        ArrayList<String> list = new ArrayList<>();
        for (Element e : doc.select("img")) {
            // System.out.println("printing"+e.attr("src"));
            list.add(e.attr("src"));
        }
        return list;
    }
}
