package com.eulerity.hackathon.imagefinder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.util.*;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

public class ImageFinderModel {
    Document doc;

    Set<FetchImagesTask> fetchImagesTasksList = new LinkedHashSet<>();

    ThreadPoolWrapper threadPoolWrapper = new ThreadPoolWrapper();

    public void doSearch(String url) throws IOException {
        // try {
            doc = Jsoup.connect(url).get();
            fetchUrls(doc);
            List<FetchImagesTask> list = new ArrayList<>();
            list.addAll(fetchImagesTasksList);
            threadPoolWrapper.computeParallely(list);
//        }
//        catch (IOException e) {
//
//        }
    }

    // get all anchor tags -- all urls
    public void fetchUrls(Document doc) throws IOException {
            for (Element e : doc.select("a")) {
                System.out.println( "url is; "+e.absUrl("href"));
                // for all urls create a list of fetch images task objects
                fetchImagesTasksList.add(new FetchImagesTask(e.absUrl("href")));
            }
        }
}
