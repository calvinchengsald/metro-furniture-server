package com.metro.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.metro.model.SubtypeHiearchy;

@Repository
public class SubtypeHiearchyRepository {

	
	@Autowired
	private DynamoDBMapper mapper;
	
	public void insert(SubtypeHiearchy p) {
		mapper.save(p);
	}
	
	public SubtypeHiearchy getOneBySubtype(String edge) {
		return mapper.load(SubtypeHiearchy.class, edge);
	}
	

	public List<SubtypeHiearchy> getAll() {
		List<SubtypeHiearchy> all = mapper.scan(SubtypeHiearchy.class, new DynamoDBScanExpression());
		return all;

	}
	
	
	public void update(SubtypeHiearchy p) {
		try {
			mapper.save(p, buildDynamoDBSaveExpression(p));
		} catch (ConditionalCheckFailedException e) {
			System.out.println("invalid data - " + e.getMessage());
		}
	}
	
	public void delete(SubtypeHiearchy p) {
		mapper.delete(p);
	}
	
	public DynamoDBSaveExpression buildDynamoDBSaveExpression(SubtypeHiearchy p ) {
		DynamoDBSaveExpression exp = new DynamoDBSaveExpression();
		Map<String , ExpectedAttributeValue> expected = new HashMap<>();
		expected.put("subtype", new ExpectedAttributeValue(new AttributeValue(p.getSubtype())).withComparisonOperator(ComparisonOperator.EQ));
		exp.setExpected(expected);
		return exp;
	}
	
	
	
}
