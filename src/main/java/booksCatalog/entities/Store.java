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
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "stores")
public class Store {
	@Id
	@NotBlank(message = "store_id is required")
	@Pattern(regexp = "^[A-Z]{2}$", message = "Store ID should be two capital letters")
	@Column(name = "store_id")
	private String store_id;
	@NotBlank(message = "location is required")
	@Column(name = "location")
	private String location;
	@NotBlank(message = "city is required")
	@Column(name = "city")
	private String city;
	@NotBlank(message = "country is required")
	@Pattern(regexp = "^[A-Z]{3}$", message = "Country should be three capital letters")
	@Column(name = "country")
	private String country;

	@OneToMany(cascade=CascadeType.ALL,mappedBy="store")
	@JsonIgnore
	private List<Sale> sales = new ArrayList<>();

	@ManyToMany(mappedBy = "storesList")
	@JsonIgnore
	Set<Title> titles = new HashSet<Title>();

	public Store() {
		super();
	}

	public Store(String store_id, String location, String city, String country) {
		super();
		this.store_id = store_id;
		this.location = location;
		this.city = city;
		this.country = country;
	}

	@Override
	public String toString() {
		return "Store [store_id=" + store_id + ", location=" + location + ", city=" + city + ", country=" + country
				+ "]";
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public int hashCode() {
		return Objects.hash(city, country, location, store_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Store other = (Store) obj;
		return Objects.equals(city, other.city) && Objects.equals(country, other.country)
				&& Objects.equals(location, other.location) && Objects.equals(store_id, other.store_id);
	}
}