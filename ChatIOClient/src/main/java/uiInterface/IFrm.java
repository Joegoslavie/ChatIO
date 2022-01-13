package uiInterface;

import enums.Display;

public interface IFrm {

    /**
     * Creates a new thread and shows the associated GUI
     * @param display the to open GUI
     */
    void takeStage(Display display);

    /**
     * Display an alert at the current GUI
     * @param message the message
     */
    void displayMessage(String message);
}
