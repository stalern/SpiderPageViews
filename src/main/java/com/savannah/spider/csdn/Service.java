package com.savannah.spider.csdn;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author stalern
 * @date 2020/01/07~16:55
 */
public class Service {

    /**
     * CSDN博客首页地址
     */
    private String blogIndexUrl;

    private static final String PAGE_URL = Constant.PAGE_URL;

    private static final String CSDN_URL = Constant.CSDN_URL;

    /**
     * @param blogIndexUrl CSDN博客首页地址
     */
    public Service(String blogIndexUrl){
        this.blogIndexUrl = blogIndexUrl;
    }


    /**
     * 获取CSDN博客所有文章链接
     * @return list
     * @throws IOException 找不到连接
     */
    public List<String> getBlogAllArticleUrl() throws IOException {
        //记录爬出来的文章Url

        Element body = getDocumentBody(blogIndexUrl);
        //获取分页div
        Element pageListDiv = body.getElementById("pageBox");

        List<String> urls = new ArrayList<>();
        int pageCount = 1;
        //多页
        if (pageListDiv != null){
            Elements countNodes = pageListDiv.getElementsByClass("ui-pager");
            for (Element countNode : countNodes) {
                if (Integer.parseInt(countNode.text()) > pageCount) {
                    pageCount = Integer.parseInt(countNode.text());
                }
            }
        }
        //获取其他页的文章Url
        for (int i = 1; i <= pageCount; i++) {
            String pageUrl = blogIndexUrl+ PAGE_URL +i;
            urls.addAll(getBlogArticleUrlByPage(pageUrl));
        }
        return urls;
    }

    /**
     * 获取那一页中所有文章的URL
     * @param pageUrl 当前文章列表页URL
     * @return 文章列表中所有文章的URL
     * @throws IOException I/O
     */
    public List<String> getBlogArticleUrlByPage(String pageUrl) throws IOException{

        Element body = getDocumentBody(pageUrl);

        //记录爬出来的文章Url
        List<String> urls = new ArrayList<>();
        Elements articleList = body.getElementsByClass("article-item-box csdn-tracking-statistics");
        if (articleList.size() != 0) {
            for (Element article : articleList){
                Element articleUrlNode = (article.select("a")).get(0);
                urls.add(articleUrlNode.attr("href"));
            }
        }

        return urls;
    }


    public static Element getDocumentBody(String url) throws IOException{
        //获取url地址的http链接Connection
        //博客首页的url地址
        Connection conn = Jsoup.connect(url)
                //http请求的浏览器设置
                .userAgent("Mozilla/5.0 (Windows NT 6.1; rv:47.0) Gecko/20100101 Firefox/47.0")
                //http连接时长
                .timeout(5000)
                .method(Connection.Method.GET);
        //获取页面的html文档
        Document doc = conn.get();
        return doc.body();
    }
}
