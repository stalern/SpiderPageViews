package com.stalern.spiderpageviews.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

/**
 * @author stalern
 * @date 2019/10/28--20:20
 */
public class HtmlUtils {
    private HtmlUtils() {}
    /**
     * 把本地的HTML页面变为document
     * @return document
     */
    public static Document html2Doc() {
        String path = System.getProperty("user.dir") + File.separator + "src\\main\\resources\\";
        String fileName = "wall.html";
        Document document = null;
        try {
            document = Jsoup.parse(new File(path + fileName), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }
}
