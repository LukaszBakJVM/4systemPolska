package org.freeddyns.systempolska.Model.Dto;

public class ReadUserDto {
    private String surname;
    private String login;

    public ReadUserDto() {
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "ReadUserDto{" +
                "surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                '}';
    }
}
