import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Message {
    String message;
    String answer_to_writing_another_message;
    int message_counter=0;
    Scanner input = new Scanner(System.in);
    public void send_message(String useremail) {
        do{
            message_counter++;
            System.out.println("Write the message:");
            message = input.nextLine();
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("chats.txt", true));
                for (String p : Conversation.participants_list) {
                    writer.write(useremail + " sends message to " + p + " " + message + "\n");
                }
                writer.write("\n");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Do you want to send another message?");
            answer_to_writing_another_message=input.nextLine();
        }    while (answer_to_writing_another_message.equalsIgnoreCase("yes"));
    }
}

