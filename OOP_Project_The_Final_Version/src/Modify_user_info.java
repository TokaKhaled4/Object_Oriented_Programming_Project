import java.util.ArrayList;
import java.util.Scanner;

public class Modify_user_info {

    private ArrayList<String> name_list;
    private ArrayList<String> gender_list;
    private ArrayList<String> email_list;
    private ArrayList<String> password_list;
    private ArrayList<String> birth_date_list;
    private int ID;
    User user =new User();
    public void Modify_user_info(ArrayList<String> name_list, ArrayList<String> gender_list, ArrayList<String> email_list,
                                 ArrayList<String> password_list, ArrayList<String> birth_date_list, int ID) {
        this.name_list = name_list;
        this.gender_list = gender_list;
        this.email_list = email_list;
        this.password_list = password_list;
        this.birth_date_list = birth_date_list;
        this.ID = ID;
    }

    public void Change_password(String pass) {
        password_list.set(ID, pass);
    }

    public void Change_email(String mail) {
        email_list.set(ID, mail);
    }

    public void Change_birth_date(String birthday) {
        birth_date_list.set(ID, birthday);
    }

    public void Change_gender(String gender) {
        gender_list.set(ID, gender);
    }

    public void Delete_account() {
        name_list.remove(ID);
        gender_list.remove(ID);
        email_list.remove(ID);
        password_list.remove(ID);
        birth_date_list.remove(ID);
    }

    public void Modify_menu() {
        System.out.println("Welcome " + name_list.get(ID) + "\n" + "-----------------------");
        System.out.println("1-Edit your information. \n" + "2-Delete Your Account.\n");
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your choice :");
        int register_button = input.nextInt();
        if (register_button == 1) {
            System.out.println("1-Change your password. \n" + "2-Change gender.\n" + "3-Change your birth_date.\n" + "4-Change your Email\n");
            Scanner cin = new Scanner(System.in);
            int choice = cin.nextInt();
            Scanner str = new Scanner(System.in);
            if (choice == 1) {
                System.out.println("Enter new password :\n");
                String s = str.nextLine();
                Change_password(s);
                System.out.println("Password is changed successfully.");
            } else if (choice == 2) {
                System.out.println("Enter new gender :\n");
                String s = str.nextLine();
                Change_gender(s);
                System.out.println("Gender is changed successfully.");
            } else if (choice == 3) {
                System.out.println("Enter new birth_date :\n");
                String s = str.nextLine();
                Change_birth_date(s);
                System.out.println("Birth_Date is changed successfully.");
            } else if (choice == 4) {
                System.out.println("Enter new email :\n");
                String s = str.nextLine();
                Change_email(s);
                System.out.println("Email is changed successfully.");
            }
        } else if (register_button == 2) {
            Delete_account();
        }
    }
}


