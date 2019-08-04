package com.example.elasticsearch.controller;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import javax.websocket.server.PathParam;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.elasticsearch.model.Product;
/**
 * @Sadique
 */

@RestController
@RequestMapping("/data")
@CrossOrigin(value="*")
public class ProductController {

    @Autowired
    Client client;
    //@PostMapping("/create")
    public String create(/*@RequestBody*/ Product product ) throws IOException {
        
        IndexResponse response = client.prepareIndex("dataweave_final", "product")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("color_code", product.getColorCode())
                        .field("title", product.getTitle())
                        .field("thumbnail", product.getThumbnail())
                        .field("description", product.getDescription())
                        .field("brand", product.getBrand())
                        .field("mrp", product.getMrp())
                        .field("country", product.getCountry())
                        .field("size", product.getSize())
                        .endObject()
                )
                .get();
               System.out.println("response id:"+response.getId());
        return response.getResult().toString();
    }


    @GetMapping("/view/{id}")
    public Map<String, Object> view(@PathVariable final String id) {
        GetResponse getResponse = client.prepareGet("dataweave_final","", id).get();
        System.out.println(getResponse.getSource());


        return getResponse.getSource();
    }
    @GetMapping("/view/name/{field}")
    public Map<String, Object> searchByName(@PathVariable final String field) {
        Map<String,Object> map = null;
        SearchResponse response = client.prepareSearch("dataweave_final")
                                .setSearchType(SearchType.QUERY_AND_FETCH)
                                .setQuery(QueryBuilders.matchQuery("_id", field))
                                .get()
                                ;
        List<SearchHit> searchHits = Arrays.asList(response.getHits().getHits());
        map =   searchHits.get(0).getSource();
        return map;

    }
    
    @GetMapping("/view/fulltext")
    public String fullTextSearch(@PathParam(value = "text") final String text) {
        Map<String,Object> map = null;
        SearchResponse response = client.prepareSearch("dataweave_final")
                                //.setTypes("employee")
                                .setSearchType(SearchType.QUERY_AND_FETCH)
                                .setQuery(QueryBuilders.multiMatchQuery(text, new String[] {"title","color_code","thumbnails","size","mrp","brand","country","description"}))
                                .get()
                                ;
        List<SearchHit> searchHits = Arrays.asList(response.getHits().getHits());
        //map =   searchHits.get(0).getSource();
        JSONParser jsonParser = new JSONParser();
        JSONArray array = new JSONArray();
        for(SearchHit sh : searchHits) {
        	String json = sh.getSourceAsString();
        	try {
				JSONObject obj = (JSONObject) jsonParser.parse(json);
				obj.put("id",sh.getId());
				array.add(obj);
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return array.toJSONString();

    }
    
    @GetMapping("/view/all")
    public List<SearchHit> searchAll() {
        Map<String,Object> map = null;
        SearchResponse response = client.prepareSearch("users")
                                .setTypes("employee")
                                .setSearchType(SearchType.QUERY_AND_FETCH)
                                .setQuery(QueryBuilders.matchAllQuery())
                                .get()
                                ;
        List<SearchHit> searchHits = Arrays.asList(response.getHits().getHits());
        //map =   searchHits.get(0).getSource();
        return searchHits;

    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable final String id) throws IOException {

        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("users")
                .type("employee")
                .id(id)
                .doc(jsonBuilder()
                        .startObject()
                        .field("name", "Rajesh")
                        .endObject());
        try {
            UpdateResponse updateResponse = client.update(updateRequest).get();
            System.out.println(updateResponse.status());
            return updateResponse.status().toString();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e);
        }
        return "Exception";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable final String id) {

        DeleteResponse deleteResponse = client.prepareDelete("users", "employee", id).get();

        System.out.println(deleteResponse.getResult().toString());
        return deleteResponse.getResult().toString();
    }
    @GetMapping("/extract")
    public String extractMeaningfullData() {
    	FileInputStream fim;
    	Scanner sc = null;
    	//JSONArray arr = new JSONArray();
		try {
			fim = new FileInputStream("20190802_20190801_1_bloomingdales-us_product.json");
		
    	sc = new Scanner(fim);
    	
    	while(sc.hasNext())
    	{
    	JSONObject obj = readLine(sc.nextLine());
    	//arr.add(obj);
    	}
    	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			sc.close();
		}
		
		//System.out.println(arr.toJSONString());
		//System.out.println("size of record = " + arr.size());
		
		return null;
    }


	private JSONObject readLine(String line) {
		
		JSONParser parse = new JSONParser();
		JSONObject jsonObject = new JSONObject();
		try {
			JSONObject jobj = (JSONObject)parse.parse(line); 
			String color = (String) jobj.get("color_code");
			String title = (String)jobj.get("title");
			String thumbnail = (String)jobj.get("thumbnail");
			String description = (String)jobj.get("description");
			String brand = (String)jobj.get("brand");
			String mrp = (String)jobj.get("mrp");
			String country = (String)jobj.get("country");
			String size = (String)jobj.get("size");
			
			
			jsonObject.put("color_code", color);
			jsonObject.put("title", title);
			jsonObject.put("thumbnail", thumbnail);
			jsonObject.put("description", description);
			jsonObject.put("brand", brand);
			jsonObject.put("mrp", mrp);
			jsonObject.put("country", country);
			jsonObject.put("size", size);
			
			Product p = new Product(color, title, thumbnail, description, brand, mrp, country, size);
			create(p);
			
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}
}