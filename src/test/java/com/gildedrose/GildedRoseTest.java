package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GildedRoseTest {

    @Test
    void sellingDatePast() {
        // Once the sell by date has passed, Quality degrades twice as fast
        int originalQuality = 10;
        Item[] items = new Item[]{
                new Item("single", 5, originalQuality),
                new Item("double", -1, originalQuality)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertEquals("single", app.items[0].name);
        assertTrue(originalQuality > app.items[0].quality, "Quality should've dropped");
        assertEquals(originalQuality - 1, app.items[0].quality, "Quality should've dropped regularly");

        assertEquals("double", app.items[1].name);
        assertTrue(originalQuality > app.items[1].quality, "Quality should've dropped");
        assertEquals(originalQuality - 2, app.items[1].quality, "Quality should've dropped twice as much");
    }

    @Test
    void negativeQuality() {
        // The Quality of an item is never negative
        Item[] items = new Item[]{new Item("noValue", 2, 0)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertEquals(0, app.items[0].quality, "Quality shouldn't drop below 0");
    }

    @Test
    void agedBrie() {
        // "Aged Brie" actually increases in Quality the older it gets
        int originalSellIn = 10;
        int originalQuality = 10;

        Item[] items = new Item[]{new Item("Aged Brie", originalSellIn, originalQuality)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertEquals("Aged Brie", app.items[0].name, "Item name should match expected");
        assertTrue(originalSellIn > app.items[0].sellIn, "sellIn date should decrease in value");
        assertTrue(originalQuality < app.items[0].quality, "Quality of \"Aged Brie\" should increase");
    }

    @Test
    void maxQuality() {
        // The Quality of an item is never more than 50
        Item[] items = new Item[]{
                new Item("Aged Brie", 10, 50),
                new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
                new Item("Aged Brie", 10, 40)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        for (Item item : app.items) {
            assertTrue(50 >= item.quality, "Quality shouldn't overtake its max capacity");
        }
    }

    @Test
    void legendaryItems() {
        // "Sulfuras", being a legendary item, never has to be sold or decreases in Quality
        int[] sellDates = new int[]{-5, 0, 5};
        Item[] items = new Item[]{
                new Item("Sulfuras, Hand of Ragnaros", sellDates[0], 80),
                new Item("Sulfuras, Hand of Ragnaros", sellDates[1], 80),
                new Item("Sulfuras, Hand of Ragnaros", sellDates[2], 80)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        for (int i = 0; i < items.length; i++) {
            assertEquals(80, app.items[i].quality, "Quality should be fixed");
            assertEquals(sellDates[i], app.items[i].sellIn, "SellIn dates should remain the same");
        }
    }

    @Test
    void backstagePasses() {
        /*
        * "Backstage passes", like aged brie, increases in Quality as its SellIn value approaches;
        * Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or less but
        * Quality drops to 0 after the concert
        * */
        String name = "Backstage passes to a TAFKAL80ETC concert";
        int originalQuality = 10;
        int[] sellDates = new int[]{-1, 10, 5};
        int[] targetQuality = new int[]{0, originalQuality+2, originalQuality+3};

        Item[] items = new Item[]{
                new Item(name, sellDates[0], originalQuality),
                new Item(name, sellDates[1], originalQuality),
                new Item(name, sellDates[2], originalQuality)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        for (int i = 0; i < items.length; i++) {
            assertEquals(targetQuality[i], app.items[i].quality, "Quality must be altered correctly");
        }
    }
}
