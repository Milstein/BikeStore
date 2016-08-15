package bikestore.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mongodb.morphia.annotations.Embedded;

import com.ebay.xcelite.annotations.Column;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LatestBikeInfo {

	@JsonProperty("id")
	private String id = new String();

	@JsonProperty("title")
	private String title = new String();

	@JsonProperty("description")
	private String description = new String();

	@JsonProperty("rating")
	private int rating;

	@JsonProperty("price")
	private double price;

	@JsonProperty("quantity")
	private int quantity;

	@JsonProperty("type")
	private String type = new String();

	@JsonProperty("primaryimage")
	private String image = new String();

	@JsonProperty("addedon")
	private Date addedon = new Date();

	@JsonProperty("isactive")
	private boolean isactive = false;

	public LatestBikeInfo() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Date getAddedon() {
		return addedon;
	}

	public void setAddedon(Date addedon) {
		this.addedon = addedon;
	}

	public boolean isIsactive() {
		return isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}

}
