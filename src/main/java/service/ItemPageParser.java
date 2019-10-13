package service;

import entity.Item;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class ItemPageParser extends Thread {

    private final String URL;
    private List<Item> itemList;

    public ItemPageParser(String URL, List<Item> itemList) {
        this.URL = URL;
        this.itemList = itemList;
    }

    @Override
    public void run() {
        try {
            Document document = Jsoup.connect(URL).get();
            String name = getItemName(document);
            String brand = getItemBrand(document);
            List<String> color = gerItemColors(document);
            BigDecimal price = getItemPrice(document);
            String articleId = getItemArticleId(document);
            Item item = new Item(name, brand, color, price, articleId);
            System.out.println(item.toString());
            itemList.add(item);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getItemArticleId(Document document) {
        Elements elements = document.getElementsByAttributeValue("data-test-id", "ArticleNumber");
        if (!elements.isEmpty()) {
            String[] tmp = elements.text().split(" ");
            return tmp[1];
        }
        return null;
    }

    private BigDecimal getItemPrice(Document document) {
        Elements elements = document.getElementsByClass("PriceBoxExtended__StyledPriceBox-sc-1t8547r-0 iogkTg");
        if (!elements.isEmpty()) {
            String[] tmp = elements.first().text().split(" ");
            String priceAsText = null;
            for (String s : tmp) {
                if (s.matches("^[0-9]*[.,][0-9]+$") && priceAsText == null) {
                    priceAsText = s.replace(",", ".");
                    double price = Double.parseDouble(priceAsText);
                    return new BigDecimal(price).setScale(2, RoundingMode.HALF_UP);
                }
            }
        }
        return null;
    }

    private List<String> gerItemColors(Document document) {
        List<String> colors = new ArrayList<String>();
        Elements elements = document.getElementsByAttributeValue("data-test-id", "ColorVariantColorInfo");
        if (!elements.isEmpty()) {
            for (Element element : elements) {
                String itemColor = element.text();
                if (!itemColor.contains(" ")) {
                    colors.add(itemColor);
                } else {
                    String[] tmp = itemColor.split(" / ");
                    for (String s : tmp) {
                        colors.add(s);
                    }
                }
            }
        }
        return colors;
    }

    private String getItemBrand(Document document) {
        Elements elements = document.getElementsByAttributeValue("data-test-id", "BrandLogo");
        if (!elements.isEmpty()) {
            return elements.attr("alt");
        }
        return null;
    }

    private String getItemName(Document document) {
        Elements elements = document.getElementsByAttributeValue("data-test-id", "ProductName");
        if (!elements.isEmpty()) {
            return elements.text();
        }
        return null;
    }
}
