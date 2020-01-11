package com.savannah.spider.csdn;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * @author stalern
 * @date 2020/01/07~17:07
 */
public class RefreshBlogThread implements Runnable{

    /**
     * 爬取频率
     */
    private int sleepTime;
    /**
     * 爬虫持续时间
     */
    private int duration = 0;
    private int number = 0;

    private String blogArticleUrl;

    public RefreshBlogThread(String blogArticleUrl) {
        this.blogArticleUrl = blogArticleUrl;
        this.duration = -1;
        this.sleepTime = 1000_0;
    }

    public RefreshBlogThread(String blogArticleUrl, int duration, int sleepTime) {

    }
    @Override
    public void run() {
        refreshBlog();
    }

    public void refreshBlog(){
        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod(blogArticleUrl);
        getMethod.setRequestHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.99 Safari/537.36 LBBROWSER");
        //其实这里我也不太懂.我只是知道cookie的生成策略,但是因为 csdn会判断力过来的cookie是啥.必须加.
        getMethod.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
        //偷懒就用了一个死循环
        while(true) {
            try {
                //这句话会造成阻塞因为想网络发起请求.
                int statusCode = httpClient.executeMethod(getMethod);
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.print("失败：" + getMethod.getStatusLine());
                }else{
                    System.out.println(blogArticleUrl+"已经刷新了:"+ ++number+"次");
                }
                Thread.sleep(sleepTime);
            } catch (Exception e) {
                System.out.print("请检查网络地址！");
            } finally {
                getMethod.releaseConnection();
            }
        }
    }
}
