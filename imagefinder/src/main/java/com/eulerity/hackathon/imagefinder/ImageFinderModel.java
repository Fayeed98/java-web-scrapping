package com.eulerity.hackathon.imagefinder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ImageFinderModel {
    Document doc;
    static HashMap<String, ArrayList<String>> map = new HashMap<>();
    ArrayList<FetchImagesTask> fetchImagesTasksList = new ArrayList<>();

    ThreadPoolWrapper threadPoolWrapper = new ThreadPoolWrapper();

    public void doSearch(String url)
            throws IOException {
        doc = Jsoup.connect(url).get();
         // String[] elements = {"img", "link"};
        // for (String s:elements) {
            fetchUrls(doc);
         threadPoolWrapper.computeParallely(fetchImagesTasksList);
        // }
    }

    public void fetchUrls(Document doc) throws IOException {
            for (Element e : doc.select("a")) {
                fetchImagesTasksList.add(new FetchImagesTask(e.absUrl("href")));
            }
            System.out.println( "All the link are create");
        }

}
