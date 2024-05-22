import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {
    private String name;
    private String about;
    private String avatar;
    @JsonProperty("_id")
    private String id;
    private String email;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Data {" + '\n' +
                "name='" + name + '\n' +
                "about='" + about + '\n' +
                "avatar='" + avatar + '\n' +
                "id='" + id + '\n' +
                "email='" + email + '\n' +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
