package com.metro.model;

import java.io.Serializable;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "SubtypeHiearchy")
public class SubtypeHiearchy implements Serializable {

	private static final long serialVersionUID = -2799150963436786782L;
	private String subtype;
	private String description;
	private String url;
	


    public static SubtypeHiearchy createSubtypeHiearchy(String size){
    	SubtypeHiearchy p = new SubtypeHiearchy();
    	p.setSubtype(size);
    	return p;
    }



    @DynamoDBHashKey
	public String getSubtype() {
		return subtype;
	}



	public void setSubtype(String type) {
		this.subtype = type;
	}





	@DynamoDBAttribute
	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	@DynamoDBAttribute
	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}
    
    
	
	
}
