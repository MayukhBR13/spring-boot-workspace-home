package com.secutest3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.secutest3.model.Item;
import com.secutest3.service.ItemService;

@RestController
public class ItemsController {
	@Autowired
	ItemService itemService;
	
	@GetMapping("/items")
	public List<Item> getAllItems(){
		return itemService.getAllItems();
	}
	@PostMapping("/items")
	public Item addItem(@RequestBody Item item){
		return itemService.add(item);
	}
}