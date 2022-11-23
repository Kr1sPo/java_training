package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.pft.addressbook.model.ContactData;

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
    driver.switchTo().alert().accept();
  }

  public void deleteSelectedContacts() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void selectContact() {
    click(By.name("selected[]"));
  }

  public void initContactModification() {
    click(By.xpath("//img[@alt='Edit']"));
  }

  public void submitContactModification() {
    click(By.name("update"));
  }
}
