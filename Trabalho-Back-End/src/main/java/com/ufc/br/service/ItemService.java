package com.ufc.br.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufc.br.model.Item;
import com.ufc.br.repository.ItemRepository;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;
	
	public void cadastrar(Item item) {
		itemRepository.save(item);
	}
	
	public List<Item> retornarItens(){
		return itemRepository.findAll();
	}
	
}