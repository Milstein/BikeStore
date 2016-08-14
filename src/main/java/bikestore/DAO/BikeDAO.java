package bikestore.DAO;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

import bikestore.DAL.MongoDBConnector;
import bikestore.model.Bike;
import bikestore.model.BikeInfo;
import bikestore.model.LatestBikeInfo;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;

public class BikeDAO extends BasicDAO<Bike, String> {

	private static final String DBNAME = "db_bikestore";
	public static final String COLLECTION_NAME = "bike";

	private static Morphia morphia;
	private static Datastore ds;
	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	private static Morphia getMorphia() throws UnknownHostException,
			MongoException {
		if (morphia == null) {
			morphia = new Morphia().map(Bike.class);
		}
		return morphia;
	}

	@Override
	public Datastore getDatastore() {
		if (ds == null) {
			try {
				ds = getMorphia().createDatastore(MongoDBConnector.getMongo(),
						DBNAME);
			} catch (UnknownHostException | MongoException e) {
				e.printStackTrace();
			}
		}
		ds.ensureIndexes();
		return ds;
	}

	public BikeDAO(MongoClient mongo, Morphia morphia, String dbName) {
		super(mongo, morphia, dbName);
	}

	public List<BikeInfo> findBikesForGrid(int offset, int limit,
			String bikeTitle, String addedOnFrom, String addedOnTo,
			Double priceFrom, Double priceTo, Boolean isActive)
			throws ParseException {
		Datastore ds = getDatastore();
		List<BikeInfo> bikes = new ArrayList<BikeInfo>();

		Query<Bike> bikeQuery = ds.createQuery(Bike.class);

		if (bikeTitle != null) {
			bikeQuery.criteria("title").containsIgnoreCase(bikeTitle);
		}

		if (addedOnFrom != null && !addedOnFrom.isEmpty()) {
			Date bikeAddedFrom = formatter.parse(addedOnFrom);
			bikeQuery.criteria("added on").greaterThanOrEq(bikeAddedFrom);
		}
		if (addedOnTo != null && !addedOnTo.isEmpty()) {
			Date bikeAddedTo = formatter.parse(addedOnTo);
			bikeQuery.criteria("added on").lessThanOrEq(bikeAddedTo);
		}

		if (priceFrom != null && priceFrom != 0.0) {
			bikeQuery.field("price").greaterThanOrEq(priceFrom);
		}
		if (priceTo != null && priceTo != 0.0) {
			bikeQuery.field("price").lessThanOrEq(priceTo);
		}

		if (isActive != null) {
			bikeQuery.criteria("is active").equal(isActive);
		}

		int rowTotal = bikeQuery.asList().size();
		List<Bike> allBikes = bikeQuery.offset(offset - 1).limit(limit)
				.order("-added on").asList();

		for (Bike bike : allBikes) {
			BikeInfo bikeInfo = new BikeInfo();

			bikeInfo.setRowTotal(rowTotal);
			bikeInfo.setId(bike.getId().toString());
			bikeInfo.setTitle(bike.getTitle());
			bikeInfo.setDescription(bike.getDescription());
			bikeInfo.setRating(bike.getRating());
			bikeInfo.setPrice(bike.getPrice());
			bikeInfo.setQuantity(bike.getQuantity());
			bikeInfo.setType(bike.getType());
			bikeInfo.setAddedon(bike.getAddedOn());
			bikeInfo.setIsactive(bike.isIsactive());
			bikes.add(bikeInfo);
		}
		// Collections.sort(delegations);
		return bikes;
	}

