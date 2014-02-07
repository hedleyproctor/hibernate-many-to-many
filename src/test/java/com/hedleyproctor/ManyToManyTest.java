package com.hedleyproctor;

import static org.testng.Assert.assertEquals;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.hedleyproctor.domain.CategorizedItem;
import com.hedleyproctor.domain.Category;
import com.hedleyproctor.domain.Category2;
import com.hedleyproctor.domain.Category3;
import com.hedleyproctor.domain.CategoryItemRelationship;
import com.hedleyproctor.domain.Item;
import com.hedleyproctor.domain.Item2;
import com.hedleyproctor.domain.Item3;



/** Three approaches:
 * 1. Join table that is not itself mapped as an entity.
 * 2. Join table that is mapped as an entity – an association class.
 * 3. Composite element class.
 * 
 * @author Hedley Proctor
 *
 */
public class ManyToManyTest {
	
    private SessionFactory sessionFactory;

    @BeforeClass
    public void setUp() throws SQLException {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }
    
    /** Using a join table that is not itself mapped as an entity.
     * 
     */
    @Test
    public void joinTableNotAnEntity() {
    	Item book1 = new Item();
    	book1.setName("A Tale of Two Cities");
    	
    	Item book2 = new Item();
    	book2.setName("Far from the Madding Crowd");
    	
    	Item book3 = new Item();
    	book3.setName("The Colour of Magic");

    	Category books = new Category();
    	books.setName("Books");
    	Set<Item> allBooks = new HashSet<Item>();
    	allBooks.add(book1);
    	allBooks.add(book2);
    	allBooks.add(book3);
    	books.setItems(allBooks);
    	
    	Category classicsCategory = new Category();
    	classicsCategory.setName("Classics");
    	Set<Item> classicBooksSet = new HashSet<Item>();
    	classicBooksSet.add(book1);
    	classicBooksSet.add(book2);
    	classicsCategory.setItems(classicBooksSet);
    	
    	Session session = sessionFactory.openSession();
    	Transaction tx = session.beginTransaction();
    	session.save(book1);
    	session.save(book2);
    	session.save(book3);
    	session.save(books);
    	session.save(classicsCategory);
    	tx.commit();
    	session.close();

    	session = sessionFactory.openSession();
    	tx = session.beginTransaction();
    	SQLQuery query = session.createSQLQuery("select count(*) from category_item");
    	List results = query.list();
    	BigInteger count = (BigInteger)results.get(0);
    	assertEquals(count,BigInteger.valueOf(5));
    	tx.commit();
    	session.close();
    }

    @Test
    public void joinTableAsEntity() {
    	Item2 book1 = new Item2();
    	book1.setName("Crime and Punishment");
    	Item2 book2 = new Item2();
    	book2.setName("War and Peace");
    	
    	Category2 classicsCategory = new Category2();
    	classicsCategory.setName("Russian classics");
    	
    	Session session = sessionFactory.openSession();
    	Transaction tx = session.beginTransaction();
    	session.save(book1);
    	session.save(book2);
    	session.save(classicsCategory);

    	// need to create these relationships after we have saved the objects, so that they have identifiers
    	CategoryItemRelationship categoryItemRelationship = new CategoryItemRelationship(classicsCategory, book1, new Date());
    	CategoryItemRelationship categoryItemRelationship2 = new CategoryItemRelationship(classicsCategory, book2, new Date());

    	session.save(categoryItemRelationship);
    	session.save(categoryItemRelationship2);
    	tx.commit();
    	session.close();
    	
    	session = sessionFactory.openSession();
    	tx = session.beginTransaction();
    	SQLQuery query = session.createSQLQuery("select count(*) from category_item_relationship");
    	List results = query.list();
    	BigInteger count = (BigInteger)results.get(0);
    	assertEquals(count,BigInteger.valueOf(2));
    	tx.commit();
    	session.close();
    }

    /** When modelled as a component, the many to many association will be created automatically when added to the set
     * in the owning category, no cascade settings are required. However, you cannot navigate directly from Item to CategorizedItem. 
     */
    @Test
    public void collectionOfComponents() {
    	Item3 book1 = new Item3();
    	book1.setName("Under the Greenwood Tree");
    	
    	Item3 book2 = new Item3();
    	book2.setName("Tess of the d'Urbervilles");
    	
    	Category3 classicsCategory = new Category3();
    	classicsCategory.setName("Classics");
    	
    	Session session = sessionFactory.openSession();
    	Transaction tx = session.beginTransaction();
    	session.save(book1);
    	session.save(book2);
    	session.save(classicsCategory);
    	tx.commit();
    	
    	CategorizedItem categorizedItem = new CategorizedItem("hedley", classicsCategory, book1);
    	CategorizedItem categorizedItem2 = new CategorizedItem("hedley", classicsCategory, book2);
    	Set<CategorizedItem> categorizedItems = new HashSet<CategorizedItem>();
    	categorizedItems.add(categorizedItem);
    	categorizedItems.add(categorizedItem2);
    	classicsCategory.setCategorizedItems(categorizedItems);
    	
    	session = sessionFactory.openSession();
    	tx = session.beginTransaction();
    	session.update(classicsCategory);
    	tx.commit();
    
    	SQLQuery query = session.createSQLQuery("select count(*) from CATEGORIZED_ITEMS");
    	List results = query.list();
    	BigInteger count = (BigInteger)results.get(0);
    	session.close();
    	assertEquals(count,BigInteger.valueOf(2));
    }
}
