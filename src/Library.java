import java.util.List;
import java.util.Scanner;

public class Library {
    private static final String menuMSG =
            "Wybierz opcję:\n" +
            "1 - Dodaj \n" +
            "2 - Pokaż wszystkie \n" +
            "3 - Usuń \n" +
            "4 - Zaktualizuj \n"+
            "5 - Wyjście";
    private static final String titleMSG = "Wprowadź tytuł:";
    private static final String authorMSG = "Wprowadź autora:";
    private static final String isbnMSG = "Wprowadź numer ISBN:";
    private static final String idMSG = "Wprowadź id:";

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
                    System.out.println(titleMSG);
                    book.setTitle(scanner.nextLine());
                    System.out.println(authorMSG);
                    book.setAuthor(scanner.nextLine());
                    System.out.println(isbnMSG);
                    book.setIsbn(scanner.nextLine());
                    bookDAO.addBook(book);
                    System.out.println("Książka dodana");
                    break;
                case 2:
                    List<Book> books = bookDAO.getAllBooks();
                    if(!books.isEmpty()) {
                        System.out.println("Książki:");
                        books.forEach(e -> System.out.println(e.toString()));
                    }else {
                        System.out.println("Nie znaleziono żadnej książki");
                    }
                    break;
                case 3:
                    scanner.nextLine();
                    System.out.println(isbnMSG);
                    bookDAO.deleteBook(scanner.nextLine());
                    System.out.println("Książka usunięta");
                    break;
                case 4:
                    Book bookUpdated = new Book();
                    scanner.nextLine();
                    System.out.println(isbnMSG);
                    String isbn = scanner.nextLine();
                    scanner.nextLine();
                    if(bookDAO.getBookByIsbn(isbn) != null) {
                        bookUpdated.setId(bookDAO.getBookByIsbn(isbn).getId());
                        System.out.println(titleMSG);
                        bookUpdated.setTitle(scanner.nextLine());
                        System.out.println(authorMSG);
                        bookUpdated.setAuthor(scanner.nextLine());
                        System.out.println(isbnMSG);
                        bookUpdated.setIsbn(scanner.nextLine());
                        bookDAO.updateBook(bookUpdated);
                        System.out.println("Książka zaktualizowana");
                    }else {
                        System.out.println("Nie znaleziono takiej książki");
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
