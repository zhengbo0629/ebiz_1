package nameEnum;
public enum EbizUserPermissionEnum {
	Active("Active","","",""),
	Administrator("Administrator","","","����Ա"), //administrator Ȩ�ޣ���������ע��Ĺ�˾�����˺ţ�����������������ѡ��
	DoctorAccountManage("Master Account Manage","","","���˺Ź���"), //������ӣ��༭��ɾ�����˺�
	ChargeManage("Charge Manage","","","�շѹ���"), //�۷ѹ���

	SystemDealSubscriptionManager("System Deal Subscription Manager","DoctorDefault","System Deal","ϵͳDeal���Ĺ���"),//ҽ����ѻ�����Ȩ�ޣ���������ſ��Զ�����Ľ��й���
	AutoEmailDealSubscription("Auto Email Deal Subscription","DoctorOption","System Deal","ϵͳ�Ƽ�deal"),//����������������deal�Զ�Ⱥ���ʼ����ģ��շ���Ŀ��ҽ��Ҫ������ɷ��òŻῪͨ                      �������Ա��ҽ��,ҽ�����ܸ���ʿ��ҽ��(��˾)û���ˣ���ʿҲ�޷���
	HotProductList("Hot Product List","NurseOption","System Deal","���Ų�Ʒ"),//��ǰ���Ų�Ʒ����ҳ���Ͽ������ü۸������ģ�ҽ���Զ�ӵ�У���ʿ��Ҫ����Ȩ���ܿ���
	RecommendedProductList("Recommended Product List","NurseOption","System Deal","ϵͳ�Ƽ���Ʒ"),//ϵͳ�Ƽ��Ĳ�Ʒ����ҳ���Ͽ������ü۸������ģ�ҽ���Զ�ӵ�У���ʿ��Ҫ����Ȩ���ܿ���
	AllProductList("All Product List","NurseOption","System Deal","���в�Ʒ"),//���в�Ʒlist����ҳ���Ͽ��������ؼ��ʹ��ˣ�Ȼ�����ü۸������ģ�ҽ���Զ�ӵ�У���ʿ��Ҫ����Ȩ���ܿ���
	MyProductSubscriptionList("My Product Subscription List","NurseOption","System Deal","�ҵĶ��Ĳ�Ʒ"),//�ҵĶ����嵥,�趨�˼۸����subscription. ���۸�����趨�۸�ʱ��ϵͳ�Զ������ʼ��������ߣ�ҽ���Զ�ӵ�У���ʿ��Ҫ����Ȩ���ܿ���
	
	
	DealMarketManager("Deal Market Manager","DoctorOption","Deal Market","Deal�г�"),//deal �г�����ҽ��Ҳ�������������Ҫadministrator����Ȩ���ܿ���������Ƕ���ķ�����Ŀ        �������Ա��ҽ�� ҽ�����ܸ���ʿ����˾Ƿ���Զ�ͣ
	MyPublishedDealList("My Published Deal List","NurseOption","Deal Market","�ҵ�deal����"),//�Ҷ��ĵĲ�Ʒ�嵥����������޸ļ۸��ɾ��
	AllPublishedDealList("All Published Deal List","NurseOption","Deal Market","����deal(2���ӳ�)"),//���й���������deal(�����ӳ������deal)��������������в��ģ�����ĳ���˷�����deal�ȽϺþͿ��Զ���
	MyDealPriceInforSetup("My Deal Price Infor Setup","NurseOption","Deal Market","�ҵ�deal��������"),//�ҵ�deal�ļ۸���Ϣ���趨�����ж����ҵ�deal����ÿ���¶�Ҫ��������۸�ɷ�
	MyDealSubscriptionUserList("My Deal Subscription User List","NurseOption","Deal Market","�Ҷ��ĵ��û�"), //�Ҷ�������˭��deal��ÿ���¶���Ǯ�ȵ��б�
	SubscriptionMyDealUserList("Subscription My Deal User List","NurseOption","Deal Market","�����ҵ��û�"), //����˭�������ҵ�deal��ÿ�����������Ǯ
	MyDealSubscriptionDealList("My Deal Subscription Deal List","NurseOption","Deal Market","�Ҷ��ĵ�deal"), //�Ҷ��ĵ����е��˷��������е�deal
	
	
	CompanyInforManager("Company Information Manager","DoctorDefault","Company Information","��˾��Ϣ����"), //ҽ���˺űر�����ʿ�˺���Ҫ������Ȩ�޲��ܿ��������6��Ȩ�ޣ������˺Ź���
	//��˾��Ϣ����Ա ���������ʿ�������飬ҽ���Լ������������Ȩ�޾ͺ���
	//NurseExclude means ҽ���޷�����Ȩ�޸���ʿ����ʿ���û���������Ҳ�޷�������ЩȨ��
	CompanyInforManage("Company Information Manage","NurseExclude","Company Information","��˾�˻�����"), //��˾���������޸Ĺ�˾���ϣ���ʿ�����������Ȩ��
	CompanyFinancial("Company Financial","NurseExclude","Company Information","��˾����"), //��˾�����ѯ
	PayCompanyBill("Pay Company Bill","NurseExclude","Company Information","��˾�˻���ֵ"), //֧����˾�˵�
	UserManage("User Manage","NurseOption","Company Information","�û�����"),//ӵ�д�Ȩ�޿ɿ������˺�list��������ӱ༭�û����޸��û�Ȩ�ޣ�
	DeleteUser("Delete User","NurseExclude","Company Information","ɾ���û�"),//ӵ�д�Ȩ�޿���ɾ���û�
	UserAnalysis("User Analysis","NurseExclude","Company Information","�û�����"),//ӵ�д�Ȩ�޿������û�����ģ�飬�����û���ɾ���ĵ���δ������
	
