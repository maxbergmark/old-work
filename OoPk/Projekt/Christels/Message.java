

public class Message {
    String message;
    String crypto;
    String key;
    String color;
    String username;
    boolean isXML;
    public Message(String messageIn, String cryptoIn, String keyIn, String usernameIn, String colorIn) {
        message = messageIn;
        crypto = cryptoIn;
        key = keyIn;
        color = colorIn;
        username = usernameIn;
        isXML = false;
    }

    public Message(String xmlMessage) {
        message = xmlMessage;
        isXML = true;
    }

    public String getText() {
        return message;
    }

    public String getCrypto() {
        return crypto;
    }

    public String getKey() {
        return key;
    }

    public String getColor() {
        return color;
    }

    public String getUsername() {
        return username;
    }

    public boolean getIfXML() {
        return isXML;
    }
}