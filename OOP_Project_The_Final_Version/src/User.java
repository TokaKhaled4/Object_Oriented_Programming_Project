import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class User {

    final String Path ="Users_data.txt";
    protected ArrayList<String> name_list =new ArrayList();
    protected ArrayList<String> gender_list =new ArrayList();
    protected ArrayList<String> email_list =new ArrayList();
    protected ArrayList<String> password_list =new ArrayList();
    protected ArrayList<String> birth_date_list =new ArrayList();
    public int ID_password=0;
    public int ID_email=0;
    public void export_data(String Path){
        try{
            FileWriter file=new FileWriter(Path);
            file.write("The Names:\n\n");
            for (String string:name_list) {
                file.write("Name: "+string+"\n");
                file.flush();

            }
            file.write("\n");
            file.write("The Gender:\n\n");
            for (String string:gender_list) {
                file.write("Gender: " + string + "\n");
                file.flush();
            }
            file.write("\n");
            file.write("The Emails:\n\n");
            for (String string:email_list) {
                file.write("Email: " + string + "\n");
                file.flush();
            }
            file.write("\n");
            file.write("The Passwords:\n\n");
            for (String string:password_list) {
                file.write("Password: " + string + "\n");
                file.flush();
            }
            file.write("\n");
            file.write("The BirthDates:\n\n");
            for (String string:birth_date_list) {
                file.write("Birth_date: "+string+"\n");
                file.flush();
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void extract_data( String Path){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(Path));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Name: ")) {
                    name_list.add(line.substring(6).trim()) ;
                }
                else if (line.startsWith("Gender: ")) {
                    gender_list.add(line.substring(8).trim());
                }
                else if (line.startsWith("Email: ")) {
                    email_list.add(line.substring(7).trim()) ;
                }
                else if (line.startsWith("Password: ")){
                    password_list.add(line.substring(10).trim()) ;
                }
                else if (line.startsWith("Birth_date: ")) {
                    birth_date_list.add(line.substring(12).trim()) ;
                }

            }
            reader.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean Check_Email(String mail){
        boolean check=false;
        for (String check_email:email_list) {
            if( check_email.equals(mail)) {
                check = true;
                break;
            }
        }
        return check;
    }
    public boolean Check_Password(String password){
        boolean check=false;
        for (String check_password:password_list) {
            if (check_password.equals(password)) {
                check = true;
                break;
            }
        }
        return check;
    }
    private static String loggedInUserEmail;
    public static String getLoggedInUserEmail() {
        return loggedInUserEmail;
    }
    public void login() {
        try {
            Scanner input = new Scanner(System.in);
            System.out.println("Login");
            System.out.println("-------");
            System.out.println("Enter your email :");
            String login_email = input.next();
            if (Check_Email(login_email)) {
                System.out.println("Enter your password :");
                String login_password = input.next();
                if (Check_Password(login_password)) {
                    for (String check_password:password_list) {
                        if (check_password.equals(login_password)) {
                            ID_password =password_list.indexOf(check_password);
                            break;
                        }
                    }
                    for (String check:email_list) {
                        if (check.equals(login_email)) {
                            ID_email=email_list.indexOf(check);
                            break;
                        }
                    }
                    if(ID_password==ID_email) {
                        System.out.println("login sucessfully");
                        loggedInUserEmail = login_email;
                        home_page();
                    }
                    else{
                        System.out.println("incorrect password");
                        register_menu();
                    }
                } else {
                    System.out.println("incorrect password");
                    register_menu();
                }
            } else {
                System.out.println("Invalid Email");
                register_menu();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void register() {
        System.out.println("Sign up");
        System.out.println("---------");
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your name :");
        String Name = input.nextLine();
        name_list.add( Name);
        System.out.println("Enter your gender :");
        String Gender = input.next();
        gender_list.add(Gender);
        System.out.println("Enter your email :");
        String Email = input.next();
        email_list.add(Email);
        System.out.println("Enter your password :");
        String Password = input.next();
        password_list.add(Password);
        System.out.println("Enter your birthdate :");
        String Birthdate = input.next();
        birth_date_list.add(Birthdate);
        export_data(Path);
    }

    public void register_menu() {
        System.out.println("Welcome"+"\n");
        System.out.println("--------"+"\n");
        System.out.println("1-Sign up\n" + "2-Login\n"+"3-Logout\n");
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your choice :");
        int register_button = input.nextInt();
        if (register_button == 1) {
            register();
            register_menu();
        } else if (register_button == 2) {
            login();
        }
        else if(register_button == 3){
            System.out.println("Thank You For Using Facebook");

        }

    }
public void home_page() {
    Scanner input = new Scanner(System.in);
    System.out.println("Home page");
    System.out.println("----------");
    System.out.println("1-Make a post\n" + "2-See your friends posts\n" + "3-Search for a friend\n" + "4-Make a conversation\n" + "5-See mutual friends\n" + "6-See mutual Posts\n" + "7-Edit user information\n" + "8-display comment on my post\n" + "9-display replies on my comment\n" + "10-logout\n");
    System.out.println("Enter your choice :");
    int homepage_choice = input.nextInt();
    if (homepage_choice == 1) {
        Post post = new Post();
        post.makePost(loggedInUserEmail);
        home_page();
    } else if (homepage_choice == 2) {
        Post post = new Post();
        post.display_Friend_post();
        home_page();
    } else if (homepage_choice == 3) {
        Friends friends = new Friends();
        friends.Search_for_friend(email_list, loggedInUserEmail);
        home_page();
    } else if (homepage_choice == 4) {
        Conversation conversation = new Conversation();
        conversation.make_conversation(loggedInUserEmail);
        home_page();
    } else if (homepage_choice == 5) {
        Friends friends = new Friends();
        friends.mutual_friends(loggedInUserEmail);
        home_page();
    } else if (homepage_choice == 6) {
        Post post = new Post();
        post.display_mutual_post();
        home_page();
    } else if (homepage_choice == 7) {
        Modify_user_info modifyUserInfo = new Modify_user_info();
        modifyUserInfo.Modify_user_info(name_list, gender_list, email_list, password_list, birth_date_list, ID_password);
        modifyUserInfo.Modify_menu();
        export_data(Path);
        register_menu();
    } else if (homepage_choice == 8) {
        Post post = new Post();
        post.Display_Post_With_Comments();
        home_page();

    } else if (homepage_choice == 9) {
        Post post = new Post();
        post.Display_Comments_With_replies();
        home_page();
    } else if (homepage_choice == 10) {
        System.exit(0);
    }
    else{
        System.out.println("Invalid choice");
    }

}
}
