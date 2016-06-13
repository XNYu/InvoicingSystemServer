package data.salesdata;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.*;

import PO.CustomerPO;
import PO.SalesPO;
import PO.UserPO;
import dataservice.salesDataService.SalesDataService;
public class SalesData extends UnicastRemoteObject implements SalesDataService{
	CustomerPO po;
	ArrayList<SalesPO> list;
	
	public SalesData() throws RemoteException {
		super();
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public void saveSales(){
		try {  
			File file=new File("Data/salesdata.ser");
			if (!file.exists()) {
				file.createNewFile();
				list=new ArrayList<SalesPO>();
			}
            ObjectOutputStream out  = new ObjectOutputStream(new FileOutputStream("Data/salesdata.ser"));  
            out.writeObject(list);   
            out.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}
	
	public void modifySales(SalesPO sales){
		try {
			if(sales.getType().equals("Sales")){
				showSales();
			}else if(sales.getType().equals("Import")){
				showImport();
			}else if(sales.getType().equals("SalesReturn")){
				showSalesReturn();
			}else if(sales.getType().equals("ImportReturn")){
				showImportReturn();
			}
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(SalesPO po:list){
			if(po.getID().equals(sales.getID())){
				po.setExamine(sales.getExamine());
			}
		}
		if(sales.getType().equals("Sales")){
			saveSales();
		}else if(sales.getType().equals("Import")){
			saveImport();
		}else if(sales.getType().equals("SalesReturn")){
			saveSalesReturn();
		}else if(sales.getType().equals("ImportReturn")){
			saveImportReturn();
		}
	}
	public void saveSalesReturn(){
		try {  
			File file=new File("Data/salesreturndata.ser");
			if (!file.exists()) {
				file.createNewFile();
				list=new ArrayList<SalesPO>();
			}
            ObjectOutputStream out  = new ObjectOutputStream(new FileOutputStream("Data/salesreturndata.ser"));  
            out.writeObject(list);   
            out.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}
	
	public void saveImport(){
		try {  
			File file=new File("Data/importdata.ser");
			if (!file.exists()) {
				file.createNewFile();
				list=new ArrayList<SalesPO>();
			}
            ObjectOutputStream out  = new ObjectOutputStream(new FileOutputStream("Data/importdata.ser"));  
            out.writeObject(list);   
            out.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}
	
	public void saveImportReturn(){
		try {  
			File file=new File("Data/importsreturndata.ser");
			if (!file.exists()) {
				file.createNewFile();
				list=new ArrayList<SalesPO>();
			}
            ObjectOutputStream out  = new ObjectOutputStream(new FileOutputStream("Data/importreturndata.ser"));  
            out.writeObject(list);   
            out.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}
	
	@Override
	public SalesPO find(String ID) throws RemoteException {
		// TODO Auto-generated method stub
		String [] tmp=ID.split("-");
		if(tmp[0].equals("XSD")){
			showSales();
		}else if(tmp[0].equals("XSTHD")){
			showSalesReturn();
		}else if(tmp[0].equals("JHD")){
			showImport();
		}else if(tmp[0].equals("JHTHD")){
			showImportReturn();
		}
		if(list==null)
			return null;
		for(SalesPO p:list){
			if(p.getID().equals(ID)){
				return p;
			}
		}
		return null;
	}
	
	@Override
	public boolean add(SalesPO po) throws RemoteException {
		// TODO Auto-generated method stub
		String [] tmp=po.getID().split("-");
		if(tmp[0].equals("XSD")){
			showSales();
			list.add(po);
			saveSales();
		}else if(tmp[0].equals("XSTHD")){
			showSalesReturn();
			list.add(po);
			saveSalesReturn();
		}else if(tmp[0].equals("JHD")){
			showImport();
			list.add(po);
			saveImport();
		}else if(tmp[0].equals("JHTHD")){
			showImportReturn();
			list.add(po);
			saveImportReturn();
		}
		return true;
	}

	@Override
	public boolean del(SalesPO po) throws RemoteException {
		// TODO Auto-generated method stub
		/*SalesPO p=find(po.getID());
		String [] tmp=po.getID().split("-");
		if(tmp[0].equals("XSD")){
			showSales();
			list.add(po);
			saveSales();
		}else if(tmp[0].equals("XSTHD")){
			showSalesReturn();
			list.add(po);
			saveSalesReturn();
		}else if(tmp[0].equals("JHD")){
			showImport();
			list.add(po);
			saveImport();
		}else if(tmp[0].equals("JHTHD")){
			showImportReturn();
			list.add(po);
			saveImportReturn();
		}*/
		return false;
	}

	@Override
	public boolean modify(SalesPO po) throws RemoteException {
		// TODO Auto-generated method stub
		String [] tmp=po.getID().split("-");
		if(tmp[0].equals("XSD")){
			showSales();
			int i=0;
			for(SalesPO p:list){
				if(p.getID().equals(po.getID())){
					System.out.println(po.getID());
					list.set(i, po);
					saveSales();
					return true;
				}
				++i;
			}
			saveSales();
		}else if(tmp[0].equals("XSTHD")){
			showSalesReturn();
			int i=0;
			for(SalesPO p:list){
				if(p.getID().equals(po.getID())){
					System.out.println(po.getID());
					list.set(i, po);
					saveSalesReturn();
					return true;
				}
				++i;
			}
			saveSalesReturn();
		}else if(tmp[0].equals("JHD")){
			showImport();
			int i=0;
			for(SalesPO p:list){
				if(p.getID().equals(po.getID())){
					System.out.println(po.getID());
					list.set(i, po);
					saveImport();
					return true;
				}
				++i;
			}
			saveImport();
		}else if(tmp[0].equals("JHTHD")){
			showImportReturn();
			int i=0;
			for(SalesPO p:list){
				if(p.getID().equals(po.getID())){
					System.out.println(po.getID());
					list.set(i, po);
					saveImportReturn();
					return true;
				}
				++i;
			}
			saveImportReturn();
		}
		
		return false;
	}

	@Override
	public boolean init() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<SalesPO> showImport() throws RemoteException {
		// TODO Auto-generated method stub
		try{  
			File file=new File("Data/importdata.ser");
			if (!file.exists()) {
				file.createNewFile();
				list=new ArrayList<SalesPO>();
				saveImport();
			}else{
				ObjectInputStream in = new ObjectInputStream(new FileInputStream("Data/importdata.ser"));  
            	list = (ArrayList<SalesPO>) in.readObject();   
			}
        }catch(FileNotFoundException e){  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (ClassNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
		return list;
		
	}

	@Override
	public ArrayList<SalesPO> showImportReturn() throws RemoteException {
		// TODO Auto-generated method stub
		try{  
			File file=new File("Data/importreturndata.ser");
			if (!file.exists()) {
				file.createNewFile();
				list=new ArrayList<SalesPO>();
				saveImportReturn();
			}else{
				ObjectInputStream in = new ObjectInputStream(new FileInputStream("Data/importreturndata.ser"));  
            	list = (ArrayList<SalesPO>) in.readObject();   
			}
        }catch(FileNotFoundException e){  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (ClassNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
		return list;
	}

	@Override
	public ArrayList<SalesPO> showSales() throws RemoteException {
		// TODO Auto-generated method stub
		try{  
			File file=new File("Data/salesdata.ser");
			if (!file.exists()) {
				file.createNewFile();
				list=new ArrayList<SalesPO>();
				saveSales();
			}else{
				
				ObjectInputStream in = new ObjectInputStream(new FileInputStream("Data/salesdata.ser"));  
            	list = (ArrayList<SalesPO>) in.readObject();   
			}
        }catch(FileNotFoundException e){  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (ClassNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
		return list;
	}

	@Override
	public ArrayList<SalesPO> showSalesReturn() throws RemoteException {
		// TODO Auto-generated method stub
		try{  
			File file=new File("Data/salesreturndata.ser");
			if (!file.exists()) {
				file.createNewFile();
				list=new ArrayList<SalesPO>();
				saveSalesReturn();
			}else{
				ObjectInputStream in = new ObjectInputStream(new FileInputStream("Data/salesreturndata.ser"));  
            	list = (ArrayList<SalesPO>) in.readObject();   
			}
        }catch(FileNotFoundException e){  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (ClassNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
		return list;
	}

	@Override
	public int getNumOfJHD(String date) throws RemoteException {
		// TODO Auto-generated method stub
		showImport();
	    if(list.size()==0){
	    	return 1;
	    }
	    SalesPO p=list.get(list.size()-1);
		String [] tmp=p.getID().split("-");
		 String d=tmp[1];
	    if(!d.equals(date)){
	    	return 1;
	    }
	    return Integer.parseInt(tmp[2])+1;
	}

	@Override
	public int getNumOfJHTHD(String date) throws RemoteException {
		// TODO Auto-generated method stub
		showImportReturn();
	    if(list.size()==0){
	    	return 1;
	    }
	    SalesPO p=list.get(list.size()-1);
		String [] tmp=p.getID().split("-");
		
		String d=tmp[1];
	    if(!d.equals(date)){
	    	return 1;
	    }
	    return Integer.parseInt(tmp[2])+1;
	}

	@Override
	public int getNumOfXSD(String date) throws RemoteException {
		// TODO Auto-generated method stub
		showSales();
		Date dt=new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");   
	    
	    if(list.size()==0){
	    	return 1;
	    }
	    SalesPO p=list.get(list.size()-1);
		String [] tmp=p.getID().split("-");
		String d=tmp[1];
	    if(!d.equals(date)){
	    	return 1;
	    }
	    return Integer.parseInt(tmp[2])+1;
	}

	@Override
	public int getNumOfXSTHD(String date) throws RemoteException {
		// TODO Auto-generated method stub
		showSalesReturn();
	    if(list.size()==0){
	    	return 1;
	    }
	    SalesPO p=list.get(list.size()-1);
		String [] tmp=p.getID().split("-");
		String d=tmp[1];
	    if(!d.equals(date)){
	    	return 1;
	    }
	    return Integer.parseInt(tmp[2])+1;
	}

	@Override
	public boolean finish() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void delAll() throws RemoteException {
		// TODO Auto-generated method stub
		list=new ArrayList<SalesPO>();
		saveSales();
		saveSalesReturn();
		saveImport();
		saveImportReturn();
	}

}
