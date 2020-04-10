package com.java.UnitOmega;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Executor {

    private static Map<Integer, String> orderBookPrice = new HashMap<>();
    private static Map<Integer, Integer> sizePrice = new HashMap<>();
    public static void execute(String line) {
        String[] parameters = line.split(",");
        switch (parameters[0]) {
            case ("o"):
                order(parameters[1]);
                break;
            case ("q"):
                switch (parameters[1]) {
                    case ("best_bid"):
                        Integer priceBid = getPriceMax();
                        printInFile(priceBid + "," + sizePrice.get(priceBid));
                        break;
                    case ("best_ask"):
                        Integer priceAsk = getPriceMin();
                        printInFile(priceAsk + "," + sizePrice.get(priceAsk));
                        break;
                    case ("size"):
                        int searchPrice = Integer.parseInt(parameters[2]);
                        printInFile(orderBookPrice.get(searchPrice) + "," + sizePrice.get(searchPrice)
                                + "," + searchPrice);
                        break;
                    default:
                        System.out.println("Wrong task!");
                }
                break;
            case ("u"):
                int price = Integer.parseInt(parameters[1]);
                int size = Integer.parseInt(parameters[2]);
                update(price, size, parameters[3]);
                break;
            default:
                System.out.println(" ");
                break;
        }
    }

    private static int getPriceMin() {
        boolean price = orderBookPrice
                .entrySet()
                .stream().filter(x -> "ask".equals(x.getValue()))
                .min(Map.Entry.comparingByKey()).isPresent();
        if (price) {
            return orderBookPrice
                    .entrySet()
                    .stream().filter(x -> "ask".equals(x.getValue()))
                    .min(Map.Entry.comparingByKey())
                    .get()
                    .getKey();
        } else {
            return 0;
        }
    }

    private static int getPriceMax() {
        boolean price = orderBookPrice
                .entrySet()
                .stream().filter(x -> "bid".equals(x.getValue()))
                .max(Map.Entry.comparingByKey()).isPresent();
        if (price) {
            return orderBookPrice
                    .entrySet()
                    .stream().filter(x -> "bid".equals(x.getValue()))
                    .max(Map.Entry.comparingByKey())
                    .get()
                    .getKey();
        } else {
            return 0;
        }
    }

    private static void printInFile(String text) {
        try(FileWriter writer = new FileWriter("result.txt", true)){
            writer.write(text + "\n");
        }catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void order(String parameter){
        if (parameter.equals("buy")) {
            Integer price = getPriceMin();
            sizePrice.replace(price, 0);
        } else if (parameter.equals("sell")) {
            Integer price = getPriceMax();
            sizePrice.replace(price, 0);
        }
    }

    private static void update(int price, int size, String type){
        if (type.equals("bid")) {
            if (orderBookPrice.get(price) == null) {
                orderBookPrice.put(price, "bid");
                sizePrice.put(price, size);
            }
            sizePrice.replace(price, size);
        } else if (type.equals("ask")) {
            if (orderBookPrice.get(price) == null) {
                orderBookPrice.put(price, "ask");
                sizePrice.put(price, size);
            }
            sizePrice.replace(price, size);
        }
    }
}
