package com.webcrawler.jsoupexample;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements; //this 4 imports has error now, days ago didn't have error
/*SOLUTION FOR BUILD FAILURE
  in the project file, Dependencies -> jsoup-1.13.1.jar -> right click 
  -> "Manually install artifact", then selecting the jsoup jar file*/

import java.io.IOException;
import java.util.ArrayList;

public class ParserEngine {

    private String baseUrl;
    private ArrayList<String> urlList;

    public ParserEngine(String baseUrl){
        this.baseUrl = baseUrl;
        this.urlList = new ArrayList<String>();
    }

    public void crawl(String url) throws IOException {
        Document doc = Jsoup.connect(url).ignoreContentType(true).get();
        Elements links = doc.select("a[href]");

        //here I found the problem cause the crawler doen't work, but it isn't
        // my actual issue, i want to make it run again
        for (Element link : links) {
            String actualUrl = link.attr("abs:href"); 

            if (!urlList.contains(actualUrl) && actualUrl.startsWith(baseUrl)){
                print(" * a: <%s>  (%s)", actualUrl, trim(link.text(), 35));
                urlList.add(actualUrl);
                crawl(actualUrl);
            }
        }
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }

    public String getBaseUrl(){
        return baseUrl;
    }

    public void setBaseUrl(String url){
        baseUrl = url;
    }

    public ArrayList<String> getUrlList(){
        return urlList;
    }

}
