package service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class NavigationPageParser extends Thread {

    private List<Thread> threads;
    private List<String> jsonItemList;

    private final String url;

    public NavigationPageParser(List<Thread> threads, List<String> jsonItemList, String url) {
        this.threads = threads;
        this.jsonItemList = jsonItemList;
        this.url = url;
    }

    @Override
    public void run() {
        try {
            Document document = Jsoup.connect(url).get();
            Elements itemLinkElements = document.getElementsByAttributeValue("data-test-id", "ProductTile");
            for (Element element : itemLinkElements) {
                String itemUrl = "https://www.aboutyou.de" + element.attr("href");
                if (itemUrl != null && !itemUrl.equals("")) {
                    ItemPageParser itemPageParser = new ItemPageParser(itemUrl, jsonItemList);
                    threads.add(itemPageParser);
                    itemPageParser.start();
                    sleep(1000);
                }
            }
        } catch (InterruptedException | IOException ex) {
            ex.printStackTrace();
        }
    }
}
