 import java.sql.*;




public class BuildTable {

    protected Connection conn = null;
    private String conStringC;
    private String conStringU;
    private String conStringP;

    public BuildTable(){

    }

    public BuildTable(String conn, String user, String pass){
        conStringC = conn;
        conStringU = user;
        conStringP = pass;
    }


    protected void tClass(){
        try{
            Class.forName("org.postgresql.Driver");
        } catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    protected Connection getConn(){
        Connection temp = null;
        try{
           temp =  DriverManager.getConnection(conStringC, conStringU, conStringP );

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return temp;
    }

    protected Connection innitConn(){
        tClass();
        return getConn();
    }

    protected void updateTable(String sql){
        try {
            Statement stmn = null;
            stmn = conn.createStatement();
            stmn.executeUpdate(sql);
            CloseStatement(stmn);
            } catch(SQLException e){ System.out.println(e); }

    }

    protected void CloseStatement(Statement stmn) throws SQLException {
        stmn.close();
        conn.close();
        conn = null;
    }

    public void BuildDatabase(){
        BuildPlayers();
        FillPlayers();
        BuildBatting();
        FillBatting();
        BuildPitching();
        FillPitching();
        BuildTeams();
        FillTeams();
        BuildTeamYear();
        FillTeamYear();
        BuildPosition();
        FillPosition();
        BuildGames();
        FillGames();
    }

    public void BuildPlayers(){


       conn = innitConn();

        Statement stmn = null;
        String sql = "CREATE TABLE Players " +
                "(PlayerID TEXT, " +
                "PRIMARY KEY(PlayerID), " +
                "FirstN TEXT, " +
                "LastN TEXT, " +
                "BirthYear INT," +
                "BirthCounty TEXT, " +
                "BirthCity TEXT)";

        try {
            stmn = conn.createStatement();
            stmn.executeUpdate(sql);
            CloseStatement(stmn);
        } catch(SQLException e){ System.out.println(e); }


    }

    public void FillPlayers() {
        conn = innitConn();
        String sql = "INSERT INTO Players (PlayerID, firstn, lastn, birthyear, birthcountry, birthcity) " +
                "SELECT PlayerID, namefirst, namelast, birthyear, birthcountry, birthcity " +
                "FROM peoplesource1 p1 " +
                "WHERE p1.birthyear > 1950";
        updateTable(sql);
    }

    public void BuildBatting(){
        conn = innitConn();
        String sql = "CREATE TABLE HITTING_STATS " +
                "(PlayerId TEXT, " +
                "Year INT, " +
                "AtBatts INT, " +
                "Hits INT, " +
                "Runs INT, " +
                "HomeRuns Int, " +
                "WAR NUMERIC, " +
                "PRIMARY KEY (PlayerID, Year), " +
                "FOREIGN KEY (PlayerID) REFERENCES Players(PlayerID)) ";
        updateTable(sql);
    }
    public void FillBatting(){
        conn = innitConn();
        String sql = "INSERT INTO Hitting_Stats (PlayerId, year, atbatts, hits, runs, homeruns) " +
                "SELECT b.playerid, b.yearid, b.ab, b.h, b.r, b.hr " +
                "FROM battingsource b " +
                "INNER JOIN Players p ON b.playerid = p.playerid  " +
                "WHERE b.yearid > 1965 " +
                "ON CONFLICT ON CONSTRAINT hitting_stats_pkey DO NOTHING ";

        updateTable(sql);
    }

    public void BuildPitching(){
        conn = innitConn();
        String sql ="CREATE TABLE Pitching_Stats " +
                "(PlayerID TEXT, " +
                "Year INT, " +
                "Wins INT, " +
                "Losses INT, " +
                "Games INT, " +
                "ERA NUMERIC, " +
                "PRIMARY KEY (PlayerID, Year), " +
                "FOREIGN KEY (PlayerID) REFERENCES Players(PlayerID)) ";

        updateTable(sql);
    }

    public void FillPitching(){
        conn = innitConn();
        String sql ="INSERT INTO Pitching_Stats (PlayerID, Year, wins, losses, games, era) " +
                "SELECT b.playerid, b.yearid, b.w, b.l, b.g, b.era " +
                "FROM pitchingsource b INNER JOIN Players p ON b.playerid = p.playerid " +
                "WHERE b.yearid > 1965 " +
                "ON CONFLICT ON CONSTRAINT pitching_stats_pkey DO NOTHING ";
        updateTable(sql);
    }

    public void BuildTeams(){
        conn = innitConn();
        String sql ="CREATE TABLE Teams " +
                "(TeamId TEXT, " +
                "Location TEXT, " +
                "Name TEXT, " +
                "Conference TEXT, " +
                "PRIMARY KEY (TeamID)) ";
        updateTable(sql);

    }

    public void FillTeams(){
        conn = innitConn();
        String sql = "INSERT INTO Teams (Teamid, location, name, conference) " +
                "SELECT t.teamid, t.franchid, t.name, t.divid " +
                "FROM teamsource t " +
                "WHERE t.yearid = 2017 ";
        updateTable(sql);
    }

    public void BuildTeamYear(){
        conn = innitConn();
        String sql = "CREATE TABLE Team_Season " +
                "(Team TEXT, " +
                "Year INT, " +
                "Games INT, " +
                "Wins INT, " +
                "Losses INT, " +
                "Runs INt, " +
                "AtBatts INT, " +
                "bpName TEXT, " +
                "ERA NUMERIC, " +
                "PRIMARY KEY (Team, Year), " +
                "FOREIGN KEY (Team) REFERENCES Teams (teamid)) ";
        updateTable(sql);
    }

    public void FillTeamYear(){
        conn = innitConn();
        String sql = "INSERT INTO team_season (team, year, games, wins, losses, runs, atbatts, bpname, era) " +
                "SELECT t.teamid, t.yearid, t.g, t.w, t.l, t.r, t.ab, t.park, t.era " +
                "FROM  teamsource t INNER JOIN teams b ON t.teamid = b.teamid " +
                "WHERE t.yearid > 1965 " +
                "ON CONFLICT ON CONSTRAINT team_season_pkey DO NOTHING ";
        updateTable(sql);
    }

    public void BuildPosition(){
        conn = innitConn();
        String sql = "CREATE TABLE Position " +
                "(PlayerID TEXT, " +
                "Year INT, " +
                "Team TEXT, " +
                "gPlay INT, " +
                "gPitch INT, " +
                "gHit INT, " +
                "g1b INT, " +
                "g2b INT, " +
                "g3b INT, " +
                "gCatch INT, " +
                "gDesHit INT, " +
                "gOut INT, " +
                "PRIMARY KEY (PlayerID, Year, Team), " +
                "FOREIGN KEY (PlayerID) REFERENCES Players (PlayerID), " +
                "FOREIGN KEY (Team) REFERENCES Teams (teamid)) ";

        updateTable(sql);
    }

    public void FillPosition(){
        conn = innitConn();
        String sql = "INSERT INTO Position (PlayerID, Year, Team, gPlay, gPitch, gHit, g1b, g2b, g3b, gCatch, gDesHit, gOut) " +
                "SELECT  a.playerid, a.yearid, a.teamid, a.g_all, a.g_p, a.g_batting, a.g_1b, a.g_2b, a.g_3b, a.g_c, g_dh,  g_of " +
                "FROM players p INNER JOIN appearancesource a  ON p.playerid = a.playerid " +
                "INNER JOIN teams t ON a.teamid = t.teamid " +
                "WHERE a.yearid > 1965 " +
                "ON CONFLICT ON CONSTRAINT position_pkey DO NOTHING ";
        updateTable(sql);
    }

    public void BuildGames(){
        conn = innitConn();
        String sql = "CREATE TABLE Games " +
                "(date INT, " +
                "Team1 TEXT, " +
                "Team2 TEXT, " +
                "seriesNum INT, " +
                "t1Score INT, " +
                "t2Score INT, " +
                "PRIMARY KEY (Team1, Team2, date, SeriesNum), " +
                "FOREIGN KEY (Team1) REFERENCES teams (teamid), " +
                "FOREIGN KEY (Team2) REFERENCES teams (teamid)) ";
        updateTable(sql);
    }

    public void FillGames(){
        conn = innitConn();
        String sql = "INSERT INTO games (date, team1, team2, seriesNum, t1score, t2score) " +
                "SELECT a.date, a.home, a.visiting, a.gamenumworthless, a.homescore, a.visitingscore " +
                "FROM gamelogsource a JOIN teams ta ON  ta.teamid = a.home JOIN teams tb ON tb.teamid = a.visiting ";

        updateTable(sql);
    }




}
