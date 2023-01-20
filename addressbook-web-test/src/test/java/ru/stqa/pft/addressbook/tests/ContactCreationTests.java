package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactCreationTests extends TestBase {
  @DataProvider
  public Iterator<Object[]> validContacts () throws IOException {
    List<Object[]> list = new ArrayList<Object[]>();
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.csv")));
    String line = reader.readLine();
    while (line!=null){
      String[] split = line.split(";");
      list.add(new Object[]{new ContactData().withFirstname(split[0]).withLastname(split[1])
              .withMobilePhone(split[2]).withEmail1(split[3])});
      line = reader.readLine();
    }
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
