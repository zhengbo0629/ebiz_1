package nameEnum;
public enum EbizUserPermissionEnum {
	Active("Active","","",""),
	Administrator("Administrator","","","管理员"), //administrator 权限，用来管理注册的公司和主账号，有了这个才有下面的选项
	DoctorAccountManage("Master Account Manage","","","主账号管理"), //可以添加，编辑，删除主账号
	ChargeManage("Charge Manage","","","收费管理"), //扣费管理

	SystemDealSubscriptionManager("System Deal Subscription Manager","DoctorDefault","System Deal","系统Deal订阅管理"),//医生免费获得这个权限，有了这个才可以对下面的进行管理
	AutoEmailDealSubscription("Auto Email Deal Subscription","DoctorOption","System Deal","系统推荐deal"),//网络爬虫爬出来的deal自动群发邮件订阅，收费项目，医生要额外缴纳费用才会开通                      网络管理员给医生,医生才能给护士。医生(公司)没有了，护士也无法用
	HotProductList("Hot Product List","NurseOption","System Deal","热门产品"),//当前热门产品，在页面上可以设置价格点击订阅，医生自动拥有，护士需要被授权才能看到
	RecommendedProductList("Recommended Product List","NurseOption","System Deal","系统推荐产品"),//系统推荐的产品，在页面上可以设置价格点击订阅，医生自动拥有，护士需要被授权才能看到
	AllProductList("All Product List","NurseOption","System Deal","所有产品"),//所有产品list，在页面上可以搜索关键词过滤，然后设置价格点击订阅，医生自动拥有，护士需要被授权才能看到
	MyProductSubscriptionList("My Product Subscription List","NurseOption","System Deal","我的订阅产品"),//我的订阅清单,设定了价格后点击subscription. 当价格低于设定价格时候系统自动发送邮件给订阅者；医生自动拥有，护士需要被授权才能看到
	
	
	DealMarketManager("Deal Market Manager","DoctorOption","Deal Market","Deal市场"),//deal 市场管理，医生也看不到这个，需要administrator的授权才能看到，这个是额外的服务项目        网络管理员给医生 医生才能给护士，公司欠费自动停
	MyPublishedDealList("My Published Deal List","NurseOption","Deal Market","我的deal管理"),//我订阅的产品清单，可以添加修改价格和删除
	AllPublishedDealList("All Published Deal List","NurseOption","Deal Market","公共deal(2天延迟)"),//所有公开发布的deal(都是延迟两天的deal)，可以在这里进行查阅；看到某个人发布的deal比较好就可以订阅
	MyDealPriceInforSetup("My Deal Price Infor Setup","NurseOption","Deal Market","我的deal订阅设置"),//我的deal的价格信息的设定，所有订阅我的deal的人每个月都要按照这个价格缴费
	MyDealSubscriptionUserList("My Deal Subscription User List","NurseOption","Deal Market","我订阅的用户"), //我都订阅了谁的deal，每个月多少钱等等列表
	SubscriptionMyDealUserList("Subscription My Deal User List","NurseOption","Deal Market","订阅我的用户"), //都是谁订阅了我的deal，每个月收入多少钱
	MyDealSubscriptionDealList("My Deal Subscription Deal List","NurseOption","Deal Market","我订阅的deal"), //我订阅的所有的人发布的所有的deal
	
	
	CompanyInforManager("Company Information Manager","DoctorDefault","Company Information","公司信息管理"), //医生账号必备，护士账号需要获得这个权限才能看到下面的6个权限，进行账号管理
	//公司信息管理员 不建议给护士或者秘书，医生自己享有这个管理权限就好了
	//NurseExclude means 医生无法将此权限给护士，护士的用户管理里面也无法管理这些权限
	CompanyInforManage("Company Information Manage","NurseExclude","Company Information","公司账户管理"), //公司管理，可以修改公司资料，护士不允许有这个权限
	CompanyFinancial("Company Financial","NurseExclude","Company Information","公司财务"), //公司账务查询
	PayCompanyBill("Pay Company Bill","NurseExclude","Company Information","公司账户充值"), //支付公司账单
	UserManage("User Manage","NurseOption","Company Information","用户管理"),//拥有此权限可看到子账号list，可以添加编辑用户和修改用户权限；
	DeleteUser("Delete User","NurseExclude","Company Information","删除用户"),//拥有此权限可以删除用户
	UserAnalysis("User Analysis","NurseExclude","Company Information","用户分析"),//拥有此权限可以有用户分析模块，看到用户的删除的单，未来开发
	
