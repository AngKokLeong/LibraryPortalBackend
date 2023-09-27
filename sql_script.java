import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class PostgreSQLJDBC {
   public static void main( String args[] ) {
      Connection c = null;
      Statement stmt = null;

      try {
         Class.forName("org.postgresql.Driver");
         c = DriverManager
            .getConnection("jdbc:postgresql://localhost:5432/reactlibrarydatabase",
            "postgres", "postgres");
         System.out.println("Opened database successfully");

         stmt = c.createStatement();
         String sql = "CREATE TABLE COMPANY " +
            "(ID INT PRIMARY KEY     NOT NULL," +
            " NAME           TEXT    NOT NULL, " +
            " AGE            INT     NOT NULL, " +
            " ADDRESS        CHAR(50), " +
            " SALARY         REAL)";

         ArrayList<String> sql_array_list = new ArrayList<String>();
         sql_array_list.add("DROP TABLE IF EXISTS review");
         sql_array_list.add("CREATE TABLE review (id SERIAL PRIMARY KEY , user_email varchar(45) DEFAULT NULL, date timestamp, rating numeric (3,2) DEFAULT NULL, book_id int, review_description text DEFAULT NULL)");
         sql_array_list.add("DROP TABLE IF EXISTS checkout");
         sql_array_list.add("CREATE TABLE checkout (id SERIAL PRIMARY KEY, user_email varchar(45) DEFAULT NULL,checkout_date varchar(45) DEFAULT NULL,return_date varchar(45) DEFAULT NULL,book_id int)");
         sql_array_list.add("DROP TABLE IF EXISTS messages");
         sql_array_list.add("CREATE TABLE messages (id SERIAL PRIMARY KEY , user_email varchar(45) DEFAULT NULL, title varchar(45) DEFAULT NULL,question text DEFAULT NULL, admin_email varchar(45) DEFAULT NULL, response text DEFAULT NULL, closed smallint DEFAULT 0)");
         sql_array_list.add("DROP TABLE IF EXISTS history");
         sql_array_list.add("CREATE TABLE history (id SERIAL PRIMARY KEY ,user_email varchar(45) DEFAULT NULL, checkout_date varchar(45) DEFAULT NULL, returned_date varchar(45) DEFAULT NULL, title varchar(45) DEFAULT NULL, author varchar(45) DEFAULT NULL, description text DEFAULT NULL, img bytea)");

         for(String query: sql_array_list){
            stmt.executeUpdate(query);
         }

         stmt.close();
         c.close();
      } catch ( Exception e ) {
         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
         System.exit(0);
      }
      System.out.println("Table created successfully");
   }
}

