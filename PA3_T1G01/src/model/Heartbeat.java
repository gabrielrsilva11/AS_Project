package model;

/**
 * Heartbeat - Class used to store status of a server and timestamp of an
 * heartbeat time message
 *
 * @author Gabriel Silva
 * @author Manuel Marcos
 *
 */
public class Heartbeat {

    /**
     * Status of the server 1 for a functioning server -1 for when a server
     * shuts down
     */
    private int status;
    /**
     * Timestamp for when the message was sent
     */
    private long timestamp;

    /**
     * Heartbeat class constructor
     *
     * @param status Integer - status of the server
     * @param timestamp Timestamp - timestamp for when the message was sent
     */
    public Heartbeat(int status, long timestamp) {
        this.status = status;
        this.timestamp = timestamp;
    }

    /**
     * Getter method for the status variable
     *
     * @return Integer - status of the server when the heartbeat was sent
     */
    public int getStatus() {
        return status;
    }

    /**
     * Setter method for the status variable
     *
     * @param status Integer - status of the server when the heartbeat was sent
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Getter method for the Timestamp variable
     *
     * @return long - Timestamp for when the message was sent
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Setter method for the Timestamp variable
     *
     * @param timestamp long - Timestamp for when the message is to be sent
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Method to convert the contents of the class into a string to be printable
     *
     * @return String - printable format of the contents of the class
     */
    @Override
    public String toString() {
        return "Heartbeat{" + "status=" + status + ", timestamp=" + timestamp + '}';
    }
}
