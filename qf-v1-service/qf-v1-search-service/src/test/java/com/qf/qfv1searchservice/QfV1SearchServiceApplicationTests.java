package com.qf.qfv1searchservice;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QfV1SearchServiceApplicationTests {

	//springboot整合solr,实现对索引库的操作

	@Autowired
	private SolrClient solrClient;

	@Test
	public void saveOrUpdateTest() throws IOException, SolrServerException {
		//solr document 文档对象
		SolrInputDocument document =new SolrInputDocument();
		//填充数据
		document.setField("id",9998);
		document.setField("product_name","西瓜zi");
		document.setField("product_price","2998");
		document.setField("product_sale_point","xixi");
		document.setField("product_images","321");
		//多个solr
		solrClient.add("collection2",document);
		solrClient.commit("collection2");
		//只有一个collection的时候
//		solrClient.add(document);
//		solrClient.commit();
	}

	@Test
	public void queryTest() throws IOException, SolrServerException {
		 //组装查询条件
		SolrQuery queryCondition = new SolrQuery();
		//查询所有
//		queryCondition.setQuery("*:*");
		//是模糊匹配
		queryCondition.setQuery("product_name:最强悍的三代手机");
		//执行查询
		QueryResponse response = solrClient.query(queryCondition);
		//
		SolrDocumentList results = response.getResults();
		//遍历
		for (SolrDocument result : results) {
			System.out.println(result.getFieldValue("product_name"));
			System.out.println(result.getFieldValue("product_price"));
		}
	}

	@Test
	public void delTest() throws IOException, SolrServerException {
		//都是先分词在匹配，不是精确的删除
		solrClient.deleteByQuery("product_name:最强悍的三代手机");
		solrClient.commit();
	}
}

