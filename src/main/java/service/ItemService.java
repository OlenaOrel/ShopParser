package service;

import com.google.gson.Gson;
import entity.Item;

public class ItemService {

    public static String getJsonFromItem(Item item) {
        return new Gson().toJson(item);
    }
}
