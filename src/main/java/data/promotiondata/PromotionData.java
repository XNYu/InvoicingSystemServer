package data.promotiondata;

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
import java.text.NumberFormat;
import java.util.ArrayList;

import dataservice.promotionDataService.PromotionDataService;
import PO.PromotionPO;

public class PromotionData extends UnicastRemoteObject implements PromotionDataService,Serializable{
	
	PromotionPO po;
	ArrayList<PromotionPO> list;
	String path;
	int ID;
	
	public static void main(String[] args) throws RemoteException {
		PromotionData p = new PromotionData();
		ArrayList<PromotionPO> l = p.show();
		for(PromotionPO pp:l)
			System.out.println(pp.getID()+pp.getType()+pp.getTotalPrice());
	}
	public PromotionData() throws RemoteException{
		super();
		// TODO Auto-generated constructor stub
		path="Data/promotiondata.ser";
		File file = new File(path);
		try {
			if (!file.exists()) {
				file.createNewFile();
				list=new ArrayList<PromotionPO>();
				save();
			}else{
				show();
			}
			
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		File file2=new File("Data/promotionID.ser");
		try {
			if (!file2.exists()) {
				file2.createNewFile();
				ID=1;
				save();
			}else{
				ObjectInputStream in = new ObjectInputStream(new FileInputStream("Data/promotionID.ser"));
				ID=(int)in.readObject(); 
				in.close();
			}
			
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		save();
	}
	
	public String getID()throws RemoteException{
		NumberFormat formatter = NumberFormat.getNumberInstance();   
        formatter.setMinimumIntegerDigits(5);   
        formatter.setGroupingUsed(false);   
        String s= formatter.format(ID);
        return s;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<PromotionPO> show() throws RemoteException {
		path="Data/promotiondata.ser";
		File file = new File(path);
		try{  
			if (!file.exists()) {
				file.createNewFile();
				list=new ArrayList<PromotionPO>();
				save();
			}
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));  
            list = (ArrayList<PromotionPO>)in.readObject();    
            in.close();
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
	public boolean modify(PromotionPO po) throws RemoteException {
		boolean j=false;
		for(PromotionPO p:list){
			if(p.getID().equals(po.getID())){
				int i=list.indexOf(p);
				list.set(i, po);
				save();
				j=true;
				break;
			}
		}
		return j;
	}

	@Override
	public boolean add(PromotionPO pro) throws RemoteException{
		if(list!=null){
		for(PromotionPO p:list){
			if(p.getID().equals(pro.getID())){
				return false;
				
			}
		}}
		list.add(pro);
		ID++;
		save();
		return true;
	}

	@Override
	public boolean delete(PromotionPO po) throws RemoteException {
		ArrayList<PromotionPO> delList = new ArrayList<PromotionPO>();
		for(PromotionPO p:list){
			if(p.getID().equals(po.getID())){
				delList.add(p);
			}
		}
		list.removeAll(delList);
		save();
		return true;
	}

	@Override
	public PromotionPO search(String id) throws RemoteException {
		PromotionPO pro = new PromotionPO();
		for(PromotionPO p:list){
			if(p.getID().equals(id)){
				pro=p;
				break;
			}
		}
		return pro;
	}

	@Override
	public void finish() throws RemoteException{
		save();
	}
	
	public void save() throws RemoteException{
		 try {  
	            ObjectOutputStream out  = new ObjectOutputStream(new FileOutputStream("Data/promotiondata.ser"));  
	            out.writeObject(list);   
	            out.close();  
	        } catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
		 
		 try {  
	            ObjectOutputStream out2  = new ObjectOutputStream(new FileOutputStream("Data/promotionID.ser"));  
	            out2.writeObject(ID);   
	            out2.close();  
	        } catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	}

	@Override
	public boolean delAll() throws RemoteException{
		// TODO Auto-generated method stub
		list=new ArrayList<PromotionPO>();
		return false;
	}
	
	
}
