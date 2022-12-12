package ru.stqa.pft.addressbook.model;

import java.util.Objects;

public class ContactData {

  private int id;
  private final String firstname;
  private final String lastname;
  private final String mobile;
  private final String email;


  public ContactData(String firstname, String lastname, String mobile, String email) {
    this.id = Integer.MAX_VALUE;
    this.firstname = firstname;
    this.lastname = lastname;
    this.mobile = mobile;
    this.email = email;
  }

  public ContactData(int id, String firstname, String lastname, String mobile, String email) {
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
    this.mobile = mobile;
    this.email = email;
  }

  public int getId() {
    return id;
  }

  public String getFirstName() {
    return firstname;
  }
  public String getLastName() {
    return lastname;
  }

  public String getMobile() {
    return mobile;
  }

  public String getEmail() {
    return email;
  }
  @Override
  public String toString() {
    return "ContactData{" +
            "id='" + id + '\'' +
            ", firstname='" + firstname + '\'' +
            ", lastname='" + lastname + '\'' +
            '}';
  }
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ContactData that = (ContactData) o;

    if (!Objects.equals(firstname, that.firstname)) return false;
    return Objects.equals(lastname, that.lastname);
  }

  @Override
  public int hashCode() {
    int result = firstname != null ? firstname.hashCode() : 0;
    result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
    return result;
  }

}