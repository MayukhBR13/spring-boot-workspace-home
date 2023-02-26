package com.secutest3.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.secutest3.model.Item;

@Service
public class ItemService {
	static List<Item> items=new ArrayList<>();
	static {
		items.add(new Item(1,"rice",20));
		items.add(new Item(2,"rice2",202));
		items.add(new Item(3,"rice3",203));
	}
	public Item add(Item item) {
		items.add(item);
		return item;
	}
	public List<Item> getAllItems() {
		return items;
	}
}