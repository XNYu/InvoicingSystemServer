package data.initialdata;

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

import PO.InitialPO;
import dataservice.initialDataService.InitialDataService;

public class InitialData extends UnicastRemoteObject implements InitialDataService,Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<InitialPO> list ;
	String path;
	public InitialData() throws RemoteException {
		super();
		path="Data/initialdata.ser";
		File file = new File(path);
		try {
			if (!file.exists()) {
				file.createNewFile();
				list = new ArrayList<InitialPO>();
				save();
			}else{
				show();
			}

		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public ArrayList<InitialPO> show() {
		// TODO 自动生成的方法存根
		 try{
	            ObjectInputStream in = new ObjectInputStream(new FileInputStream("Data/initialdata.ser"));
	            list = (ArrayList<InitialPO>) in.readObject();
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

	private void save() {
		// TODO 自动生成的方法存根
		 try {
	            ObjectOutputStream out  = new ObjectOutputStream(new FileOutputStream("Data/initialdata.ser"));
	            out.writeObject(list);
	            out.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}

	@Override
	public boolean add(InitialPO ini) throws RemoteException {
		// TODO 自动生成的方法存根
		for(InitialPO po:list){
			if(po.getYear()==ini.getYear()){
				return false;
			}
		}

		list.add(ini);
		save();
		return true;
	}

	@Override
	public InitialPO find(String year) throws RemoteException {
		// TODO 自动生成的方法存根
		InitialPO ini = null;
		for(InitialPO po:list){
			if(po.getYear().compareTo(year)==0){
				ini = po;
				break;
			}
		}

		return ini;
	}

	@Override
	public void finish() throws RemoteException {
		// TODO 自动生成的方法存根

	}

	@Override
	public void delAll() throws RemoteException {
		// TODO 自动生成的方法存根
		list = new ArrayList<InitialPO>();
		save();
	}

}
