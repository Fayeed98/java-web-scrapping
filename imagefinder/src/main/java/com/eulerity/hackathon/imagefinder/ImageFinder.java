package com.eulerity.hackathon.imagefinder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@WebServlet(
    name = "ImageFinder",
    urlPatterns = {"/main"}
)
public class ImageFinder extends HttpServlet{

	ImageFinderModel ifm = null;  // The "business model" for this app
	ThreadPoolWrapper threadPoolWrapper;
	// Initiate this servlet by instantiating the model that it will use.
	@Override
	public void init() {
		threadPoolWrapper = new ThreadPoolWrapper();
	}
	private static final long serialVersionUID = 1L;

	protected static final Gson GSON = new GsonBuilder().create();

	//This is just a test array
	public static final String[] testImages = {
			"https://images.pexels.com/photos/545063/pexels-photo-545063.jpeg?auto=compress&format=tiny",
			"https://images.pexels.com/photos/464664/pexels-photo-464664.jpeg?auto=compress&format=tiny",
			"https://images.pexels.com/photos/406014/pexels-photo-406014.jpeg?auto=compress&format=tiny",
			"https://images.pexels.com/photos/1108099/pexels-photo-1108099.jpeg?auto=compress&format=tiny"
  };

	@Override
	protected final void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/json");
		String path = req.getServletPath();
		String url = req.getParameter("url");
		ifm = new ImageFinderModel();
		System.out.println("Got request of:" + path + " with query param:" + url);

		ifm.doSearch(url);

		Set<String> allImageURLlist = new LinkedHashSet<>();
		// System.out.println("size is"+ ifm.fetchImagesTasksList.size());
		for(FetchImagesTask link : ifm.fetchImagesTasksList){
			// System.out.println("out of threads now: "+ link.);
			for(String s: link.imageURLs) {
				System.out.println("img is"+ s);
				if(isValid(s)) {
					allImageURLlist.add(s);
					// System.out.println("out of threads now: "+ s);
				}
			}
		}

		System.out.println("Arryalist is"+ allImageURLlist);

		resp.getWriter().print(GSON.toJson(allImageURLlist.toArray()));
	}

	private static boolean isValid(String url) {
		String urlPattern = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		Pattern pattern = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(url);
		return matcher.matches();
	}
}
