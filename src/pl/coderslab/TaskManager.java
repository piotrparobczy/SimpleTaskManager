package pl.coderslab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {

  static final String FILE_PATH = "tasks.csv";
  static String[][] loadedData;

  public static void main(String[] args) {
    loadedData = getLoadedData();
    showOptions();

    Scanner input = new Scanner(System.in);
    String inputLine = "";
    while (!"exit".equals(inputLine)) {
      inputLine = input.nextLine();
      if ("add".equals(inputLine)) {
        addTask();
      } else if ("remove".equals(inputLine)) {
        removeTask();
      } else if ("list".equals(inputLine)) {
        listTask();
      } else if ("exit".equals(inputLine)) {
        exitTask();
      } else {
        System.out.println("Please select a correct option.");
      }
    }
  }

  static String[][] getLoadedData() {
    File file = new File("tasks.csv");
    String[] tasksAsLines = new String[0];
    Scanner scan = null;
    // TODO: 11.11.2020 try with resources! or kill process
    try {
      scan = new Scanner(file);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    while (scan.hasNextLine()) {
      String taskLine = scan.nextLine();
      tasksAsLines = Arrays.copyOf(tasksAsLines, tasksAsLines.length + 1);
      tasksAsLines[tasksAsLines.length - 1] = taskLine;
    }

    String[][] loadedData = oneDimensionToTwoDimension(tasksAsLines);
    return loadedData;
  }

  public static void showOptions() {
    System.out.println(ConsoleColors.BLUE + "Please select an option:");
    System.out.println(ConsoleColors.WHITE + "add\nremove\nlist\nexit");
  }

  public static void addTask() {
    String[] tasksAsLines = new String[0];

    for (int i = 0; i < loadedData.length; i++) {
      StringBuilder stringBuilder = new StringBuilder();
      for (int j = 0; j < loadedData[i].length; j++) {
        stringBuilder.append(loadedData[i][j]).append(",");
      }
      tasksAsLines = Arrays.copyOf(tasksAsLines, tasksAsLines.length + 1);
      tasksAsLines[i] = stringBuilder.toString();
    }
    tasksAsLines = Arrays.copyOf(tasksAsLines, tasksAsLines.length + 1);
    Scanner input = new Scanner(System.in);
    StringBuilder taskLines = new StringBuilder();

    System.out.println("Please add task description");
    taskLines.append(input.nextLine()).append(",");
    System.out.println("Please add task due date");
    taskLines.append(input.nextLine()).append(",");
    System.out.println("Is your task important: true/false");
    taskLines.append(input.nextLine()).append(",");
    // TODO: 12.11.220 musi zwracac do zmiennej globalnej
    tasksAsLines[tasksAsLines.length - 1] = taskLines.toString();
    loadedData = oneDimensionToTwoDimension(tasksAsLines);
    showOptions();
  }

  public static void removeTask() {
    showOptions();
  }

  public static void listTask() {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < loadedData.length; i++) {
      for (int j = 0; j < loadedData[i].length; j++) {
        stringBuilder.append(loadedData[i][j]).append(",");
      }
      stringBuilder.append("\n");
    }
    System.out.println(stringBuilder.toString());
    showOptions();
  }

  public static void exitTask() {}

  public static String[][] oneDimensionToTwoDimension(String[] oneDimension) {
    String[][] twoDimensional = new String[oneDimension.length][3];
    for (int i = 0; i < twoDimensional.length; i++) {
      for (int j = 0; j < twoDimensional[i].length; j++) {
        twoDimensional[i][j] = oneDimension[i].split(",")[j];
      }
    }
    return twoDimensional;
  }
}
