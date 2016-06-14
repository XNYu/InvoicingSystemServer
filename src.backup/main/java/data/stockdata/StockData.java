package data.stockdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Calendar;

import utility.DateHelper;

import PO.*;
import dataservice.stockDataService.StockDataService;


public class StockData extends UnicastRemoteObject implements StockDataService , Serializable{
	private static final long serialVersionUID = 1L;
	BatchID batchID ;

	ArrayList<StockPO> stoList ;
	ArrayList<StockPO> showStoList;
	
	
	public StockData() throws RemoteException{
		super();
		init();
	}
	//create a stock
	public boolean createStock(StockPO stoPO){
		System.out.println("yes");
		stoList.add(stoPO);
		save();saveBatchID();
		return true;
		
	}
	//init the data
	public ArrayList<StockPO> init() {
		File fuck = new File("Data/stockBatchID.ser");
		if(fuck.exists()){
			try {
				ObjectInputStream in = new ObjectInputStream(
						new FileInputStream("Data/stockBatchID.ser"));
				batchID =  (BatchID) in.readObject();
				in.close();
			} catch (FileNotFoundException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}else{
			batchID = new BatchID();
		}
		File fvck = new File("Data/stockData.ser");
		if(fvck.exists()){
        ObjectInputStream in2;
		try {
			in2 = new ObjectInputStream(
					new FileInputStream("Data/stockData.ser"));
			 stoList = (ArrayList<StockPO>) in2.readObject();  
		     in2.close();
		     return stoList;
		} catch ( IOException | ClassNotFoundException e) {

			e.printStackTrace();
		}  
		}else{
			stoList = new ArrayList<StockPO>();
		}
		return null;
	}

	//show stock in specific date
	public ArrayList<StockPO> showStock(String startStr, String endStr) {

		showStoList = new ArrayList<StockPO>();
		for(StockPO p: stoList){
			int date = Integer.parseInt(p.getBatch());
			int start = Integer.parseInt(startStr);
			int end = Integer.parseInt(endStr);
			if((date>=start)&&(date<=end)){
				showStoList.add(p);
			}
		}
		return showStoList;
	}
	//save the data
	public void save(){
		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(new FileOutputStream("Data/stockData.ser"));
			out.writeObject(stoList);   
	        out.close();
		} catch (IOException e) {
	
			e.printStackTrace();
		}  
        
	}
	//save batch id
	private void saveBatchID(){
		ObjectOutputStream out2;
		try {
			out2 = new ObjectOutputStream(new FileOutputStream("Data/stockBatchID.ser"));
			out2.writeObject(batchID);
	        out2.close();
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
	//auto-generated get a batch id
	public String getBatchID() {
		String s =  batchID.getBatchID();
		saveBatchID();
		return s;
	}

	//auto-generated get a batch id
    private class BatchID implements Serializable{
    	private static final long serialVersionUID = 1L;
		String id="000000";
    	String date;
    	DateHelper dh = new DateHelper();
		public BatchID(){
			date = dh.getDate();
		}
    	public String getBatchID(){
    		String today = dh.getDate();

			if(today.equals(date)){

				String str = String.valueOf(Integer.parseInt(id)+1);
	    		for(int i = 6-str.length() ; i > 0 ;i--){
	    			str = "0"+str;
	    		}
	    		id = str;
				return id;
			}else{

				date = today;
				id = "000000";
				return id;
			}
    	}
    	
	}
    //clear the data for junit test
    public void clear() {
    	stoList.clear();
    	batchID = new BatchID();
    	save();
    	saveBatchID();
    }

    
}
