package etlmain;

import java.io.*;
import java.sql.*;
import java.util.*;

public class ETLMain {
    static ArrayList<String> InformazioniTitoli;
    static Connection conn = null;
    private static String RUNLOG = "run.log";
    private static String ERRORLOG = "error.log";
    static FileWriter runLog = null;
    static FileWriter errorLog = null;
    
    /*  runLog = new FileWriter(RUNLOG ,true);
        errorLog = new FileWriter(ERRORLOG, true);*/
    
    public static void LeggiCSV(String fileName, String scelta) throws SQLException, IOException{
        String linea;
        BufferedReader bd = null;
        InformazioniTitoli = new ArrayList<>();
        errorLog = new FileWriter(ERRORLOG, true);
        try {
            bd = new BufferedReader(new FileReader(fileName));
            linea = bd.readLine();
            String[] ciao = linea.split(",");

            for (String ciao1 : ciao) {
                InformazioniTitoli.add(ciao1);
            }
            
            //solo leggere
            while(scelta.equals("ScriviCSV") && (linea = bd.readLine())!= null){
                String[] cmp = linea.split(",");
                for (String cmp1 : cmp) {
                    System.out.println(cmp1);
                }
            }
            
            //leggere e inserire nel DB
            while(scelta.equals("InserisciDB") && (linea = bd.readLine()) != null){
                String[] cmp = linea.split(",");
                aggiungiDB(conn, cmp);
            }
        } catch (FileNotFoundException ex) {
            errorLog.append("Errore: "+ex.toString()+"\n");
            errorLog.close();
        } 
    }
    
    public static boolean creaTabella(Connection c) throws IOException {
        runLog = new FileWriter(RUNLOG ,true);
        errorLog = new FileWriter(ERRORLOG, true);
        try {
            LeggiCSV("SCUANAGRAFESTAT20252620250901.csv", "");
            runLog = new FileWriter(RUNLOG ,true);
            errorLog = new FileWriter(ERRORLOG, true);
            Statement stmt = c.createStatement();
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS gestioneScuole ("
                    + InformazioniTitoli.get(0) +" VARCHAR(100), " +
                    InformazioniTitoli.get(1) +" VARCHAR(100), " +
                    InformazioniTitoli.get(2) +" VARCHAR(100), " +
                    InformazioniTitoli.get(3) +" VARCHAR(100), " +
                    InformazioniTitoli.get(4) +" VARCHAR(100), " +
                    InformazioniTitoli.get(5) +" VARCHAR(100), " +
                    InformazioniTitoli.get(6) +" VARCHAR(100), " +
                    InformazioniTitoli.get(7) +" VARCHAR(100), " +
                    InformazioniTitoli.get(8) +" VARCHAR(100), " +
                    InformazioniTitoli.get(9) +" VARCHAR(100), " +
                    InformazioniTitoli.get(10) +" VARCHAR(100), " +
                    InformazioniTitoli.get(11) +" VARCHAR(100), " +
                    InformazioniTitoli.get(12) +" VARCHAR(100), " +
                    InformazioniTitoli.get(13) +" VARCHAR(100), " +
                    InformazioniTitoli.get(14) +" VARCHAR(100), " +
                    InformazioniTitoli.get(15) +" VARCHAR(100), " +
                    InformazioniTitoli.get(16) +" VARCHAR(100), " +
                    InformazioniTitoli.get(17) +" VARCHAR(100), " +
                    InformazioniTitoli.get(18) +" VARCHAR(100), " +
                    InformazioniTitoli.get(19) +" VARCHAR(100))"
            );
            int righe = stmt.getResultSetType();
            runLog.append("Creazione Tabella righe: "+righe);
            runLog.close();
            return true;
        } catch (SQLException ex) {
            System.out.println("Errore creazione tabella ");
            errorLog.append("Errore: "+ ex.toString()+"\n");
            errorLog.close();
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
    
    public static void aggiungiDB(Connection c, String[] Dato) throws SQLException, IOException{
        runLog = new FileWriter(RUNLOG ,true);
        String InserisciScuola = "INSERT INTO gestioneScuole ("+
                InformazioniTitoli.get(0)+","+
                InformazioniTitoli.get(1)+","+
                InformazioniTitoli.get(2)+","+
                InformazioniTitoli.get(3)+","+
                InformazioniTitoli.get(4)+","+
                InformazioniTitoli.get(5)+","+
                InformazioniTitoli.get(6)+","+
                InformazioniTitoli.get(7)+","+
                InformazioniTitoli.get(8)+","+
                InformazioniTitoli.get(9)+","+
                InformazioniTitoli.get(10)+","+
                InformazioniTitoli.get(11)+","+
                InformazioniTitoli.get(12)+","+
                InformazioniTitoli.get(13)+","+
                InformazioniTitoli.get(14)+","+
                InformazioniTitoli.get(15)+","+
                InformazioniTitoli.get(16)+","+
                InformazioniTitoli.get(17)+","+
                InformazioniTitoli.get(18)+","+
                InformazioniTitoli.get(19)+") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = c.prepareStatement(InserisciScuola);
        for (int i = 0; i < Dato.length; i++) {
            if(Dato[i].equals("Non Disponibile")){
                Dato[i] = null;
            }
        }
        ps.setString(1, Dato[0]);
        ps.setString(2, Dato[1]);
        ps.setString(3, Dato[2]);
        ps.setString(4, Dato[3]);
        ps.setString(5, Dato[4]);
        ps.setString(6, Dato[5]);
        ps.setString(7, Dato[6]);
        ps.setString(8, Dato[7]);
        ps.setString(9, Dato[8]);
        ps.setString(10, Dato[9]);
        ps.setString(11, Dato[10]);
        ps.setString(12, Dato[11]);
        ps.setString(13, Dato[12]);
        ps.setString(14, Dato[13]);
        ps.setString(15, Dato[14]);
        ps.setString(16, Dato[15]);
        ps.setString(17, Dato[16]);
        ps.setString(18, Dato[17]);
        ps.setString(19, Dato[18]);
        ps.setString(20, Dato[19]);
        int righe = ps.executeUpdate();
        runLog.append("Inserite: "+righe+" righe");
        runLog.close();
    }
    
    public static void cancellaDati(Connection conn) throws SQLException, IOException{
        runLog = new FileWriter(RUNLOG ,true);
        String cancellaDati = "DELETE FROM gestioneScuole";
        PreparedStatement ps = conn.prepareStatement(cancellaDati);
        System.out.println("Dati Cancellati");
        int righe = ps.executeUpdate();
        runLog.write(" Cancellate: "+righe+" righe");
        runLog.close();
    }
    
    public static void main(String[] args) throws IOException {
        Scanner sc = null;
        String scelta;
        try {
            errorLog = new FileWriter(ERRORLOG, true);
            conn = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/sys?user=root&password="
            );
            if(!esiste(conn, "gestioneScuole")){
                creaTabella(conn);
                System.out.println("Tabella: gestioneScuole. Creata!");
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
        } catch (SQLException | IOException ex) {
            // Stampa l'errore a schermo così lo vedi subito in console!
            ex.printStackTrace();

            // Scrivi sul file di log
            if (errorLog != null) {
                errorLog.append("Errore: " + ex.toString() + "\n");
                errorLog.close();
            }
        }
    }   
}