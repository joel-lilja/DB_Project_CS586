import java.lang.reflect.AccessibleObject;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryClass extends BuildTable {

    ResultSet rs = null;

    public QueryClass() {

    }




    public void PrettyPrint(ResultSet rs) {
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int bound = rsmd.getColumnCount();
            for (int i = 1; i <= bound; ++i) {
            //System.out.print(rsmd.getColumnName(i) + "\t");
            System.out.printf("%-17.20s", rsmd.getColumnName(i) );
            }
            System.out.println();

            while(rs.next()){
                for(int i = 1; i <= bound; ++i){
                    String p = rs.getString(i);
                   // System.out.print(p + "\t");
                    System.out.printf("%-17.20s", p);
                }
                System.out.println();
            }




        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    protected void Query(String sql){
        conn = innitConn();
        try {
            Statement stmn = conn.createStatement();
            rs = stmn.executeQuery(sql);
            PrettyPrint(rs);
            CloseStatement(stmn);
        } catch (SQLException e){ System.out.println(e.getMessage());}
    }

    protected void AppQuery(String sql){
        System.out.print("The Query is: ");
        System.out.println(sql);
        Query(sql);
        System.out.println();
    }


    public void Query1(){
        String sql = "SELECT * FROM players WHERE birthyear > 1980";
        AppQuery(sql);
    }

    public void Query2(){
        String sql = "SELECT p1.era, p2.firstn, p2.lastn FROM pitching_stats p1 JOIN players p2 ON p1.playerid = p2.playerid " +
                "WHERE p1.era = (SELECT MIN(era) FROM pitching_stats)" +
                "AND p1.year = 2017";
        AppQuery(sql);
    }


    public void Query3(){
        String sql = "SELECT SUM(t1.homeruns) AS dingers, t2.firstn, t2.lastn " +
                "FROM hitting_stats t1 JOIN players t2 ON t1.playerid = t2.playerid " +
                "GROUP BY t2.firstn, t2.lastn " +
                "ORDER BY dingers DESC ";

        AppQuery(sql);
    }

    public void Query4(){
        String sql = "SELECT t1.name FROM teams t1 JOIN team_season t2 ON t1.teamid = t2.team " +
                "WHERE t2.year = 2017 AND t2.wins > t2.losses";
        AppQuery(sql);
    }

    public void Query5(){
        String sql = "SELECT t1.name FROM teams t1 JOIN team_season t2 ON t1.teamid = t2.team " +
                "WHERE t2.year = 2017 and t2.wins < 65";
        AppQuery(sql);
    }

    public void Query6(){
        String sql = "SELECT t1.firstn, t1.lastn, t2.hits FROM players t1 JOIN hitting_stats t2 ON t1.playerid = t2.playerid " +
                "WHERE t2.hits IN (SELECT MAX(hits) FROM hitting_stats WHERE year = 2017)";
        AppQuery(sql);
    }

    public void Query7(){
        String sql = "SELECT * FROM players t1 JOIN hitting_stats t2 ON t1.playerid = t2.playerid " +
                "WHERE t2.year = 2017 " +
                "ORDER BY t2.hits DESC " +
                "LIMIT 10 ";
        AppQuery(sql);

    }

    public void Query8(){
        String sql = "SELECT t2.firstn, t2.lastn, t1.g3b AS games " +
                "FROM position t1 JOIN players t2 ON t1.playerid = t2.playerid " +
                "WHERE t1.year = 2017 AND t1.team = 'LAA' AND t1.g3b != 0";
        AppQuery(sql);
    }

    public void Query9(){
        String sql = "SELECT * FROM games WHERE date > 20170000";
        AppQuery(sql);
    }

    public void Query10(){
        String sql = "SELECT pl.firstn, pl.lastn, po.gpitch AS games " +
                "FROM players pl JOIN position po ON pl.playerid = po.playerid " +
                "WHERE po.gpitch IN (SELECT MAX(gpitch) FROM position)";
        AppQuery(sql);
    }

    public void Query11(){
        String sql = "SELECT DISTINCT pl.firstn, pl.lastn, ps.era " +
                "FROM players pl JOIN pitching_stats ps ON pl.playerid = ps.playerid " +
                "WHERE era != 0 AND ps.games > 20 " +
                "ORDER BY ps.era " +
                "LIMIT 5";
        AppQuery(sql);
    }

    public void Query12(){
        String sql = "SELECT t.name, ts.wins " +
                "FROM teams t JOIN team_season ts ON ts.team = t.teamid " +
                "WHERE ts.wins > (SELECT AVG(wins) FROM team_season) AND ts.year = 2017 ";
        AppQuery(sql);
    }

    public void Query13(){
        String sql = "SELECT DISTINCT t.name " +
                "FROM teams t JOIN position p ON p.team = t.teamid JOIN players pl ON pl.playerid = p.playerid " +
                "WHERE pl.firstn = 'Ichiro' AND pl.lastn = 'Suzuki' ";
        AppQuery(sql);
    }

    public void Query14(){
        String sql = "SELECT t.name, ts.wins " +
                "FROM teams t JOIN team_season ts on ts.team = t.teamid " +
                "where ts.year = 2017 ";
        AppQuery(sql);
    }

    public void Query15(){

        System.out.println();
        System.out.println("2017 team stats");
        String sql1 = "SELECT * FROM team_season WHERE year = 2017 AND team = 'SEA'";
        AppQuery(sql1);

        System.out.println();
        System.out.println("HITTING STATS");

        String sql2 = "SELECT DISTINCT pl.firstn, pl.lastn,  hs.* " +
                "FROM hitting_stats hs JOIN position p ON p.playerid = hs.playerid JOIN players pl ON pl.playerid = hs.playerid " +
                "WHERE p.team = 'SEA' AND hs.year = 2017";
        AppQuery(sql2);

        System.out.println();
        System.out.println("PITCHING STATS");
        String sql3 = "SELECT DISTINCT pl.firstn, pl.lastn, ps.* " +
                "FROM pitching_stats ps JOIN position p ON p.playerid = ps.playerid JOIN players pl ON pl.playerid = ps.playerid " +
                "WHERE p.team = 'SEA' AND ps.year = 2017";
        AppQuery(sql3);
    }

    public void Query16(){
        String sql = "SELECT * FROM games g WHERE date > 20170000 AND date < 20180000 AND (g.team1 = 'SEA' OR g.team2 = 'SEA') ";
        AppQuery(sql);
    }

    public void Qeury17(){
        String sql = "SELECT * FROM games g WHERE t1score - t2score > 9 OR t2score - t1score > 9";
        AppQuery(sql);
    }

    public void Qeury18(){
        String sql = "SELECT pl.firstn, pl.lastn " +
                "FROM pitching_stats ps JOIN players pl ON pl.playerid = ps.playerid " +
                "ORDER BY ps.era DESC " +
                "LIMIT 5 ";
        AppQuery(sql);
    }

    public void Query19(){
        String sql = "SELECT COUNT(*) FROM games g WHERE g.seriesnum != 0 AND g.date > 20170000";
        AppQuery(sql);
    }

    public void Query20(){
        String sql = "SELECT * FROM players";
        AppQuery(sql);
    }

}



