import dao.FileSystemItemDao;
import service.NavigationPageParser;
import service.PaginationParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String url = "https://www.aboutyou.de/maenner/bekleidung";

        List<String> jsonItemList = Collections.synchronizedList(new ArrayList<>());
        List<Thread> threads = Collections.synchronizedList(new ArrayList<>());

        NavigationPageParser parser = new NavigationPageParser(threads, jsonItemList, url);
        threads.add(parser);
        parser.start();

        PaginationParser paginationParser = new PaginationParser(threads, jsonItemList, url);
        threads.add(paginationParser);
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

        System.out.println("Amount of extracted products: " + jsonItemList.size());
        System.out.println("Amount of triggered HTTP requests: " + jsonItemList.size());

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
