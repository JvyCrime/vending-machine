package com.digitalmediavending.hardware.nayax_sdk_utils.menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/* JADX INFO: loaded from: classes.dex */
public class GenericMenu {
    private ArrayList<MenuItem> menuItems = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    private void addDefaultMenuItems() {
    }

    private void addMenuItem(String key, String name) {
        this.menuItems.add(new MenuItem(key, name));
    }

    public void addMenuItem(String key, String name, Runnable runnable) {
        this.menuItems.add(new MenuItem(key, name, runnable));
    }

    private void printMenuItems() {
        for (MenuItem menuItem : this.menuItems) {
            System.out.println("[" + menuItem.getKey() + "]: " + menuItem.getName());
        }
    }

    private void runCommand(String key) throws Exception {
        ArrayList arrayList = new ArrayList();
        for (MenuItem menuItem : this.menuItems) {
            if (menuItem.getKey().toLowerCase().equals(key)) {
                arrayList.add(menuItem);
            }
        }
        if (arrayList.size() > 0) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ((MenuItem) it.next()).getRunnable().run();
            }
        }
    }

    private String scanLine() {
        System.out.print("> ");
        return this.scanner.nextLine();
    }

    public void initMenu() {
        addDefaultMenuItems();
        Boolean bool = false;
        while (!bool.booleanValue()) {
            System.out.println("--- Options ---");
            printMenuItems();
            String lowerCase = scanLine().toLowerCase();
            byte b = -1;
            try {
                if (lowerCase.hashCode() == 113 && lowerCase.equals("q")) {
                    b = 0;
                }
                if (b == 0) {
                    System.out.println("Quitting application...");
                    bool = true;
                } else {
                    runCommand(lowerCase);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println();
        }
    }
}
