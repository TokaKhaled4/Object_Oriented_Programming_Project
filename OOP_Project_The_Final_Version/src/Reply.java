import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
public class Reply extends Comment {
    private String reactionToReply;
    public int ReactionIndex_Reply=0 ;
    Scanner input = new Scanner(System.in);
    String email_2;
    String content_2;
    public void AddContent() {
        System.out.println("Reply as " + User.getLoggedInUserEmail() + "...");
        Scanner Add_Comment = new Scanner(System.in);
        content_2 = Add_Comment.nextLine();
        email_2 = User.getLoggedInUserEmail();
    }

    public void ReactToContent() {
        System.out.println("Do you want to react to reply?");
        String choice = input.nextLine();
        if(choice.equalsIgnoreCase("yes")) {
            do {
                System.out.println("Like Love Care Haha Wow Sad Angry");
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter your reaction:");
                reactionToReply = scanner.nextLine().toLowerCase();
                switch (reactionToReply) {
                    case "like":
                    case "love":
                    case "care":
                    case "haha":
                    case "wow":
                    case "sad":
                    case "angry":
                        ReactionIndex_Reply++;
                        break;
                    default:
                        System.out.println("Invalid reaction");
                }
                System.out.println("Do you want to change reaction? (Yes/No) ");
                choice=input.next();
            } while (choice.equalsIgnoreCase("Yes"));
            SaveReactionToContent();
        }
    }
    public void SaveReactionToContent() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("ReactToReply.txt", true));
            writer.write("reaction number: " +ReactionIndex_Reply);
            writer.newLine();
            writer.write("Reactor name: " +User.getLoggedInUserEmail());
            writer.newLine();
            writer.write("reaction type: "+reactionToReply);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}