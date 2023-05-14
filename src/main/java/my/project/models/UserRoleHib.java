package my.project.models;


public enum UserRoleHib {

    MANAGER("Manager"),
    EMPLOYEE("Employee"),
    FREELANCER("Freelancer"),

    DEFAULT("Default");



    private String value;

    private UserRoleHib(String value) {
        this.value = value;


    }

}