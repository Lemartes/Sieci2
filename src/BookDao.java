import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/Sieci2";
    private static final String USER = "Cibor";
    private static final String PASS = "Fresia123";
    private Connection connection;

    public BookDAO() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            System.out.println("brak nośnika");
        } catch (SQLException e) {
            System.out.println("problem z połączeniem");
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addBook(Book book) {
        final String query = "insert into books(Title, Author, ISBN) values(?, ?, ?)";
        try {
            PreparedStatement prepStmt = connection.prepareStatement(query);
            prepStmt.setString(1, book.getTitle());
            prepStmt.setString(2, book.getAuthor());
            prepStmt.setString(3, book.getIsbn());
            prepStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBook(String isbn) {
        final String query = "delete from books where ISBN = ?";
        try {
            PreparedStatement prepStmt = connection.prepareStatement(query);
            prepStmt.setString(1, isbn);
            prepStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBook(Book book) {
        final String query = "update books set title = ?, author = ?, isbn = ? where id = ?";
        try {
            PreparedStatement prepStmt = connection.prepareStatement(query);
            prepStmt.setString(1, book.getTitle());
            prepStmt.setString(2, book.getAuthor());
            prepStmt.setString(3, book.getIsbn());
            prepStmt.setLong(4, book.getId());
            prepStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Book getBookByIsbn(String isbn) {
        final String query = "select id, title, author, isbn from books where isbn = ?";
        try {
            PreparedStatement prepStmt = connection.prepareStatement(query);
            prepStmt.setString(1, isbn);
            ResultSet resultSet = prepStmt.executeQuery();
            Book book = getBook(resultSet);
            if (book != null) return book;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Book getBookById(Long id) {
        final String query = "select id, title, author, isbn from books where id = ?";
        try {
            PreparedStatement prepStmt = connection.prepareStatement(query);
            prepStmt.setLong(1, id);
            ResultSet resultSet = prepStmt.executeQuery();
            Book book = getBook(resultSet);
            if (book != null) return book;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Book getBook(ResultSet resultSet) throws SQLException {
        if(resultSet.next()) {
            Book book = new Book();
            book.setId(resultSet.getLong("id"));
            book.setIsbn(resultSet.getString("isbn"));
            book.setAuthor(resultSet.getString("author"));
            book.setTitle(resultSet.getString("title"));
            return book;
        }
        return null;
    }

    public List<Book> getAllBooks() {
        final String query = "select * from books";
        try {
            PreparedStatement premtStmt = connection.prepareStatement(query);
            ResultSet resultSet = premtStmt.executeQuery();
            List<Book> books = new ArrayList<>();
            while (resultSet.next()){
                Book book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setIsbn(resultSet.getString("isbn"));
                book.setAuthor(resultSet.getString("author"));
                book.setTitle(resultSet.getString("title"));
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
