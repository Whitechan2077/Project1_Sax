package com.sax.utils;

import com.sax.dtos.AccountDTO;

import java.io.*;

public class AccountUtils {
    public static void remember(AccountDTO accountDTO){
        String filePath = "account.dat";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(accountDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static AccountDTO retrieve() throws FileNotFoundException {
        String filePath = "account.dat";
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            AccountDTO accountDTO = (AccountDTO) ois.readObject();
            return accountDTO;
        } catch (IOException | ClassNotFoundException e) {
            throw new FileNotFoundException("Không có file");
        }
    }
    public static void deleteFile() {
        String filePath = "account.dat";
        File file = new File(filePath);
        file.delete();
    }
}
