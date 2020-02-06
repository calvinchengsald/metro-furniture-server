package com.metro.model;

import java.io.Serializable;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "TypeHiearchy")
public class TypeHiearchy implements Serializable {

	
	private static final long serialVersionUID = 2119134097011967738L;
	private String type;
	private List<SubtypeHiearchy> subtype;
	private String description;
	private String url;
	


    public static TypeHiearchy createTypeHiearchy(String size){
    	TypeHiearchy p = new TypeHiearchy();
    	p.setType(size);
    	return p;
    }



    @DynamoDBHashKey
	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



    @DynamoDBHashKey
	public List<SubtypeHiearchy> getSubtype() {
		return subtype;
	}



	public void setSubtype(List<SubtypeHiearchy> subtype) {
		this.subtype = subtype;
	}



    @DynamoDBHashKey
	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



    @DynamoDBHashKey
	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}
    
    
	
	
}
