package database;

/*
-- Creare l'utente (se non esiste)
CREATE USER 'utprova'@'%' IDENTIFIED BY 'pwprova';

-- Assegnare i permessi
GRANT ALL PRIVILEGES ON sys.* TO 'utprova'@'%';

-- Ricaricare i privilegi
FLUSH PRIVILEGES;
*/

import java.util.Scanner;
import java.sql.*;

public class DataBase {
        public static void visualTab(Connection c)throws SQLException{
        Statement stmt = null;
        ResultSet rs = null;
        stmt = c.createStatement();
        rs = stmt.executeQuery("SELECT * FROM prodotto1");
        while(rs.next()){
            System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString("descr")+" "+rs.getString("prezzo"));
        }
        stmt.close();
    }
    
    public static int insertProdotto(Connection c, String nome, String descr, double prezzo)throws SQLException{
        int r;
        String insertTableSQL = "INSERT INTO prodotto1 (nome,descr,prezzo) values (?,?,?)";
        PreparedStatement ps = c.prepareStatement(insertTableSQL);//PreparedStatement obbligatorio
        ps.setString(1, nome);
        ps.setString(2, descr);
        ps.setDouble(3, prezzo);
        r = ps.executeUpdate();
        return r;
    }
    
    public static int modificaTab(Connection c, int id, double prezzo)throws SQLException{
        int i;
        String modificaTabSQL = "UPDATE prodotto1 SET prezzo = ? WHERE id = ?";
        PreparedStatement ps = c.prepareStatement(modificaTabSQL);
        ps.setDouble(1, prezzo);
        ps.setInt(2, id);
        i = ps.executeUpdate();
        return i;
    }
    
    public static void main(String[] args) {
        Scanner T = new Scanner(System.in);
        String nome, descr;
        double prezzo;
        int id;
        String scelta;
        //jdbc java database connection
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/sys?user=root&password="); //cambiare dati
            boolean cont = true;
            
            
            while(cont){
                System.out.println("0 Uscita  1 Visualizza \n2 Inserisci  3 Modifica");
                System.out.println("Scelta:");
                scelta = T.nextLine();
                
                switch (scelta) {
                    case "0" -> {
                        cont = false; break;
                    }
                    case "1" -> {
                        System.out.println("Visualizzo tabella:");
                        visualTab(conn);
                        break;
                    }
                    case "2" -> {
                            System.out.println("Inserisci il nome del prodotto");
                            nome = T.nextLine();
                            System.out.println("Inserisci la descrizione del prodotto");
                            descr = T.nextLine();
                            System.out.println("Inserisci il prezzo del prodotto (formato X,X)");
                            prezzo = T.nextDouble();
                            int ris = insertProdotto(conn, nome, descr, prezzo);
                            if(ris==1){
                                System.out.println("Il prodotto è stato inserito, visualizzo tabella");
                                visualTab(conn);
                            }else if (ris==0){
                                System.out.println("Il prodotto non è stato inserito, non visualizzo tabella");
                            }
                            break;
                    }
                    case "3" -> {
                            System.out.println("Inserisci l'id del prodotto");
                            id = T.nextInt();
                            T.nextLine();
                            System.out.println("Inserisci il prezzo del prodotto da modificare");
                            prezzo = T.nextDouble();
                            int ris = modificaTab(conn, id, prezzo);
                            if(ris==1){
                                System.out.println("Il prodotto è stato modificato con successo, visualizzo tabella");
                                visualTab(conn);
                            }else if (ris==0){
                                System.out.println("Il prodotto non è stato modificato, non visualizzo tabella");
                            }
                            break;
                    }
                    default -> {
                        System.out.println("Inserisci i numeri predisposti");
                        break;
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: "+ex.getMessage());
            System.out.println("SQLState: "+ex.getSQLState());
            System.out.println("VendorError: "+ex.getErrorCode());
        }
    }
}    