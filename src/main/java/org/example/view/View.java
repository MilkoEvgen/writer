package org.example.view;

import java.util.Scanner;

public abstract class View {
    protected final Scanner scanner;
    protected String ACTION;

    public View(Scanner scanner) {
        this.scanner = scanner;
    }

    public void action() {
        System.out.println(ACTION);
        switch (scanner.nextInt()) {
            case 1:
                create();
                break;
            case 2:
                getById();
                break;
            case 3:
                getAll();
                break;
            case 4:
                update();
                break;
            case 5:
                delete();
                break;
        }
    }
    protected abstract void create();

    protected abstract void getById();

    protected abstract void getAll();

    protected abstract void update();

    protected abstract void delete();
}
