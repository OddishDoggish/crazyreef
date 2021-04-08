package com.crazyreefs.data;

import java.util.Set;

import com.crazyreefs.beans.Stock;

public interface StockDAO {

    public int createNewStockItem(Stock s);
    public Set<Stock> findItems();
    public Stock findStockByName(String name);
    public Stock findItemByStockId(int stockId);
    public void updateStockItem(Stock s);
    public void deleteStockItem(Stock s);

}
