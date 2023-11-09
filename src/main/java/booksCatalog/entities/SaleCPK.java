package booksCatalog.entities;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class SaleCPK implements Serializable {
	@Column(name = "store_id")
	private String store_id;
	@Column(name = "title_id")
	private String title_id;
	@Override
	public int hashCode() {
		return Objects.hash(store_id, title_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SaleCPK other = (SaleCPK) obj;
		return Objects.equals(store_id, other.store_id) && Objects.equals(title_id, other.title_id);
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getTitle_id() {
		return title_id;
	}

	public void setTitle_id(String title_id) {
		this.title_id = title_id;
	}
}