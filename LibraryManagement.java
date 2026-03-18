import java.util.*;
class Book {
    private String title;
    private int totalCopies = 5;
    private int availableCopies;
    public Book(String title) {
        this.title = title;
        this.availableCopies = totalCopies;
    }
    public String getTitle() {
        return title;
    }
    public int getAvailableCopies() {
        return availableCopies;
    }
    public void reduceCopy() {
        if (availableCopies > 0) {
            availableCopies--;
        }
    }
    public void increaseCopy() {
        if (availableCopies < totalCopies) {
            availableCopies++;
        }
    }
}
class User {
    private int userId;
    private String name;
    private ArrayList<String> borrowedBooks = new ArrayList<>();
    public User(int userId, String name) {
        this.userId = userId;
        this.name = name;
    }
    public int getUserId() {
        return userId;
    }
    public String getName() {
        return name;
    }
    public ArrayList<String> getBorrowedBooks() {
        return borrowedBooks;
    }
}
public class LibraryManagement {
    static HashMap<Integer, User> users = new HashMap<>();
    static HashMap<String, Book> books = new HashMap<>();
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        books.put("java", new Book("Java"));
        books.put("python", new Book("Python"));
        books.put("c++", new Book("C++"));
        while (true) {
            System.out.println("----- Library Menu -----");
            System.out.println("1. Register User");
            System.out.println("2. Borrow Book");
            System.out.println("3. Return Book");
            System.out.println("4. View User Details");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    borrowBook();
                    break;
                case 3:
                    returnBook();
                    break;
                case 4:
                    viewUser();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    static void registerUser() {
        System.out.print("Enter User ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        if (users.containsKey(id)) {
            System.out.println("User already registered!");
            return;
        }
        System.out.print("Enter User Name: ");
        String name = sc.nextLine();
        users.put(id, new User(id, name));
        System.out.println("User Registered Successfully!");
    }
    static void borrowBook() {
        System.out.print("Enter User ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        if (!users.containsKey(id)) {
            System.out.println("User not registered!");
            return;
        }
        User u = users.get(id);
        if (u.getBorrowedBooks().size() >= 3) {
            System.out.println("You already borrowed 3 books!");
            return;
        }
        System.out.print("Enter Book Title: ");
        String title = sc.nextLine().toLowerCase(); 
        if (!books.containsKey(title)) {
            System.out.println("Book not found!");
            return;
        }
        Book b = books.get(title);
        long count = u.getBorrowedBooks()
                .stream()
                .filter(x -> x.equals(title))
                .count();
        if (count >= 2) {
            System.out.println("You cannot borrow more than 2 copies of same book!");
            return;
        }
        if (b.getAvailableCopies() == 0) {
            System.out.println("No copies available!");
            return;
        }
        b.reduceCopy();
        u.getBorrowedBooks().add(title);
        System.out.println("Book Borrowed Successfully!");
    }
    static void returnBook() {
        System.out.print("Enter User ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        if (!users.containsKey(id)) {
            System.out.println("User not found!");
            return;
        }
        User u = users.get(id);
        System.out.print("Enter Book Title to Return: ");
        String title = sc.nextLine().toLowerCase(); 
        if (!u.getBorrowedBooks().contains(title)) {
            System.out.println("You didn’t borrow this book!");
            return;
        }
        u.getBorrowedBooks().remove(title);
        books.get(title).increaseCopy();
        System.out.println("Book Returned Successfully!");
    }
    static void viewUser() {
        System.out.print("Enter User ID: ");
        int id = sc.nextInt();
        if (!users.containsKey(id)) {
            System.out.println("User not found!");
            return;
        }
        User u = users.get(id);
        System.out.println("--- User Details ---");
        System.out.println("Name: " + u.getName());
        System.out.println("Borrowed Books: " + u.getBorrowedBooks());
    }
}