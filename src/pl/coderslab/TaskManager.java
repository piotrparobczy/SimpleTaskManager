package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
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
    scan.close();
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
        stringBuilder.append(loadedData[i][j]).append(", ");
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
    tasksAsLines[tasksAsLines.length - 1] = taskLines.toString();
    loadedData = oneDimensionToTwoDimension(tasksAsLines);
    showOptions();
  }

  public static void removeTask() {
    Scanner input = new Scanner(System.in);
    String indexToDelete = "-1";
    while (!isNumberAndGreaterThanZero(indexToDelete)) {
      indexToDelete = input.nextLine();
    }
    Integer indexToDeleteInt = Integer.valueOf(indexToDelete);
    loadedData = ArrayUtils.remove(loadedData, indexToDeleteInt);
    showOptions();
  }

  public static boolean isNumberAndGreaterThanZero(String input) {
    if (NumberUtils.isParsable(input)) {
      if (Integer.valueOf(input) < 0) {
        System.out.println("Type value equal or greater than 0");
      }
      return Integer.valueOf(input) >= 0;
    }
    System.out.println("Type value that is number");
    return false;
  }

  public static void listTask() {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < loadedData.length; i++) {
      stringBuilder.append("[").append(String.valueOf(i)).append("] ");
      for (int j = 0; j < loadedData[i].length; j++) {
        stringBuilder.append(loadedData[i][j]).append(",");
      }
      stringBuilder.append("\n");
    }
    System.out.println(stringBuilder.toString());
    showOptions();
  }

  public static void exitTask() {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < loadedData.length; i++) {
      for (int j = 0; j < loadedData[i].length; j++) {
        stringBuilder.append(loadedData[i][j]).append(",");
      }
      stringBuilder.append("\n");
    }
    stringBuilder.setLength(stringBuilder.length() - 1);
    PrintWriter printWriter = null;
    try {
      printWriter = new PrintWriter("tasks.csv");
      printWriter.print(stringBuilder.toString());
      printWriter.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    System.out.println("Bye bye");
  }

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
