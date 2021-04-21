package com.example.restservice.app.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class Item implements Serializable {
    @Id
    private final String id;
    private int price;

    @Column(unique = true)
    private final String url;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
    private List<Subscription> subscriptions;

    public Item(String id, String url) {
        this(id, 0, url);
    }

    public Item(String id, int price, String url) {
        this.id = id;
        this.price = price;
        this.url = url;
    }

    protected Item() {
        this("", "");
    }

    public String getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", price=" + price +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id) && item.getPrice() == getPrice();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
