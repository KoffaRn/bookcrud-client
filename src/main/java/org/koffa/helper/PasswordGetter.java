package org.koffa.helper;

import java.util.Scanner;

public class PasswordGetter {
    public static String getPassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter super secret password: ");
        return scanner.nextLine();
    }
}
