import java.io.IOException;
import java.util.*;

/**
 * Created by bshekhawat
 */
class Localytics {

    public static void main(String[] args) throws IOException {
        List<Object[]> productsArray = Helpers.productsToObjectMapper();
        List<Object[]> salesArray = Helpers.salesToObjectMapper();
        List<Category> categories = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        Map<String, List<String>> multimap = new HashMap<>();

        for (Object[] objects : productsArray) {
            Category category = new Category();
            category.setProductName(((String) objects[0]).trim());
            category.setCategory(((String) objects[1]).trim());
            categories.add(category);
        }

        for (Object[] objects : salesArray) {
            Product product = new Product();
            product.setName(((String) objects[0]).trim());
            product.setPrice(Double.parseDouble((String) objects[1]));
            products.add(product);
        }

        Set<String> cats = new HashSet<>();
        List<String> names = new ArrayList<>();

        for (Category category : categories) {
            cats.add(category.getCategory());
        }

        for (String cat : cats) {
            for (Category category : categories) {
                if (category.getCategory().equals(cat)) {
                    names.add(category.getProductName());
                    multimap.put(cat, new ArrayList<>(names));
                }
            }
            names.clear();
        }

        //1. What are the minimum and maximum sales price for 'Canned Goods'

        List<String> productNames = new ArrayList<>();
        for (Category category : categories) {
            if (category.getCategory().equals("Canned Goods")) {
                productNames.add(category.getProductName());
            }
        }

        List<Double> prices = new ArrayList<>();
        for (Product sales : products) {
            for (String p : productNames) {
                if (sales.getName().contains(p)) {
                    prices.add(sales.getPrice());
                }
            }
        }

        System.out.println("What are the minimum and maximum sales price for 'Canned Goods'");
        System.out.println("Highest Price: " + Collections.max(prices));
        System.out.println("Lowest Price: " + Collections.min(prices));
        System.out.println("----");

        //2. What category has the highest average sales price?

        double avg = 0.0;
        int counter = 0;
        double price = 0.0;
        Map<String, Double> averages = new HashMap<>();
        for (Map.Entry<String, List<String>> map : multimap.entrySet()) {
            for (String val : map.getValue()) {
                for (Product p : products) {
                    if (p.getName().equals(val)) {
                        price = price + p.getPrice();
                        counter++;
                    }
                }
            }
            averages.put(map.getKey(), price / counter);
            price = 0.0;
            counter = 0;
        }

        System.out.println("Category with average prices");
        System.out.println("--------------------------------");
        averages.forEach((k, v) -> System.out.println("Category : " + k + "|" + " Avg Price : " + v));

        // Highest avg sales price
        double highest = Collections.max(averages.values());
        System.out.println("");
        System.out.println("What category has the highest average sales price?");
        averages.entrySet().stream().filter(stringDoubleEntry -> stringDoubleEntry.getValue().equals(highest)).forEach
                (System.out::println);
    }

}
