package com.hly.webmagic.Spider;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * @author :hly
 * @date :2018/6/2
 */
public class MyWebMagic implements PageProcessor {
    private Site site = Site.me();

    public void process(Page page) {

        List<String> listTitle = page.getHtml().xpath("//div[@class='title']/h2/a/text()").regex(".*区块链.*").all();
        //不用括号括起来，就全部返回
        List<String> listBlock = page.getHtml().xpath("//div[@class='title']/h2").regex("<a\\b[^>]+\\bhref=\"([^\"]*)\"[^>]*>.*区块链.*</a>").all();

        for(int i =0;i<listTitle.size();i++)
            System.out.println(listTitle.get(i)+" :"+listBlock.get(i));
    }
    public Site getSite() {
        return site;
    }
    public static void main(String[] args) {
        Spider.create(new MyWebMagic()).addUrl("https://blog.csdn.net/nav/blockchain")
                .addPipeline(new ConsolePipeline()).run();
    }

}