package ru.stqa.pft.addressbook.tests;

import com.thoughtworks.xstream.XStream;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.*;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactCreationTests extends TestBase {
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
