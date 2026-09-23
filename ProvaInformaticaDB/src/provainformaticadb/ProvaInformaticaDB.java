package provainformaticadb;

import java.sql.*;
import java.time.LocalDate;
import java.sql.Date;
import java.time.format.DateTimeParseException;
import java.util.*;

public class ProvaInformaticaDB {
    
    public static boolean creaTabella(Connection c) {
        try {
            Statement stmt = c.createStatement();
            stmt.executeUpdate(
                    "CREATE TABLE prova ("
                    + "idp int(11) AUTO_INCREMENT primary key,"
                    + "nome varchar(50) NOT NULL,"
                    + "data_odierna DATE NOT NULL)"
            );
            return true;
        } catch (SQLException e) {
            System.out.println("Errore creazione tabella ");
            System.out.println(e);
            return false;
        }
    }
    
    public static int ProvaInsert(Connection conn, String nome, LocalDate dataOdierna) throws SQLException{
        int i;
        String Insert = "INSERT INTO prova (nome,data_odierna) VALUES (?,?)";
        PreparedStatement ps = conn.prepareStatement(Insert);
        ps.setString(1, nome);
        Date date = Date.valueOf(dataOdierna);
        ps.setDate(2, date);
        i = ps.executeUpdate();
        return i;
    }
    
    public static void ProvaSelect(Connection conn) throws SQLException{
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM prova");
        while(rs.next()){
            System.out.println("Idp: "+rs.getInt(1)+" Nome: "+rs.getString(2)+" Data: "+rs.getDate(3));
        }
    }
    
    public static int ProvaUpdate(Connection conn, String nome, int idp) throws SQLException{
        int i;
        String Update = "UPDATE prova SET nome=? WHERE idp=?";
        PreparedStatement ps = conn.prepareStatement(Update);
        ps.setString(1, nome);
        ps.setInt(2, idp);
        i = ps.executeUpdate();
        return i;
    }
    public static int ProvaDeleteId(Connection conn, int idp) throws SQLException{
        int i;
        String Update = "DELETE FROM prova WHERE idp=?";
        PreparedStatement ps = conn.prepareStatement(Update);
        ps.setInt(1, idp);
        i = ps.executeUpdate();
        return i;
    }
    
    public static int ProvaDeleteAll(Connection conn) throws SQLException{
        int i;
        String Update = "DELETE FROM prova";
        PreparedStatement ps = conn.prepareStatement(Update);
        i = ps.executeUpdate();
        return i;
    }
    
    public static void main(String[] args) {
        Connection conn = null;
        Scanner sc = new Scanner(System.in);
        int idp;
        String nome, DataStringa;
        LocalDate DataOdierna;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/sys?user=root&password=");
            System.out.println("Inserisci l'id di cancellazione tabella: ");
            idp = sc.nextInt();
            sc.nextLine();
            ProvaDeleteId(conn,idp);
            ProvaDeleteAll(conn);
        } catch (SQLException ex) {
            System.out.println("Errore: "+ex);
        }finally{
            sc.close();
        }
    }
    
}
