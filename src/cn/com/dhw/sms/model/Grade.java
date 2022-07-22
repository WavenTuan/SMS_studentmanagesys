package cn.com.dhw.sms.model;

public class Grade {
	private Long id;
    private String name;

    public Grade() {}
    public Grade(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public Grade(int id, String name) {
        this.id = (long) id;
        this.name = name;
    }

    public Long getId() {return id;}
    public void setId(Long id) { this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
}
