package org.example.view;

import org.example.controller.PostController;
import org.example.exceptions.EntityNotFoundException;
import org.example.model.Post;
import org.example.model.PostStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PostView extends View {
    private final PostController postController;
    private final String ENTER_CONTENT = "Enter content:";
    private final String ENTER_ID = "Enter post id:";

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

    protected void create() {
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
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    protected void getById() {
        System.out.println(ENTER_ID);
        Integer id = scanner.nextInt();
        System.out.println("Getting post by id...");
        try {
            Post post = postController.getById(id);
            System.out.println("Post{" +
                    "id=" + id +
                    ", content='" + post.getContent() + '\'' +
                    ", created=" + post.getCreated() +
                    ", updated=" + post.getUpdated() +
                    ", labels=" + post.getLabels() +
                    ", author={" +
                    "id=" + post.getAuthor().getId() +
                    ", first name=" + post.getAuthor().getFirstName() +
                    ", last name=" + post.getAuthor().getLastName() +
                    "}, postStatus=" + post.getPostStatus() +
                    "}");
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    protected void getAll() {
        System.out.println("Choose action:\n" +
                "1) Get all posts\n" +
                "2) Get all posts by author id");
        List<Post> posts;
        switch (scanner.nextInt()) {
            case 1:
                System.out.println("Getting all posts...");
                posts = postController.getAll();
                for (Post post : posts) {
                    System.out.println(post);
                }
                break;
            case 2:
                System.out.println("Enter author id:");
                Integer authorId = scanner.nextInt();
                System.out.println("Getting all posts by author id...");
                posts = postController.getAllByAuthorId(authorId);
                for (Post post : posts) {
                    System.out.println(post);
                }
                break;
        }
    }

    protected void update() {
        System.out.println(ENTER_ID);
        Integer postId = scanner.nextInt();
        scanner.nextLine();
        System.out.println(ENTER_CONTENT);
        System.out.println("(if you don't want to update content, leave the field blank)");
        String content = scanner.nextLine();
        System.out.println("Enter post status:\n" +
                "1) ACTIVE\n" +
                "2) UNDER_REVIEW (default)\n" +
                "3) DELETED");

        PostStatus status = PostStatus.UNDER_REVIEW;
        String statusInput = scanner.nextLine();
        if (!statusInput.trim().isEmpty()){
            switch (Integer.parseInt(statusInput)){
                case 1: status = PostStatus.ACTIVE;
                    break;
                case 2: status = PostStatus.UNDER_REVIEW;
                    break;
                case 3: status = PostStatus.DELETED;
                    break;
                default:
                    System.out.println("Incorrect value");;
            }
        }
        System.out.println("Updating post...");
        try {
            Post post = postController.update(postId, content, status);
            System.out.println(post);
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    protected void delete() {
        System.out.println(ENTER_ID);
        Integer id = scanner.nextInt();
        System.out.println("Deleting post...");
        try {
            if (postController.delete(id)) {
                System.out.println("Post deleted successfully!");
            }
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
