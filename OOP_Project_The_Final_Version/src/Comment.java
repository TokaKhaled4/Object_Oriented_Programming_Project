import java.io.*;
import java.util.*;

public class Comment {
    private static int ID = 1;
    private String reactionType;
    String content;
    Scanner input = new Scanner(System.in);
    static int ReactionIndexOfComment=0;
    String email;
    Reply reply;
    public void AddContent() {
        System.out.println("Comment as " + User.getLoggedInUserEmail() + "...");
        Scanner Add_Comment = new Scanner(System.in);
        email = User.getLoggedInUserEmail();
        content = Add_Comment.nextLine();
    }

    public void ReactToContent () {
        System.out.println("Would you like to react to comment? ");
        String choice = input.nextLine();
        if (choice.equalsIgnoreCase("yes")) {
            do {
                System.out.println("Like Love Care Haha Wow Sad Angry");
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter your reaction:");
                reactionType = scanner.nextLine().toLowerCase();
                switch (reactionType) {
                    case "like":
                    case "love":
                    case "care":
                    case "haha":
                    case "wow":
                    case "sad":
                    case "angry":
                        ReactionIndexOfComment++;
                        break;
                    default:
                        System.out.println("Invalid reaction");
                }
                System.out.println("Do you want to change reaction? (Yes/No) ");
                choice = input.nextLine();
            } while (choice.equalsIgnoreCase("Yes"));
            SaveReactionToContent();
        }
    }
    public void SaveReactionToContent() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("ReactToComment.txt", true));
            writer.write("reaction number: " + ReactionIndexOfComment);
            writer.newLine();
            writer.write("Reactor name: " + User.getLoggedInUserEmail());
            writer.newLine();
            writer.write("reaction type: "+ reactionType);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}