	//��˾��Ʒ����Ա
	CompanyProductManager("Company Product Manager","DoctorDefault","Company Product","��˾��Ʒ����"), //ҽ���˺űر�����ʿ�˺���Ҫ������Ȩ�޲��ܿ��������7��Ȩ�ޣ������˺Ź���
	ProductManage("Manage Product","NurseOption","Company Product","��Ʒ����"),//ӵ�д�Ȩ�޿��Կ�����Ʒlist����������޸�ɾ����Ʒ��
	DealManage("Deal Manage","NurseOption","Company Product","Deal����"),//ӵ�д�Ȩ�޿���Ⱥ��deal����ʿ������޸�ɾ��deal
	PackageManage("Package Manage","DoctorDefault","Company Product","��������"),//ӵ�д�Ȩ�޿��Կ����޸����л�ʿ�����а���
	InventoryManage("Inventory Manage","DoctorDefault","Company Product","������"),//ӵ�д�Ȩ�޿��Բ鿴��Ʒ�Ŀ��״��
	
	//���ĸ�����Ҫ����ͬһ�ű�����棬���������������Ȩ�޵ľ���ʾ��������Ȩ�޵ľ�����
	CheckPackage("Check Package","NurseOption","Company Product","�Ե�"), //ӵ�д�Ȩ�޿��ԶԵ���ȷ�ϻ�������������������޸Ķ���״̬
	MakeLabel("Make Label","NurseOption","Company Product","����Lable"), //ӵ�д�Ȩ�޿�������label������ʿemail label
	PayPackage("Pay Package","NurseOption","Company Product","֧���û�"),//ӵ�д�Ȩ�޿���֧����ʿǮ�����Ҹ���ʿ��֧��ȷ���ʼ�
	SendEmail("Send Email","NurseOption","Company Product","���ʼ�"),//ӵ�д�Ȩ�޿���ͨ��ϵͳ���ʼ���
	
	
	//��ʿȨ��
	NursePackageManager("Nurse Package Manager","NurseDefault","Nurse Package","�������"),//ҽ����ѻ�����Ȩ�ޣ�ҽ���������˺����Ȩ�ޣ����˺Ų��ܿ��������4��Ȩ���б������Ȩ�ޣ�
	ReportPackage("Report Package","NurseDefault","Nurse Package","��Ʊ/Ԥ��"),//ӵ�д�Ȩ�޻㱨���ҹ����Լ���package������ʿ��Ȩ�ޣ�
	LiveDeal("Live Deal","NurseOption","Nurse Package","�����չ�"),//ҽ��ӵ�����Ȩ�ޣ���ʿ��Ҫ��ҽ�����������Ȩ�޲��ܿ��� live deal��
	
	
	OverSeaBusinessManager("OverSea Business Manager","DoctorOption","OverSea Business","����ҵ�����"),// ҽ����Ҫ������Ȩ�޺󣬲��ܿ�ͨ�������⣬���ܸ����Լ��Ļ�ʿ���������Ȩ�ޣ�Ҳ���ܽ��ܺ����û���ע��  �������Ա��ҽ�� ҽ�����ܸ���ʿ����˾Ƿ���Զ�ͣ
	AcceptPublicTask("Accept Public Task","NurseOption","OverSea Business","��������(�����г�)"),//��ʿ��Ҫ��ҽ�����������Ȩ�ޣ�ӵ�д�Ȩ���ܿ�����ǰ�Ĺ��������嵥�����Խ��ܵ�ǰ�Ĺ�������
	RecommendedProductsList("Recommended Products List","NurseOption","OverSea Business","�Ƽ���Ʒ����"),//��ʿ��Ҫ��ҽ�����������Ȩ�ޣ�ӵ�д�Ȩ�޵Ļ�ʿ�����Ƽ�����޸�ɾ���Լ����Ƽ���Ʒ
	//��������û�Ȩ�ޣ�ע��over sea buyer �˺��Զ������������Ȩ��
	CheckRecommendedProductsList("Check Recommended Products List","OverseaBuyerDefault","OverSea Business","�鿴�Ƽ���Ʒ"),//�鿴��˾��Χ�������Ƽ���Ʒ������ֱ�ӽ����Ƽ���Ʒ������ֱ�ӷ����Ƽ��ˡ�
	PublishTask("Publish Task","OverseaBuyerDefault","OverSea Business","��������"),//ӵ�д�Ȩ�޵��˿��Է�����������
	
	
	
