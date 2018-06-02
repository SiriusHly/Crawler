package com.hly.webmagic.Spider;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import java.util.List;

/**
 * @author :hly
 * @date :2018/6/2
 */
public class ArticleSpider implements PageProcessor {
    @Override
    public void process(Page page) {
        List<String> articleList =  page.getHtml().xpath("//div[@class='con_main']/p").replace("<p>"," ").replace("</p>","").all();
        for(String str:articleList){
            for(int i=0;i<str.length();i++){
                if(i%40!=0) {
                    char j = str.charAt(i);
                    System.out.print(j);
                }
                else{
                    System.out.println();
                    char j = str.charAt(i);
                    System.out.print(j);
                }
            }
        }
    }
    //配置
    @Override
    public Site getSite() {
        return Site.me();
    }
    public  static  void  main(String[]arv){
        Spider.create(new ArticleSpider()).addUrl("http://www.xuexila.com/lunwen/jiaoyulunwen/2669901_2.html").thread(10).run();
    }
}
