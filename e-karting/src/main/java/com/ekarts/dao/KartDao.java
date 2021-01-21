package com.ekarts.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ekarts.dto.Kart;

public class KartDao {

	/*
	 * Llista tots els karts de la base de dades
	 * 
	 */
	public List<Kart> listar() {
		String SQL_SELECT = "SELECT krt_id, krt_name, krt_description " + " FROM kart";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Kart kart = null;
		List<Kart> karts = new ArrayList<>();
		
		try {
			conn = DBConnection.getConnection();
			stmt = conn.prepareStatement(SQL_SELECT);
			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("krt_id");
				String name = rs.getString("krt_name");
				String description = rs.getString("krt_description");
				
				kart = new Kart(id, name, description);
				karts.add(kart);
			}
		} catch (SQLException ex) {
			ex.printStackTrace(System.out);
		} finally {
			DBConnection.close(rs);
			DBConnection.close(stmt);
			DBConnection.close(conn);
		}
		return karts;
	}

	/*
	 * Recupera un kart a la base de dades segons el seu ID
	 * 
	 */
	public Kart findById(Kart kart) {
		String SQL_SELECT_BY_ID = "SELECT krt_id, krt_name, krt_description "
				+ " FROM kart WHERE krt_id = ?";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.getConnection();
			stmt = conn.prepareStatement(SQL_SELECT_BY_ID);
			stmt.setInt(1, kart.getId());
			rs = stmt.executeQuery();
			rs.absolute(1);// nos posicionamos en el primer registro devuelto

			String name = rs.getString("krt_name");
			String desc = rs.getString("krt_description");
			
			kart.setName(name);
			kart.setDescription(desc);
			

		} catch (SQLException ex) {
			ex.printStackTrace(System.out);
		} finally {
			DBConnection.close(rs);
			DBConnection.close(stmt);
			DBConnection.close(conn);
		}
		return kart;
	}

	/*
	 * Crea un kart a la base de dades
	 * 
	 */
	public int create(Kart kart) {
		String SQL_INSERT = "INSERT INTO kart(krt_name, krt_description) "
				+ " VALUES(?, ?)";
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {
			conn = DBConnection.getConnection();
			stmt = conn.prepareStatement(SQL_INSERT);
			stmt.setString(1, kart.getName());
			stmt.setString(2, kart.getDescription());

			rows = stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace(System.out);
		} finally {
			DBConnection.close(stmt);
			DBConnection.close(conn);
		}
		return rows;
	}

	/*
	 * Modifica un kart de la base de dades
	 * 
	 */
	public int update(Kart kart) {
		String SQL_UPDATE = "UPDATE kart "
				+ " SET krt_name=?, krt_description=? WHERE krt_id=?";
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {
			conn = DBConnection.getConnection();
			stmt = conn.prepareStatement(SQL_UPDATE);
			int i = 1;
			stmt.setString(i++, kart.getName());
			stmt.setString(i++, kart.getDescription());
			stmt.setInt(i++, kart.getId());

			rows = stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace(System.out);
		} finally {
			DBConnection.close(stmt);
			DBConnection.close(conn);
		}
		return rows;
	}

	/*
	 * Esborra un kart de la base de dades
	 * 
	 */
	public int delete(Kart kart) {
		String SQL_DELETE = "DELETE FROM kart WHERE krt_id = ?";
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {
			conn = DBConnection.getConnection();
			stmt = conn.prepareStatement(SQL_DELETE);
			stmt.setInt(1, kart.getId());
			rows = stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace(System.out);
		} finally {
			DBConnection.close(stmt);
			DBConnection.close(conn);
		}
		return rows;
	}

}
