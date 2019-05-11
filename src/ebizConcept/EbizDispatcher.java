package ebizConcept;
import java.util.*;

import javax.servlet.annotation.WebServlet;

import dataCenter.EbizDataBase;
import ebizConcept.EbizModelListener.Event;



/**
 * Acts as the dispatcher of the services.
 */
@WebServlet("/EbizDispatcher")
public class EbizDispatcher {
    private static EbizDispatcher instance;
    private final List<EbizModelListener> listeners;

    private EbizDataBase ebizDataBase;
    

    private EbizDispatcher() {
        listeners = new ArrayList<EbizModelListener>();

    }

    public static synchronized EbizDispatcher getInstance() {
        if (instance == null) {
            instance = new EbizDispatcher();
        }
        return instance;
    }
	public void addListener(EbizModelListener listener) {
        listeners.add(listener);
    }



    public void removeListener(EbizModelListener listener) {
        listeners.remove(listener);
    }
    public List<EbizModelListener> getListeners(){
    	return listeners;
    }

	public void fireModelChanged(Event event, Object value) {

		try {
			for (EbizModelListener listener : listeners) {
				listener.modelChanged(event, value);
			}
			//if (event==Event.Close){
				//EbizDataBase.Close();			
			//}
		} catch (Exception e) {
			
		}
	}
	public EbizDataBase getDataBase(){
		if(ebizDataBase==null){
			ebizDataBase=new EbizDataBase();
		}
		return ebizDataBase;
	}

    public void exit() {
    	fireModelChanged(Event.Close,null);
        System.exit(0);
    }

}
