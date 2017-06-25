package cn.itcast.lucene.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.FloatField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import cn.itcast.lucene.dao.LuceneDao;
import cn.itcast.lucene.dao.LuceneDaoImpl;
import cn.itcast.lucene.pojo.Book;

public class LuceneTest {
	@Test
	public void demo() throws Exception{
		//1.采集数据
		LuceneDao luceneDao = new LuceneDaoImpl();
		List<Book> books = luceneDao.findbooks();
		
		//2.创建Document文档对象
		Document document = new Document();
		
		//3.创建分析器（分词器） 	
		Analyzer analyzer = new IKAnalyzer();
		//4.创建IndexWriterConfig配置信息类
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LATEST, analyzer);
		//5.创建Directory对象，声明索引库存储位置
		Directory directory = FSDirectory.open(new File("G:\\love"));
		//6.创建IndexWriter写入对象
		IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
		//7.把Document写入到索引库中
		for (Book book : books) {
			Integer id = book.getId();
			String name = book.getName();
			String desc = book.getDesc();
			Float price = book.getPrice();
			String pic = book.getPic();
			document.add(new StringField("id",id.toString(),Store.YES));
			document.add(new TextField("name",name,Store.YES));
			document.add(new TextField("desc",desc,Store.YES));
			document.add(new FloatField("price",price,Store.YES));
			document.add(new StoredField("pic",pic));
			//保存文档到索引库存（   索引 保存  文档保存）
			indexWriter.addDocument(document);
		}
		//8.释放资源
		indexWriter.close();
	}
	@Test
	public void demo01() throws Exception{
		// 1. 创建Query搜索对象
		Query query = new TermQuery(new Term("name", "java"));
		// 2. 创建Directory流对象,声明索引库位置
		Directory directory = FSDirectory.open(new File("G:\\love"));
		 //3. 创建索引读取对象IndexReader
		IndexReader indexReader = DirectoryReader.open(directory);
		 //4. 创建索引搜索对象IndexSearcher
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		// 5. 使用索引搜索对象，执行搜索，返回结果集TopDocs
		TopDocs topDocs = indexSearcher.search(query, 5);
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (ScoreDoc scoreDoc : scoreDocs) {
			// 6. 解析结果集
			Document document = indexSearcher.doc(scoreDoc.doc);
			System.out.println(document.get("id"));
			System.out.println(document.get("name"));
			System.out.println(document.get("pic"));
			System.out.println(document.get("price"));
			System.out.println(document.get("desc"));
		}
		// 7. 释放资源
		directory.close();
	}
}
