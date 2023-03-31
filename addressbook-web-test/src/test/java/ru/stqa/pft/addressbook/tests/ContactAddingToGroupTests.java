package ru.stqa.pft.addressbook.tests;

import com.thoughtworks.xstream.XStream;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactAddingToGroupTests extends TestBase {
  @DataProvider
  public Iterator<Object[]> validContacts () throws IOException {
    try (BufferedReader reader = new BufferedReader
            (new FileReader(new File("src/test/resources/contacts.xml")))) {
      String xml = "";
      String line = reader.readLine();
      while (line != null) {
        xml += line;
        line = reader.readLine();
      }
      XStream xstream = new XStream();
      xstream.processAnnotations(ContactData.class);
      xstream.allowTypes(new Class[]{ContactData.class});
      List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml);
      return contacts.stream().map((c) -> new Object[]{c}).toList().iterator();
    }
  }

  @BeforeMethod
  public void ensurePreconditions(){
    if(app.db().groups().size()==0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName ("test"));
    }
    if(app.db().contacts().size()==0){
      app.contact().create(new ContactData()
              .withFirstname("Ivan").withLastname("Ivanov").withMobilePhone("89274223344").withEmail1("ivanivanov@gmail.com"), false);
    }
    app.goTo().homepage();
  }

  /*
  @Test (dataProvider = "validContacts")
  public void testAddingContactToGroup(ContactData contact) throws Exception{
    Groups groups = app.db().groups();
    GroupData selectedGroup = groups.iterator().next();
    contact = contact.inGroup(selectedGroup);
    Contacts before = app.db().contacts();
    app.contact().create(contact,true);
    app.goTo().homepage();
    assertThat(app.contact().count(),equalTo(before.size()+1));
    Contacts after= app.db().contacts();
    ContactData newContact = contact.withId(after.stream().mapToInt(c -> c.getId()).max().getAsInt());
    Contacts beforeLater = before.withAdded(contact.withId(after.stream().mapToInt(c -> c.getId()).max().getAsInt()));
    assertEquals(after,before.withAdded(contact.withId(after.stream().mapToInt(c -> c.getId()).max().getAsInt())));
    verifyContactListInUi();
  }
  */
  @Test
  public void testAddingContactToGroup() throws Exception{
    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next();

    GroupData selectedGroup = null;
    List<GroupData> unusedGroups = new ArrayList<>();

    //Contacts selectedContactDB = app.db().contactsInGroups(modifiedContact.getId());
    int groupsNumber = modifiedContact.getGroups().size();
    int totalGroupsNumber = app.db().groups().size();
    if (modifiedContact.getGroups().size()==app.db().groups().size())
    {
      app.goTo().groupPage();
      GroupData group = new GroupData().withName ("test");
      app.group().create(group);
      selectedGroup=group;
    }

    else if (modifiedContact.getGroups().size()>0
            && modifiedContact.getGroups().size()<app.db().groups().size()) {
        for (GroupData group : app.db().groups()) {
          boolean foundFlag = false;
          for (GroupData groupInContact : modifiedContact.getGroups()) {
            if (group.getId() == groupInContact.getId())
            {
              foundFlag = true;
              break;
            }
          }
          if (!foundFlag) unusedGroups.add(group);
        }
      selectedGroup = unusedGroups.iterator().next();
      }

    //Groups groups = app.db().groups();
   //selectedGroup = groups.iterator().next();

    ContactData contact  = modifiedContact.inGroup(selectedGroup);

    app.contact().addToGroup(contact);
    app.goTo().homepage();
    assertThat(app.contact().count(),equalTo(before.size()));
    Contacts after= app.db().contacts();
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    verifyContactListInUi();
  }
}
