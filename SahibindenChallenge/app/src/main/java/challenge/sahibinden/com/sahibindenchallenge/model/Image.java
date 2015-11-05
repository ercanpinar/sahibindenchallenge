package challenge.sahibinden.com.sahibindenchallenge.model;

/**
 * Created by ercanpinar on 04/11/15.
 */
public class Image {
    public Long id;
    public String image_url;
    public String name;
    public String description;
    public User user;
    public static class User
    {
        public String username;
        public Avatars avatars;
        public static class Avatars
        {
            public Small small;
            public Large large;
            public static class Small
            {
                public String https;
            }
            public static class Large
            {
                public String https;
            }
        }
    }
}
