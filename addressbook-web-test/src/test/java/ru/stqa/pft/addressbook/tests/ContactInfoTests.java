package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactInfoTests extends TestBase{

  @BeforeMethod
  public void ensurePreconditions(){
    if(app.db().contacts().size()==0){
      app.contact().create(new ContactData()
              .withFirstname("Ivan").withLastname("Ivanov")
              .withAddress("Russian Federation, Republic of Tatarstan, Kazan, Pushkin st. 13/54 building 5, 177 apartment")
              .withMobilePhone("89274223344").withHomePhone("123456").withWorkPhone("777543")
              .withEmail1("ivanivanov1@gmail.com").withEmail2("ivanivanov2@gmail.com").withEmail3("ivanivanov3@gmail.com"), false);
    }
    app.goTo().homepage();
  }

  @Test
  public void testContactInfo(){
    app.goTo().homepage();
    ContactData contact = app.db().contacts().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

    assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
    assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
    assertThat(contact.getAddress(), equalTo(contactInfoFromEditForm.getAddress()));
  }
  private String mergePhones(ContactData contact) {
    return Stream.of(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone()).filter((s) -> !s.equals(""))
            .map(ContactInfoTests::cleaned)
            .collect(Collectors.joining("\n"));
  }
  private String mergeEmails(ContactData contact) {
    return Stream.of(contact.getEmail1(), contact.getEmail2(), contact.getEmail3()).filter((s) -> !s.equals(""))
            .collect(Collectors.joining("\n"));
  }
  public static String cleaned(String phone){
    return phone.replaceAll("\\s","").replaceAll("[-()]","");
  }
}
