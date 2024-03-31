import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
public class Friends {
    String answer_to_search_for_another_user;
    String answer_to_add_user;
    String friendship_type;
    String friendEmailToCheck_MutualFriends;
    Scanner input = new Scanner(System.in);
    private ArrayList<String> friends_list = new ArrayList();
    private ArrayList<String> restricted_friends = new ArrayList();
    private ArrayList<String> regular_friends = new ArrayList();
    private ArrayList<String> friends_list_of_other_user = new ArrayList();


    public ArrayList<String> getFriends_list() {
        return friends_list;
    } //used in coversation

    public void Search_for_friend(ArrayList<String> email_list, String useremail) {
        Scanner input = new Scanner(System.in);
        String friend_email;
        boolean user_is_found = false;
        do {
            System.out.println("Enter the email of the user that you are looking for:");
            friend_email = input.nextLine();
            for (String search : email_list) {
                if (friend_email.equals(search)) {
                    user_is_found = true;
                    System.out.println("User exists\n");
                    System.out.println("Do you want to add this user to your friends?");
                    answer_to_add_user = input.nextLine();
                    if (answer_to_add_user.equals("yes")) {
                        System.out.println("Do you want this friend to be restricted or regular ?");
                        friendship_type = input.nextLine();
                        try {
                            BufferedWriter writer = new BufferedWriter(new FileWriter("friends.txt", true));
                            writer.write("This friend of " + useremail+" is "+friendship_type+" : " + friend_email);
                            writer.write("\n");
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
            if (!user_is_found) {
                System.out.println("User doesn't exist");
            }
            System.out.println("Do you want to search for another user?");
            answer_to_search_for_another_user = input.nextLine();
            if (!answer_to_search_for_another_user.equals("yes")) {
                break;

            }
        } while (true);
    }

    public void display_friends(String useremail){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("friends.txt"));
            String line;
            String store;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("This friend of " + useremail + " is restricted : ")) {
                    store = line.substring(("This friend of " + useremail + " is restricted : ").length()).trim();
                    restricted_friends.add(store);
                    friends_list.add(store);
                    System.out.println("Restricted friends: " +store);
                }
                if (line.startsWith("This friend of " + useremail + " is regular : ")) {
                    store = line.substring(("This friend of " + useremail + " is regular : ").length()).trim();
                    regular_friends.add(store);
                    friends_list.add(store);
                    System.out.println("Regular friends: " +store);
                }

            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mutual_friends(String useremail){
        display_friends(useremail);
        System.out.println("Enter the friend that you want to see your mutual friends with them ");
        friendEmailToCheck_MutualFriends=input.next();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("friends.txt"));
            String line;
            String store;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("This friend of " + friendEmailToCheck_MutualFriends + " is restricted : ")) {
                    store = line.substring(("This friend of " + friendEmailToCheck_MutualFriends + " is restricted : ").length()).trim();
                    friends_list_of_other_user.add(store);
                }
                if (line.startsWith("This friend of " + friendEmailToCheck_MutualFriends + " is regular : ")) {
                    store = line.substring(("This friend of " + friendEmailToCheck_MutualFriends + " is regular : ").length()).trim();
                    friends_list_of_other_user.add(store);
                }

            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> mutualFriends = new ArrayList<>(friends_list);
        mutualFriends.retainAll(friends_list_of_other_user);
        System.out.println("Mutual friends with " + friendEmailToCheck_MutualFriends + ": " + mutualFriends);
    }

}
