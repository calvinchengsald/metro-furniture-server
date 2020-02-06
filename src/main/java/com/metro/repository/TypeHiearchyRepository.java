package com.metro.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.metro.model.TypeHiearchy;

@Repository
public class TypeHiearchyRepository {

	
	@Autowired
	private DynamoDBMapper mapper;
	
	public void insert(TypeHiearchy p) {
		mapper.save(p);
	}
	
	public TypeHiearchy getOneByType(String edge) {
		return mapper.load(TypeHiearchy.class, edge);
	}
	

	public List<TypeHiearchy> getAll() {
		List<TypeHiearchy> all = mapper.parallelScan(TypeHiearchy.class, new DynamoDBScanExpression(), 2);
		return all;

	}
	
	
	public void update(TypeHiearchy p) {
		try {
			mapper.save(p, buildDynamoDBSaveExpression(p));
		} catch (ConditionalCheckFailedException e) {
			System.out.println("invalid data - " + e.getMessage());
		}
	}
	
	public void delete(TypeHiearchy p) {
		mapper.delete(p);
	}
	
	public DynamoDBSaveExpression buildDynamoDBSaveExpression(TypeHiearchy p ) {
		DynamoDBSaveExpression exp = new DynamoDBSaveExpression();
		Map<String , ExpectedAttributeValue> expected = new HashMap<>();
		expected.put("type", new ExpectedAttributeValue(new AttributeValue(p.getType())).withComparisonOperator(ComparisonOperator.EQ));
		exp.setExpected(expected);
		return exp;
	}
	
	
	
}
