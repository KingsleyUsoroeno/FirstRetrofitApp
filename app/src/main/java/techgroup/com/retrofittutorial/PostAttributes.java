package techgroup.com.retrofittutorial;

import com.google.gson.annotations.SerializedName;

public class PostAttributes {

    private int UserId;
    private int Id;
    private String Title;
    @SerializedName("body")
    private String Body;

    public PostAttributes(int userId, int id, String title, String body){
        this.UserId = userId;
        this.Id = id;
        this.Title = title;
        this.Body = body;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

}
