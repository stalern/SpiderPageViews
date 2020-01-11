package csdn;

import com.savannah.spider.csdn.Constant;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.savannah.spider.csdn.Service.getDocumentBody;

/**
 * @author stalern
 * @date 2020/01/10~13:05
 */
public class TestElements {

    @Test
    public void getArticle() throws IOException {
        List<String> urls = new ArrayList<>();
        Element body = getDocumentBody(Constant.BLOG_INDEX_URL);
        //获取分页div -- 需要改
        Elements articleList = body.getElementsByClass("article-item-box csdn-tracking-statistics");
        if (articleList.size() != 0) {
            for (Element article : articleList){
                Element articleUrlNode = (article.select("a")).get(0);
                urls.add(articleUrlNode.attr("href"));
            }
        }
        System.out.println(urls);
    }


}
