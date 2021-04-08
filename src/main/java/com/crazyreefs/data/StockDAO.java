package com.crazyreefs.data;

import java.util.Set;

import com.crazyreefs.beans.Stock;

public interface StockDAO {

    public int createNewStockItem(Stock s);
    public Set<Stock> findItems();
    public Stock findStockByName(String name);
    public Stock findStockByStockId(int stockId);
    public void updateInventory(Stock s);
    public void deleteStockItem(Stock s);

}
