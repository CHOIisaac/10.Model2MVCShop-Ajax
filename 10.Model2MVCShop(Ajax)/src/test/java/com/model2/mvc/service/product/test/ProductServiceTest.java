package com.model2.mvc.service.product.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;


/*
 *	FileName :  UserServiceTest.java
 * ㅇ JUnit4 (Test Framework) 과 Spring Framework 통합 Test( Unit Test)
 * ㅇ Spring 은 JUnit 4를 위한 지원 클래스를 통해 스프링 기반 통합 테스트 코드를 작성 할 수 있다.
 * ㅇ @RunWith : Meta-data 를 통한 wiring(생성,DI) 할 객체 구현체 지정
 * ㅇ @ContextConfiguration : Meta-data location 지정
 * ㅇ @Test : 테스트 실행 소스 지정
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration	(locations = {	"classpath:config/context-common.xml",
		"classpath:config/context-aspect.xml",
		"classpath:config/context-mybatis.xml",
		"classpath:config/context-transaction.xml" })
public class ProductServiceTest {

	//==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

//	@Test
	public void testAddProduct() throws Exception {
		
		Product product = new Product();
		product.setProdName("testProductId");
		product.setProdDetail("testProdDetail");
		product.setManuDate("20190506");
		product.setPrice(111111);
		product.setFileName("1111");
		
		productService.addProduct(product);
		
		product = productService.getProduct(10032);

		//==> console 확인
		System.out.println(product);
		
		//==> API 확인
		Assert.assertEquals(10032, product.getProdNo());
		Assert.assertEquals("testProductId", product.getProdName());
		Assert.assertEquals("testProdDetail", product.getProdDetail());
		Assert.assertEquals("20190506", product.getManuDate());
		Assert.assertEquals(111111, product.getPrice());
		Assert.assertEquals("1111", product.getFileName());
	}
	

//	@Test
	public void testGetProduct() throws Exception {
		
		Product product = new Product();
		product = productService.getProduct(10032);

		//==> console 확인
		System.out.println(product);
		
		//==> API 확인
		Assert.assertEquals(10032, product.getProdNo());
		Assert.assertEquals("testProductId", product.getProdName());
		Assert.assertEquals("testProdDetail", product.getProdDetail());
		Assert.assertEquals("20190506", product.getManuDate());
		Assert.assertEquals(111111, product.getPrice());
		Assert.assertEquals("1111", product.getFileName());

		Assert.assertNotNull(productService.getProduct(10000));
		Assert.assertNotNull(productService.getProduct(10001));
	}
	
//	@Test
	 public void testUpdateProduct() throws Exception{
		 
		Product product = productService.getProduct(10032);
		Assert.assertNotNull(product);
		
//		Assert.assertEquals(10080, product.getProdNo());
//		Assert.assertEquals("testProductId", product.getProdName());
//		Assert.assertEquals("testProdDetail", product.getProdDetail());
//		Assert.assertEquals("20120506", product.getManuDate());
//		Assert.assertEquals(111111, product.getPrice());
//		Assert.assertEquals("1111", product.getFileName());

		product.setProdName("testProductId22");
		product.setProdDetail("testProdDetail22");
		product.setManuDate("22222222");
		product.setPrice(222222);
		product.setFileName("2222");
		
		productService.updateProduct(product);
		
		product = productService.getProduct(10032);
		Assert.assertNotNull(product);
		
//		//==> console 확인
		System.out.println(product);
			
		//==> API 확인
		Assert.assertEquals("testProductId22", product.getProdName());
		Assert.assertEquals("testProdDetail22", product.getProdDetail());
		Assert.assertEquals("22222222", product.getManuDate());
		Assert.assertEquals(222222, product.getPrice());
		Assert.assertEquals("2222", product.getFileName());
	 }
	 
	 //==>  주석을 풀고 실행하면....
//	 @Test
	 public void testGetProductListAll() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Product> list = (List<Product>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console 확인
	 	
	 	for (Product product : list) {
			System.out.println(product);
		}
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword("%자전%");
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Product>)map.get("list");
	 	Assert.assertEquals(1, list.size());
	 	
	 	//==> console 확인
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
//	@Test
	 public void testGetProductListByProdNo() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("10000");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(1, list.size());
	 	
//		//==> console 확인
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
//		//==> console 확인
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
//	@Test
	 public void testGetProductListByProdName() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword("%자%");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(2, list.size());
	 	
		//==> console 확인
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console 확인
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }	 
}