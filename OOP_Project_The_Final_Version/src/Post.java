import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
public class Post {
    static int IndexOfReactionPost = 0;
    Scanner words = new Scanner(System.in);
    String email;
    String Friends_email;
    String PostPrivacy;
    String answer_to_tag_question;
    String tagged_friend;
    String check_tagged_friend;
    String reactionType;
    String line;
    Comment comment=new Comment();
    Scanner input = new Scanner(System.in);

    private  ArrayList<String> Post_comment_array = new ArrayList<>();

    private  ArrayList<String> post_comment = new ArrayList<>();
    private ArrayList<String>reply_comments_array=new ArrayList<>();
    private  ArrayList<String> public_posts = new ArrayList();
    private  ArrayList<String> friends_only_posts = new ArrayList();
    private  ArrayList<String> restricted_friends_of_other_user = new ArrayList();
    private  ArrayList<String> regular_friends_of_other_user = new ArrayList();
    private  ArrayList<String> tagged_friends = new ArrayList();
    private  ArrayList<String>Reply_comments=new ArrayList<>();
    private  ArrayList<String> Array_Of_Post = new ArrayList<>();
    private  HashSet<String> uniquePosts = new HashSet<>();
    Reply reply = new Reply();
    public void makePost(String useremail) {
        System.out.println("Would you like to make a post?");
        String choice = words.nextLine();
        String choice_2;
        if (choice.equalsIgnoreCase("yes")) {
            do {
                System.out.println("Choose the audience: (public or friends only)");
                PostPrivacy = words.nextLine().toLowerCase();
                System.out.println("Do you want to tag someone to the post?");
                answer_to_tag_question = words.nextLine().toLowerCase();
                if (answer_to_tag_question.equalsIgnoreCase("yes")) {
                    Friends friends = new Friends();
                    friends.display_friends(User.getLoggedInUserEmail());
                    System.out.println("Enter the friend that you want to tag:");
                    tagged_friend = words.nextLine();
                    tagged_friends.add(tagged_friend);
                    System.out.println("Start writing your post: ");
                    String line = words.nextLine();
                    Array_Of_Post.add(PostPrivacy + " Post of " + User.getLoggedInUserEmail() + " mentioned " + tagged_friends + ": " + line);
                    tagged_friends.remove(tagged_friend);
                } else if (answer_to_tag_question.equalsIgnoreCase("no")) {
                    System.out.println("Start writing your post: ");
                    String line = words.nextLine();
                    Array_Of_Post.add(PostPrivacy + " Post of " + User.getLoggedInUserEmail() + ": " + line);
                }
                System.out.println("Do you wish to make another post?");
                choice = words.nextLine();
            } while (choice.equalsIgnoreCase("yes"));
            Save_in_File();
        }
    }

