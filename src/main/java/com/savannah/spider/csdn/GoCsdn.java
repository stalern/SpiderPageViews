package com.savannah.spider.csdn;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author stalern
 * @date 2020/01/07~17:07
 */
public class GoCsdn {

    private static final int MAX_POOL_SIZE = 10;
    private static final int QUEUE_CAPACITY = 100;
    private static final Long KEEP_ALIVE_TIME = 1L;

    public  static void main(String[] args) {
        Service service = new Service(Constant.BLOG_INDEX_URL);
        List<String> articleUrls = null;
        try {
            articleUrls = service.getBlogAllArticleUrl();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (articleUrls != null) {
            //用一个线程池
            ThreadPoolExecutor executor = new ThreadPoolExecutor(
                    articleUrls.size(),
                    MAX_POOL_SIZE + articleUrls.size(),
                    KEEP_ALIVE_TIME,
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                    new ThreadPoolExecutor.CallerRunsPolicy());

            for (String articleUrl : articleUrls) {
                Runnable refreshBlogThread = new RefreshBlogThread(articleUrl);
                executor.execute(refreshBlogThread);
            }
            executor.shutdown();
        }else{
            System.out.println("该博客没有文章！");
        }

    }
}
