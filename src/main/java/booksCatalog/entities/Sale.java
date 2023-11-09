package booksCatalog.entities;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "sales")
public class Sale {
	@EmbeddedId
	private SaleCPK key;
	@NotBlank(message = "qty_sold cannot be null")
	@Column(name = "qty_sold")
	private String qty_sold;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "title_id", insertable = false, updatable = false)
	private Title title;
	@ManyToOne
	@JoinColumn(name = "store_id", insertable = false, updatable = false)
	private Store store;

	public String getQty_sold() {
		return qty_sold;
	}

	public void setQty_sold(String qty_sold) {
		this.qty_sold = qty_sold;
	}

	public SaleCPK getKey() {
		return key;
	}

	public void setKey(SaleCPK key) {
		this.key = key;
	}

	@Override
	public int hashCode() {
		return Objects.hash(key, qty_sold);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sale other = (Sale) obj;
		return Objects.equals(key, other.key) && Objects.equals(qty_sold, other.qty_sold);
	}
}