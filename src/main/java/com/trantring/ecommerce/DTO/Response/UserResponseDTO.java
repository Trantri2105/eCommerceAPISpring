package com.trantring.ecommerce.DTO.Response;


public class UserResponseDTO {
    private int id;
    private String email;
    private String firstName;
    private String lastName;

    public UserResponseDTO(int id, String email, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
