package org.example.util;

import org.example.controller.LabelController;
import org.example.controller.PostController;
import org.example.controller.WriterController;
import org.example.repository.LabelRepository;
import org.example.repository.PostRepository;
import org.example.repository.WriterRepository;
import org.example.repository.hiberImpl.LabelRepositoryImpl;
import org.example.repository.hiberImpl.PostRepositoryImpl;
import org.example.repository.hiberImpl.WriterRepositoryImpl;
import org.example.service.LabelService;
import org.example.service.PostService;
import org.example.service.WriterService;
import org.example.service.impl.LabelServiceImpl;
import org.example.service.impl.PostServiceImpl;
import org.example.service.impl.WriterServiceImpl;
import org.example.view.LabelView;
import org.example.view.PostView;
import org.example.view.View;
import org.example.view.WriterView;

import java.util.Scanner;

public class ApplicationContext {
    private final Scanner scanner;
    private final View writerView;
    private final View postView;
    private final View labelView;


    private final String MENU = "Select an entity:\n" +
            "1) Writer\n" +
            "2) Post\n" +
            "3) Label\n" +
            "4) Quit";

    public ApplicationContext() {
        this.scanner = new Scanner(System.in);

        LabelRepository labelRepository = new LabelRepositoryImpl();
        LabelService labelService = new LabelServiceImpl(labelRepository);
        LabelController labelController = new LabelController(labelService);
        this.labelView = new LabelView(scanner, labelController);

        WriterRepository writerRepository = new WriterRepositoryImpl();
        WriterService writerService = new WriterServiceImpl(writerRepository);
        WriterController writerController = new WriterController(writerService);
        this.writerView = new WriterView(scanner, writerController);

        PostRepository postRepository = new PostRepositoryImpl();
        PostService postService = new PostServiceImpl(postRepository, writerRepository, labelRepository);
        PostController postController = new PostController(postService);
        this.postView = new PostView(scanner, postController);

    }

    public void start(){
        boolean exitLoop = false;

        while (!exitLoop) {
            System.out.println(MENU);
            switch (scanner.nextInt()) {
                case 1:
                    writerView.action();
                    break;
                case 2:
                    postView.action();
                    break;
                case 3:
                    labelView.action();
                    break;
                case 4:
                    exitLoop = true;
                    break;
            }
            System.out.println("\n=================================================\n");
        }
        System.out.println("Exit the application...");
    }
}
