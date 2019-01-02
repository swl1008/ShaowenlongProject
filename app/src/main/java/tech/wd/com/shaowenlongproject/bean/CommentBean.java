package tech.wd.com.shaowenlongproject.bean;

public class CommentBean {
    private String cMessage;

    public CommentBean(String cMessage) {
        this.cMessage = cMessage;
    }

    public String getcMessage() {
        return cMessage;
    }

    public void setcMessage(String cMessage) {
        this.cMessage = cMessage;
    }
}
