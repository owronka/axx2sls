package com.axxessio.axx2sls.content;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.axxessio.axx2sls.content.service.pdo.Area;
import com.axxessio.axx2sls.content.service.pdo.Picture;

public class ReadDB {
	private static String END_POINT = "https://dynamodb.eu-central-1.amazonaws.com";
	
	@Test
	public void test() {
        AmazonDynamoDBClient client = new AmazonDynamoDBClient().withEndpoint(END_POINT);
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        
		Area a = null;
		Picture p = null;
		
		try {
			// read aarea
			a = mapper.load (Area.class, "1");

			System.out.println(a.toJSON());

			p = mapper.load (Picture.class, "1");

			System.out.println(p.toJSON());
			
			// update area
			a.setName("ReadDB Test");
			
			mapper.save(a);

			// insert area
			a.setId(null);
			a.setVersion(null);
			
			mapper.save(a);
			
			mapper.delete(a);
			
			List<Area> areas = mapper.scan(Area.class, new DynamoDBScanExpression());
			
			System.out.println("Größe der Area-Liste ist [" + areas.size() + "]");

			List<Picture> pictures = mapper.scan(Picture.class, new DynamoDBScanExpression());
			
			System.out.println("Größe der Picture-Liste ist [" + pictures.size() + "]");

		    Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		    eav.put(":val", new AttributeValue().withS("1"));
		 
			List<Picture> areaPictures = mapper.scan(Picture.class, new DynamoDBScanExpression().withFilterExpression("areaId = :val").withExpressionAttributeValues(eav));
			
			System.out.println("Größe der Picture-Liste für Area [1] ist [" + areaPictures.size() + "]");
		} catch (Exception xcptn) {
			xcptn.printStackTrace();
		}
		
	}
}
