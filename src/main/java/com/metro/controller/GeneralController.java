package com.metro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.metro.model.EdgeOption;
import com.metro.model.ProductInfo;
import com.metro.repository.ProductInfoRepository;

@RestController
@RequestMapping("/product")
public class GeneralController {

	@Autowired
	private ProductInfoRepository repository;


	@PostMapping
	public String insertIntoDynamoDB(@RequestBody ProductInfo p) {
		repository.insert(p);
		return "Succuess in inserting";
	}
    

	@GetMapping
	public ResponseEntity<ProductInfo> getOneProductInfoDetails(@RequestParam String item_code) {
		ProductInfo p = repository.getOneByItemCode(item_code);
		return new ResponseEntity<ProductInfo>(p, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "{item_code}")
	public void deleteProductInfo(@PathVariable("item_code") String item_code) {
		repository.delete(ProductInfo.createProductInfo(item_code));
	}
    

	@GetMapping(value = "/all")
	public ResponseEntity<List<ProductInfo>> getAll() {
		List<ProductInfo> p = repository.getAll();
		return new ResponseEntity<List<ProductInfo>>(p, HttpStatus.OK);
	}
 
}
