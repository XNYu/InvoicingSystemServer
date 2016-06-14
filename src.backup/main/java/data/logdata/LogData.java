package data.logdata;

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

import PO.LogPO;
import dataservice.logDataService.LogDataService;

public class LogData extends UnicastRemoteObject implements Serializable, LogDataService {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<LogPO> loglist = new ArrayList<LogPO>();
	String path;

	public LogData() throws RemoteException {
		super();
		path="Data/logdata.ser";
		File file = new File(path);
		try {
			if (!file.exists()) {
				file.createNewFile();
				loglist=new ArrayList<LogPO>();
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
	private ArrayList<LogPO> show() throws RemoteException {
		// TODO 自动生成的方法存根
		try{
	            ObjectInputStream in = new ObjectInputStream(new FileInputStream("Data/logdata.ser"));
	            loglist = (ArrayList<LogPO>) in.readObject();
	            in.close();
	        }catch(FileNotFoundException e){
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (ClassNotFoundException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }

		return loglist;
	}


	private void save() {
		// TODO 自动生成的方法存根
		try {
            ObjectOutputStream out  = new ObjectOutputStream(new FileOutputStream("Data/logdata.ser"));
            out.writeObject(loglist);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}


	@Override
	public boolean add(LogPO po) throws RemoteException {
		// TODO 自动生成的方法存根
		loglist.add(po);
		save();
		return true;
	}

	@Override
	public ArrayList<LogPO> find(String time1,String time2) throws RemoteException {
		// TODO 自动生成的方法存根
		ArrayList<LogPO> list = new ArrayList<LogPO>();

		for(LogPO po:loglist){
			if(po.getTime().compareTo(time1)>=0&&po.getTime().compareTo(time2)<=0){
				list.add(po);
			}

		}

		return list;
	}


	@Override
	public void delAll() throws RemoteException {
		// TODO 自动生成的方法存根
		loglist = new ArrayList<LogPO>();

		save();
	}

	@Override
	public boolean finish()  throws RemoteException {
		// TODO 自动生成的方法存根
		return false;
	}

}
