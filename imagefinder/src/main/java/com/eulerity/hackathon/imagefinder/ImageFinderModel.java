package com.eulerity.hackathon.imagefinder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ImageFinderModel {
    Document doc;
    static HashMap<String, ArrayList<String>> map = new HashMap<>();
    ArrayList<FetchImagesTask> links = new ArrayList<>();

    ThreadPoolWrapper threadPoolWrapper = new ThreadPoolWrapper();

    public void doSearch(String url)
            throws IOException {
        doc = Jsoup.connect(url).get();
         // String[] elements = {"img", "link"};
        // for (String s:elements) {
            fetchUrls(doc);
         threadPoolWrapper.computeParallely(links);
        // }
    }

    public void fetchUrls(Document doc) throws IOException {
//        ArrayList<String> list = new ArrayList<>();
//        if (tag.equalsIgnoreCase("img")) {
//            for (Element e : doc.select("img")) {
//                // System.out.println("printing"+e.attr("src"));
//                list.add(e.attr("src"));
//            }
//            map.put("images", list);
//        }
//        else if(tag.equalsIgnoreCase("link")) {
            for (Element e : doc.select("a")) {
                System.out.println("printing"+e.absUrl("href"));
                links.add(new FetchImagesTask(e.absUrl("href")));
            }
            System.out.println(links
                    .toString());
            // map.put("links", list);
        }



}
