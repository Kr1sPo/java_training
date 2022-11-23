package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.pft.addressbook.model.ContactData;


import static org.testng.Assert.assertTrue;

public class ContactHelper extends HelperBase {
  public ContactHelper(WebDriver driver) {
    super (driver);
  }
  public void submitContactCreation() {
    click(By.xpath("//div[@id='content']/form/input[21]"));
  }

  public void fillContactForm(ContactData contactData) {
    type(By.name("firstname"),contactData.firstname());
    type(By.name("lastname"),contactData.lastname());
    type(By.name("mobile"),contactData.mobile());
    type(By.name("email"),contactData.email());
  }

  public void initContactCreation() {
    click(By.linkText("add new"));
  }

  public void acceptContactDeletion() {
    assertTrue(closeAlertAndGetItsText().matches("^Delete 1 addresses[\\s\\S]$"));
  }

  public void deleteSelectedContacts() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void selectContact() {
    click(By.name("selected[]"));
  }

}
