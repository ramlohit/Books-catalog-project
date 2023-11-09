package booksCatalog.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Table(name = "publishers")
@Entity
public class Publisher {
	@Id
	@Column(name = "pub_id")
	@Size(max = 4, message = "pub_id must be less than or equal to 4 characters")
	@Pattern(regexp = "^\\d{4}$", message = "pub_id should Have 4 digits")
	@NotBlank(message = "pub_id cannot be blank")
	private String pub_id;
	@NotBlank(message = "pub_name is required")
	@Column(name = "pub_name")
	private String pubName;
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format")
	@Column(name = "email")
	private String email;
	@NotBlank(message = "country is required")
	@Column(name = "country")
	private String country;
	@NotBlank(message = "city is required")
	@Column(name = "city")
	private String city;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "publisher")
	@JsonIgnore
	private List<Title> titlesList = new ArrayList<Title>();

	public List<Title> getTitlesList() {
		return titlesList;
	}

	public void setTitlesList(List<Title> titlesList) {
		this.titlesList = titlesList;
	}

	public String getPub_id() {
		return pub_id;
	}

	public Publisher(String pub_id, String pub_name, String email, String country, String city) {
		super();
		this.pub_id = pub_id;
		this.pubName = pub_name;
		this.email = email;
		this.country = country;
		this.city = city;
	}

	public Publisher() {
		super();
	}

	public void setPub_id(String pub_id) {
		this.pub_id = pub_id;
	}

	public String getPub_name() {
		return pubName;
	}

	public void setPub_name(String pub_name) {
		this.pubName = pub_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "Publisher [pub_id=" + pub_id + ", pub_name=" + pubName + ", email=" + email + ", country=" + country
				+ ", city=" + city + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(city, country, email, pub_id, pubName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Publisher other = (Publisher) obj;
		return Objects.equals(city, other.city) && Objects.equals(country, other.country)
				&& Objects.equals(email, other.email) && Objects.equals(pub_id, other.pub_id)
				&& Objects.equals(pubName, other.pubName);
	}
}