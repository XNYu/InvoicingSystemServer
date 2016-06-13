package data.document;

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

import PO.CommodityPO;
import PO.DocumentPO;
import PO.PresentPO;
import dataservice.documentDataService.DocumentDataService;

public class DocumentData extends UnicastRemoteObject implements DocumentDataService , Serializable {
	private static final long serialVersionUID = 1L;

	ArrayList<CommodityPO> comList;
	ArrayList<DocumentPO> docList ;
	public DocumentData() throws RemoteException {
		super();
		load();
	}
	//init the data
	public void load() throws RemoteException {
		try{  
			//======================================================================
			File fvck = new File("Data/documentData.ser");
			if(fvck.exists()){
            ObjectInputStream in = new ObjectInputStream(
            		new FileInputStream("Data/documentData.ser"));  
            docList = (ArrayList<DocumentPO>) in.readObject();  
            in.close();
			}else
				docList = new ArrayList<DocumentPO>();
          //======================================================================
			File fvck2 = new File("Data/commodityData.ser");
			if(fvck2.exists()){
            ObjectInputStream in2 = new ObjectInputStream(
            		new FileInputStream("Data/commodityData.ser"));  
            comList = (ArrayList<CommodityPO>) in2.readObject();  
            in2.close();
			}else{
            	comList=new ArrayList<CommodityPO>();
			}		
			
        }catch(FileNotFoundException e){  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();  
        }  
	}
	//show the documents
	public ArrayList<DocumentPO> showDoc(String type){
		ArrayList<DocumentPO> backList = new ArrayList<DocumentPO>();
		for(DocumentPO p :docList){
			if(p.getDocType().equals(type))
				backList.add(p);
		}
		return backList;
	}
	//String docType,String name,String type,String id,int reportAmount,
	//int systemAmount,int realAmount
	//create a damage document
	public boolean createDamage(String name, String type, String id,
			int oldAmount, int newAmount) {
		DocumentPO p = new DocumentPO("DAMAGE",name,type,id,0,oldAmount,newAmount);
		docList.add(p);
		save();
		return true;
	}
	//create a report document
	public boolean createReport(String name, String type, String id,
			int reportAmount) {
		DocumentPO p = new DocumentPO("REPORT",name,type,id,reportAmount,0,0);
		docList.add(p);
		save();
		return true;
	}
	//create a overflow document
	public boolean createOverflow(String name,String type,String id,int oldAmount,int newAmount){
		DocumentPO p = new DocumentPO("OVERFLOW",name,type,id,0,oldAmount,newAmount);
		docList.add(p);
		save();
		return true;
	}
	//save data
	public void save(){
		ObjectOutputStream out2;
		try {
			out2 = new ObjectOutputStream(new FileOutputStream("Data/documentData.ser"));
	        out2.writeObject(docList); 
	        out2.close(); 
		} catch ( IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}  
 
	}
	
	//show all documents
	public ArrayList<DocumentPO> showAll() {
		// TODO 自动生成的方法存根
		return docList;
	}
	//show present documents
	public ArrayList<PresentPO> showPresent() throws RemoteException {
		ArrayList<PresentPO> presentList = new ArrayList<PresentPO>();
		File fvck = new File("Data/presentdata.ser");
		if(fvck.exists()){
        ObjectInputStream in;
		try {
			in = new ObjectInputStream(
					new FileInputStream("Data/presentdata.ser"));
			presentList = (ArrayList<PresentPO>) in.readObject();  
	        in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}  
        
		}else{
        	return presentList;
		}		
		return presentList;
	}
	//examine a document
	public boolean examineDocument(String id, boolean examineState)
			throws RemoteException {

		for(DocumentPO p:docList){
			if(id.equals(p.getDocumentID())){
				p.setExamined(examineState);
				break;
				}
		}
		save();
		return true;
	}
	//calculate income result from overflow documents
	public double overflowIncome() throws RemoteException {
		double income = 0.0;
		for(DocumentPO p : docList){
			if(p.getType().equals("OVERFLOW")){
				int amount = p.getRealAmount()-p.getSystemAmount();
				String name = p.getName();
				String type = p.getType();
				double price = 0.0;
				for(CommodityPO com:comList){
					if(com.getName().equals(name)&&com.getType().equals(type)){
						price = com.getExpPrice();
						break;					
					}
				}
				income+= amount*price;
			}
		}
		return income;
	}
	//calculate outcome result from damage documents
	public double damageOutcome() throws RemoteException {
		double outcome = 0.0;
		for(DocumentPO p : docList){
			if(p.getType().equals("DAMAGE")){
				int amount = p.getSystemAmount()-p.getRealAmount();
				String name = p.getName();
				String type = p.getType();
				double price = 0.0;
				for(CommodityPO com:comList){
					if(com.getName().equals(name)&&com.getType().equals(type)){
						price = com.getImpPrice();
						break;					
					}
				}
				outcome+= amount*price;
			}
		}
		return outcome;
	}

	//clear the data for junit test
	public void clear() throws RemoteException{
		docList.clear();
		save();
	}
}

