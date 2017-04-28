package com.axxessio.axx2sls.content.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.axxessio.axx2sls.content.service.pdo.Area;
import com.axxessio.axx2sls.content.service.pdo.Picture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class ContentService {
	private static BasicAWSCredentials creds = new BasicAWSCredentials("################", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

	private DynamoDBMapper mapper;

	public ContentService (LambdaLogger llog){
		AmazonDynamoDB addb = AmazonDynamoDBClientBuilder.
						      standard().
							  withCredentials(new AWSStaticCredentialsProvider(creds)).
							  withEndpointConfiguration(new EndpointConfiguration("dynamodb.eu-central-1.amazonaws.com", "eu-central-1")).build();

        mapper = new DynamoDBMapper(addb);
	}
	
	public Area getArea(String id) {
		Area a = null;
		
		a = mapper.load (Area.class, id);
		
		return a;
	}

	public List<Area> getAreas() {
		List<Area> areas = null;
		
		areas = mapper.scan(Area.class, new DynamoDBScanExpression());
		
		ArrayList<Area> al = new ArrayList<Area>(areas);
		
		Collections.sort(al);
		
		return al;
	}

	public List<Picture> getPictures(String areaId) {
		List<Picture> pictures = null; 
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
	    
		eav.put(":val", new AttributeValue().withS(areaId));
	 
		pictures = mapper.scan(Picture.class, new DynamoDBScanExpression().withFilterExpression("areaId = :val").withExpressionAttributeValues(eav));
		
		ArrayList<Picture> al = new ArrayList<Picture>(pictures);
		
		Collections.sort(al);
		
		return al;
	}
	
	public void delArea (String id) {
		Area a = mapper.load (Area.class, id);

		mapper.delete(a);
	}

	public void setArea (Area newArea) {
		if (newArea.getId() == null) {
			newArea.setVersion(null);
		}
		
		mapper.save(newArea);
	}
}
