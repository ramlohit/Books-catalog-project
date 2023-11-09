package booksCatalog.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import booksCatalog.entities.Publisher;
import booksCatalog.entities.Title;
import booksCatalog.entities.repo.AuthorRepo;
import booksCatalog.entities.repo.PublisherRepo;
import booksCatalog.entities.repo.TitleRepo;
import booksCatalog.entities.repo.dto.TitleAndAuthorDTO;
import booksCatalog.entities.repo.dto.TitleDetailsAuthorNamePublisherNameDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
public class TitleController {
	@Autowired
	TitleRepo titleRepo;
	@Autowired
	PublisherRepo publisherRepo;
	@Autowired
	AuthorRepo authorRepo;

	@GetMapping("/titles")
	@Operation(summary = "list of all title")
	public List<Title> getAllTitles() {
		return titleRepo.findAll();
	}

	@PostMapping("/addtitle")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Add a new Title", description = "Adding a new title details")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Added new Title Successfully"),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "500", description = "An issue occurred on the server while processing a request ") })
	public Title addNewTitle(@Valid @RequestBody Title title) {
		try {
			var optTitle = titleRepo.findById(title.getPub_id());
			if (optTitle.isPresent())
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "publisher already present");
			titleRepo.save(title);
			return title;
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deletetitle/{title_id}")
	@Operation(summary = "Delete a Title By title_id", description = "Given a title_id it deletes the existing Title by the given title_id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Deleted Title Successfully"),
			@ApiResponse(responseCode = "404", description = "Given title_id not found"),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "500", description = "an issue occurred on the server while processing a request") })
	public void deleteOnePTitle(@PathVariable("title_id") String title_id) {
		var optTitle = titleRepo.findById(title_id);
		if (optTitle.isPresent())
			titleRepo.deleteById(title_id);
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Title ID Not Found!");
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/titles/{title_id}")
	@Operation(summary = "Update a Title By title_id", description = "Given a title_id it Updates the existing title by the given title_id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Updated Title Successfully"),
			@ApiResponse(responseCode = "404", description = "title_id Not Found"),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "500", description = "an issue occurred on the server while processing a request") })
	public void updateTitle(@RequestBody Title updateTitle, @PathVariable("title_id") String title_id) {
		var optTitle = titleRepo.findById(title_id);
		if (optTitle.isPresent()) {
			var title = optTitle.get();
			title.setTitle(updateTitle.getTitle());
			title.setPrice(updateTitle.getPrice());
			title.setPub_id(updateTitle.getPub_id());
			title.setYtd_sales(updateTitle.getYtd_sales());
			title.setRelease_year(updateTitle.getRelease_year());
			titleRepo.save(title);
		} else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "titleid Not Found!");
	}

//9
	@GetMapping("/titlesbytitle/{title}")
	@Operation(summary = "Get All Titles that match the given title", description = "Get All Titles that match the Given Title")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Retrieved data Successfully"),
			@ApiResponse(responseCode = "404", description = "Title Not Found"),
			@ApiResponse(responseCode = "500", description = "An issue occurred on the server while processing a request") })
	public List<Title> matchGivenTitle(@PathVariable("title") String title) {
		List<Title> titles = titleRepo.findBytitle(title);
		if (titles.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "title not found");
		return titles;

	}

//10

	@GetMapping("/rangeOfPrice/{min}/{max}")
	@Operation(summary = "Get All Titles Between The Given Range of price", description = "Get All Titles Started Between the Given Range of prices")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Retrieved All Titles Successfully"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	public List<Title> titlesByprice(@PathVariable("min") String min, @PathVariable("max") String max) {
		List<Title> titles = titleRepo.findBypriceBetween(min, max);
		if (titles.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "given range is  not found");
		return titles;

	}

//11
	@GetMapping("/ytdSales")
	@Operation(summary = "Get Top 5 titles by year to day sale", description = "Get top 5 titles by year to day sale")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retrieved data Successfully"),
			@ApiResponse(responseCode = "500", description = "An issue occurred on the server while processing a request") })
	public List<Title> gettitles() {
		List<Title> titles = titleRepo.findTop5ByOrderByYtdsalesDesc();
		if (titles.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no titles found");
		return titles;

	}

	// 7
	@GetMapping("/titlesByPublisher/{pub_name}")
	@Operation(summary = "Get all titles By pub_name", description = "Get all titles by the given pub_name")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Retrieved data Successfully"),
			@ApiResponse(responseCode = "404", description = "pub_name Not Found"),
			@ApiResponse(responseCode = "500", description = "An issue occurred on the server while processing a request") })
	public List<Title> getTitlesByPublisherName(@PathVariable("pub_name") String publisherName) {
		Optional<Publisher> publisher = publisherRepo.findByPubName(publisherName);
		if (publisher!= null) {
			return titleRepo.findByPublisher(publisher);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No titles found in the specified publisher");
		}
	}

//8
	@GetMapping("/titlesByAuthor/{au_name}")
	@Operation(summary = "Get all Titles by au_name", description = "List all titles for the given au_name")
	@ApiResponses(value = { @ApiResponse(responseCode = "404", description = "au_name Not Found"),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "200", description = "Retrieved data Successfully"),
			@ApiResponse(responseCode = "500", description = "An issue occurred on the server while processing a request") })
	public List<TitleAndAuthorDTO> displayTitlesByAuthor(@PathVariable("au_name") String au_name) {

		var listObj = titleRepo.findTitlesByAuthor(au_name);
		if (listObj.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No authors found by the given name");
		}
		return listObj;
	}

//13
	@GetMapping("/titleDetailsAuthorNamePublisherNamebytitle_id/{title_id}")
	@Operation(summary = "Get Title Details, Author Name, Publisher Name by the given title_id", description = "Get All Title Details, Author Name, Publisher Name By the Given title_id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Retrieved data Successfully"),
			@ApiResponse(responseCode = "404", description = "title_id Not Found"),
			@ApiResponse(responseCode = "500", description = "An issue occurred on the server while processing a request") })
	public List<TitleDetailsAuthorNamePublisherNameDTO> displayTitleDetailsAuthorNamePublisherName(
			@PathVariable("title_id") String title_id) {
		var listObj = titleRepo.findTitleDetailsAuthorNamePublisherName(title_id);
		if (listObj.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "title id is not found");
		return listObj;
	}
}