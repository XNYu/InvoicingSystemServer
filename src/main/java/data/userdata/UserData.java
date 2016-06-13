package data.userdata;

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

import PO.CustomerPO;
import PO.UserPO;
import dataservice.userDataService.UserDataService;

public class UserData extends UnicastRemoteObject implements UserDataService , Serializable{
	UserPO po;
	ArrayList<UserPO> list;String path;
	public UserData() throws RemoteException{
		super();
		
		// TODO Auto-generated constructor stub
		path="Data/userdata.ser";
		File file = new File(path);
		try {
			if (!file.exists()) {
				file.createNewFile();
				list=new ArrayList<UserPO>();
				save();
			}else{
				show();
			}
			
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		save();
	}
	@Override
	public UserPO find(String ID) throws RemoteException {
		// TODO Auto-generated method stub
		for(UserPO p:list){
			if(p.getID().equals(ID)){
				return p;
			}
		}
		return null;
	}

	@Override
	public boolean add(UserPO po) throws RemoteException {
		for(UserPO p:list){
			if(p.getID().equals(po.getID())){
				return false;
			}
		}
		list.add(po);
		save();
		return true;
	}

	@Override
	public boolean del(String ID) throws RemoteException {
		// TODO Auto-generated method stub
		for(UserPO p:list){
			if(p.getID().equals(ID)){
				list.remove(p);
				save();
				return true;
			}
		}
	
		return false;
	}

	@Override
	public boolean modify(UserPO po) throws RemoteException {
		// TODO Auto-generated method stub
		
		int i=0;
		for(UserPO p:list){
			if(p.getID().equals(po.getID())){
				System.out.println(po.getID());
				list.set(i, po);
				save();
				return true;
			}
			++i;
		}
		return false;
	}

	@Override
	public ArrayList<UserPO> show() throws RemoteException {
		// TODO Auto-generated method stub
		
		 try{  
	            ObjectInputStream in = new ObjectInputStream(new FileInputStream("Data/userdata.ser"));  
	            
	            list = (ArrayList<UserPO>) in.readObject();   
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
	            ObjectOutputStream out  = new ObjectOutputStream(new FileOutputStream("Data/userdata.ser"));  
	            out.writeObject(list);   
	            out.close();  
	        } catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	}
	@Override
	public void delAll() throws RemoteException {
		// TODO Auto-generated method stub
		list.clear();
		save();
	}
	
	public static void main(String[] args) throws RemoteException {
		ArrayList<UserPO> user = new ArrayList<UserPO> ();
		UserData ud = new UserData();
		user = ud.show();
		for(UserPO usp : user)
			System.out.println(usp.getID()+"+"+usp.getPassword());
	}

}
