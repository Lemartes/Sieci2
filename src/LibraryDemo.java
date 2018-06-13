import java.util.List;
import java.util.Scanner;

public class LibraryDemo {
    private static final String menuMSG =
            "Choose operation: \n" +
            "1 - Add \n" +
            "2 - Show all \n" +
            "3 - Delete \n" +
            "4 - Update \n"+
            "5 - Exit";
    private static final String titleReqMSG = "Enter a title:";
    private static final String authorReqMSG = "Enter a author:";
    private static final String isbnReqMSG = "Enter ISBN number:";
    private static final String idReqMSG = "Enter book id:";

    public static void main(String[] args) {
        BookDAO bookDAO = new BookDAO();
        Scanner scanner = new Scanner(System.in);
        boolean run = true;
        while (run) {
            System.out.println(menuMSG);
            switch (scanner.nextInt()) {
                case 1:
                    Book book = new Book();
                    scanner.nextLine();
                    System.out.println(titleReqMSG);
                    book.setTitle(scanner.nextLine());
                    System.out.println(authorReqMSG);
                    book.setAuthor(scanner.nextLine());
                    System.out.println(isbnReqMSG);
                    book.setIsbn(scanner.nextLine());
                    bookDAO.addBook(book);
                    System.out.println("Book added");
                    break;
                case 2:
                    List<Book> books = bookDAO.getAllBooks();
                    if(!books.isEmpty()) {
                        System.out.println("Books:");
                        books.forEach(e -> System.out.println(e.toString()));
                    }else {
                        System.out.println("No books in database");
                    }
                    break;
                case 3:
                    scanner.nextLine();
                    System.out.println(idReqMSG);
                    bookDAO.deleteBook(scanner.nextLong());
                    break;
                case 4:
                    Book bookUpdated = new Book();
                    scanner.nextLine();
                    System.out.println(idReqMSG);
                    long id = scanner.nextLong();
                    scanner.nextLine();
                    if(bookDAO.getBookById(id) != null) {
                        bookUpdated.setId(id);
                        System.out.println(titleReqMSG);
                        bookUpdated.setTitle(scanner.nextLine());
                        System.out.println(authorReqMSG);
                        bookUpdated.setAuthor(scanner.nextLine());
                        System.out.println(isbnReqMSG);
                        bookUpdated.setIsbn(scanner.nextLine());
                        bookDAO.updateBook(bookUpdated);
                        System.out.println("Book updated");
                    }else {
                        System.out.println("Book doesnt exist");
                    }
                    break;
                case 5:
                    bookDAO.close();
                    run = false;
                    break;
            }
        }
    }
}
