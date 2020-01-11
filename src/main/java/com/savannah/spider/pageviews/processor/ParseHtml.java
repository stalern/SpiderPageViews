package com.savannah.spider.pageviews.processor;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

/**
 * 解析html
 * @author stalern
 * @date 2019/10/28--20:30
 */
public class ParseHtml {

    public Map<String, String> jSoupParseHtml(Document document) {
        Map<String, String> map = new HashMap<>(20);
        Element list = document.getElementById("host_home_feeds");
        Elements postItems = list.getElementsByClass("f-single");
        for (Element postItem : postItems) {
            String text = postItem.text();
            StringBuilder date = new StringBuilder();
            StringBuilder browses = new StringBuilder();
            int i = 0;
            for (; i < text.length(); i ++) {
                if (text.charAt(i) == ':') {
                    date.append(text.charAt(i - 2))
                            .append(text.charAt(i - 1));
                    // 省略分钟
//                            .append(text.charAt(i))
//                            .append(text.charAt(i + 1))
//                            .append(text.charAt(i + 2));
                    break;
                }
            }
            for (; i < text.length(); i ++) {
                if ("浏览".equals(text.substring(i, i + 2))) {
                    boolean flag = true;
                    while ((text.charAt(i) >= '0' && text.charAt(i) <= '9') || flag) {
                        if (text.charAt(i) >= '0' && text.charAt(i) <= '9') {
                            browses.append(text.charAt(i++));
                            flag = false;
                        } else {
                            i ++;
                        }
                    }
                    break;
                }
            }
            String value;
            if (map.get(date.toString()) == null) {
                value = browses.toString();
            } else {
                value = String.valueOf(Integer.parseInt(map.get(date.toString())) + Integer.parseInt(browses.toString()));
            }

            map.put(date.toString(), value);
//            System.out.print("发表时间:" + date);
//            System.out.print("浏览量:" + browses + " ");
//            System.out.println(map.get(date.toString()));
        }
        return map;
    }
}
