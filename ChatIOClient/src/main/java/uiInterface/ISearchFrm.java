package uiInterface;

public interface ISearchFrm {

    /**
     * Invoked when the client receives data from the server to display
     * Incoming data is always of List<T>
     * @param data the received data for this search frame
     */
    void loadData(Object data);

    /**
     * Invoked when the client receives data from the server to display
     * Incoming data is always received as single Type
     * @param data the received message for this search frame
     */
    void handleIncomingMessage(Object data);
}


