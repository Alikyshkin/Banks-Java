package consoleapp;

import java.io.IOException;
import java.util.*;

public class Menu {
    private int selectedIndex;
    private final String[] options;
    private final String prompt;
    private final Scanner scanner = new Scanner(System.in);

    public Menu(String prompt, List<String> options) {
        this.prompt = prompt;
        this.options = options.toArray(new String[0]);
        this.selectedIndex = 0;
    }

    public int run() {
        clearConsole();
        displayOptions();
        selectedIndex = scanner.nextInt();

        return selectedIndex;
    }

    private void displayOptions() {
        System.out.println(prompt);

        for (int i = 0; i < options.length; i++) {
            String currentOption = options[i];

            System.out.println(i + ". << " + currentOption + " >>");
        }
    }

    public void clearConsole() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (IOException ignored) {
        }
    }
}