	//�ֿ������ԱȨ��
	WareHouseAccountManager("WareHouse Account Manager","DoctorOption","WareHouse","�ֿ����"), //ҽ���������˺����Ȩ�ޣ����˺Ų��ܿ��������5��Ȩ���б������Ȩ�ޣ� �������Ա��ҽ�� ҽ�����ܸ���ʿ����˾Ƿ���Զ�ͣ
	ReceivePackage("Receive Package","NurseOption","WareHouse","���ܰ���"),//ӵ�д�Ȩ�޿��Խ��ܰ�����δ�����ֿ�Ա��ʹ��
	PackPackage("Pack Package","NurseOption","WareHouse","���"),//ӵ�д�Ȩ�޿��Դ����δ�����ֿ⹤�˴�Ȩ��
	WarehouseInventoryManage("Warehouse Inventory Manage","NurseOption","WareHouse","������"),//ӵ�д�Ȩ�޿��Բ鿴�����棬���ҿ����������ͻ���
	WareHouseOrderManage("WareHouse Order Manage","NurseOption","WareHouse","�ֿⶩ������"),//ӵ�д�Ȩ�޿��Թ���ֿⷢ�����������Լ�����վ���۲�Ʒ����������������
	BuyerPackageManage("Buyer Package Manage","NurseOption","WareHouse","�ֿ��������");//ӵ�д�Ȩ�޿��Թ�����ҵİ�����һ���ü�������ʲô�Ķ��ڴ�����

    private final String name;
    private final String role;
    private final String group;
    private final String chinese;

    EbizUserPermissionEnum(String name,String role,String group,String chinese) {
        this.name = name;
        this.role=role;
        this.group=group;
        this.chinese=chinese;
    }

    public String getName() {
        return name;
    }
    public String getRole() {
        return role;
    }
    public String getGroup(){
    	return group;
    }
    public String getChinese(){
    	return chinese;
    }
    public String toString() {
    	EbizUserPermissionEnum[] columns=EbizUserPermissionEnum.values();
    	String temp="";
    	for(int i=0;i<columns.length;i++){
    		if(i<columns.length-1){
    			temp=temp+columns[i].name+",";
    		}else{
    			temp=temp+columns[i].name;
    		}
    	}

    	
        return temp;
    }
}
