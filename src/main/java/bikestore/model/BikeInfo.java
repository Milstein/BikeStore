package bikestore.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ebay.xcelite.annotations.Column;
import com.ebay.xcelite.annotations.Row;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "rowTotal", "id", "title", "description", "rating",
		"price", "quantity", "type", "images", "addedOn", "isactive" })
@Row(colsOrder = { "Title", "Description", "Rating", "Price", "Quantity",
		"Type", "Added On", "Is Active?" })
public class BikeInfo {

	@JsonProperty("rowTotal")
	private int rowTotal;

	@JsonProperty("id")
	private String id = new String();

	@JsonProperty("title")
	@Column(name = "Title")
	private String title = new String();

	@JsonProperty("description")
	@Column(name = "Description")
	private String description = new String();

	@JsonProperty("rating")
	@Column(name = "Rating")
	private double rating;

	@JsonProperty("price")
	@Column(name = "Price")
	private double price;

	@JsonProperty("quantity")
	@Column(name = "Quantity")
	private int quantity;

	@JsonProperty("type")
	@Column(name = "Type")
	private String type = new String();

	@JsonProperty("images")
	private List<Image> images = new ArrayList<Image>();

	@JsonProperty("addedOn")
	@Column(name = "Added On", dataFormat = "yyyy/MM/dd hh:mm:ss")
	private Date addedOn = new Date();

	@JsonProperty("isactive")
	@Column(name = "Is Active?")
	private boolean isactive = false;

	public BikeInfo() {

	}

	public int getRowTotal() {
		return rowTotal;
	}

	public void setRowTotal(int rowTotal) {
		this.rowTotal = rowTotal;
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

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
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

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public Date getAddedOn() {
		return addedOn;
	}

	public void setAddedOn(Date addedOn) {
		this.addedOn = addedOn;
	}

	public boolean isIsactive() {
		return isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}

}
