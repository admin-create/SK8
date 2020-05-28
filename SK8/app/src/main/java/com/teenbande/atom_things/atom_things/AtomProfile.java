package com.teenbande.atom_things.atom_things;

import java.nio.charset.Charset;
import java.util.UUID;

public class AtomProfile {

    // UUID for the UART BTLE client characteristic which is necessary for notifications.
    public static UUID DESCRIPTOR_CONFIG = UUID.fromString("E08B33D5-8233-41E4-AE10-68542662DA9E");
    public static UUID DESCRIPTOR_USER_DESC = UUID.fromString("23B28DD6-000C-4EEE-87F2-0BC6B8E0A1A4");

    public static UUID SERVICE_UUID = UUID.fromString("CDD40377-837A-4062-A8CA-9EC248D2E33D");
    public static UUID CHARACTERISTIC_SPEED_UUID = UUID.fromString("139D6AEE-0B19-4611-B627-1CCA8BD23EDC");
    public static UUID CHARACTERISTIC_INTERACTOR_UUID = UUID.fromString("EB37EC9C-A71B-49A5-83DA-1FEEDEF41287");

    public static byte[] getUserDescription(UUID characteristicUUID) {
        String desc;

        if (CHARACTERISTIC_SPEED_UUID.equals(characteristicUUID)) {
            desc = "Indicates the number of time you have been awesome so far";
        } else if (CHARACTERISTIC_INTERACTOR_UUID.equals(characteristicUUID)) {
            desc = "Write any value here to move the cat’s paw and increment the awesomeness counter";
        } else {
            desc = "";
        }

        return desc.getBytes(Charset.forName("UTF-8"));
    }
}
