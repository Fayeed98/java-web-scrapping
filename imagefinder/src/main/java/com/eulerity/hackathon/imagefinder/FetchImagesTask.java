package com.eulerity.hackathon.imagefinder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.util.*;

public class FetchImagesTask implements Runnable{
    String url;
    Set<String> imageURLs = null;

    public FetchImagesTask(String url){
        this.url = url;
    }

    public void fetchAndStore() throws IOException {
        System.out.println("here to fetch img of url: "+ this.url);
        Document doc = Jsoup.connect(url).get();
        imageURLs = new LinkedHashSet<>();
        for (Element e : doc.select("img")) {
            System.out.println("printing: "+e.attr("src"));
            imageURLs.add(e.attr("src"));
        }
    }

    // Put all the urls in threadpoolwrapper- compute parallely
    @Override
    public void run() {
        try {
            fetchAndStore();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



