package com.example.apptualidad.Model;

public class UserDto {
    private String name;
    private String password;
    private String email;
    private String role;
    private String picture;

    public UserDto(String name, String password, String email, String role, String picture) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
        this.picture = picture;
    }

    public UserDto(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public UserDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        if (name != null ? !name.equals(userDto.name) : userDto.name != null) return false;
        if (password != null ? !password.equals(userDto.password) : userDto.password != null)
            return false;
        if (email != null ? !email.equals(userDto.email) : userDto.email != null) return false;
        if (role != null ? !role.equals(userDto.role) : userDto.role != null) return false;
        return picture != null ? picture.equals(userDto.picture) : userDto.picture == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (picture != null ? picture.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
