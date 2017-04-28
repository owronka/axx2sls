package com.axxessio.axx2sls.content;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.junit.Test;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class LoadDB {

    @Test
    public void test() throws JsonParseException, IOException {
    Table table;
    JsonParser parser;
    JsonNode rootNode;

    
    AmazonDynamoDBClient client = new AmazonDynamoDBClient().withEndpoint("https://dynamodb.eu-central-1.amazonaws.com");
    DynamoDB dynamoDB = new DynamoDB(client);

    table = dynamoDB.getTable("area");

    parser = new JsonFactory().createParser(new File("./src/main/resources/areas.json"));
    
    rootNode = new ObjectMapper().readTree(parser);

    loadTable (rootNode, table);
    
    parser.close();    

    table = dynamoDB.getTable("picture");

    parser = new JsonFactory().createParser(new File("./src/main/resources/pictures.json"));
    
    rootNode = new ObjectMapper().readTree(parser);

    loadTable (rootNode, table);
    
    parser.close();
  }

  private void loadTable (JsonNode rootNode, Table table) {
    Iterator<JsonNode> iter = rootNode.iterator();

    ObjectNode currentNode;

    while (iter.hasNext()) {
      currentNode = (ObjectNode) iter.next();

      String id = currentNode.path("id").asText();

      try {
        table.putItem(Item.fromJSON(currentNode.toString()).withPrimaryKey("id", id));
        System.out.println("PutItem succeeded [" + id + "] into table [" + table.getTableName() + "]");

      } catch (Exception e) {
        System.err.println("Unable to add data set [" + id + "] into table [" + table.getTableName() + "]");
        System.err.println(e.getMessage());
        break;
      }
    }
  }
}
