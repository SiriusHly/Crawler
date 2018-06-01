package com.hly.webmagic.Spider;

import com.hly.webmagic.download.SpiderDownload;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author :hly
 * @date :2018/6/1
 */
public class mmImgSpider implements PageProcessor {
    //页面URL的正则表达式
    //.是匹配所有的字符，//.表示只匹配一个.,?同理
    public static String REGEX_PAGE_URL = "https://v\\.taobao\\.com/v/content/live\\?catetype=704&from=taonvlang&page=\\w+";

    //爬取的页数
    public  static  int PAGE_SIZE = 3;

    @Override
    public void process(Page page) {

        List<String> listPage = new ArrayList<>();
        //添加目标url
        for (int i = 1; i < PAGE_SIZE; i++) {
            listPage.add("https://v.taobao.com/v/content/live?catetype=704&from=taonvlang&page=" + i);
        }
        page.addTargetRequests(listPage);
        System.out.println("1");

        if (page.getUrl().regex(REGEX_PAGE_URL).match()) {
            System.out.println("2");
            List<String> urls = page.getHtml().xpath("//li[@class=\"live-info-item\"]/a").links().all();

            List<String> s = page.getHtml().xpath("//div[@class=\"info-item-tags\"]/span/text()").all();
            System.out.println("s:"+s.size());

            System.out.println("6:"+urls.size());
            for (String url : urls) {
                System.out.println(url);
                page.addTargetRequest("https:"+url);
            }
        } else {
            System.out.println("3");
            //获取名称
            String name = page.getHtml().xpath("//h1[@class=\"task-title\"]/text()").toString();
            System.out.println(name);
            //获取图片
            String imgURL = page.getHtml().xpath("//div[@class='ice-img sharp']/div").css("img", "src").toString();
            System.out.println(imgURL);

            try {
                SpiderDownload.download(imgURL,name + ".jpg", "F:\\mmImage\\");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //配置
    private Site site = Site.me();
    @Override
    public Site getSite() {
        return site;
    }

    public static  void main(String[] arv){
        Spider.create(new mmImgSpider()).addUrl("https://v.taobao.com/v/content/live?catetype=704&from=taonvlang&page=1")
                .thread(10)
                .run();
    }
}
