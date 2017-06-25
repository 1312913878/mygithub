package cn.itcast.solrj;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class SolrDemo {
	@Test
	public void solrTest() throws Exception{
		Directory directory = FSDirectory.open(new File("G:\\love"));
		Analyzer analyzer = new IKAnalyzer();
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
		IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
		indexWriter.deleteDocuments(new Term("name","java"));
		indexWriter.close();
	}
	@Test
	public void solrjTest2() throws Exception{
		Directory directory = FSDirectory.open(new File("G:\\love"));
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_4_10_3, null);
		IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
		indexWriter.deleteAll();
		indexWriter.close();
	}
	@Test
	public void update() throws IOException{
		Directory directory = FSDirectory.open(new File("G:\\love"));
		Analyzer analyzer = new IKAnalyzer();
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
		IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
		Document document = new Document();
		document.add(new TextField("id","1888",Store.YES));
		document.add(new TextField("name","lucene测试test 002",Store.YES));
		indexWriter.updateDocument(new Term("name","test"), document);
		indexWriter.close();
	}
	@Test
	public void demo01() throws Exception{
		Query query = new TermQuery(new Term("name", "lucene"));
		doSearch(query);
	}
	private void doSearch(Query query) throws Exception {
		Directory directory = FSDirectory.open(new File("G:\\love"));
		IndexReader indexReader = DirectoryReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		TopDocs topDocs = indexSearcher.search(query, 10);
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (ScoreDoc scoreDoc : scoreDocs) {
			int i = scoreDoc.doc;
			Document document = indexSearcher.doc(i);
			System.out.println("docID:" + i);
			System.out.println("bookId:" + document.get("id"));
			System.out.println("name:" + document.get("name"));
			System.out.println("price:" + document.get("price"));
			System.out.println("pic:" + document.get("pic"));
			
		}
		indexReader.close();
	}
	@Test
	public void demo02() throws Exception{
		Query query = NumericRangeQuery.newFloatRange("price",54f, 56f, false, true);
		doSearch(query);
	}
	@Test
	public void demo03() throws Exception{
		Query query1 = new TermQuery(new Term("name","lucene"));
		Query query2 = NumericRangeQuery.newFloatRange("price",54f, 56f, false, true);
		BooleanQuery booleanQuery = new BooleanQuery();
		booleanQuery.add(query1, Occur.MUST);
		booleanQuery.add(query2, Occur.MUST);
		doSearch(booleanQuery);
	}
	@Test
	public void demo04() throws Exception{
		IKAnalyzer ikAnalyzer = new IKAnalyzer();
		QueryParser queryParser = new QueryParser("desc",ikAnalyzer );
		Query query = queryParser.parse("desc:java AND lucene");
		System.out.println(query);
		doSearch(query);
	}
	@Test
	public void demo05() throws Exception{
		Analyzer analyzer = new IKAnalyzer();
		String[] fields = {"desc","name"};
		MultiFieldQueryParser multiFieldQueryParser = new MultiFieldQueryParser(fields, analyzer);
		Query query = multiFieldQueryParser.parse("lucene");
		System.out.println(query);
		doSearch(query);
	}
}
