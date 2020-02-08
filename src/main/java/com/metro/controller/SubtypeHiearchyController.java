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

import com.metro.model.EdgeOption;
import com.metro.model.SubtypeHiearchy;
import com.metro.repository.SubtypeHiearchyRepository;

@RestController
@RequestMapping("/subSubtypeHiearchy")
@CrossOrigin(origins = "http://localhost:3000")
public class SubtypeHiearchyController {

	@Autowired
	private SubtypeHiearchyRepository repository;


	@PostMapping
	public String insertIntoDynamoDB(@RequestBody SubtypeHiearchy p) {
		repository.insert(p);
		return "Succuess in inserting";
	}
    

	@GetMapping
	public ResponseEntity<SubtypeHiearchy> getOneSubtypeHiearchyDetails(@RequestParam String edge) {
		SubtypeHiearchy p = repository.getOneBySubtype(edge);
		return new ResponseEntity<SubtypeHiearchy>(p, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "{subtype}")
	public void deleteSubtypeHiearchy(@PathVariable("subtype") String subtype) {
		repository.delete(SubtypeHiearchy.createSubtypeHiearchy(subtype));
	}
    

	@GetMapping(value = "/all")
	public ResponseEntity<List<SubtypeHiearchy>> getAll() {
		List<SubtypeHiearchy> p = repository.getAll();
		return new ResponseEntity<List<SubtypeHiearchy>>(p, HttpStatus.OK);
	}
 
}
