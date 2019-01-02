package tech.wd.com.shaowenlongproject.bean;

public class MessageBean {
    private String title;
    private String message;

    public MessageBean(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
}
