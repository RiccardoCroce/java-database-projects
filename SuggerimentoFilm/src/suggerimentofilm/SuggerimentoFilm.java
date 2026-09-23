package suggerimentofilm;

import java.sql.*;
import java.util.*;

//tabella si chiama filmSuggeriti

public class SuggerimentoFilm {
    public static boolean creaTabella(Connection c) {
        try {
            Statement stmt = c.createStatement();
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS filmSuggeriti ("
                    + "titolo VARCHAR(100) NOT NULL UNIQUE,"
                    + "nomeAmico VARCHAR(100) NOT NULL,"
                    + "giornoSuggerimento VARCHAR(100) NOT NULL, "
                    + "dataVisione VARCHAR(100))"
            );
            return true;
        } catch (SQLException e) {
            System.out.println("Errore creazione tabella ");
            System.out.println(e);
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
    
    public static int AggiungiFilmSuggerito(Connection cn, String TitoloFilm, String nomeAmico, String dataSuggerimento, String dataVisione) throws SQLException{
        int i;
        String InserisciSuggerimento = "INSERT INTO filmSuggeriti (titolo,nomeAmico,giornoSuggerimento,dataVisione) values (?,?,?,?)";
        PreparedStatement ps = cn.prepareStatement(InserisciSuggerimento);
        ps.setString(1, TitoloFilm);
        ps.setString(2, nomeAmico);
        ps.setString(3, dataSuggerimento);
        if(dataVisione.equals("")){
            dataVisione = null;
            ps.setString(4, dataVisione);
        }else{
            ps.setString(4, dataVisione);
        }
        i = ps.executeUpdate();
        return i;
    }
    
    public static void VisualizzaElencoFilm(Connection cn)throws SQLException{
        Statement stmt = null;
        ResultSet rs = null;
        stmt = cn.createStatement();
        rs = stmt.executeQuery("SELECT * FROM filmSuggeriti");
        while(rs.next()){
            System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4));
        }
        stmt.close();
    }
    
    public static int aggiungiVisioneFilm(Connection cn, String Titolo, String dataVisione) throws SQLException{
        int i;
        String InserisciVisione = "UPDATE filmSuggeriti SET dataVisione = ? WHERE titolo = ?";
        PreparedStatement ps = cn.prepareStatement(InserisciVisione);
        ps.setString(1, dataVisione);
        ps.setString(2, Titolo);
        i = ps.executeUpdate();
        return i;
    }
    
    public static void visualizzaVisioneFilm(Connection cn)throws SQLException{
        Statement stmt = null;
        ResultSet rs = null;
        stmt = cn.createStatement();
        rs = stmt.executeQuery("SELECT * FROM filmSuggeriti");
        while(rs.next()){
            System.out.println("Film: "+ rs.getString(1)+", data: "+rs.getString(4));
        }
        stmt.close();
    }
    
    public static int cancellaFilm(Connection cn, String Titolo) throws SQLException{
        int i;
        String CancellaFilm = "DELETE FROM filmSuggeriti WHERE titolo = ?";
        PreparedStatement ps = cn.prepareStatement(CancellaFilm);
        ps.setString(1, Titolo);
        i = ps.executeUpdate();
        return i;
    }
    
    public static int modificaTitoloFilm(Connection cn, String TitoloDaCambiare, String Titolo) throws SQLException{
        int i;
        String CancellaFilm = "UPDATE filmSuggeriti SET titolo = ? WHERE titolo = ?";
        PreparedStatement ps = cn.prepareStatement(CancellaFilm);
        ps.setString(1, TitoloDaCambiare);
        ps.setString(2, Titolo);
        i = ps.executeUpdate();
        return i;
    }
    
