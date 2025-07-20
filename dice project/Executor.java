import java.util.ArrayList;
import java.util.Scanner;

public class Executor {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Welcome to the Dice Game Simulator!");
        System.out.print("Give the number of players: ");
        int num = sc.nextInt();
        sc.nextLine(); 
        
        ArrayList<Player> list = new ArrayList<>();
        
        for (int i = 0; i < num; i++) {
            System.out.print("Player " + (i + 1) + " name: ");
            String name = sc.nextLine();
            list.add(new Player(name));
        }
        
        GameEngine g = new GameEngine(list);
        
        System.out.print("\nHow many rounds would you like to play? ");
        int rounds = sc.nextInt();
        
        for (int i = 0; i < rounds; i++) {
            System.out.println("\nRound " + (i + 1) + ":");
            g.playRound();
        }
        
        g.showResults();
        
        System.out.println("\nThank you for playing the Dice Game Simulator!");
        sc.close();
    }
}