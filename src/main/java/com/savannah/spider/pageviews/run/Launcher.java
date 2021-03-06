package com.savannah.spider.pageviews.run;

import com.savannah.spider.pageviews.processor.ParseHtml;
import com.savannah.spider.pageviews.util.HtmlUtils;
import com.savannah.spider.pageviews.util.TimeUtils;

/**
 * 爬虫启动类
 * @author stalern
 * @date 2019/10/28--15:31
 */
public class Launcher {
    public static void main(String[] args) {
        ParseHtml parseHtml = new ParseHtml();
        TimeUtils.time2Night(parseHtml.jSoupParseHtml(HtmlUtils.html2Doc()));
    }
}
