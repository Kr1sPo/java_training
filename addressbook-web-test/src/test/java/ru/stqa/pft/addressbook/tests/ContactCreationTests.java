package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactCreationTests extends TestBase {
  @DataProvider
  public Iterator<Object[]> validContacts (){
    List<Object[]> list = new ArrayList<Object[]>();
    list.add(new Object[] {new ContactData().withFirstname("firstname1")
            .withLastname("lastname1").withMobilePhone("mobile1").withEmail1("email1")});
    list.add(new Object[] {new ContactData().withFirstname("firstname2")
            .withLastname("lastname2").withMobilePhone("mobile2").withEmail1("email2")});
    list.add(new Object[] {new ContactData().withFirstname("firstname3")
            .withLastname("lastname3").withMobilePhone("mobile3").withEmail1("email3")});
    return list.iterator();
  }
  @Test (dataProvider = "validContacts")
  public void testContactCreation(ContactData contact) throws Exception {
    Contacts before = app.contact().all();
    File photo = new File("src/test/resources/belka.png");
    app.contact().create(contact);
    app.goTo().homepage();
    assertThat(app.contact().count(),equalTo(before.size()+1));
    Contacts after= app.contact().all();
    assertEquals(after,before.withAdded(contact.withId(after.stream().mapToInt(c -> c.getId()).max().getAsInt())));
  }
}
