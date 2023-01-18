package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {
  @Parameter(names = "-c", description = "Contact count")
  public int count;
  @Parameter (names = "-f", description = "Target file")
  public String file;
  public static void main (String []args) throws IOException {
    ContactDataGenerator generator = new ContactDataGenerator();
    JCommander jCommander = new JCommander(generator);
    try {
      jCommander.parse(args);
    } catch (ParameterException ex) {
      jCommander.usage();
      return;
    }
    generator.run();
  }

  private void run() throws IOException {
    List<ContactData> contacts = generateContacts(count);
    save(contacts, new File(file));
  }

  private void save(List<ContactData> contacts, File file) throws IOException {
    Writer writer = new FileWriter(file);
    for (ContactData contact: contacts) {
      writer.write(String.format("%s;%s;%s;%s\n",contact.getFirstName(), contact.getLastName()
              , contact.getMobilePhone(), contact.getEmail1()));
    }
    writer.close();
  }

  private  List<ContactData> generateContacts(int count) {
    List<ContactData> contacts = new ArrayList<ContactData>();
    for (int i=0; i<count; i++){
      contacts.add(new ContactData().withFirstname(String.format("firstname %s", i))
              .withLastname(String.format("lastname %s", i)).withMobilePhone(String.format("mobilePhone %s", i))
              .withEmail1(String.format("email %s", i)));
    }
    return contacts;
  }
}
