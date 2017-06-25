package cn.itcast.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrjDemo {
	private HttpSolrServer httpSolrServer;
	private void init() {
		String url = "http://localhost:8080/solr/";
		this.httpSolrServer = new HttpSolrServer(url);
	}
	@Test
	public void solrjdemo01() throws Exception, Exception{
		String url = "http://localhost:8080/solr/";
		HttpSolrServer httpSolrServer = new HttpSolrServer(url);
		SolrInputDocument sid = new SolrInputDocument();
		sid.addField("name", "吕家豪");
		sid.addField("id", "88");
		httpSolrServer.add(sid);
		httpSolrServer.commit();
	}
	@Test
	public void delete() throws Exception, Exception{
		this.httpSolrServer.deleteByQuery("name:吕家豪");
		this.httpSolrServer.commit();
	}
	@Test
	public void selete() throws Exception{
		SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		QueryResponse queryResponse = this.httpSolrServer.query(query);
		SolrDocumentList results = queryResponse.getResults();
		// 遍历搜索结果
		for (SolrDocument solrDocument : results) {

		System.out.println("----------------------------------------------------");

			System.out.println("id：" + solrDocument.get("id"));
			System.out.println("content" + solrDocument.get("content"));
		}
	}
}
