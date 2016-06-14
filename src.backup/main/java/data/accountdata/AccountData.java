package data.accountdata;

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
import PO.AccountPO;
import dataservice.accountDataService.AccountDataService;

public class AccountData extends UnicastRemoteObject implements AccountDataService,Serializable {
	/**
	 * account data层 
	 */
	private static final long serialVersionUID = -8667583047485384924L;
	AccountPO po;
	ArrayList<AccountPO> list;
	String path;
	public AccountData() throws RemoteException {
		super();
		path="Data/accountdata.ser";
		File file = new File(path);
		try {
			if (!file.exists()) {
				file.createNewFile();
				list=new ArrayList<AccountPO>();
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
	public boolean add(AccountPO acc) throws RemoteException {
		// TODO 自动生成的方法存根
		for(AccountPO p:list){
			if(p.getName().equals(acc.getName())){
				return false;
			}
		}
		
		list.add(acc);
		save();
		return true;
	}

	@Override
	public boolean del(AccountPO acc) throws RemoteException {
		// TODO 自动生成的方法存根
		for(AccountPO p:list){
			if(p.getName().equals(acc.getName())){
				list.remove(p);
				save();
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean modify(AccountPO oldacc, AccountPO newacc)
			throws RemoteException {
		// TODO 自动生成的方法存根
		boolean flag = false;
		for(AccountPO p:list){
			if(p.getName().equals(oldacc.getName())){
				for(AccountPO p1:list){
					if(p1.getName().equals(newacc.getName())&&!p1.getName().equals(oldacc.getName())){
						return false;
					}
				}
				flag = true;
				p.setName(newacc.getName());
				save();
				break;
			}
		}
		return flag;
	}
	
	
	

	@Override
	public AccountPO find(String s) throws RemoteException {
		// TODO 自动生成的方法存根
		for(AccountPO p:list){
			if(p.getName().equals(s)){
				return p;
			}
		}
		return null;
	}

	@Override
	public boolean changeMoney(AccountPO acc) throws RemoteException {
		// TODO 自动生成的方法存根
		boolean flag = false;
		for(AccountPO p:list){
			if(p.getName().equals(acc.getName())){
				flag = true;
				p.setMoney(p.getMoney()+acc.getMoney());
				save();
				break;
			}
		}
		return flag;
	}
	

	@Override
	public ArrayList<AccountPO> search(String s) throws RemoteException {
		// TODO 自动生成的方法存根
		ArrayList<AccountPO> list1 = new ArrayList<AccountPO>();
		
		for(AccountPO p:list){
			if(p.getName().contains(s)){
				list1.add(p);
			}
		}
		return list1;
	}
	

	@Override
	public void finish() throws RemoteException {
		// TODO 自动生成的方法存根
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<AccountPO> show() throws RemoteException {
		// TODO Auto-generated method stub
		
		 try{  
	            ObjectInputStream in = new ObjectInputStream(new FileInputStream("Data/accountdata.ser"));  
	            list = (ArrayList<AccountPO>) in.readObject();   
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
	
	public void save(){
		 try {  
	            ObjectOutputStream out  = new ObjectOutputStream(new FileOutputStream("Data/accountdata.ser"));  
	            out.writeObject(list);   
	            out.close();  
	        } catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	}



}
