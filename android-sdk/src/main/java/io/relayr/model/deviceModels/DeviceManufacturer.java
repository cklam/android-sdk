package io.relayr.model.deviceModels;

import java.io.Serializable;

public class DeviceManufacturer implements Serializable {

    private final String name;
    private final String website;
    private final ContactInfo contactInfo;

    public DeviceManufacturer(String name, String website, ContactInfo contactInfo) {
        this.name = name;
        this.website = website;
        this.contactInfo = contactInfo;
    }

    public String getName() {
        return name;
    }

    public String getWebsite() {
        return website;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public class ContactInfo {
        private final String email;
        private final String phone;

        private ContactInfo(String email, String phone) {
            this.email = email;
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }
    }
}
