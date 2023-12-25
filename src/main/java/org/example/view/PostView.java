package org.example.view;

import org.example.controller.PostController;
import org.example.exceptions.EntityNotFoundException;
import org.example.model.Post;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PostView extends View{
    private final PostController postController;
    private final String ENTER_CONTENT = "Enter content:";
    private final String ENTER_ID = "Enter post id:";
    private final String NOT_EXIST = "Post does not exist";

    public PostView(Scanner scanner, PostController postController) {
        super(scanner);
        this.postController = postController;
        ACTION = "Choose action:\n" +
                "1) Create post\n" +
                "2) Get post by id\n" +
                "3) Get all posts\n" +
                "4) Update post\n" +
                "5) Delete post";
    }

    protected void create(){
        scanner.nextLine();
        System.out.println(ENTER_CONTENT);
        String content = scanner.nextLine();
        System.out.println("Enter author id:");
        Integer authorId = scanner.nextInt();
        System.out.println("Enter label id separated by spaces:");
        scanner.nextLine();
        List<Integer> labels = Arrays.stream(scanner.nextLine().split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        System.out.println("Creating post...");
        try {
            Post post = postController.create(content, authorId, labels);
            System.out.println(post);
        } catch (EntityNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    protected void getById(){
        System.out.println(ENTER_ID);
        int id = scanner.nextInt();
        System.out.println("Getting post by id...");
        try {
            Post post = postController.getById(id);
            System.out.println(post);
        } catch (EntityNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    protected void getAll(){
        System.out.println("Choose action:\n" +
                "1) Get all posts\n" +
                "2) Get all posts by author id");
        List<Post> posts;
        switch (scanner.nextInt()){
            case 1:
                System.out.println("Getting all posts...");
                posts = postController.getAll();
                for (Post post : posts) {
                    System.out.println(post);
                }
                break;
            case 2:
                System.out.println("Enter author id:");
                int authorId = scanner.nextInt();
                System.out.println("Getting all posts by author id...");
                posts = postController.getAllByAuthorId(authorId);
                for (Post post : posts) {
                    System.out.println(post);
                }
                break;
        }
    }

    protected void update(){
        int postId;
        Post post;
        System.out.println("1) Update post content\n" +
                "2) Update post status");
        switch (scanner.nextInt()){
            case 1:
                System.out.println(ENTER_ID);
                postId = scanner.nextInt();
                scanner.nextLine();
                System.out.println(ENTER_CONTENT);
                String content = scanner.nextLine();
                System.out.println("Updating post...");
                try {
                    post = postController.update(postId, content);
                    System.out.println(post);
                } catch (EntityNotFoundException e){
                    System.out.println(e.getMessage());
                }
                break;
            case 2:
                System.out.println(ENTER_ID);
                postId = scanner.nextInt();
                System.out.println("Enter post status id:\n" +
                        "1) ACTIVE\n" +
                        "2) UNDER_REVIEW\n" +
                        "3) DELETED");
                int statusId = scanner.nextInt();
                System.out.println("Updating post status...");
                try {
                    post = postController.updateStatus(postId, statusId);
                    System.out.println(post);
                } catch (EntityNotFoundException e){
                    System.out.println(e.getMessage());
                }
                break;
        }
    }

    protected void delete(){
        System.out.println(ENTER_ID);
        int id = scanner.nextInt();
        System.out.println("Deleting post...");
        try {
            if (postController.delete(id)){
                System.out.println("Post deleted successfully!");
            }
        } catch (EntityNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
}
