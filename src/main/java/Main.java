import dao.FileSystemItemDao;
import service.NavigationPageParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String url = "https://www.aboutyou.de/maenner/bekleidung?page=2&sort=topseller";

        List<String> jsonItemList = Collections.synchronizedList(new ArrayList<>());
        List<Thread> threads = Collections.synchronizedList(new ArrayList<>());

        NavigationPageParser parser = new NavigationPageParser(threads, jsonItemList, url);
        threads.add(parser);
        parser.start();
        do {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (threadsStillWork(threads));

        FileSystemItemDao fileSystemItemDao = new FileSystemItemDao();
        for (String item : jsonItemList) {
            fileSystemItemDao.writeItem(item);
        }
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
