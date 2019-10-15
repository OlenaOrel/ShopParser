package service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class PaginationParser extends Thread {

    private List<Thread> threads;
    private List<String> jsonItemList;

    private final String url;

    public PaginationParser(List<Thread> threads, List<String> jsonItemList, String url) {
        this.threads = threads;
        this.jsonItemList = jsonItemList;
        this.url = url;
        this.start();
    }

    @Override
    public void run() {
        Document document;
        try {
            document = Jsoup.connect(url).get();
            Elements navigationLinkElements = document.getElementsByAttributeValue("data-test-id", "PaginationWrapper");
            String paginationNumbers = navigationLinkElements.last().text();
            String lastPageNumber = paginationNumbers.trim().substring(10);
            int i = Integer.valueOf(lastPageNumber);
            if (i > 0) {
                for (int k = 2; k <= i; k++) {
                    String navigationUrl = "https://www.aboutyou.de/maenner/bekleidung?page=" + k;
                    NavigationPageParser parser = new NavigationPageParser(threads, jsonItemList, url);
                    threads.add(parser);
                    parser.start();
                    sleep(5000);
                }
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
