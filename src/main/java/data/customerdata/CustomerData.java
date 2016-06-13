package data.customerdata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import dataservice.customerDataService.CustomerDataService;
import PO.CustomerPO;
import PO.PresentPO;
import PO.UserPO;

public class CustomerData extends UnicastRemoteObject implements CustomerDataService{
	CustomerPO po;
	ArrayList<CustomerPO> list;String path;
	ArrayList<PresentPO> presentlist;
	public CustomerData() throws RemoteException{
		super();
		// TODO Auto-generated constructor stub
		path="Data/customerdata.ser";
		File file = new File(path);
		try {
			if (!file.exists()) {
				file.createNewFile();
				list=new ArrayList<CustomerPO>();
				save();
			}else{
				show();
			}
			
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@Override
	public CustomerPO find(String ID) throws RemoteException {
		// TODO Auto-generated method stub
		for(CustomerPO p:list){

			if(p.getID().equals(ID)){
				
				return p;
			}
		}
		return null;
	}
	
	@Override
	public boolean add(CustomerPO po) throws RemoteException {
		for(CustomerPO p:list){
			if(p.getName().equals(po.getName())){
				return false;
			}
		}
		list.add(po);
		writeNum(readNum()+1);
		save();
		return true;
	}
	
	public boolean givePresent(PresentPO present){
		File file = new File("Data/presentdata.ser");
		try {
			if (!file.exists()) {
				file.createNewFile();
				presentlist=new ArrayList<PresentPO>();
				savePresent();
			}else{
				showPresent();
			}
			
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		presentlist.add(present);
		savePresent();
		writePresentNum(readPresentNum()+1);
		return true;
	}
	
	@Override
	public boolean del(String ID) throws RemoteException {
		// TODO Auto-generated method stub
		for(CustomerPO p:list){
			if(p.getID().equals(ID)){
				list.remove(p);
				save();
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean modify(CustomerPO po) throws RemoteException {
		// TODO Auto-generated method stub
		
		int i=0;
		for(CustomerPO p:list){
			if(p.getID().equals(po.getID())){
			
				list.set(i, po);
				save();
				return true;
			}
			++i;
		}
		return false;
	}

	@Override
	public ArrayList<CustomerPO> show() throws RemoteException {
		// TODO Auto-generated method stub
		
		 try{  
	            ObjectInputStream in = new ObjectInputStream(new FileInputStream("Data/customerdata.ser"));  
	            list = (ArrayList<CustomerPO>) in.readObject();
	   
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
	
	public ArrayList<PresentPO> showPresent() throws RemoteException {
		// TODO Auto-generated method stub
		
		 try{  
	            ObjectInputStream in = new ObjectInputStream(new FileInputStream("Data/presentdata.ser"));  
	            presentlist = (ArrayList<PresentPO>) in.readObject();
	   
	        }catch(FileNotFoundException e){  
	        	presentlist = new ArrayList<PresentPO>();
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } catch (ClassNotFoundException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
		return presentlist;
	}

	@Override
	public boolean init() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean finish() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void save(){
		 try {  
	            ObjectOutputStream out  = new ObjectOutputStream(new FileOutputStream("Data/customerdata.ser"));  
	            out.writeObject(list);   
	            out.close();  
	        } catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	}
	
	public void savePresent(){
		 try {  
			 ObjectOutputStream out  = new ObjectOutputStream(new FileOutputStream("Data/presentdata.ser"));  
			 out.writeObject(presentlist);
			 out.close();  
	     } catch (FileNotFoundException e) {  
	    	 e.printStackTrace();  
	     } catch (IOException e) {  
	    	 e.printStackTrace();  
	     }
		 
	}
	
	public int readPresentNum(){
		File f=new File("Data/presentnum.txt");
		
		try {
			if(!f.exists()){
				f.createNewFile();
				writePresentNum(0);
				return 0;
			}else{
				BufferedReader br=new BufferedReader(new FileReader(f));
				int num=Integer.parseInt(br.readLine());
				br.close();
				return num;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public void writePresentNum(int num){
		File f=new File("Data/presentnum.txt");
		try {
			if(!f.exists()){
				f.createNewFile();
				BufferedWriter bw=new BufferedWriter(new FileWriter(f));
				bw.write(0);
				bw.close();
			}else{
				BufferedWriter bw=new BufferedWriter(new FileWriter(f));
				bw.write(String.valueOf(num));
				bw.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public ArrayList<CustomerPO> vagueFind(String str) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<CustomerPO> returnList=new ArrayList<CustomerPO>();
		for(CustomerPO p:list){
			if(p.getID().contains(str)||p.getName().contains(str)){
				returnList.add(p);
				
			}
		}
		return returnList;
	}
	
	public int readNum(){
		File f=new File("Data/customernum.txt");
		
		try {
			if(!f.exists()){
				f.createNewFile();
				writeNum(0);
				return 0;
			}else{
				BufferedReader br=new BufferedReader(new FileReader(f));
				int num=Integer.parseInt(br.readLine());
				br.close();
				return num;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public void writeNum(int num){
		File f=new File("Data/customernum.txt");
		try {
			if(!f.exists()){
				f.createNewFile();
				BufferedWriter bw=new BufferedWriter(new FileWriter(f));
				bw.write(0);
				bw.close();
			}else{
				BufferedWriter bw=new BufferedWriter(new FileWriter(f));
				bw.write(String.valueOf(num));
				bw.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void delAll() throws RemoteException {
		// TODO Auto-generated method stub
		writeNum(0);
		list.clear();
		save();
	}

	@Override
	public boolean modifyPresent(PresentPO po) throws RemoteException {
		// TODO Auto-generated method stub
		int i=0;
		for(PresentPO p:presentlist){
			if(p.getID().equals(po.getID())){
				presentlist.set(i, po);
				savePresent();
				return true;
			}
			++i;
		}
		return false;
	}
}