    public static void main(String[] args) {
        String Titolo, nomeAmico, giornoSuggerimento, dataVisione;
        int risultatoOperazione;
        Connection conn = null;
        Scanner sc = new Scanner(System.in);
        String scegliere;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/sys?user=root&password="
            );
            if(!esiste(conn, "filmSuggeriti")){
                creaTabella(conn);
                System.out.println("Ho creato la tabella");
            }else{
                System.out.println("Tabella gia' esistente");
            }
            boolean Boole = true;
            while(Boole){
                System.out.println("Scegli tra questi");
                System.out.println("""
                                   0 esciElencoFilm
                                   1 aggiungiFilm 
                                   2 visualizzaFilm 
                                   3 aggiungiVisioneFilm
                                   4 visualizzaVisioneFilm 
                                   5 cancellaFilm 
                                   6 modificaTitoloFilm""");
                scegliere = sc.nextLine();
                switch(scegliere){
                    case "0" -> Boole = false;
                    case "1" -> {
                        System.out.println("Inserisci il titolo del film suggerito: ");
                        Titolo = sc.nextLine();
                        System.out.println("Inserisci il nome dell'amico che ha suggerito il film: ");
                        nomeAmico = sc.nextLine();
                        System.out.println("Inserisci il giorno in cui ti ha suggerito il film: ");
                        giornoSuggerimento = sc.nextLine();
                        System.out.println("Inserisci il giorno in cui hai visto il film: ");
                        dataVisione = sc.nextLine();
                        risultatoOperazione = AggiungiFilmSuggerito(conn, Titolo, nomeAmico, giornoSuggerimento, dataVisione);
                        if(risultatoOperazione==1){
                            System.out.println("Il suggerimento e' stato inserito, visualizzo tabella suggerimenti");
                            VisualizzaElencoFilm(conn);
                        }else if (risultatoOperazione==0){
                            System.out.println("Il suggerimento non e' stato inserito, non visualizzo tabella");
                        }
                        break;
                    }
                    case "2" -> {
                        System.out.println("Visualizzo elenco film suggeriti");
                        VisualizzaElencoFilm(conn);
                        break;
                    }
                    case "3" -> {
                        System.out.println("Inserisci il titolo del film visto");
                        Titolo = sc.nextLine();
                        System.out.println("Inserisci la data della visione del film");
                        dataVisione = sc.nextLine();
                        risultatoOperazione = aggiungiVisioneFilm(conn, Titolo, dataVisione);
                        if(risultatoOperazione==1){
                            System.out.println("Il suggerimento e' stato inserito, visualizzo tabella suggerimenti");
                            VisualizzaElencoFilm(conn);
                        }else if (risultatoOperazione==0){
                            System.out.println("Il suggerimento non e' stato inserito, non visualizzo tabella");
                        }
                        break;
                    }
                    case "4" -> {
                        System.out.println("Visualizzo i film visti(quelli vuoti sono nulli)");
                        visualizzaVisioneFilm(conn);
                        break;
                    }
                    case "5" -> {
                        System.out.println("Inserisci il titolo del film da rimuovere");
                        Titolo = sc.nextLine();
                        risultatoOperazione = cancellaFilm(conn, Titolo);
                        if(risultatoOperazione==1){
                            System.out.println("Il suggerimento e' stato inserito, visualizzo tabella suggerimenti");
                            VisualizzaElencoFilm(conn);
                        }else if (risultatoOperazione==0){
                            System.out.println("Il suggerimento non e' stato inserito, non visualizzo tabella");
                        }
                        break;
                    }
                    case "6" -> {
                        System.out.println("Inserisci il titolo del film da cambiare");
                        Titolo = sc.nextLine();
                        System.out.println("Inserisci il titolo del nuovo film");
                        String TitoloCambiato = sc.nextLine();
                        risultatoOperazione = modificaTitoloFilm(conn, TitoloCambiato, Titolo);
                        if(risultatoOperazione==1){
                            System.out.println("Il suggerimento e' stato inserito, visualizzo tabella suggerimenti");
                            VisualizzaElencoFilm(conn);
                        }else if (risultatoOperazione==0){
                            System.out.println("Il suggerimento non e' stato inserito, non visualizzo tabella");
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
            System.out.println("Non ho trovato nessun database");
            System.out.println(ex);
        }
    }
}