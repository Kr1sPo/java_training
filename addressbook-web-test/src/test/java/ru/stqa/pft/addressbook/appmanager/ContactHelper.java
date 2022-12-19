package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactHelper extends HelperBase {
  public ContactHelper(WebDriver driver) {
    super (driver);
  }
  public void submitContactCreation() {
    click(By.xpath("//div[@id='content']/form/input[21]"));
  }

  public void fillContactForm(ContactData contactData) {
    type(By.name("firstname"),contactData.getFirstName());
    type(By.name("lastname"),contactData.getLastName());
    type(By.name("mobile"),contactData.getMobile());
    type(By.name("email"),contactData.getEmail());
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

  public void selectContactById(int id) {
    driver.findElement(By.cssSelector("input[value='"+id+"']")).click();
  }
  private void initContactModificationById(int id) {
    driver.findElement(By.xpath("//*[@id='" + id + "']/ancestor::tr[@name='entry']//img[@alt='Edit']")).click();
  }
  public void submitContactModification() {
    click(By.name("update"));
  }

  public void create(ContactData contact) {
    initContactCreation();
    fillContactForm(contact);
    submitContactCreation();
  }
  public void modifyContact(ContactData contact) {
    selectContactById(contact.getId());
    initContactModificationById(contact.getId());
    fillContactForm(contact);
    submitContactModification();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectedContacts();
    acceptContactDeletion();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public int getContactCount() {
    return driver.findElements(By.name("selected[]")).size();
  }

  public Contacts all() {
    Contacts contacts = new Contacts();
    List<WebElement> elements = driver.findElements(By.cssSelector("tr[name='entry']"));
    for (WebElement element:elements){
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      String lastname= element.findElement(By.cssSelector("td:nth-child(2)")).getText();
      String firstname= element.findElement(By.cssSelector("td:nth-child(3)")).getText();
      contacts.add(new ContactData().withId(id).withFirstname(firstname).withLastname(lastname));
    }
    return contacts;
  }
}
