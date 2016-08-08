package pubme.requests;

public class RegisterUserRequest {

    private String id;
    private String name;

    public RegisterUserRequest(String id) {
        this.id = id;
    }

    public RegisterUserRequest(String id, String name) {
        this.id = id;
        this.name = name;
    }

    protected RegisterUserRequest() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
