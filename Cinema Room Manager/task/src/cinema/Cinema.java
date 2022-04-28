package cinema;

import java.util.Arrays;
import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {

        // get cinema dimensions and initialize cinema
        int[] dimensions = getCinemaDimensions();
        int rows = dimensions[0];
        int seatsInRow = dimensions[1];

        String[][] cinema = initializeEmptyCinema(rows, seatsInRow);

        // Menu
        menuAction(cinema, rows, seatsInRow);
    }

    // returns true if row is front and false otherwise
    private static boolean isFrontRow(int row, int totalRows) {
        return row <= totalRows / 2;
    }

    // initialize empty cinema
    private static String[][] initializeEmptyCinema(int rows, int seatsInRow) {
        // Set the cinema stage
        String[][] cinema = new String[rows + 1][seatsInRow + 1];
        for (String[] row : cinema) {
            Arrays.fill(row, "S");
        }
        cinema[0][0] = " ";
        for (int i = 1; i < cinema[0].length; i++) {
            cinema[0][i] = Integer.toString(i);
        }

        for (int i = 1; i < cinema.length; i++) {
            cinema[i][0] = Integer.toString(i);
        }
        return cinema;
    }

    // takes cinema array and prints it
    private static void printCinema(String[][] cinema) {
        System.out.println("Cinema:");
        for (String[] strings : cinema) {
            for (String string : strings) {
                System.out.print(string + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // ask user for dimensions of cinema to further initialize
    private static int[] getCinemaDimensions() {
        Scanner scanner = new Scanner(System.in);

        // get rows and seats in row
        System.out.println("Enter the number of rows:");
        System.out.print("> ");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        System.out.print("> ");
        int seatsInRow = scanner.nextInt();
        System.out.println();
        return new int[]{rows, seatsInRow};
    }

    // ask user for a desired seat to occupy
    private static int[] getDesiredSeat(String[][] cinema, int rows, int seatsInRow) {
        Scanner scanner = new Scanner(System.in);
        int[] chosenSeat = new int[2];

        while (true) {
            System.out.println("Enter a row number:");
            System.out.print("> ");
            int row = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            System.out.print("> ");
            int seat = scanner.nextInt();
            System.out.println();

            if (row >= 0 && row <= rows && seat >= 0 && seat <= seatsInRow) {
                if (!cinema[row][seat].equals("B")) {
                    chosenSeat[0] = row;
                    chosenSeat[1] = seat;
                    break;
                } else {
                    System.out.println("That ticket has already been purchased!");
                    System.out.println();
                }
            } else {
                System.out.println("Wrong input!");
                System.out.println();
            }
        }

        return chosenSeat;
    }

    // calculate and return ticket price
    private static int calculateTicketPrice(int chosenRow, int rows, int seatsInRow) {
        int totalSeats = rows * seatsInRow;

        if (totalSeats <= 60) {
            return 10;
        } else if (isFrontRow(chosenRow, rows)){
            return 10;
        } else {
            return 8;
        }
    }

    // book a seat after user chooses it
    private static void bookSeat(String[][] cinema, int chosenRow, int chosenSeat) {
        cinema[chosenRow][chosenSeat] = "B";
    }

    // show menu
    private static void showMenu() {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

    // perform menu action
    private static void menuAction(String[][] cinema, int rows, int seatsInRow) {
        Scanner scanner = new Scanner(System.in);
        int purchases = 0;
        int income = 0;

        while (true) {
            showMenu();
            System.out.print("> ");
            int action = scanner.nextInt();
            System.out.println();


            switch (action) {
                case 1:
                    printCinema(cinema);
                    break;
                case 2:
                    // input desired seat
                    int[] desiredSeat = getDesiredSeat(cinema, rows, seatsInRow);
                    int chosenRow = desiredSeat[0];
                    int chosenSeat = desiredSeat[1];
                    // calculate and print price
                    int ticketPrice = calculateTicketPrice(chosenRow, rows, seatsInRow);
                    System.out.println("Ticket price: $" + ticketPrice);
                    System.out.println();
                    bookSeat(cinema, chosenRow, chosenSeat);
                    purchases++;
                    income += ticketPrice;
                    break;
                case 3:
                    showStats(purchases, income, rows, seatsInRow);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Unknown command, please choose again.");
                    break;
            }
        }
    }

    private static void showStats(int purchases, int income, int rows, int seatsInRow) {
        int totalSeats = rows * seatsInRow;
        double percentageSold = purchases * 100.0 / totalSeats;
        int totalIncome = calculateTotalIncome(rows, seatsInRow);
        System.out.println("Number of purchased tickets: " + purchases);
        System.out.printf("Percentage: %.2f%%", percentageSold);
        System.out.println();
        System.out.println("Current income: $" + income);
        System.out.println("Total income: $" + totalIncome);
        System.out.println();
    }

    private static int calculateTotalIncome(int rows, int seatsInRow) {
        int totalSeats = rows * seatsInRow;
        if (totalSeats <= 60) {
            return totalSeats * 10;
        } else {
            int frontRows = rows / 2;
            int backRows = rows - frontRows;
            return frontRows * seatsInRow * 10 + backRows * seatsInRow * 8;
        }
    }

}