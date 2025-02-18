package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            // "Sulfuras", being a legendary item, never has to be sold or decreases in Quality
            if (item.name.equals("Sulfuras, Hand of Ragnaros"))
                continue;

            // Update sellIn
            item.sellIn--;

            // Update quality
            item.quality += deltaQuality(item);

            qualityBoundsCheck(item);
        }
    }

    private void qualityBoundsCheck(Item item) {
        // The Quality of an item is never negative
        if (item.quality <= 0) {
            item.quality = 0;
        }
        // The Quality of an item is never more than 50
        else if (item.quality > 50) {
            item.quality = 50;
        }
    }

    private int deltaQuality(Item item) {
        // "Backstage passes" increases in Quality as its SellIn value approaches
        if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
            // Quality drops to 0 after the concert
            if (item.sellIn < 0)
                return -item.quality;

            //Quality increases by 3 when there are 5 days or less
            if (item.sellIn <= 5)
                return 3;

            //Quality increases by 2 when there are 10 days or less
            if (item.sellIn <= 10)
                return 2;

            //Quality increases by 1 when there are more the 10 days left
            return 1;
        }

        int deltaQuality = -1;

        // "Aged Brie" actually increases in Quality the older it gets
        // todo: Is "Conjured Aged Brie" be something that should be accounted for?
        if (item.name.equals("Aged Brie")) {
            deltaQuality = 1;
        }

        // Once the sell by date has passed, Quality degrades twice as fast
        // "Conjured" items degrade in Quality twice as fast as normal items
        if (item.sellIn < 0 || item.name.contains("Conjured")) {
            deltaQuality *= 2;
        }

        return deltaQuality;
    }
}