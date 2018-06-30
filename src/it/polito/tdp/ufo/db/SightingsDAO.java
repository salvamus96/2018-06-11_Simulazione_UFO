package it.polito.tdp.ufo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.ufo.model.AnnoCount;
import it.polito.tdp.ufo.model.Arco;
import it.polito.tdp.ufo.model.Sighting;

public class SightingsDAO {
	
	public List<Sighting> getSightings() {
		String sql = "SELECT * FROM sighting" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Sighting> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				list.add(new Sighting(res.getInt("id"),
						res.getTimestamp("datetime").toLocalDateTime(),
						res.getString("city"), 
						res.getString("state"), 
						res.getString("country"),
						res.getString("shape"),
						res.getInt("duration"),
						res.getString("duration_hm"),
						res.getString("comments"),
						res.getDate("date_posted").toLocalDate(),
						res.getDouble("latitude"), 
						res.getDouble("longitude"))) ;
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	/**
	 * Restituisce la lista utile per popolare il menù a tendina
	 * @return
	 */
	public List<AnnoCount> getAnni() {

		String sql = "SELECT DISTINCT YEAR(datetime) as anno, COUNT(id) as cnt " + 
					 "FROM sighting " +
					 "WHERE country = 'us' " +
					 "GROUP BY anno " + 
					 "ORDER BY anno ASC" ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<AnnoCount> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) 
									// conversione da int in Year
				list.add(new AnnoCount(Year.of(res.getInt("anno")), res.getInt("cnt")));
			
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}

	/**
	 * Restituisce i vertici del grafo
	 * @param anno
	 * @return
	 */
	public List <String> getStati (Year anno){
		String sql = "SELECT DISTINCT state " + 
					 "FROM sighting\r\n" + 
					 "WHERE country = 'us' AND YEAR(datetime) = ? " + 
					 "ORDER BY state ASC" ;
	
		try {
			Connection conn = DBConnect.getConnection() ;
	
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno.getValue());
			
			List<String> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) 

				list.add(res.getString("state"));
			
			conn.close();
			return list ;
	
		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
		
	}
	
	/**
	 * Restituisce la lista completa di archi tra due stati
	 * @param anno
	 * @return
	 */
	public List<Arco> allEdge (Year anno) {
		String sql = "SELECT s1.state AS partenza, s2.state AS arrivo " + 
					 "FROM sighting s1, sighting s2 " + 
					 "WHERE YEAR(s1.datetime) = YEAR (s2.datetime) " + 
					 "		AND YEAR (s1.datetime) = ? " + 
					 "		AND s1.country = s2.country " + 
					 "		AND s1.country = 'us' " + 
					 "		AND s2.datetime > s1.datetime " + 
					 "		AND s1.state <> s2.state " + 
					 "GROUP BY s1.state, s2.state";
				
			try {
			Connection conn = DBConnect.getConnection() ;
	
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno.getValue());
			
			List<Arco> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) 
	
				list.add(new Arco(res.getString("partenza"), res.getString("arrivo")));
			
			conn.close();
			return list ;
	
		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
}
