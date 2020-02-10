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
import com.metro.exception.DatabaseExceptions;
import com.metro.exception.ItemAlreadyExistsException;
import com.metro.exception.UndefinedItemCodeException;
import com.metro.model.ProductInfo;
import com.metro.model.TypeHiearchy;
import com.metro.utils.Standardization;

@Repository
public class TypeHiearchyRepository {

	
	@Autowired
	private DynamoDBMapper mapper;
	
	public void insert(TypeHiearchy p) throws DatabaseExceptions {

		System.out.println(p.toString());
		if(Standardization.isInvalidString(p.getM_type())) { 
			throw new UndefinedItemCodeException("Unable to process item with invalid item code: [" +p.getM_type() + "]" );
		}
		if (mapper.load(ProductInfo.class, p.getM_type()) != null) {
			throw new ItemAlreadyExistsException("Item Code : [" +p.getM_type() + " already exists in the database. Please use a different item code" );
		}
		System.out.println(p.toString());
		mapper.save(p);
	}
	
	public TypeHiearchy getOneByType(String edge) {
		return mapper.load(TypeHiearchy.class, edge);
	}
	

	public List<TypeHiearchy> getAll() {
		List<TypeHiearchy> all = mapper.scan(TypeHiearchy.class, new DynamoDBScanExpression());
		return all;

	}
	
	
	


	
	public void update(TypeHiearchy p) throws  DatabaseExceptions{
		if(Standardization.isInvalidString(p.getM_type())) { 
			throw new UndefinedItemCodeException("Unable to process item with invalid item code: [" +p.getM_type() + "]" );
		}
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
		expected.put("type", new ExpectedAttributeValue(new AttributeValue(p.getM_type())).withComparisonOperator(ComparisonOperator.EQ));
		exp.setExpected(expected);
		return exp;
	}
	
	
	
}
