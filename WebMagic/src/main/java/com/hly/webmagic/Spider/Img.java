package com.hly.webmagic.Spider;

import com.hly.webmagic.download.SpiderDownload;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author :hly
 * @date :2018/6/1
 */
public class Img implements PageProcessor {
    //页面URL的正则表达式
    //.是匹配所有的字符，//.表示只匹配一个.,?同理
    //public static String REGEX_PAGE_URL = "https://v\\.taobao\\.\\.com/v/content/live\\?catetype=704&from=taonvlang&page=\\w+";

    //爬取的页数
    public  static  int PAGE_SIZE = 30;




    @Override
    public void process(Page page) {

        List<String> listPage = new ArrayList<>();
        //添加目标url
        /* for(int i=1;i<PAGE_SIZE;i++) {*/
        listPage.add("http://image.baidu.com/search/index?tn=baiduimage&ct=201326592&lm=-1&cl=2&ie=gb18030&word=%CD%BC&fr=ala&ala=1&alatpl=others&pos=0");
        //}
        page.addTargetRequests(listPage);
        //获取名称
        String name = page.getHtml().xpath("//img[@class='main_img img-hover']/text()").toString();
        System.out.println(name);
        //获取图片
        String imgURL = page.getHtml().xpath("//div[@class='ice-img sharp anchor-avatar']").css("img","src").toString();
        System.out.println(imgURL);

        try {
            SpiderDownload.download("https:"+imgURL,name+".jpg","F:\\mmImage\\");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //配置
    private Site site = Site.me();
    @Override
    public Site getSite() {
        return site;
    }

    public static  void main(String[] arv){
        Spider.create(new Img()).addUrl("http://image.baidu.com/search/index?tn=baiduimage&ct=201326592&lm=-1&cl=2&ie=gb18030&word=%CD%BC&fr=ala&ala=1&alatpl=others&pos=0")
                .thread(10)
                .run();
    }
}
