package ebizConcept;

import javax.servlet.annotation.WebServlet;

@WebServlet("/EbizModelListener")
public interface EbizModelListener {
	enum Event {
		AddDeal,AddProduct, UpdateProduct, DeleteDeal,DeleteProduct, UpdatePackage, DeletePackage, AddPackToInventory, AddPackToPackList, 
		Close, Error, CreatWarehouseOrder, UpdateWarehouseOrder, updateInventory,updateUser,addUser,updateCompany, 
	}

	
	void modelChanged(Event event, Object value);
}
