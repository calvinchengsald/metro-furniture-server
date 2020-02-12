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
import com.metro.model.DeleteUpdateModel;
import com.metro.model.EdgeOption;
import com.metro.model.SubtypeHiearchy;
import com.metro.model.TypeHiearchy;
import com.metro.repository.SubtypeHiearchyRepository;
import com.metro.utils.Standardization;

@RestController
@RequestMapping("/subtypehiearchy")
@CrossOrigin(origins = "http://localhost:3000")
public class SubtypeHiearchyController {

	@Autowired
	private SubtypeHiearchyRepository repository;


	@PostMapping
	public ResponseEntity<ApiResponse<SubtypeHiearchy>> insertIntoDynamoDB(@RequestBody SubtypeHiearchy p) {
		try {
			repository.insert(p);
			return new ResponseEntity<ApiResponse<SubtypeHiearchy>>(new ApiResponse<SubtypeHiearchy>(p,HttpStatus.OK, ""),HttpStatus.OK);
		} catch (DatabaseExceptions e ) {
			if (e.getClass().equals(ItemAlreadyExistsException.class)){
				return new ResponseEntity<ApiResponse<SubtypeHiearchy>>(new ApiResponse<SubtypeHiearchy>(p,HttpStatus.BAD_REQUEST, "An item with this item code: [" +p.getM_subtype() + "] already exists"),HttpStatus.BAD_REQUEST);
			}
			
			return new ResponseEntity<ApiResponse<SubtypeHiearchy>>(new ApiResponse<SubtypeHiearchy>(p,HttpStatus.BAD_REQUEST, e.getMessage()),HttpStatus.BAD_REQUEST);
		}
		
		
	}
    


	@GetMapping
	public ResponseEntity<ApiResponse<SubtypeHiearchy>> getOneProductInfoDetails(@RequestParam String item_code) {
		SubtypeHiearchy p = repository.getOneBySubtype(item_code);
		return new ResponseEntity<ApiResponse<SubtypeHiearchy>>(new ApiResponse<SubtypeHiearchy>(p,HttpStatus.OK, ""),HttpStatus.OK);
	}
	
	@PostMapping(value = "/delete")
	public ResponseEntity<ApiResponse<SubtypeHiearchy>> deleteProductInfo(@RequestBody SubtypeHiearchy p) {
		repository.delete(p);
		return new ResponseEntity<ApiResponse<SubtypeHiearchy>>(new ApiResponse<SubtypeHiearchy>(p,HttpStatus.OK, ""),HttpStatus.OK);
	}
    
	@GetMapping(value = "/all")
	public  ResponseEntity<ApiResponse<List<SubtypeHiearchy>>> getAll() {
		List<SubtypeHiearchy> p = repository.getAll();
		return new ResponseEntity<ApiResponse<List<SubtypeHiearchy>>>(new ApiResponse<List<SubtypeHiearchy>>(p,HttpStatus.OK, ""),HttpStatus.OK);
	}
 

	@PostMapping(value= "/update")
	public ResponseEntity<ApiResponse<SubtypeHiearchy>> updateDynamoDB(@RequestBody SubtypeHiearchy p) {
		
		try {
			repository.update(p);
			return new ResponseEntity<ApiResponse<SubtypeHiearchy>>(new ApiResponse<SubtypeHiearchy>(p,HttpStatus.OK, ""),HttpStatus.OK);
		} catch (DatabaseExceptions e ) {
			
			return new ResponseEntity<ApiResponse<SubtypeHiearchy>>(new ApiResponse<SubtypeHiearchy>(p,HttpStatus.BAD_REQUEST, e.getMessage()),HttpStatus.BAD_REQUEST);
		}
	}



	//Used when you want to change the primary key. Will delete the record with that key and add in a new record
	@PostMapping(value= "/deleteupdate")
	public ResponseEntity<ApiResponse<SubtypeHiearchy>> deleteUpdateDynamoDB(@RequestBody DeleteUpdateModel<SubtypeHiearchy> p) {
		try {
			if(Standardization.isInvalidString(p.getModel().getM_subtype())) { 
				throw new UndefinedItemCodeException("Unable to process item with invalid item code: [" +p.getModel().getM_subtype() + "]" );
			}
			repository.delete( SubtypeHiearchy.createSubtypeHiearchy(p.getPrePrimaryKey()));
			repository.insert(p.getModel());
			return new ResponseEntity<ApiResponse<SubtypeHiearchy>>(new ApiResponse<SubtypeHiearchy>(p.getModel(),HttpStatus.OK, ""),HttpStatus.OK);
		} catch (DatabaseExceptions e ) {
			
			return new ResponseEntity<ApiResponse<SubtypeHiearchy>>(new ApiResponse<SubtypeHiearchy>(p.getModel(),HttpStatus.BAD_REQUEST, e.getMessage()),HttpStatus.BAD_REQUEST);
		}
	}
	
	
 
}
