import java.util.*;

public class EnhancedEscapeRoomGame {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<String> rooms = Arrays.asList("Living Room", "Kitchen", "Bathroom", "Garage");
    private static final Map<String, String> puzzles = new HashMap<>(); // Room -> Puzzle
    private static final Map<String, Integer> points = new HashMap<>(); // Room -> Points
    private static final Map<String, String> hints = new HashMap<>(); // Room -> Hint

    private static String currentRoom;
    private static int totalPoints = 0;
    private static List<String> inventory = new ArrayList<>();
    private static Random random = new Random();
    private static int timeLimit = 30; // Time limit in seconds for each puzzle

    public static void main(String[] args) {
        setupGame();
        System.out.println("Welcome to the Enhanced Escape Room Puzzle Game!");
        System.out.println("Solve puzzles, collect items, and escape!");
        System.out.println("You have " + timeLimit + " seconds for each puzzle.");

        while (true) {
            System.out.println("\nCurrent Room: " + currentRoom);
            System.out.println("Total Points: " + totalPoints);
            System.out.println("Inventory: " + inventory);
            System.out.println("Available Actions: [1] Solve Puzzle [2] Move to Next Room [3] Use Hint [4] Exit Game");
            System.out.print("Choose an action: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    solvePuzzle();
                    break;
                case "2":
                    moveToNextRoom();
                    break;
                case "3":
                    useHint();
                    break;
                case "4":
                    System.out.println("Thank you for playing! You earned " + totalPoints + " points.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void setupGame() {
        currentRoom = rooms.get(random.nextInt(rooms.size())); // Start in a random room

        // Define puzzles, hints, and corresponding points
        puzzles.put("Living Room", "What has keys but can't open locks? (Answer: A piano)");
        points.put("Living Room", 10);
        hints.put("Living Room", "Think about musical instruments!");

        puzzles.put("Kitchen", "What has to be broken before you can use it? (Answer: An egg)");
        points.put("Kitchen", 15);
        hints.put("Kitchen", "It's something that can be cooked!");

        puzzles.put("Bathroom", "What is full of holes but still holds water? (Answer: A sponge)");
        points.put("Bathroom", 20);
        hints.put("Bathroom", "You use it while bathing!");

        puzzles.put("Garage", "I’m tall when I’m young, and I’m short when I’m old. What am I? (Answer: A candle)");
        points.put("Garage", 25);
        hints.put("Garage", "It provides light when burned!");
    }

    private static void solvePuzzle() {
        if (!puzzles.containsKey(currentRoom)) {
            System.out.println("No puzzle available in this room.");
            return;
        }

        String puzzle = puzzles.get(currentRoom);
        System.out.println("Puzzle: " + puzzle);
        
        // Start timer
        long startTime = System.currentTimeMillis();
        System.out.print("Your answer: ");
        String answer = scanner.nextLine().trim();

        // Check if the answer is correct or time exceeded
        long elapsedTime = (System.currentTimeMillis() - startTime) / 1000; // in seconds
        if (elapsedTime > timeLimit) {
            System.out.println("Time's up! You didn't answer in time.");
        } else if (isCorrectAnswer(answer.toLowerCase())) {
            int earnedPoints = points.get(currentRoom);
            totalPoints += earnedPoints;
            inventory.add(currentRoom); // Add the room to inventory
            System.out.println("Correct! You've earned " + earnedPoints + " points.");
            puzzles.remove(currentRoom); // Remove the puzzle once solved
        } else {
            System.out.println("Incorrect answer. Try again or move to another room.");
        }
    }

    private static boolean isCorrectAnswer(String answer) {
        switch (currentRoom) {
            case "Living Room":
                return answer.equals("a piano");
            case "Kitchen":
                return answer.equals("an egg");
            case "Bathroom":
                return answer.equals("a sponge");
            case "Garage":
                return answer.equals("a candle");
            default:
                return false;
        }
    }

    private static void moveToNextRoom() {
        List<String> remainingRooms = new ArrayList<>(rooms);
        remainingRooms.remove(currentRoom); // Remove current room from the list
        if (!remainingRooms.isEmpty()) {
            currentRoom = remainingRooms.get(random.nextInt(remainingRooms.size())); // Randomly select a new room
            System.out.println("You moved to the " + currentRoom + ".");
        } else {
            System.out.println("You've visited all rooms! Solve puzzles or exit.");
        }
    }

    private static void useHint() {
        if (hints.containsKey(currentRoom)) {
            System.out.println("Hint: " + hints.get(currentRoom));
            totalPoints -= 5; // Deduct points for using a hint
            System.out.println("You lost 5 points for using a hint. Total Points: " + totalPoints);
        } else {
            System.out.println("No hint available in this room.");
        }
    }
}
