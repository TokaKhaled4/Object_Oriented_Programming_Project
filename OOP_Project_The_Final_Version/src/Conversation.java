import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
public class Conversation {
    Scanner input = new Scanner(System.in);
    protected String participant_email;
    protected String answer_to_adding_participant;
    protected String answer_for_making_conversation;
    boolean participant_is_found = false;
    int participants=0;
    static int conversationID=0;
    public static ArrayList<String> participants_list = new ArrayList();
    public void make_conversation(String useremail){
        do {
            do{
                System.out.println("Choose a friend or more to start a coversation with them\n");
                System.out.println("Your Friends are :\n");
                Friends friends=new Friends();
                friends.display_friends(User.getLoggedInUserEmail());
                System.out.println("Enter the email of your friend that you want to chat with\n");
                participant_email=input.next();
                for (String search : friends.getFriends_list()) {
                    if (participant_email.equals(search)) {
                        participant_is_found = true;
                        participants_list.add(participant_email);
                        participants++;
                    }
                }
                if (!participant_is_found) {
                    System.out.println("Participant is not found");
                }
                System.out.println("Do you want to add another participant?");
                answer_to_adding_participant = input.next();
                input.nextLine();
                if (!answer_to_adding_participant.equals("yes")) {
                    break;
                }
            }while(true);
            conversationID++;
            System.out.println("This is conversation number "+conversationID);
            Message message=new Message();
            message.send_message(useremail);
            participants_list.clear();
            System.out.println("Do you want to make another conversation ?");
            answer_for_making_conversation=input.nextLine();


        } while (answer_for_making_conversation.equalsIgnoreCase("yes"));
    }
}

