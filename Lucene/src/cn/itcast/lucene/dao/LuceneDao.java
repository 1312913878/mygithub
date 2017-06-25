package cn.itcast.lucene.dao;

import java.util.List;
import java.util.ListIterator;

import cn.itcast.lucene.pojo.Book;

public interface LuceneDao {
	public List<Book> findbooks();
}
