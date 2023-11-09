package booksCatalog.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "titles")
public class Title {
	@Id
	@NotBlank(message = "Title ID is required")
	@Column(name = "title_id")
	private String title_id;
	@NotBlank(message = "Title Name is required")
	@Column(name = "title")
	private String title;
	@NotBlank(message = "Price is required")
	@Column(name = "price")
	private String price;
	@NotNull(message = "Publisher ID is required")
	@Column(name = "pub_id")
	private String pubid;
	@NotBlank(message = "Ytd sales is required")
	@Column(name = "ytd_sales")
	private String ytdsales;
	@Size(max = 10, message = "should be of 10 characters")
	@NotBlank(message = "releaseyear is required")
	@Column(name = "release_year")
	private String release_year;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "pub_id", insertable = false, updatable = false)
	private Publisher publisher;


	@OneToMany(cascade=CascadeType.ALL,mappedBy="title")
	@JsonIgnore
	private List<Sale> sales = new ArrayList<Sale>();

	public Set<Author> getAuthorsList() {
		return authorsList;
	}

	public void setAuthorsList(Set<Author> authorsList) {
		this.authorsList = authorsList;
	}

	public Set<Store> getStoresList() {
		return storesList;
	}

	public void setStoresList(Set<Store> storesList) {
		this.storesList = storesList;
	}

	@ManyToMany
	@JoinTable(name = "titleauthor", joinColumns = @JoinColumn(name = "title_id"), inverseJoinColumns = @JoinColumn(name = "au_id"))
	@JsonIgnore
	private Set<Author> authorsList = new HashSet<Author>();

	@ManyToMany
	@JoinTable(name = "sales", joinColumns = @JoinColumn(name = "title_id"), inverseJoinColumns = @JoinColumn(name = "store_id"))
	@JsonIgnore
	private Set<Store> storesList = new HashSet<Store>();


	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public Title(String title_id, String title, String price, String pub_id, String ytd_sales, String release_year) {
		super();
		this.title_id = title_id;
		this.title = title;
		this.price = price;
		this.pubid = pub_id;
		this.ytdsales = ytd_sales;
		this.release_year = release_year;
	}

	public Title() {
		super();
	}

	public String getTitle_id() {
		return title_id;
	}

	public void setTitle_id(String title_id) {
		this.title_id = title_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPub_id() {
		return pubid;
	}

	public void setPub_id(String pub_id) {
		this.pubid = pub_id;
	}

	public String getYtd_sales() {
		return ytdsales;
	}

	public void setYtd_sales(String ytd_sales) {
		this.ytdsales = ytd_sales;
	}

	public String getRelease_year() {
		return release_year;
	}

	public void setRelease_year(String release_year) {
		this.release_year = release_year;
	}

	@Override
	public int hashCode() {
		return Objects.hash(price, pubid, release_year, title, title_id, ytdsales);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Title other = (Title) obj;
		return Objects.equals(price, other.price) && Objects.equals(pubid, other.pubid)
				&& Objects.equals(release_year, other.release_year) && Objects.equals(title, other.title)
				&& Objects.equals(title_id, other.title_id) && Objects.equals(ytdsales, other.ytdsales);
	}

	@Override
	public String toString() {
		return "Title [title_id=" + title_id + ", title=" + title + ", price=" + price + ", pub_id=" + pubid
				+ ", ytd_sales=" + ytdsales + ", release_year=" + release_year + "]";
	}
}