	public List<BikeInfo> findAllBikes(String bikeTitle, String addedOnFrom,
			String addedOnTo, Double priceFrom, Double priceTo, Boolean isActive)
			throws ParseException {
		Datastore ds = getDatastore();
		List<BikeInfo> bikes = new ArrayList<BikeInfo>();

		Query<Bike> bikeQuery = ds.createQuery(Bike.class);

		if (bikeTitle != null) {
			bikeQuery.criteria("title").containsIgnoreCase(bikeTitle);
		}

		if (addedOnFrom != null && !addedOnFrom.isEmpty()) {
			Date bikeAddedFrom = formatter.parse(addedOnFrom);
			bikeQuery.criteria("added on").greaterThanOrEq(bikeAddedFrom);
		}
		if (addedOnTo != null && !addedOnTo.isEmpty()) {
			Date bikeAddedTo = formatter.parse(addedOnTo);
			bikeQuery.criteria("added on").lessThanOrEq(bikeAddedTo);
		}

		if (priceFrom != null && priceFrom != 0.0) {
			bikeQuery.field("price").greaterThanOrEq(priceFrom);
		}
		if (priceTo != null && priceTo != 0.0) {
			bikeQuery.field("price").lessThanOrEq(priceTo);
		}

		if (isActive != null) {
			bikeQuery.criteria("is active").equal(isActive);
		}

		int rowTotal = bikeQuery.asList().size();
		List<Bike> allBikes = bikeQuery.order("-added on").asList();

		for (Bike bike : allBikes) {
			BikeInfo bikeInfo = new BikeInfo();

			bikeInfo.setRowTotal(rowTotal);
			bikeInfo.setId(bike.getId().toString());
			bikeInfo.setTitle(bike.getTitle());
			bikeInfo.setDescription(bike.getDescription());
			bikeInfo.setRating(bike.getRating());
			bikeInfo.setPrice(bike.getPrice());
			bikeInfo.setQuantity(bike.getQuantity());
			bikeInfo.setType(bike.getType());
			bikeInfo.setAddedon(bike.getAddedOn());
			bikeInfo.setIsactive(bike.isIsactive());
			bikes.add(bikeInfo);
		}
		// Collections.sort(delegations);
		return bikes;
	}

	public Bike findBikeImageDetailsByBikeId(ObjectId id) {
		Datastore ds = getDatastore();
		return ds.createQuery(Bike.class).field("_id").equal(id)
				.retrievedFields(true, "images").get();
	}

	public Bike findBikeDetailsByBikeId(ObjectId id) {
		Datastore ds = getDatastore();
		return ds.createQuery(Bike.class).field("_id").equal(id).get();
	}

	public List<Bike> findAllBikesInStore() {
		Datastore ds = getDatastore();
		return ds.createQuery(Bike.class).order("-added on").field("is active")
				.equal(true).asList();
	}

	public List<LatestBikeInfo> findLatestBikesInStore(String uploadPath)
			throws IOException {
		Datastore ds = getDatastore();
		List<LatestBikeInfo> bikes = new ArrayList<LatestBikeInfo>();
		List<Bike> allBikes = ds.createQuery(Bike.class).order("-added on")
				.field("is active").equal(true).field("images").exists()
				.asList();

		// Image rankImage = new Image();
		// rankImage.setSource(new ClassResource(String.format(
		// "/images/level%d.png", 1)));

		for (Bike bike : allBikes) {
			LatestBikeInfo bikeInfo = new LatestBikeInfo();
			bikeInfo.setId(bike.getId().toString());
			bikeInfo.setTitle(bike.getTitle());
			bikeInfo.setDescription(bike.getDescription());
			bikeInfo.setRating(bike.getRating());
			bikeInfo.setPrice(bike.getPrice());
			bikeInfo.setQuantity(bike.getQuantity());
			bikeInfo.setType(bike.getType());
			bikeInfo.setImage(bike.getImages().get(0).getFilepath());

			// File relativeFile;
			// try {
			// File f = new File(uploadPath
			// + bike.getImages().get(0).getFilename());
			// if (f.exists()) {
			// System.out.println(f.getCanonicalPath());
			// relativeFile = new File(getClass().getResource(
			// bike.getImages().get(0).getFilepath()).toURI());
			// bikeInfo.setImage(relativeFile.getAbsolutePath());
			// }
			// } catch (URISyntaxException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }

			bikeInfo.setAddedon(bike.getAddedOn());
			bikeInfo.setIsactive(bike.isIsactive());
			bikes.add(bikeInfo);
		}
		// Collections.sort(delegations);
		return bikes;
	}

	public void saveBike(Bike newBike) {
		Datastore ds = getDatastore();
		ds.save(newBike);
	}

	public void updateBike(Bike existingBike) {
		Datastore ds = getDatastore();
		ds.save(existingBike);
	}

	public boolean updateBikeIsActiveField(ObjectId id, boolean isActive) {
		try {
			Datastore ds = getDatastore();
			Bike existingBike = findBikeDetailsByBikeId(id);
			existingBike.setIsactive(isActive);
			ds.save(existingBike);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	// public String getFontFilePath(String classpathRelativePath) {
	// Resource rsrc = new ClassPathResource(classpathRelativePath);
	// return rsrc.getFile().getAbsolutePath();
	// }

}