    public void store_friends_of_other_user(String friend_mail) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("friends.txt"));
            String line;
            String store;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("This friend of " + friend_mail + " is restricted : ")) {
                    store = line.substring(("This friend of " + friend_mail + " is restricted : ").length()).trim();
                    restricted_friends_of_other_user.add(store);
                }
                if (line.startsWith("This friend of " + friend_mail + " is regular : ")) {
                    store = line.substring(("This friend of " + friend_mail + " is regular : ").length()).trim();
                    regular_friends_of_other_user.add(store);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Save_in_File() {
        try {
            BufferedWriter write_file = new BufferedWriter(new FileWriter("Posts Made.txt", true));
            for (String P : Array_Of_Post) {
                if (uniquePosts.add(P)) {
                    write_file.write(P);
                    write_file.newLine();
                    write_file.write("--------------------------");
                    write_file.newLine();
                }
            }
            write_file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void store_posts(String friend_mail) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Posts Made.txt"));
            String line;
            String store;
            while ((line = reader.readLine()) != null) {
                if (line.contains(friend_mail)) {
                    if (line.contains("public Post" )) {
                        store = line.substring(("public Post of " ).length()).trim();
                        public_posts.add(store);
                    }
                    if (line.contains("friends only Post" )) {
                        store = line.substring(("friends only Post of ").length()).trim();
                        friends_only_posts.add(store);
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void display_Friend_post() {
        boolean friend_is_found = false;
        System.out.println("Your Friends are:\n");
        Friends friends = new Friends();
        friends.display_friends(User.getLoggedInUserEmail());
        Friends_email = words.next();
        store_friends_of_other_user(Friends_email);
        store_posts(Friends_email);
        boolean isRestrictedFriend = restricted_friends_of_other_user.contains(User.getLoggedInUserEmail());
        boolean isRegularFriend = regular_friends_of_other_user.contains(User.getLoggedInUserEmail());
        if (isRegularFriend) {
            for (String s : friends_only_posts) {
                System.out.println(s);
                ReactToPost();
                System.out.println("Do you want to add comments? ");
                String choice = input.nextLine();

                if (choice.equalsIgnoreCase("yes")) {
                    comment.AddContent();
                    Post_comment_array.add(s + " comment by: " + comment.email + " " + comment.content);
                }
            }
        } if (isRestrictedFriend||isRegularFriend)
            for (String s : public_posts) {
                System.out.println(s);
                ReactToPost();
                System.out.println("Do you want to add comments? ");
                String choice = input.nextLine();
                if (choice.equalsIgnoreCase("yes")) {
                    comment.AddContent();
                    Post_comment_array.add(s + " comment by: " + comment.email + " " + comment.content);
                }
            }
        StorePostsWithComments();
    }
    public void StoreCommentsWithReplies() {
        try {
            BufferedWriter writer=new BufferedWriter(new FileWriter("RepliesAndComments.txt"));
            for (String r:Reply_comments) {
                writer.write(r);
                writer.newLine();
                Reply_comments.remove(reply.content_2);
            }
            writer.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public void Display_Comments_With_replies() {
        read_comments_replies();
        for (String post_comments:reply_comments_array) {
            System.out.println(post_comments);
            reply.ReactToContent();

        }
    }
    public void read_comments_replies() {
        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader("RepliesAndComments.txt"));
            while ((line = reader.readLine()) != null) {
                if (line.contains("comment by: "+User.getLoggedInUserEmail())) {
                    reply_comments_array.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void Read_post_With_comments() {
        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader("CommentsAndPosts.txt"));
            while ((line = reader.readLine()) != null) {
                post_comment.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void Display_Post_With_Comments() {
        Read_post_With_comments();
        for (String post_comments:post_comment) {
            System.out.println(post_comments);
            comment.ReactToContent();
            System.out.println("Do you want to reply? ");
            {
                String choice = input.nextLine();
                if (choice.equalsIgnoreCase("yes")) {
                    reply.AddContent();
                    Reply_comments.add(post_comments + "  "+"Reply by: "+reply.email_2+" reply: "+reply.content_2);

                }
            }
        }
        StoreCommentsWithReplies();
    }

    public void StorePostsWithComments() {
        try{
            BufferedWriter writer=new BufferedWriter(new FileWriter("CommentsAndPosts.txt"));
            for (String c :Post_comment_array) {
                writer.write(c);
                writer.newLine();

            }

            writer.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void display_mutual_post() {
        System.out.println("Enter the friend that you want to see your mutual posts with them :");
        check_tagged_friend = words.nextLine();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Posts Made.txt"));
            String line;
            String store;
            String delimiter = ":";
            while ((line = reader.readLine()) != null) {
                if (line.contains(" mentioned " + "["+check_tagged_friend+"]")) {
                    int index = line.indexOf(delimiter);
                    if (index != 1) {
                        store = line.substring(index + 1).trim();
                        System.out.println(store);
                    }
                }
                if (line.contains(" mentioned " + "["+User.getLoggedInUserEmail()+"]")) {
                    int index = line.indexOf(delimiter);
                    if (index != 1) {
                        store = line.substring(index + 1).trim();
                        System.out.println(store);
                    }
                }

            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ReactToPost() {
        System.out.println("Would you like to react to the post?");
        String choice=words.next();
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
                        IndexOfReactionPost++;
                        break;
                    default:
                        System.out.println("Invalid reaction");
                }
                System.out.println("Do you want to change reaction? (Yes/No) ");
                choice = words.next();
            } while (choice.equalsIgnoreCase("Yes"));
            SaveReactionToPost();
        }
    }

    public void SaveReactionToPost() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("ReactToPost.txt", true));
            writer.write("reaction number: " + IndexOfReactionPost);
            writer.newLine();
            writer.write("Reactor name: " + User.getLoggedInUserEmail());
            writer.newLine();
            writer.write("reaction type: " + reactionType);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}