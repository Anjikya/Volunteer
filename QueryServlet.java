package QueryServlet;

 

import java.io.IOException;

import java.io.PrintWriter;

import java.sql.*;

import java.sql.ResultSet;

import java.sql.SQLException;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;


public class QueryServlet extends HttpServlet {


    public void doPost(HttpServletRequest request,

                     HttpServletResponse response)

      throws ServletException, IOException {

    response.setContentType("text/html");

    PrintWriter out = response.getWriter();

    String docType =

      "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +

      "Transitional//EN\"\n";

    String title = "Search Results";

    out.print(docType +

              "<HTML>\n" +

              "<HEAD><TITLE>" + title + "</TITLE>" +

             "<link href=../styles/styles.css rel=\"stylesheet\" type=\"text/css\">\n" +

             "</HEAD>\n" +

             "<BODY BGCOLOR=\"#FDF5E6\"><CENTER>\n" +

             "<H1>Search Results</H1>\n");

    String driver = "sun.jdbc.odbc.JdbcOdbcDriver";

    String url = "jdbc:mysql://localhost/emergency";

    String userName = "root";

    String password = "appsum";

    //String isbn = request.getParameter("search");

    String volunteer_id = request.getParameter("volunterr_id");

    String first_name = request.getParameter("first_name");

    String middle_name = request.getParameter("middle_name");

    String last_name = request.getParameter("last_name");

	String mail_id = request.getParameter("mail_id");

    String phone_no = request.getParameter("phone_no");

    String last_updated = request.getParameter("last_updated");

    showTable(driver, url, userName, password, volunteer_id, first_name, middle_name, last_name,

            mail_id, phone_no, last_updated,out);

    out.println("</CENTER></BODY></HTML>");

  }

 

    public void showTable(String driver, String url,

                        String username, String password,String volunteer_id,

                        String first_name, String middle_name, String last_name, String mail_id,

                        String phone_no,String last_updated,

                        PrintWriter out) {

 

        try {

      // Load database driver if it's not already loaded.

      Class.forName(driver);

      // Establish network connection to database.

      Connection connection =

        DriverManager.getConnection(url, username, password);

      // Look up info about the database as a whole.

      DatabaseMetaData dbMetaData = connection.getMetaData();

      out.println("<UL>");

 

      String productName =

        dbMetaData.getDatabaseProductName();

 

      String productVersion =

        dbMetaData.getDatabaseProductVersion();

      out.println("  <LI><B>Database:</B> " + productName +

                  "  <LI><B>Version:</B> " + productVersion +

                  "</UL>");

      Statement statement = connection.createStatement();

      // Send query to database and store results.

 

      //String query = null;

 

 

       String query = "INSERT INTO Customers(volunteer_id,first_name,middle_name,lastName,mail_id,phone_no)"

               + "VALUES ("+volunteer_id+","+first_name+","+middle_name+","+last_name+","+mail_id+","+phone_no+","+last_updated+") ";

 

      ResultSet resultSet = statement.executeQuery(query);

      // Print results.

      out.println("<TABLE BORDER=1>");

      ResultSetMetaData resultSetMetaData =

        resultSet.getMetaData();

      int columnCount = resultSetMetaData.getColumnCount();

      out.println("<TR>");

      // Column index starts at 1 (a la SQL), not 0 (a la Java).

      for(int i=1; i <= columnCount; i++) {

        out.print("<TH>" + resultSetMetaData.getColumnName(i));

      }

      out.println();

      // Step through each row in the result set.

      while(resultSet.next()) {

        out.println("<TR>");

        // Step across the row, retrieving the data in each

        // column cell as a String.

        for(int i=1; i <= columnCount; i++) {
          out.print("<TD>" + resultSet.getString(i));

        }

        out.println();

      }

      out.println("</TABLE>");

      out.println("<div align=\"center\">\n"+

              "<a href=\"homepage3.jsp\"/>Please click here to go back to Homepage.</div>");

      connection.close();

    } catch(ClassNotFoundException cnfe) {

      System.err.println("Error loading driver: " + cnfe);

    } catch(SQLException sqle) {

      System.err.println("Error connecting: " + sqle);

    } catch(Exception ex) {

      System.err.println("Error with input: " + ex);

    }

  }

 
  private static void showResults(ResultSet results)

      throws SQLException {

    while(results.next()) {

      System.out.print(results.getString(1) + " ");

    }

    System.out.println();

  }

 
  private static void printUsage() {

    System.out.println("Usage: PreparedStatements host " +

                       "dbName username password " +

                       "vendor [print].");

  }
}