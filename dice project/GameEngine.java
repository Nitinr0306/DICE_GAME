import java.util.ArrayList;

public class GameEngine {
    private ArrayList<Player> players;
    private Dice dice;
    
    public GameEngine(ArrayList<Player> players) {
        this.players = players;
        this.dice = new Dice();
    }
    
    public void playRound() {
        int highestOutput = 0;
        Player winPlayer = null;
        
        for (Player p : players) {
            int number = dice.roll();
            
            if (number > highestOutput) {
                highestOutput = number;
                winPlayer = p;
            } else if (number == highestOutput) {
                winPlayer = null;
            }
        }
        
        if (winPlayer != null) {
            System.out.println("The player who won this round is " + winPlayer.showName() + 
                             ". The output was " + highestOutput);
            winPlayer.incrementWin();
        } else {
            System.out.println("This round ended in a tie. The highest output was " + highestOutput);
        }
    }
    
    public void showResults() {
        System.out.println("\nFinal Results:");
        for (Player p : players) {
            System.out.println("Player " + p.showName() + " has won " + p.showWins() + " rounds.");
        }
    }
}