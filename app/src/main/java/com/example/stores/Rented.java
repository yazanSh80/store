package com.example.stores;

import java.util.Date;

public class Rented {
    Item item;
    Date start;
    Date end;

    public Rented(Item item, Date start, Date end) {
        this.item = item;
        this.start = start;
        this.end = end;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return item.toString() +"\n"+
                ", start=" + start + "\n"+
                ", end=" + end +
                '}';
    }
}
