package com.metro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.metro.entities.ApiResponse;
import com.metro.exception.DatabaseExceptions;
import com.metro.exception.ItemAlreadyExistsException;
import com.metro.exception.UndefinedItemCodeException;
import com.metro.model.ProductInfo;
import com.metro.repository.ProductInfoRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/product")
public class GeneralController {

	@Autowired
	private ProductInfoRepository repository;


	@PostMapping
	public ResponseEntity<ApiResponse<ProductInfo>> insertIntoDynamoDB(@RequestBody ProductInfo p) {
		try {
			repository.insert(p);
			return new ResponseEntity<ApiResponse<ProductInfo>>(new ApiResponse<ProductInfo>(p,HttpStatus.OK, ""),HttpStatus.OK);
		} catch (DatabaseExceptions e ) {
			if (e.getClass().equals(ItemAlreadyExistsException.class)){
				return new ResponseEntity<ApiResponse<ProductInfo>>(new ApiResponse<ProductInfo>(p,HttpStatus.BAD_REQUEST, "An item with this item code: [" +p.getItem_code() + "] already exists"),HttpStatus.BAD_REQUEST);
			}
			
			return new ResponseEntity<ApiResponse<ProductInfo>>(new ApiResponse<ProductInfo>(p,HttpStatus.BAD_REQUEST, e.getMessage()),HttpStatus.BAD_REQUEST);
		}
		
		
	}
	

	@PostMapping(value= "/update")
	public ResponseEntity<ProductInfo> updateDynamoDB(@RequestBody ProductInfo p) {
		repository.update(p);
		return new ResponseEntity<ProductInfo>(p, HttpStatus.OK);
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
	

	@GetMapping(value = "/m_type")
	public ResponseEntity<List<ProductInfo>> getAllType(@RequestParam String value) {
		List<ProductInfo> p = repository.getAllByAttr("m_type", value);
		return new ResponseEntity<List<ProductInfo>>(p, HttpStatus.OK);
	}

	@GetMapping(value = "/m_subtype")
	public ResponseEntity<List<ProductInfo>> getAllSubtype(@RequestParam String value) {
		List<ProductInfo> p = repository.getAllByAttr("m_subtype", value);
		return new ResponseEntity<List<ProductInfo>>(p, HttpStatus.OK);
	}

	@GetMapping(value = "/base_code")
	public ResponseEntity<List<ProductInfo>> getAllBase_code(@RequestParam String value) {
		List<ProductInfo> p = repository.getAllByAttr("base_code", value);
		return new ResponseEntity<List<ProductInfo>>(p, HttpStatus.OK);
	}

	@GetMapping(value = "/tag")
	public ResponseEntity<List<ProductInfo>> getAllTag(@RequestParam String value) {
		List<ProductInfo> p = repository.getAllByAttrList("tag", value);
		return new ResponseEntity<List<ProductInfo>>(p, HttpStatus.OK);
	}
 
}
