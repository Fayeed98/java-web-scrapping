package com.eulerity.hackathon.imagefinder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FetchImagesTask implements Runnable{
    String url;
    List<String> imageURLs = null;

    public FetchImagesTask(String url){
        this.url = url;
    }


    public void fetchAndStore() throws IOException {
        Document doc = Jsoup.connect(url).get();
        imageURLs = new ArrayList<>();
        for (Element e : doc.select("img")) {
            // System.out.println("printing"+e.attr("src"));
            imageURLs.add(e.attr("src"));
        }
    }

    @Override
    public void run() {
        try {
            fetchAndStore();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// get all anchor tags -- all urls
// for all urls create a list of fetch images task objects
// then put it in threadpoolwrapper. compute parallely