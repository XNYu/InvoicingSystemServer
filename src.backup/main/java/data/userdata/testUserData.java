package data.userdata;

import java.rmi.RemoteException;
import java.util.ArrayList;

import PO.UserPO;

public class testUserData {
	public static void main(String[] args) throws RemoteException {
		
		run();
		
	}
	
	static void run() throws RemoteException{
		UserData ud = new UserData();
		ArrayList<UserPO> arr = ud.show();
		for (UserPO us:arr)
			System.out.println("ID:"+us.getID()+"pwd:"+us.getPassword()+"type:"+us.getType());
	}
}
