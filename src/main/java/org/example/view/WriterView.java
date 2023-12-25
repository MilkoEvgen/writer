package org.example.view;

import org.example.controller.WriterController;
import org.example.exceptions.EntityNotFoundException;
import org.example.model.Writer;

import java.util.List;
import java.util.Scanner;

public class WriterView extends View{
    private final WriterController writerController;
    private final String ENTER_FIRSTNAME = "Enter first name:";
    private final String ENTER_LASTNAME = "Enter last name:";
    private final String ENTER_ID = "Enter writer id:";
    private final String NOT_EXIST = "Writer does not exist";

    public WriterView(Scanner scanner, WriterController writerController) {
        super(scanner);
        this.writerController = writerController;
        ACTION = "Choose action:\n" +
                "1) Create writer\n" +
                "2) Get writer by id\n" +
                "3) Get all writers\n" +
                "4) Update writer\n" +
                "5) Delete writer";
    }

    protected void create() {
        scanner.nextLine();
        System.out.println(ENTER_FIRSTNAME);
        String firstName = scanner.nextLine();
        System.out.println(ENTER_LASTNAME);
        String lastName = scanner.nextLine();
        System.out.println("Creating writer...");
        Writer writer = writerController.create(firstName, lastName);
        System.out.println(writer);
    }

    protected void getById() {
        System.out.println(ENTER_ID);
        int id = scanner.nextInt();
        System.out.println("Getting writer by id...");
        try {
            Writer writer = writerController.getById(id);
            System.out.println(writer);
        } catch (EntityNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    protected void getAll() {
        System.out.println("Getting all writers...");
        List<Writer> writers = writerController.getAll();
        for (Writer writer : writers) {
            System.out.println("Writer {" +
                    "id=" + writer.getId() +
                    ", firstName=" + writer.getFirstName() +
                    ", lastName="+ writer.getLastName() + "}");
        }
    }

    protected void update() {
        System.out.println(ENTER_ID);
        Integer id = scanner.nextInt();
        scanner.nextLine();
        System.out.println(ENTER_FIRSTNAME);
        String firstName = scanner.nextLine();
        System.out.println(ENTER_LASTNAME);
        String lastName = scanner.nextLine();
        System.out.println("Updating writer...");
        try {
            Writer writer = writerController.update(id, firstName, lastName);
            System.out.println(writer);
        } catch (EntityNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    protected void delete() {
        System.out.println(ENTER_ID);
        int id = scanner.nextInt();
        System.out.println("Deleting writer...");
        try {
            if (writerController.delete(id)){
                System.out.println("Writer deleted successfully!");
            }
        } catch (EntityNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
}
