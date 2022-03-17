package contactsmgr;

import java.util.Locale;

public class Contact implements Comparable {
    private String contactName;
    private String contactPhone;

    public Contact(String contactName, String contactPhone) {
        this.contactName = contactName;
        this.contactPhone = contactPhone;
    }

    @Override
    public String toString() {
        return contactName + ", " + contactPhone;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    @Override
    public int compareTo(Object o) {
        Contact c2 = (Contact) o;
        return this.contactName.toLowerCase().compareTo(c2.getContactName().toLowerCase());
    }
}
