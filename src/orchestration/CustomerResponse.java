package orchestration;

/**
 * Indication of whether a Customer Request was accepted by the network.
 *
 * @author      Dong Young Kim, Alex Brashear, Alex Lyons, Razzi Abuissa
 * @version     1.0
 * @since       2015-04-21
 */

public class CustomerResponse {

	/** Indicate if the request was accepted. */
    public boolean accepted;

    /** Create a Customer Response object with the specified parameters. */
    public CustomerResponse(boolean accepted) {
        this.accepted = accepted;
    }
}
