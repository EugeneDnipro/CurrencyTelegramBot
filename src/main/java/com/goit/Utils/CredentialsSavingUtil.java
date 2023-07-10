package com.goit.Utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class CredentialsSavingUtil {

    public static void credentialsSetToFile(Map<String, UserPreferences> preferences) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/main/resources/Users.dat"))) {
            oos.writeObject(preferences);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static Map<String, UserPreferences> credentialsGetFromFile() {
        Map<String, UserPreferences> preferences = new HashMap<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/main/resources/Users.dat"))) {
            preferences = (Map<String, UserPreferences>) ois.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return preferences;
    }

}
