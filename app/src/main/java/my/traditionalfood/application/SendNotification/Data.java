package my.traditionalfood.application.SendNotification;

public class Data {

    private String Title;
    private String Message;
    private String TypePage;

    public Data(String title, String message, String typePage) {
        Title = title;
        Message = message;
        TypePage = typePage;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getTypePage() {
        return TypePage;
    }

    public void setTypePage(String typePage) {
        TypePage = typePage;
    }
}
