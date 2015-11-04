package challenge.sahibinden.com.sahibindenchallenge.model;

/**
 * Created by ercanpinar on 04/11/15.
 */
public class Image {
    private Long id;
    private String name;
    private String urlImage;
    private String nameImage;
    private String userAvatarUrlSmall;
    private String userAvatarUrlBig;
    private String description;
    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getNameImage() {
        return nameImage;
    }

    public void setNameImage(String nameImage) {
        this.nameImage = nameImage;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getUserAvatarUrlBig() {
        return userAvatarUrlBig;
    }

    public void setUserAvatarUrlBig(String userAvatarUrlBig) {
        this.userAvatarUrlBig = userAvatarUrlBig;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserAvatarUrlSmall() {
        return userAvatarUrlSmall;
    }

    public void setUserAvatarUrlSmall(String userAvatarUrlSmall) {
        this.userAvatarUrlSmall = userAvatarUrlSmall;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
