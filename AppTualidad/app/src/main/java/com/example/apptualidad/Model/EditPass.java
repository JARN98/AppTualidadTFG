package com.example.apptualidad.Model;

public class EditPass {
    private String password;

    public EditPass(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EditPass editPass = (EditPass) o;

        return password != null ? password.equals(editPass.password) : editPass.password == null;
    }

    @Override
    public int hashCode() {
        return password != null ? password.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "EditPass{" +
                "password='" + password + '\'' +
                '}';
    }
}
