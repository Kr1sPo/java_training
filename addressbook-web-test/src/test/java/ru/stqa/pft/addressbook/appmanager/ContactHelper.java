package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.List;

public class ContactHelper extends HelperBase {
  public ContactHelper(WebDriver driver) {
    super (driver);
  }
  public void submitContactCreation() {
    click(By.xpath("//div[@id='content']/form/input[21]"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"),contactData.getFirstName());
    type(By.name("lastname"),contactData.getLastName());
    type(By.name("address"),contactData.getAddress());
    type(By.name("email"),contactData.getEmail1());
    type(By.name("email2"),contactData.getEmail2());
    type(By.name("email3"),contactData.getEmail3());
    type(By.name("home"),contactData.getHomePhone());
    type(By.name("mobile"),contactData.getMobilePhone());
    type(By.name("work"),contactData.getWorkPhone());
    attach(By.name("photo"),contactData.getPhoto());

    if (creation){
      if (contactData.getGroups().size()>0){
        Assert.assertTrue(contactData.getGroups().size()==1);
        new Select(driver.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
      } else {
        Assert.assertFalse(isElementPresent(By.name("new_group")));
      }
    }

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
    driver.findElement(By.xpath(String.format("//*[@id='%s']/ancestor::tr[@name='entry']//img[@alt='Edit']",id))).click();
  }
  public void submitContactModification() {
    click(By.name("update"));
  }

  public void create(ContactData contact, boolean groupCreation) {
    initContactCreation();
    fillContactForm(contact,groupCreation);
    submitContactCreation();
    contactCache=null;
  }
  public void modifyContact(ContactData contact, boolean groupCreation) {
    selectContactById(contact.getId());
    initContactModificationById(contact.getId());
    fillContactForm(contact,groupCreation);
    submitContactModification();
    contactCache=null;
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectedContacts();
    acceptContactDeletion();
    contactCache=null;
  }

  public void addToGroup(ContactData contact) {
    selectContactById(contact.getId());
    //click(By.xpath("//input[@value='Delete']"));
    //click(By.name("to_group"));
    addGroupToSelectedContact(contact);
    //deleteSelectedContacts();
    //acceptContactDeletion();
    contactCache=null;
  }

  public void addGroupToSelectedContact(ContactData contact) {
    if (contact.getGroups().size()>0){
      //Assert.assertTrue(contact.getGroups().size()==1);
      new Select(driver.findElement(By.name("to_group"))).selectByVisibleText(contact.getGroups().iterator().next().getName());
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
    click(By.name("add"));
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public int count() {
    return driver.findElements(By.name("selected[]")).size();
  }
  private Contacts contactCache = null;
  public Contacts all() {
    if(contactCache!=null){
      return new Contacts(contactCache);
    }
    contactCache = new Contacts();
    List<WebElement> elements = driver.findElements(By.cssSelector("tr[name='entry']"));
    for (WebElement element:elements){
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      String lastname= element.findElement(By.cssSelector("td:nth-child(2)")).getText();
      String firstname= element.findElement(By.cssSelector("td:nth-child(3)")).getText();
      String address= element.findElement(By.cssSelector("td:nth-child(4)")).getText();
      String allEmails = element.findElement(By.cssSelector("td:nth-child(5)")).getText();
      String allPhones = element.findElement(By.cssSelector("td:nth-child(6)")).getText();
      //String[] phones = allPhones.split("\n");
      contactCache.add(new ContactData().withId(id).withFirstname(firstname).withLastname(lastname)
              .withAddress(address).withAllEmails(allEmails).withAllPhones(allPhones));
    }
    return new Contacts(contactCache);
  }

  public ContactData infoFromEditForm(ContactData contact) {
    initContactModificationById(contact.getId());
    String firstname = driver.findElement(By.name("firstname")).getAttribute("value");
    String lastname = driver.findElement(By.name("lastname")).getAttribute("value");
    String email1 = driver.findElement(By.name("email")).getAttribute("value");
    String email2 = driver.findElement(By.name("email2")).getAttribute("value");
    String email3 = driver.findElement(By.name("email3")).getAttribute("value");
    String home = driver.findElement(By.name("home")).getAttribute("value");
    String mobile = driver.findElement(By.name("mobile")).getAttribute("value");
    String work = driver.findElement(By.name("work")).getAttribute("value");
    String address = driver.findElement(By.name("address")).getAttribute("value");

    driver.navigate().back();
    return new ContactData().withId(contact.getId()).withFirstname(firstname).withLastname(lastname)
            .withAddress(address).withEmail1(email1).withEmail2(email2).withEmail3(email3)
            .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work);
  }
}
