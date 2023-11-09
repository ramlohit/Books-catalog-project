package booksCatalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import booksCatalog.entities.repo.AuthorRepo;                
import booksCatalog.entities.repo.PublisherRepo;
import booksCatalog.entities.repo.SaleRepo;
import booksCatalog.entities.repo.StoreRepo;
import booksCatalog.entities.repo.TitleAuthorRepo;
import booksCatalog.entities.repo.TitleRepo;
@SpringBootApplication
public class TestApplication implements CommandLineRunner{
	@Autowired
	TitleRepo tr;
	
	@Autowired
	PublisherRepo pr;
	
	@Autowired
	AuthorRepo ar;
	
	@Autowired
	TitleAuthorRepo tar;
	
	@Autowired
	SaleRepo sr;
	
	@Autowired
	StoreRepo st;
	
//	public List<Sale> displayAllSales() {
//		var opPublisher=sr.findAll();
//		return opPublisher;
//	}
//	public List<Publisher> displayAllPublishers() {
//		var opPublisher=tr.findAll();
//		return opPublisher;
//	}
//	public void titlepubid(){
//		for(var c: tr.findByid()) {
//			System.out.println(c.getTitlesList());
//		}
//	}
	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		
//		System.out.println(displayAllPublishers());
//		titlepubid();
		
//				for (var v : tr.sortByPublisherCity()) {
//					System.out.println(v);
////				}
//		String authorId = "101";
//        var titlesByAuthor = tr.findByAuthorId(authorId);
//        System.out.println("Titles by Author ID " + authorId + ": " + titlesByAuthor);
//		String storeId = "AA";
//      var titlesByAuthor = tr.findByStoreId(storeId);
//      System.out.println("Titles by Store ID " + storeId + ": " + titlesByAuthor);
//		String storeId = "AA";
//		var titlesByStore = tr.findByStoreId(storeId);
//		System.out.println("Titles by Store ID " + storeId + ": " + titlesByStore);
////		String titleId="11";
//		var titlesByPublisher = pr.findByTitleId(titleId);
//		System.out.println("Publishers by TitleId " + titleId+ ": " + titlesByPublisher );
		
//		 List<Author> authors = ar.findByTitles_TitleId("11");
//
//	        // Process the retrieved authors
//	        for (Author author : authors) {
//	            System.out.println("Author ID: " + author.getAu_id() + ", Name: " + author.getAu_name());
		
//		List<TitleAuthor> titleAuthors = tar.findByTitle_idAndAuthor_id("11", "101");
//
//		for (TitleAuthor titleAuthor : titleAuthors) {
//		    System.out.println(titleAuthor);
//		   
//		System.out.println(displayAllSales());
//		
//		List<Sale> titleSales = sr.findByTitle_idAndStore_id("11", "AA");
//
//		for (Sale titleSale : titleSales) {
//		    System.out.println(titleSale);
//		}
		
//		 List<Store> stores = st.findByTitles_TitleId("11");
//		
//		 	        // Process the retrieved authors
//		 	        for (Store store : stores) {
//		 	            System.out.println("Store ID: " + store.getStore_id() + ", Name: " + store.getLocation());
//	       //	 List<Store> stores = st.findByTitles_TitleId("11");
//
		    
//	            String titleId = "66";
//	            List<Sale> salesForTitle = tr.findSalesForTitle(titleId);
//
//	            System.out.println("Sales related to the title with ID " + titleId + ":");
//	            for (Sale sale : salesForTitle) {
//	            	if (sale != null) {
//	                System.out.println("Sale ID: " + sale.getKey().getStore_id()); // Adjust this based on your Sale entity
//	                System.out.println("Quantity Sold: " + sale.getQty_sold());
//	                // Print other sale details if needed
//	               
//	            	}
//	            }
//		String storeId = "AA";
//        List<Sale> salesForTitle = st.findSalesForStore(storeId);
//        System.out.println("Sales related to the title with ID " + storeId + ":");
//        for (Sale sale : salesForTitle) {
//        	if (sale != null) {
//            System.out.println("Sale ID: " + sale.getKey().getStore_id()); // Adjust this based on your Sale entity
//            System.out.println("Quantity Sold: " + sale.getQty_sold());
            // Print other sale details if needed
////
        	}
}