package com.eulerity.hackathon.imagefinder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

public class ImageFinderModel {
    Document doc;
    static HashMap<String, ArrayList<String>> map = new HashMap<>();

    public void doSearch(String url)
            throws IOException {
        doc = Jsoup.connect(url).get();
        String[] elements = {"img", "link"};
        for (String s:elements) {
            fetch(doc, s);
        }
    }

    public void fetch(Document doc, String tag) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        if (tag.equalsIgnoreCase("img")) {
            for (Element e : doc.select("img")) {
                // System.out.println("printing"+e.attr("src"));
                list.add(e.attr("src"));
            }
            map.put("images", list);
        }
        else if(tag.equalsIgnoreCase("link")) {
            for (Element e : doc.select("a")) {
                System.out.println("printing"+e.absUrl("href"));
                list.add(e.absUrl("href"));
            }
            map.put("links", list);
        }
    }


}
