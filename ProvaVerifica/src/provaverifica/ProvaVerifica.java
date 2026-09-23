package provaverifica;

import java.io.*;
import java.sql.*;
import java.util.*;

public class ProvaVerifica {
    
    static ArrayList<String> InformazioniTitoli;
    static Connection conn = null;
    public static void LeggiCSV(String fileName, String scelta) throws SQLException, IOException{
        String linea;
        BufferedReader bd = null;
        InformazioniTitoli = new ArrayList<>();
        bd = new BufferedReader(new FileReader(fileName));
        linea = bd.readLine();
        String[] ciao = linea.split(";");
            
        for (String ciao1 : ciao) {
            InformazioniTitoli.add(ciao1);
        }
            
        //solo leggere
            while(scelta.equals("ScriviCSV") && (linea = bd.readLine())!= null){
                String[] cmp = linea.split(";");
                for (String cmp1 : cmp) {
                    System.out.println(cmp1);
                }
            }
            
            //leggere e inserire nel DB
            while(scelta.equals("InserisciDB") && (linea = bd.readLine()) != null){
                String[] cmp = linea.split(";");
                aggiungiDB(conn, cmp);
            }
        
        
    }
    
    public static boolean creaTabella(Connection c) throws IOException {
        try {
            LeggiCSV("SCUANAGRAFESTAT20252620250901.csv", "");
            Statement stmt = c.createStatement();
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS gestioneEventi ("
                    + InformazioniTitoli.get(0) +" VARCHAR(100), " +
                    InformazioniTitoli.get(1) +" VARCHAR(100), " +
                    InformazioniTitoli.get(2) +" VARCHAR(100), " +
                    InformazioniTitoli.get(3) +" VARCHAR(100), " +
                    InformazioniTitoli.get(4) +" VARCHAR(100), " +
                    InformazioniTitoli.get(5) +" VARCHAR(100))"
            );
            int righe = stmt.getResultSetType();
            return true;
        } catch (SQLException ex) {
            System.out.println("Errore creazione tabella ");
            // oppure log
            return false;
        }
    }
    
    public static boolean esiste(Connection c, String tab) throws SQLException {
        DatabaseMetaData md = c.getMetaData();
        ResultSet rs = md.getTables(null, null, "%", null);
        while (rs.next()) {
            String tbName = rs.getString(3);
            if (tab.equalsIgnoreCase(tbName)) {
                return true;
            }
        }
        return false;
    }
    
    public static void aggiungiDB(Connection c, String[] Dato) throws SQLException{
        String InserisciScuola = "INSERT INTO gestioneEventi ("+
                InformazioniTitoli.get(0)+","+
                InformazioniTitoli.get(1)+","+
                InformazioniTitoli.get(2)+","+
                InformazioniTitoli.get(3)+","+
                InformazioniTitoli.get(4)+","+
                InformazioniTitoli.get(5)+") values (?,?,?,?,?,?)";
        PreparedStatement ps = c.prepareStatement(InserisciScuola);
        
        
        ps.setString(1, Dato[0]);
        ps.setString(2, Dato[1]);
        ps.setString(3, Dato[2]);
        ps.setString(4, Dato[3]);
        ps.setString(5, Dato[4]);
        ps.setString(6, Dato[5]);
        ps.executeUpdate();
    }
    
    public static void cancellaDati(Connection conn) throws SQLException{
        String cancellaDati = "DELETE FROM gestioneEventi";
        PreparedStatement ps = conn.prepareStatement(cancellaDati);
        System.out.println("Dati Cancellati");
        ps.executeUpdate();
    }
    
    public static void main(String[] args) throws IOException {
        Scanner sc = null;
        String scelta;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/sys?user=root&password="
            );
            if(!esiste(conn, "gestioneEventi")){
                creaTabella(conn);
                System.out.println("Tabella: gestioneEventi. Creata!");
            }else{
                System.out.println("Tabella gia' esistente");
            }
            sc = new Scanner(System.in);
            boolean caso = true;
            while(caso){
                System.out.println("Scegliere cosa fare oggi:");
                System.out.println("""
                                   0 uscire
                                   1 LeggiCSV
                                   2 inserisciDaCSVaDB
                                   3 cancellaDatiTabella""");
                scelta = sc.nextLine();
                
                switch (scelta) {
                    case "0" -> caso = false; 
                    case "1" -> {
                        LeggiCSV("SCUANAGRAFESTAT20252620250901.csv", "ScriviCSV");
                        break;
                    }
                    case "2" ->{
                        LeggiCSV("SCUANAGRAFESTAT20252620250901.csv", "InserisciDB");
                        System.out.println("Dati Inseriti nel DB");
                        break;
                    }
                    case "3" -> {
                        System.out.println("Sei sicuro di cancellare i dati della tabella? si/no");
                        scelta = sc.nextLine();
                        switch (scelta) {
                            case "si" -> cancellaDati(conn);
                            case "no" -> {
                                break;
                        }
                            default -> {
                                System.out.println("Inserisci si o no");
                                break;
                        }
                        }
                    }
                    default ->{
                        System.out.println("Inserisci qualcosa!");
                        break;
                    }
                        
                }
            }
        } catch (SQLException ex) {}
    }   
    
}
