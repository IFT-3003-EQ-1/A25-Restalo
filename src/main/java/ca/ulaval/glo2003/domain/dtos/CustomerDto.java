package ca.ulaval.glo2003.domain.dtos;

public class CustomerDto {
    public  String name;
    public  String email;
    public  String phoneNumber;

    public CustomerDto() {
    }

    public CustomerDto(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
