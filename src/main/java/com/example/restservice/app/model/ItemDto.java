package com.example.restservice.app.model;

public class ItemDto {
    private final Item item;
    private final int oldPrice;

    public ItemDto(Item item, int oldPrice) {
        this.item = item;
        this.oldPrice = oldPrice;
    }

    public Item getItem() {
        return item;
    }

    public int getOldPrice() {
        return oldPrice;
    }
}
