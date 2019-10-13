import entity.Item;
import service.NavigationPageParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String url = "https://www.aboutyou.de/maenner/bekleidung?page=2&sort=topseller";

        List<Item> itemList = Collections.synchronizedList(new ArrayList<Item>());
        List<Thread> threads = Collections.synchronizedList(new ArrayList<Thread>());

        NavigationPageParser parser = new NavigationPageParser(threads, itemList, url);
        parser.start();
        do {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (threadsStillWork(threads));

    }

    private static boolean threadsStillWork(List<Thread> threads) {
        for (Thread thread : threads) {
            if (thread.getState().equals(Thread.State.NEW) || thread.isAlive()) {
                return true;
            }
        }

        return false;
    }

}
