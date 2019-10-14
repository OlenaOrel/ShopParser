package service;

import entity.Item;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class NavigationPageParser extends Thread {

    private List<Thread> threads;
    private List<Item> items;

    private final String url;

    public NavigationPageParser(List<Thread> threads, List<Item> items, String url) {
        this.threads = threads;
        this.items = items;
        this.url = url;
    }

    @Override
    public void run() {
        try {
            Document document = Jsoup.connect(url).get();
            Elements itemLinkElements = document.getElementsByAttributeValue("data-test-id", "ProductTile");
            for (Element element : itemLinkElements) {
                String itemUrl = "https://www.aboutyou.de" + element.attr("href");
                System.out.println(itemUrl);
                if (itemUrl != null && !itemUrl.equals("")) {
                    ItemPageParser itemPageParser = new ItemPageParser(itemUrl, items);
                    threads.add(itemPageParser);
                    itemPageParser.start();
                    sleep(1000);
                }
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
