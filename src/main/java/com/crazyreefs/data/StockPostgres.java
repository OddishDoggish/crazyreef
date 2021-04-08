package com.crazyreefs.data;

import com.crazyreefs.beans.Stock;
import com.crazyreefs.beans.User;
import com.crazyreefs.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

public class StockPostgres implements StockDAO {

	private ConnectionUtil cu = ConnectionUtil.getConnectionUtil();

	@Override
	public int createNewStockItem(Stock s) {
		Integer id = 0;
		
		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			String sql = "INSERT INTO inventory VALUES "
					+ "(default, ?, ?, ?, ?, ?)";
			String[] keys = {"id"};
			PreparedStatement pstmt = conn.prepareStatement(sql, keys);
			pstmt.setString(1, s.getName());

			
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			
			if (rs.next()) {
				id = rs.getInt(1);
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		s.setStockId(id);
		return id;
	}

	@Override
	public Set<Stock> findItems() {
		
		HashSet<Stock> stocks = null;
		
		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			String sql = "SELECT * FROM inventory";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			
			stocks = new HashSet<>();
			
			while(rs.next()) {
				Stock s = new Stock();
				s.setStockId(rs.getInt("id"));
				s.setName(rs.getString("name"));

				stocks.add(s);
				
			}
			
		} catch (Exception e) {
				e.printStackTrace();
				}
		
		return stocks;
	}

	@Override
	public Stock findStockByName(String name) {
		Stock s = null;
		
		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			String sql = "SELECT * FROM inventory " +
					"WHERE name = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,name);
			
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				s.setStockId(rs.getInt("id"));
				s.setName(rs.getString("name"));

			}
			
		} catch (Exception e) {
				e.printStackTrace();
				}
		
		return s;
	}
	
	@Override
	public Stock findItemByStockId(int stockId) {
		Stock s = null;
		
		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			String sql = "SELECT * FROM inventory " +
					"WHERE id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,stockId);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				s = new Stock();
				s.setStockId(rs.getInt("id"));
				s.setName(rs.getString("name"));
			}
			
		} catch (Exception e) {
				e.printStackTrace();
				}
		
		return s;
	}

	@Override
	public void updateStockItem(Stock s) {
		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			String sql = "UPDATE inventory SET username = ?, passwd = ?, " +
					"first_name = ?, last_name = ?, email = ? " +
					"WHERE user_id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, s.getName());

			
			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteStockItem(Stock s) {
		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			String sql = "DELETE FROM inventory " +
					"WHERE id = ?";
				
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, s.getStockId());
			
			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}				
	}

}
