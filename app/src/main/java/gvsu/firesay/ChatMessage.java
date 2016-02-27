package gvsu.firesay;

/**
 * Created by droidowl on 2/27/16.
 */
public class ChatMessage {
    String message;
    String name;

    public ChatMessage(String message, String name) {
        this.message = message;
        this.name = name;
    }

    public ChatMessage() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
