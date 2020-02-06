package com.metro.model;

import java.io.Serializable;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "ProductInfo")
public class ProductInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String item_code;
    private String notes;
    private String cost;
    private String thickness;
    private String type;
    private String subtype;
    private List<String> tag;
    private List<String> size_option;
    private List<String> edge_option;
 
    
    public static ProductInfo createProductInfo(String item_code){
    	ProductInfo p = new ProductInfo();
    	p.setItem_code(item_code);
    	return p;
    }
    
    @DynamoDBHashKey
    public String getItem_code() {
        return item_code;
    }
 
    @DynamoDBAttribute
    public String getCost() {
        return cost;
    }


    @DynamoDBAttribute
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

    @DynamoDBAttribute
	public String getThickness() {
		return thickness;
	}

	public void setThickness(String thickness) {
		this.thickness = thickness;
	}

    @DynamoDBAttribute
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

    @DynamoDBAttribute
	public List<String> getTag() {
		return tag;
	}

	public void setTag(List<String> tag) {
		this.tag = tag;
	}

    @DynamoDBAttribute
	public List<String> getSize_option() {
		return size_option;
	}

	public void setSize_option(List<String> size_option) {
		this.size_option = size_option;
	}

    @DynamoDBAttribute
	public List<String> getEdge_option() {
		return edge_option;
	}

	public void setEdge_option(List<String> edge_option) {
		this.edge_option = edge_option;
	}


	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

    @DynamoDBAttribute
	public String getSubtype() {
		return subtype;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}
	
	

}