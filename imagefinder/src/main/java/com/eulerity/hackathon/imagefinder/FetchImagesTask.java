package com.eulerity.hackathon.imagefinder;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.util.*;

public class FetchImagesTask implements Runnable {
    String url;
    Set<String> imageURLs = null;

    public FetchImagesTask(String url) {
        this.url = url;
    }

    /**
     * Fetches for all the images in the URL
     * @throws IOException
     */
    public void fetchAndStore() throws IOException{
        try {
            System.out.println("here to fetch img of url: " + this.url);
            imageURLs = new LinkedHashSet<>();
            Document doc = Jsoup.connect(url).get();
            for (Element e : doc.select("img")) {
                imageURLs.add(e.attr("src"));
            }
        } catch (
                HttpStatusException e) {
            System.out.println("Url rejected: " + url);
        } catch (IOException e) {
            System.out.println("Url rejected" + url);
        }
    }

    /**
     * Put all the urls in threadpoolwrapper- compute parallely
     *
     */
    @Override
    public void run() {
        try {
            fetchAndStore();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



