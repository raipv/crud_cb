import java.sql.*;

public class DatabaseConnection {

    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3306/crud_db"; // Adjust if necessary
    private static final String USER = "root"; // Replace with your MySQL username
    private static final String PASSWORD = "990190"; // Replace with your MySQL password

    // Method to establish a connection
    public static Connection connect() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connection successful!");
            return connection;
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            throw e;
        }
    }

    // Method to insert a user into the database
    public static void insertUser(Connection connection, String name, String email, int age) {
        String sql = "INSERT INTO users (name, email, age) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);  // Set name
            statement.setString(2, email); // Set email
            statement.setInt(3, age);      // Set age

            int rowsAffected = statement.executeUpdate(); // Execute the insert query
            if (rowsAffected > 0) {
                System.out.println("User inserted successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error while inserting user: " + e.getMessage());
        }
    }

    // Method to retrieve all users from the database
    public static void getUsers(Connection connection) {
        String sql = "SELECT * FROM users";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                int age = resultSet.getInt("age");

                System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email + ", Age: " + age);
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving users: " + e.getMessage());
        }
    }

    // Method to update a user's details based on email
    public static void updateUser(Connection connection, String email, String newName, int newAge) {
        String sql = "UPDATE users SET name = ?, age = ? WHERE email = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newName);  // Set new name
            statement.setInt(2, newAge);      // Set new age
            statement.setString(3, email);    // Set email (where to update)

            int rowsAffected = statement.executeUpdate(); // Execute the update query
            if (rowsAffected > 0) {
                System.out.println("User updated successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error while updating user: " + e.getMessage());
        }
    }

    // Method to delete a user from the database by email
    public static void deleteUser(Connection connection, String email) {
        String sql = "DELETE FROM users WHERE email = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);  // Set email to delete

            int rowsAffected = statement.executeUpdate(); // Execute the delete query
            if (rowsAffected > 0) {
                System.out.println("User deleted successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error while deleting user: " + e.getMessage());
        }
    }

    // Main method to test CRUD operations
    public static void main(String[] args) {
        try (Connection connection = connect()) {
            // Test Create
            insertUser(connection, "Alice Brown", "alice.brown@example.com", 30);

            // Test Read
            getUsers(connection); 


            // // Test Update
            updateUser(connection, "alice.brown@example.com", "Alice Johnson", 32);

            // // Test Delete
            deleteUser(connection, "alice.brown@example.com");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
