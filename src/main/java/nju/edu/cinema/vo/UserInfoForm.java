package nju.edu.cinema.vo;

public class UserInfoForm {
    /**
     * 用户id
     */
    private int id;
    /**
     * 用户头像
     */
    private String profilePicture;
    /**
     * 用户名字
     */
    private String name;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProfilePicture() {
        return profilePicture;
    }
}
