package com.eulerity.hackathon.imagefinder;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.LinkedHashSet;
import java.util.Set;

public class ImageFinderModel {
    Document doc;
    String domainURL;

    Set<FetchImagesTask> fetchImagesTasksList = new LinkedHashSet<>();
    ThreadPoolWrapper threadPoolWrapper = new ThreadPoolWrapper();

    /**
     *Search method to crawl the given URL
     *
     * @param url
     * @throws IOException
     */
    public void doSearch(String url) throws IOException {
        try {
            domainURL = url;
            doc = Jsoup.connect(url).get();
            fetchUrls(doc);
            List<FetchImagesTask> list = new ArrayList<>();
            list.addAll(fetchImagesTasksList);
            threadPoolWrapper.computeParallely(list);
        } catch (HttpStatusException e) {
            System.out.println("Url rejected: " + url);
        } catch (IOException e) {
            System.out.println("Url rejected" + url);
        }
    }

    /**
     * Fetch method to get all the urls embedded in the website
     * @param doc
     * @throws IOException
     */
    public void fetchUrls(Document doc) throws IOException {
        for (Element e : doc.select("a")) {
            // for all urls create a list of fetch images task objects for same domain
            if (isURLSameDomain(e.absUrl("href"))) {
                fetchImagesTasksList.add(new FetchImagesTask(e.absUrl("href")));
            }
        }
    }

    /**
     * This function checks i the URL belongs to same domain or not
     * @param imageLink
     * @return
     * @throws MalformedURLException
     */
    public boolean isURLSameDomain(String imageLink) throws MalformedURLException {
        URL imageURL = new URL(imageLink);
        URL domainURI = new URL(domainURL);

        if (imageURL.getHost().equalsIgnoreCase(domainURI.getHost()))
            return true;
        return false;
    }
}
