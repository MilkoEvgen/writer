package org.example.view;

import org.example.controller.LabelController;
import org.example.exceptions.EntityNotFoundException;
import org.example.model.Label;

import java.util.List;
import java.util.Scanner;

public class LabelView extends View {
    private final LabelController labelController;
    private final String ENTER_NAME = "Enter name:";
    private final String ENTER_ID = "Enter label id:";

    private final String NOT_EXIST = "Label does not exist";

    public LabelView(Scanner scanner, LabelController labelController) {
        super(scanner);
        this.labelController = labelController;
        ACTION = "Choose action:\n" +
                "1) Create label\n" +
                "2) Get label by id\n" +
                "3) Get all labels\n" +
                "4) Update label\n" +
                "5) Delete label";
    }

    protected void create(){
        scanner.nextLine();
        System.out.println(ENTER_NAME);
        String name = scanner.nextLine();
        System.out.println("Creating label...");
        try {
            Label label = labelController.create(name);
            System.out.println(label);
        } catch (EntityNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    protected void getById(){
        System.out.println(ENTER_ID);
        int id = scanner.nextInt();
        System.out.println("Getting label by id...");
        try {
            Label label = labelController.getById(id);
            System.out.println(label);
        } catch (EntityNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    protected void getAll(){
        System.out.println("Getting all labels...");
        List<Label> labels = labelController.getAll();
        for (Label label : labels) {
            System.out.println(label);
        }
    }

    protected void update(){
        System.out.println(ENTER_ID);
        Integer id = scanner.nextInt();
        scanner.nextLine();
        System.out.println(ENTER_NAME);
        String name = scanner.nextLine();
        System.out.println("Updating label...");
        try {
            Label label = labelController.update(id, name);
            System.out.println(label);
        } catch (EntityNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    protected void delete(){
        System.out.println(ENTER_ID);
        int id = scanner.nextInt();
        System.out.println("Deleting label...");
        try {
            if (labelController.delete(id)){
                System.out.println("Label deleted successfully!");
            }
        } catch (EntityNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

}
