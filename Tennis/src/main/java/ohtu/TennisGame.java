package ohtu;

public class TennisGame {

    private int player1Score = 0;
    private int player2Score = 0;
    private final String player1Name;
    private final String player2Name;

    public TennisGame(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    public void wonPoint(String playerName) {
        if (playerName.equals("player1")) {
            player1Score++;
        } else {
            player2Score++;
        }
    }

    public String getScore() {
        // Both players have same score
        if (player1Score == player2Score) {
            return scoreEven(player1Score);

            // One of the players has over 4 points
        } else if (player1Score >= 4 || player2Score >= 4) {
            if (player1Score > player2Score) {
                return isWinning(player1Score - player2Score, 1); //Player 1 is leading
            } else {
                return isWinning(player2Score - player1Score, 2); //Player 2 is leading
            }
        }
        // One of the players is leading, but less then 4 points
        return score();

    }

    public String scoreEven(int points) {
        switch (points) {
            case 0:
                return "Love-All";
            case 1:
                return "Fifteen-All";
            case 2:
                return "Thirty-All";
            case 3:
                return "Forty-All";
            default:
                return "Deuce";
        }
    }

    public String isWinning(int points, int player) {
        int minusResult = player1Score - player2Score;
        if (minusResult == 1) {
            return "Advantage player1";
        } else if (minusResult == -1) {
            return "Advantage player2";
        } else if (minusResult >= 2) {
            return "Win for player1";
        } else {
            return "Win for player2";
        }
    }

    public String playerScoreToString(int score) {
        switch (score) {
            case 0:
                return "Love";
            case 1:
                return "Fifteen";
            case 2:
                return "Thirty";
            case 3:
                return "Forty";
        }
        return "";
    }

    private String score() {
        return playerScoreToString(player1Score) + "-" + playerScoreToString(player2Score);
    }
}
