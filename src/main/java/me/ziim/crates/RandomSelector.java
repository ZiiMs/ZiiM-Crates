/*
    Code snippet found @ https://stackoverflow.com/questions/9330394/how-to-pick-an-item-by-its-probability
    Credits to https://stackoverflow.com/users/706727/usman-ismail & https://stackoverflow.com/users/215969/ilia-choly
 */

package me.ziim.crates;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomSelector {
    List<Item> items = new ArrayList<>();
    Random rand = new Random();
    int totalSum = 0;

    public RandomSelector(List<Item> itemList) {
        items = itemList;
        for (Item item : items) {
            totalSum += item.prob;
        }
    }

    public Item getRandom() {
        int index = rand.nextInt(totalSum);
        int sum = 0;
        int i = 0;
        while (sum < index) {
            sum = sum + items.get(i++).prob;
        }
        return items.get(Math.max(0, i - 1));
    }

}
