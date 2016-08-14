package bikestore.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class BikeAlreadyExistsException extends WebApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BikeAlreadyExistsException(String msg) {
		super(Response.status(Response.Status.FORBIDDEN).entity(msg)
				.type("text/plain").build());
	}
}
