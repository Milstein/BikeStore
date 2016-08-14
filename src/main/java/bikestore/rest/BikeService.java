package bikestore.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.io.File;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Morphia;

import bikestore.DAL.MongoDBConnector;
import bikestore.DAO.BikeDAO;
import bikestore.model.Bike;
import bikestore.model.BikeInfo;
import bikestore.model.Image;
import bikestore.model.LatestBikeInfo;
import bikestore.utils.SerializationHelper;

import com.ebay.xcelite.Xcelite;
import com.ebay.xcelite.sheet.XceliteSheet;
import com.ebay.xcelite.writer.SheetWriter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;

@Path("/bikes")
@Api(value = "/bikes", description = "Manage Bikes")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
		MediaType.APPLICATION_FORM_URLENCODED, MediaType.TEXT_PLAIN,
		MediaType.TEXT_HTML })
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
		MediaType.TEXT_PLAIN, MediaType.TEXT_HTML })
public class BikeService {

	MongoClient mongoClient = null;
	Morphia morphia = null;
	String dbName = "db_bikestore";
	BikeDAO bikeDAO = null;

	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	private static final Logger log = Logger.getLogger(BikeService.class
			.getName());

	public BikeService() {
		mongoClient = MongoDBConnector.getMongo();
		morphia = new Morphia();
		morphia.map(Bike.class);
		bikeDAO = new BikeDAO(mongoClient, morphia, dbName);
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "Test Bike Service", notes = "This API tests whether the service is working or not")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success: { Hello World! }"),
			@ApiResponse(code = 400, message = "Failed: { \"error\":\"error description\", \"status\": \"FAIL\" }") })
	public Response testService() {
		try {
			log.info("BikeService::testService started");

			return Response.status(Response.Status.OK).entity("Hello World!")
					.build();
		} catch (Exception e) {
			log.error("Could not connect the Bike Service error e=", e);
		}

		return Response
				.status(Response.Status.BAD_REQUEST)
				.entity("{\"error\": \"Could Not Find Bike Service\", \"status\": \"FAIL\"}")
				.build();
	}

	@POST
	@Path("/GetBikesList")
	@ApiOperation(value = "Get all Bikes", notes = "This API gets all Bikes")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success: { Bike Info }"),
			@ApiResponse(code = 400, message = "Failed: { \"error\":\"error description\", \"status\": \"FAIL\" }") })
	public Response produceBikesJSON(
			@ApiParam(value = "Message", required = true, defaultValue = "", allowableValues = "", allowMultiple = false) String message) {
		try {
			log.info("BikeService::produceBikesJSON started");

			List<BikeInfo> bikes = new ArrayList<BikeInfo>();

			int offset = 0, limit = 0;
			String bikeTitle = new String();
			String addedOnFrom = new String();
			String addedOnTo = new String();
			Double priceFrom = 0.0;
			Double priceTo = 0.0;
			Boolean isActive = null;

			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(message);

			if (root != null && root.has("offset")) {
				offset = root.get("offset").intValue();
			}

			if (root != null && root.has("limit")) {
				limit = root.get("limit").intValue();
			}

			if (root != null && root.has("bikeBindObj")) {
				JsonNode bikeObj = root.get("bikeBindObj");
				if (bikeObj != null && bikeObj.has("bikeTitle")) {
					bikeTitle = bikeObj.get("bikeTitle").textValue();
				}

				if (bikeObj != null && bikeObj.has("addedOnFrom")) {
					addedOnFrom = bikeObj.get("addedOnFrom").textValue();
				}

				if (bikeObj != null && bikeObj.has("addedOnTo")) {
					addedOnTo = bikeObj.get("addedOnTo").textValue();
				}

				if (bikeObj != null && bikeObj.has("priceFrom")) {
					if (bikeObj.get("priceFrom").textValue() != null) {
						priceFrom = Double.valueOf(bikeObj.get("priceFrom")
								.textValue());
					}
				}

				if (bikeObj != null && bikeObj.has("priceTo")) {
					if (bikeObj.get("priceTo").textValue() != null) {
						priceTo = Double.valueOf(bikeObj.get("priceTo")
								.textValue());
					}
				}

				if (bikeObj != null && bikeObj.has("isActive")) {
					if (!bikeObj.get("isActive").isNull()) {
						isActive = bikeObj.get("isActive").booleanValue();
					} else {
						isActive = null;
					}
				}
			}

			bikes = bikeDAO.findBikesForGrid(offset, limit, bikeTitle,
					addedOnFrom, addedOnTo, priceFrom, priceTo, isActive);

			return Response
					.status(Response.Status.OK)
					.entity(mapper.writerWithDefaultPrettyPrinter()
							.writeValueAsString(bikes)).build();

		} catch (Exception e) {
			log.error("Could not find all bikes error e=", e);
		}

		return Response
				.status(Response.Status.BAD_REQUEST)
				.entity("{\"error\": \"Could Not Find All Bikes\", \"status\": \"FAIL\"}")
				.build();
	}

	@POST
	@Path("/BikesExportToExcel")
	@Produces(MediaType.TEXT_HTML)
	@ApiOperation(value = "Export all Bikes in a grid", notes = "This API exports all Bikes shown in a grid")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success: { Excel Filename/ No Record}"),
			@ApiResponse(code = 400, message = "Failed: { \"error\":\"error description\", \"status\": \"FAIL\" }") })
	public Response exportBikesForExcelJSON(
			@ApiParam(value = "Message", required = true, defaultValue = "", allowableValues = "", allowMultiple = false) String message) {
		try {
			log.info("BikeService::exportBikesForExcelJSON started");

			List<BikeInfo> bikes = new ArrayList<BikeInfo>();

			String bikeTitle = new String();
			String addedOnFrom = new String();
			String addedOnTo = new String();
			Double priceFrom = 0.0;
			Double priceTo = 0.0;
			Boolean isActive = null;

			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(message);

			if (root != null && root.has("bikeBindObj")) {
				JsonNode bikeObj = root.get("bikeBindObj");
				if (bikeObj != null && bikeObj.has("bikeTitle")) {
					bikeTitle = bikeObj.get("bikeTitle").textValue();
				}

				if (bikeObj != null && bikeObj.has("addedOnFrom")) {
					addedOnFrom = bikeObj.get("addedOnFrom").textValue();
				}

				if (bikeObj != null && bikeObj.has("addedOnTo")) {
					addedOnTo = bikeObj.get("addedOnTo").textValue();
				}

				if (bikeObj != null && bikeObj.has("priceFrom")) {
					if (bikeObj.get("priceFrom").textValue() != null) {
						priceFrom = Double.valueOf(bikeObj.get("priceFrom")
								.textValue());
					}
				}

				if (bikeObj != null && bikeObj.has("priceTo")) {
					if (bikeObj.get("priceTo").textValue() != null) {
						priceTo = Double.valueOf(bikeObj.get("priceTo")
								.textValue());
					}
				}

				if (bikeObj != null && bikeObj.has("isActive")) {
					if (!bikeObj.get("isActive").isNull()) {
						isActive = bikeObj.get("isActive").booleanValue();
					} else {
						isActive = null;
					}
				}
			}

			bikes = bikeDAO.findAllBikes(bikeTitle, addedOnFrom, addedOnTo,
					priceFrom, priceTo, isActive);

			String filename = new String();
			if (bikes.size() > 0) {
				Xcelite xcelite = new Xcelite();
				XceliteSheet sheet = xcelite.createSheet("Bikes");
				SheetWriter<BikeInfo> writer = sheet
						.getBeanWriter(BikeInfo.class);

				writer.write(bikes);

				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
				Date date = new Date();

				String fileName = String.format("%s.%s",
						RandomStringUtils.randomAlphanumeric(8) + "_"
								+ dateFormat.format(date), "xlsx");

				String downloadLocation = this.getClass()
						.getResource("/uploads").toURI().getPath();

				xcelite.write(new File(downloadLocation + fileName));

				filename = mapper.writerWithDefaultPrettyPrinter()
						.writeValueAsString(fileName);
			} else {
				filename = mapper.writerWithDefaultPrettyPrinter()
						.writeValueAsString("No Record");
			}
			return Response.status(Response.Status.OK).entity(filename).build();

		} catch (Exception e) {
			log.error("Could not export all Bikes error e=", e);
		}

		return Response
				.status(Response.Status.BAD_REQUEST)
				.entity("{\"error\": \"Could Not Export All Bikes\", \"status\": \"FAIL\"}")
				.build();
	}

	@POST
	@Path("/GetFirstBikeFromDB")
	@ApiOperation(value = "Get First Bike Details in Store", notes = "This API gets First Bike Details in Store")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success: { Bike }"),
			@ApiResponse(code = 400, message = "Failed: { \"error\":\"error description\", \"status\": \"FAIL\" }") })
	public Response produceFirstBikeFromDB(
			@ApiParam(value = "Message", required = true, defaultValue = "", allowableValues = "", allowMultiple = false) String message) {
		try {
			log.info("BikeService::produceFirstBikeFromDB started");

			ObjectMapper mapper = new ObjectMapper();
			Bike bike = new Bike();

			List<Bike> bikes = bikeDAO.findAllBikesInStore();
			if (bikes.size() > 0) {
				bike = getRandomBike(bikes);
			}

			return Response
					.status(Response.Status.OK)
					.entity(mapper.setDateFormat(formatter)
							.writerWithDefaultPrettyPrinter()
							.writeValueAsString(bike)).build();

		} catch (Exception e) {
			log.error("Could not find Bike Details by BikeId error e=", e);
		}

		return Response
				.status(Response.Status.BAD_REQUEST)
				.entity("{\"error\": \"Could Not Find Bike Details By BikeId\", \"status\": \"FAIL\"}")
				.build();
	}

	@POST
	@Path("/GetLatestBikesFromDB")
	@ApiOperation(value = "Get All Latest Bikes in Store", notes = "This API gets All Latest Bikes in Store")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success: { Bike }"),
			@ApiResponse(code = 400, message = "Failed: { \"error\":\"error description\", \"status\": \"FAIL\" }") })
	public Response produceLatestBikesFromDB(
			@ApiParam(value = "Message", required = true, defaultValue = "", allowableValues = "", allowMultiple = false) String message) {
		try {
			log.info("BikeService::produceLatestBikesFromDB started");

			ObjectMapper mapper = new ObjectMapper();
			String uploadPath = this.getClass().getResource("/uploads").toURI()
					.getPath();

			List<LatestBikeInfo> bikes = bikeDAO.findLatestBikesInStore(uploadPath);

			return Response
					.status(Response.Status.OK)
					.entity(mapper.setDateFormat(formatter)
							.writerWithDefaultPrettyPrinter()
							.writeValueAsString(bikes)).build();

		} catch (Exception e) {
			log.error("Could not find Latest Bikes error e=", e);
		}

		return Response
				.status(Response.Status.BAD_REQUEST)
				.entity("{\"error\": \"Could Not Find Latest Bikes\", \"status\": \"FAIL\"}")
				.build();
	}

	@POST
	@Path("/GetBikeDetailsByBikeId")
	@ApiOperation(value = "Get Bike Details by BikeId", notes = "This API gets Bike Details by BikeId")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success: { Bike }"),
			@ApiResponse(code = 400, message = "Failed: { \"error\":\"error description\", \"status\": \"FAIL\" }") })
	public Response produceBikeDetailsByBikeId(
			@ApiParam(value = "Message", required = true, defaultValue = "", allowableValues = "", allowMultiple = false) String message) {
		try {
			log.info("BikeService::produceBikeDetailsByBikeId started");

			Bike bike = new Bike();
			String bikeId = new String();

			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(message);

			if (root != null && root.has("bikeId")) {
				bikeId = root.get("bikeId").textValue();
			}

			ObjectId id = new ObjectId(bikeId);
			bike = bikeDAO.findBikeDetailsByBikeId(id);

			return Response
					.status(Response.Status.OK)
					.entity(mapper.setDateFormat(formatter)
							.writerWithDefaultPrettyPrinter()
							.writeValueAsString(bike)).build();

		} catch (Exception e) {
			log.error("Could not find Bike Details by BikeId error e=", e);
		}

		return Response
				.status(Response.Status.BAD_REQUEST)
				.entity("{\"error\": \"Could Not Find Bike Details By BikeId\", \"status\": \"FAIL\"}")
				.build();
	}

	@POST
	@Path("/GetBikeImageDetailsByBikeId")
	@ApiOperation(value = "Get Bike Image Details by BikeId", notes = "This API gets Bike Image Details by BikeId")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success: { Bike }"),
			@ApiResponse(code = 400, message = "Failed: { \"error\":\"error description\", \"status\": \"FAIL\" }") })
	public Response produceBikeImageDetailsByBikeId(
			@ApiParam(value = "Message", required = true, defaultValue = "", allowableValues = "", allowMultiple = false) String message) {
		try {
			log.info("BikeService::produceBikeImageDetailsByBikeId started");

			Bike bike = new Bike();
			String bikeId = new String();

			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(message);

			if (root != null && root.has("bikeId")) {
				bikeId = root.get("bikeId").textValue();
			}

			ObjectId id = new ObjectId(bikeId);
			bike = bikeDAO.findBikeImageDetailsByBikeId(id);

			return Response
					.status(Response.Status.OK)
					.entity(mapper.setDateFormat(formatter)
							.writerWithDefaultPrettyPrinter()
							.writeValueAsString(bike)).build();

		} catch (Exception e) {
			log.error("Could not find Bike Details by BikeId error e=", e);
		}

		return Response
				.status(Response.Status.BAD_REQUEST)
				.entity("{\"error\": \"Could Not Find Bike Image Details By BikeId\", \"status\": \"FAIL\"}")
				.build();
	}

	@POST
	@Path("/SaveUpdateBike")
	@ApiOperation(value = "Save a New Bike or Update an existing Bike", notes = "This API saves a New Bike or updates an existing Bike")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success: { True }"),
			@ApiResponse(code = 403, message = "Failed: { \"error\":\"error description\", \"status\": \"FAIL\" }") })
	public Response saveUpdateBike(
			@ApiParam(value = "Message", required = true, defaultValue = "", allowableValues = "", allowMultiple = false) String message) {
		try {
			log.info("BikeService::saveUpdateBike started");

			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(message);

			String bikeId = new String();
			Bike existingBike = new Bike();
			Bike oldBike = new Bike();

			if (root != null && root.has("bikeInfo")) {
				JsonNode bikeInfo = root.get("bikeInfo");

				if (bikeInfo != null && bikeInfo.has("BikeId")) {
					bikeId = bikeInfo.get("BikeId").textValue();
					if (!bikeId.equals("0")) {
						ObjectId id = new ObjectId(bikeId);
						existingBike = bikeDAO.findBikeDetailsByBikeId(id);
						// using our serializable method for cloning
						oldBike = SerializationHelper
								.cloneThroughSerialize(existingBike);
					}
				}

				// Image Info
				if (bikeInfo != null && bikeInfo.has("ImageInfo")) {
					List<Image> imageInfo = Arrays.asList(mapper
							.readValue(bikeInfo.get("ImageInfo").toString(),
									Image[].class));
					if (imageInfo.size() != 0) {

						String UPLOAD_PATH = new String();
						try {
							UPLOAD_PATH = this.getClass()
									.getResource("/uploads").toURI().getPath();
						} catch (URISyntaxException e) {
							e.printStackTrace();
						}

						List<String> existingFiles = new ArrayList<String>();
						if (!bikeId.equals("0")) {
							for (Image image : oldBike.getImages()) {
								boolean alreadyExist = false;
								for (Image imageObj : imageInfo) {
									if (image.getFilename().equalsIgnoreCase(
											imageObj.getFilename())) {
										alreadyExist = true;
										existingFiles.add(imageObj
												.getFilename());
										break;
									}
								}
								if (!alreadyExist) {
									existingBike.getImages().remove(image);
								}
							}

							for (Image uploadFile : imageInfo) {
								String fileName = uploadFile.getFilename();
								if (!existingFiles.contains(fileName)) {
									File file = new File(UPLOAD_PATH + fileName);

									String extension = "";
									int i = fileName.lastIndexOf('.');
									if (i > 0) {
										extension = fileName.substring(i + 1);

										if (verifyValidImageExtension(extension)) {
											uploadFile.setExtension(extension);
										} else {
											return Response
													.status(403)
													.entity(extension
															+ " is not allowed. Allowed extensions: jpg,png,gif,jpeg,bmp,png")
													.build();
										}
									}

									long fileSize = file.length();
									if (verifyValidImageSize(fileSize)) {
										uploadFile.setFilesize(fileSize);
									} else {
										return Response
												.status(403)
												.entity("The uploaded file is larger than 5MB")
												.build();
									}
									uploadFile.setFilepath("/uploads/"
											+ fileName);

									existingBike.getImages().add(uploadFile);
								}
							}
						} else {
							for (Image uploadFile : imageInfo) {
								String fileName = uploadFile.getFilename();
								File file = new File(UPLOAD_PATH + fileName);

								String extension = "";
								int i = fileName.lastIndexOf('.');
								if (i > 0) {
									extension = fileName.substring(i + 1);
									if (verifyValidImageExtension(extension)) {
										uploadFile.setExtension(extension);
									} else {
										return Response
												.status(403)
												.entity(extension
														+ " is not allowed. Allowed extensions: jpg,png,gif,jpeg,bmp,png")
												.build();
									}
								}

								long fileSize = file.length();
								if (verifyValidImageSize(fileSize)) {
									uploadFile.setFilesize(fileSize);
								} else {
									return Response
											.status(403)
											.entity("The uploaded file is larger than 5MB")
											.build();
								}
								uploadFile.setFilesize(fileSize);
								uploadFile.setFilepath("/uploads/" + fileName);

								existingBike.getImages().add(uploadFile);
							}
						}
					} else {
						existingBike.setImages(imageInfo);
					}
				}

				if (bikeInfo != null && bikeInfo.has("Type")) {
					String bikeType = bikeInfo.get("Type").textValue().trim()
							.replaceAll("\\<[^>]*>", "");
					if (validateNotEmptyValue(bikeType)) {
						if (!bikeId.equals("0")) {
							if (!existingBike.getType().equals(bikeType)) {
								existingBike.setType(bikeType);
							}
						} else {
							existingBike.setType(bikeType);
						}
					} else {
						throw new Exception("The Type can not be Empty");
					}
				}

				if (bikeInfo != null && bikeInfo.has("Title")) {
					String bikeTitle = bikeInfo.get("Title").textValue().trim()
							.replaceAll("\\<[^>]*>", "");
					if (validateNotEmptyValue(bikeTitle)) {
						if (!bikeId.equals("0")) {
							if (!existingBike.getTitle().equals(bikeTitle)) {
								existingBike.setTitle(bikeTitle);
							}
						} else {
							existingBike.setTitle(bikeTitle);
						}
					} else {
						throw new Exception("The Title can not be Empty");
					}
				}

				if (bikeInfo != null && bikeInfo.has("Description")) {
					String bikeDescription = bikeInfo.get("Description")
							.textValue().trim().replaceAll("\\<[^>]*>", "");
					if (validateNotEmptyValue(bikeDescription)) {
						if (!bikeId.equals("0")) {
							if (!existingBike.getDescription().equals(
									bikeDescription)) {
								existingBike.setDescription(bikeDescription);
							}
						} else {
							existingBike.setDescription(bikeDescription);
						}
					} else {
						throw new Exception("The Description can not be Empty");
					}
				}

				if (bikeInfo != null && bikeInfo.has("Price")) {
					String bikePrice = bikeInfo.get("Price").textValue().trim()
							.replaceAll("\\<[^>]*>", "");
					if (validateNotEmptyValue(bikePrice)) {
						if (!bikeId.equals("0")) {
							if (existingBike.getPrice() != Double
									.parseDouble(bikePrice)) {
								existingBike.setPrice(Double
										.parseDouble(bikePrice));
							}
						} else {
							existingBike
									.setPrice(Double.parseDouble(bikePrice));
						}
					} else {
						throw new Exception("The Price can not be Empty");
					}
				}

				if (bikeInfo != null && bikeInfo.has("Quantity")) {
					String bikeQuantity = bikeInfo.get("Quantity").textValue()
							.trim().replaceAll("\\<[^>]*>", "");
					if (validateNotEmptyValue(bikeQuantity)) {
						if (!bikeId.equals("0")) {
							if (existingBike.getQuantity() != Integer
									.parseInt(bikeQuantity)) {
								existingBike.setQuantity(Integer
										.parseInt(bikeQuantity));
							}
						} else {
							existingBike.setQuantity(Integer
									.parseInt(bikeQuantity));
						}
					} else {
						throw new Exception("The Quantity can not be Empty");
					}
				}

				if (bikeInfo != null && bikeInfo.has("Rating")) {
					String bikeRating = bikeInfo.get("Rating").textValue()
							.trim().replaceAll("\\<[^>]*>", "");
					if (!bikeId.equals("0")) {
						if (existingBike.getRating() != Integer
								.parseInt(bikeRating)) {
							existingBike
									.setRating(Integer.parseInt(bikeRating));
						}
					} else {
						existingBike.setRating(Integer.parseInt(bikeRating));
					}
				}

				if (bikeInfo != null && bikeInfo.has("IsActive")) {
					boolean bikeIsActive = bikeInfo.get("IsActive")
							.booleanValue();
					if (!bikeId.equals("0")) {
						if (existingBike.isIsactive() != bikeIsActive) {
							existingBike.setIsactive(bikeIsActive);
						}
					} else {
						existingBike.setIsactive(bikeIsActive);
					}
				}

				if (!bikeId.equals("0")) {
					if (!existingBike.equals(oldBike)) {
						bikeDAO.updateBike(existingBike);
					}
				} else {
					bikeDAO.saveBike(existingBike);
				}

				return Response
						.status(200)
						.type(MediaType.APPLICATION_JSON)
						.entity(mapper.writerWithDefaultPrettyPrinter()
								.writeValueAsString(true)).build();
			} else {
				return Response.status(Response.Status.BAD_REQUEST)
						.type(MediaType.APPLICATION_JSON)
						.entity("No Bike Info is send!").build();
			}
		} catch (Exception e) {
			log.error(
					"Could not save a New Bike or update an existing Bike error e=",
					e);
		}

		return Response
				.status(403)
				.entity("{\"error\": \"Could Not Save A New Bike OR Update An Existing Bike\", \"status\": \"FAIL\"}")
				.build();
	}

	@POST
	@Path("/UpdateBikeIsActiveByBikeId")
	@ApiOperation(value = "Update Is Active Field By BikeId", notes = "This API updates Is Active Field of A Bike")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success: { True }"),
			@ApiResponse(code = 400, message = "Failed: { \"error\":\"error description\", \"status\": \"FAIL\" }") })
	public Response updateBikeIsActiveByBikeId(
			@ApiParam(value = "Message", required = true, defaultValue = "", allowableValues = "", allowMultiple = false) String message) {
		try {
			log.info("BikeService::updateBikeIsActiveByBikeId started");

			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(message);

			String bikeId = new String();

			if (root != null && root.has("bikeId")) {
				bikeId = root.get("bikeId").textValue();
			}

			boolean isUpdated = false;
			if (root != null && root.has("isActive")) {
				boolean isActive = root.get("isActive").booleanValue();

				ObjectId id = new ObjectId(bikeId);
				isUpdated = bikeDAO.updateBikeIsActiveField(id, isActive);
			}

			if (isUpdated) {
				return Response
						.status(Response.Status.OK)
						.entity(mapper.writerWithDefaultPrettyPrinter()
								.writeValueAsString(true)).build();
			}
		} catch (Exception e) {
			log.error("Could not update Bike error e=", e);
		}

		return Response
				.status(Response.Status.BAD_REQUEST)
				.entity("{\"error\": \"Could Not update Bike\", \"status\": \"FAIL\"}")
				.build();
	}

	@POST
	@Path("/DeactivateMultipleBikesByBikeId")
	@ApiOperation(value = "Deactivate Multiple Bikes by BikeId", notes = "This API deactivates Multiple Bikes by BikeId")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success: { True }"),
			@ApiResponse(code = 400, message = "Failed: { \"error\":\"error description\", \"status\": \"FAIL\" }") })
	public Response deactivateMultipleBikesByBikeId(
			@ApiParam(value = "Message", required = true, defaultValue = "", allowableValues = "", allowMultiple = false) String message) {
		try {
			log.info("BikeService::deactivateMultipleBikesByBikeId started");

			String bikeIds = new String();
			String bikes[] = new String[0];

			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(message);

			if (root != null && root.has("bikeIds")) {
				bikeIds = root.get("bikeIds").textValue();
				bikes = bikeIds.split(",");
			}

			for (String bikeId : bikes) {
				ObjectId id = new ObjectId(bikeId);
				boolean isDeleted = bikeDAO.updateBikeIsActiveField(id, false);
				if (!isDeleted) {
					return Response
							.status(Response.Status.BAD_REQUEST)
							.entity("{\"error\": \"Could Not Delete Multiple Proposals\", \"status\": \"FAIL\"}")
							.build();
				}
			}

			return Response
					.status(Response.Status.OK)
					.entity(mapper.writerWithDefaultPrettyPrinter()
							.writeValueAsString(true)).build();

		} catch (Exception e) {
			log.error("Could not delete Multiple Proposals error e=", e);
		}

		return Response
				.status(Response.Status.BAD_REQUEST)
				.entity("{\"error\": \"Could Not Delete Multiple Proposals\", \"status\": \"FAIL\"}")
				.build();

	}

	// Only 5MB is allowed from client to upload
	private boolean verifyValidImageSize(long fileSize) {
		if (fileSize <= 5 * 1024 * 1024) {
			return true;
		} else {
			return false;
		}
	}

	// Allowed extensions: jpg,png,gif,jpeg,bmp,png
	private boolean verifyValidImageExtension(String extension) {
		List<String> list = Arrays.asList("jpg", "png", "gif", "jpeg", "bmp",
				"png");
		if (list.contains(extension)) {
			return true;
		} else {
			return false;
		}
	}

	// Server Side Empty Field Validation Check
	private boolean validateNotEmptyValue(String value) {
		if (!value.equalsIgnoreCase("")) {
			return true;
		} else {
			return false;
		}
	}

	private Bike getRandomBike(List<Bike> bikes) {
		int index = ThreadLocalRandom.current().nextInt(bikes.size());
		return bikes.get(index);
	}

}
