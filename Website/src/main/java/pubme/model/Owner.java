package pubme.model;


import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class Owner {
    @Id
    private String id;
    private String fullName;
    private String userName;
    private String mail;
    private String password;
    private List<String> ownedPubsIds;

    public Owner() {
        ownedPubsIds = new ArrayList<>();
    }

    public Owner(String userName) {
        this.userName = userName;
        ownedPubsIds = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getOwnedPubsIds() {
        return ownedPubsIds;
    }

    public void setOwnedPubsIds(List<String> ownedPubsIds) {
        this.ownedPubsIds = ownedPubsIds;
    }

    public void addPubId(String pubid) {
        ownedPubsIds.add(pubid);
    }
}
