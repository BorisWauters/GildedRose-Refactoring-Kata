package com.gildedrose;

public class Item {

    // item name
    public String name;

    // amount of days left to sell item
    public int sellIn;

    // quality of the item
    public int quality;

    public Item(String name, int sellIn, int quality) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
    }

   @Override
   public String toString() {
        return this.name + ", " + this.sellIn + ", " + this.quality;
    }
}
