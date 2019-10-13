import entity.Item;
import service.ItemParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String url = "https://www.aboutyou.de/p/only-sons/hose-mark-pant-gw-0209-4305913";

        List<Item> itemList = Collections.synchronizedList(new ArrayList<Item>());

        ItemParser itemParser = new ItemParser(url, itemList);
        itemParser.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(itemList.get(0));

    }

}
