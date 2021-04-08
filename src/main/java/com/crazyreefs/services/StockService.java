package com.crazyreefs.services;

import com.crazyreefs.beans.Stock;
import com.crazyreefs.data.StockDAO;

import java.util.Set;

public class StockService {

    private StockDAO stockDAO;

    public StockService(StockDAO sd) { stockDAO = sd; }

    // Create
    public int addNewStockItem(Stock s) {
        return stockDAO.createNewStockItem(s);
    }

    // Read
    public Stock getStockByName(String name) { return stockDAO.findStockByName(name); }

    public Stock getStockByID(int id) { return stockDAO.findItemByStockId(id); }

    public Set<Stock> getStock() { return stockDAO.findItems();  }

    // Update
    public void updateStock(Stock s) {
        stockDAO.updateStockItem(s);
    }

    // Delete
    public void deleteStock(Stock s) {
        stockDAO.deleteStockItem(s);
    }
}
