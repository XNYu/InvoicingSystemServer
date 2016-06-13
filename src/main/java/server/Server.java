package server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import data.accountdata.AccountData;
import data.billdata.BillData;
import data.commoditydata.CommodityData;
import data.customerdata.CustomerData;
import data.document.DocumentData;
import data.initialdata.InitialData;
import data.logdata.LogData;
import data.promotiondata.PromotionData;
import data.salesdata.SalesData;
import data.stockdata.StockData;
import data.userdata.UserData;
import dataservice.accountDataService.AccountDataService;
import dataservice.billDataService.BillDataService;
import dataservice.commodityDataService.CommodityDataService;
import dataservice.customerDataService.CustomerDataService;
import dataservice.datafactoryService.DatafactoryService;
import dataservice.documentDataService.DocumentDataService;
import dataservice.initialDataService.InitialDataService;
import dataservice.logDataService.LogDataService;
import dataservice.promotionDataService.PromotionDataService;
import dataservice.salesDataService.SalesDataService;
import dataservice.stockDataService.StockDataService;
import dataservice.userDataService.UserDataService;



public class Server {
	DatafactoryService datafactory;
	UserDataService uds;
	CustomerDataService cds;
	SalesDataService sds;
	BillDataService bds;
	AccountDataService ads;
	PromotionDataService pds;
	LogDataService lds;
	InitialDataService ids;

	//=====================seven=============================================
	StockDataService stockds;
	CommodityDataService commodityds;
	DocumentDataService documentds;

	public Server(){


		try {
			
			uds=new UserData();
			cds=new CustomerData();
			sds=new SalesData();
			pds=new PromotionData();
			bds=new BillData();
			ads=new AccountData();
			lds = new LogData();
			ids = new InitialData();
			stockds = new StockData();
			commodityds = new CommodityData();
			documentds = new DocumentData();

			LocateRegistry.createRegistry(6666);
			
			Naming.rebind("rmi://127.0.0.1:6666/uds",uds);
			Naming.rebind("rmi://127.0.0.1:6666/cds",cds);
			Naming.rebind("rmi://127.0.0.1:6666/bds",bds);
			Naming.rebind("rmi://127.0.0.1:6666/ads",ads);
			Naming.rebind("rmi://127.0.0.1:6666/sds",sds);
			Naming.rebind("rmi://127.0.0.1:6666/pds",pds);
			Naming.rebind("rmi://127.0.0.1:6666/lds",lds);
			Naming.rebind("rmi://127.0.0.1:6666/ids",ids);
			Naming.rebind("rmi://127.0.0.1:6666/stockds",stockds);
			Naming.rebind("rmi://127.0.0.1:6666/commodityds",commodityds);
			Naming.rebind("rmi://127.0.0.1:6666/documentds",documentds);

		} catch (RemoteException | MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String [] args){
		new Server();
		System.out.println(">>>>>>>>  Server pulse on . . .");
		//new LoginFrame();
	}
}
