package booksCatalog.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import booksCatalog.entities.TitleAuthor;
import booksCatalog.entities.repo.TitleAuthorRepo;

@RestController
public class TitleAuthorController {
	@Autowired
	TitleAuthorRepo titleauthorRepo;

	@GetMapping("/titleauthors")
	public List<TitleAuthor> getAllTitleAuthor() {
		return titleauthorRepo.findAll();
	}
//@PostMapping("/addsale/9")
//public Sale addNewSale(@RequestBody Sale sale) {
//	try {
//		var optSale=saleRepo.findById(sale.getKey());
//		if(optSale.isPresent())
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"sale already present");
//		saleRepo.save(sale);
//		return sale;
//	}
//	catch(DataAccessException ex) {
//		throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage());
//	}
//}

//@Transactional
//@DeleteMapping("/deletepublishers/{pub_id}")
//public void deleteOnePublisher(@PathVariable("pub_id") String pub_id) {
//	var optPublisher = publisherRepo.findById(pub_id);
//	if (optPublisher.isPresent())
//		publisherRepo.deleteById(pub_id);
//	else
//		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher ID Not Found!");
//}
//
//
//@PutMapping("/publishers/{pub_id}")
//public void updatePublisher(@PathVariable("pub_id") String pub_id, 
//		@RequestParam("pub_name") String pub_name) {
//	var optPublisher = publisherRepo.findById(pub_id);
//	if (optPublisher.isPresent()) {
//		var pub = optPublisher.get();
//		pub.setPub_name(pub_name);
//		publisherRepo.save(pub);
//	} else
//		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pub id Not Found!");
//}
}