	//公司产品管理员
	CompanyProductManager("Company Product Manager","DoctorDefault","Company Product","公司产品管理"), //医生账号必备，护士账号需要获得这个权限才能看到下面的7个权限，进行账号管理
	ProductManage("Manage Product","NurseOption","Company Product","产品管理"),//拥有此权限可以看到产品list，可以添加修改删除产品；
	DealManage("Deal Manage","NurseOption","Company Product","Deal管理"),//拥有此权限可以群发deal给护士，添加修改删除deal
	PackageManage("Package Manage","DoctorDefault","Company Product","包裹管理"),//拥有此权限可以看到修改所有护士的所有包裹
	InventoryManage("Inventory Manage","DoctorDefault","Company Product","库存管理"),//拥有此权限可以查看产品的库存状况
	
	//这四个功能要放在同一张表格上面，更方便操作，具有权限的就显示，不具有权限的就隐藏
	CheckPackage("Check Package","NurseOption","Company Product","对单"), //拥有此权限可以对单，确认货物数量和种类无误后修改订单状态
	MakeLabel("Make Label","NurseOption","Company Product","发送Lable"), //拥有此权限可以制作label并给护士email label
	PayPackage("Pay Package","NurseOption","Company Product","支付用户"),//拥有此权限可以支付护士钱，并且给护士发支付确认邮件
	SendEmail("Send Email","NurseOption","Company Product","发邮件"),//拥有此权限可以通过系统发邮件；
	
	
	//护士权限
	NursePackageManager("Nurse Package Manager","NurseDefault","Nurse Package","任务操作"),//医生免费获得这个权限，医生赋予子账号这个权限，子账号才能看到下面的4个权限中被赋予的权限；
	ReportPackage("Report Package","NurseDefault","Nurse Package","领票/预报"),//拥有此权限汇报并且管理自己的package，给护士此权限，
	LiveDeal("Live Deal","NurseOption","Nurse Package","正在收购"),//医生拥有这个权限，护士需要从医生那里获得这个权限才能看到 live deal，
	
	
	OverSeaBusinessManager("OverSea Business Manager","DoctorOption","OverSea Business","代购业务管理"),// 医生需要获得这个权限后，才能开通代购生意，才能赋予自己的护士下面的两个权限，也才能接受海外用户的注册  网络管理员给医生 医生才能给护士，公司欠费自动停
	AcceptPublicTask("Accept Public Task","NurseOption","OverSea Business","接受任务(任务市场)"),//护士需要从医生那里获得这个权限，拥有此权限能看到当前的公开任务清单，可以接受当前的公开任务；
	RecommendedProductsList("Recommended Products List","NurseOption","OverSea Business","推荐产品管理"),//护士需要从医生那里获得这个权限，拥有此权限的护士可以推荐添加修改删除自己的推荐产品
	//海外买家用户权限，注册over sea buyer 账号自动获得下面两个权限
	CheckRecommendedProductsList("Check Recommended Products List","OverseaBuyerDefault","OverSea Business","查看推荐产品"),//查看公司范围的所有推荐产品，可以直接接受推荐产品，任务直接发给推荐人。
	PublishTask("Publish Task","OverseaBuyerDefault","OverSea Business","发布任务"),//拥有此权限的人可以发布公开任务
	
	
	
	//仓库管理人员权限
	WareHouseAccountManager("WareHouse Account Manager","DoctorOption","WareHouse","仓库管理"), //医生赋予子账号这个权限，子账号才能看到下面的5个权限中被赋予的权限； 网络管理员给医生 医生才能给护士，公司欠费自动停
	ReceivePackage("Receive Package","NurseOption","WareHouse","接受包裹"),//拥有此权限可以接受包裹，未来给仓库员工使用
	PackPackage("Pack Package","NurseOption","WareHouse","打包"),//拥有此权限可以打包，未来给仓库工人此权限
	WarehouseInventoryManage("Warehouse Inventory Manage","NurseOption","WareHouse","库存管理"),//拥有此权限可以查看管理库存，并且可以批量发送货物
	WareHouseOrderManage("WareHouse Order Manage","NurseOption","WareHouse","仓库订单管理"),//拥有此权限可以管理仓库发货，假设有自己的网站出售产品。。。。。。。。
	BuyerPackageManage("Buyer Package Manage","NurseOption","WareHouse","仓库包裹管理");//拥有此权限可以管理买家的包裹，一单好几个包裹什么的都在此生成

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
