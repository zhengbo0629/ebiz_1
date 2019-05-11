package nameEnum;


public enum EbizNurseGroupTypeEnum {
	//UID("UID"),
	//only active user are included
	AllUser("All Users","�����û�"), //�����û�
	NewUser("New Users","���û�"),  //���û���ע�����������ڵ� ��Ĭ��checked
	OnePackagesUser("One Packages Users","һ���û�"), //�������ڽ��׹�1�ε��û� Ĭ��checked��
	FivePackagesUser("Five Packages Users","�嵥�û�"), //�������ڽ��׹�5�ε��û�
	TenPackagesUser("Ten Packages Users","ʮ���û�"),//�������ڽ��׹�10�ε��û�
	TrustedUser("Trusted User","�����û�"), //��ע���������û���Ĭ�����յ����е�Ⱥ���ʼ���Ĭ����checked��
	UnTrustedUser("UnTrusted User","�������û�"); //��ע���ķ������û� ����ѡ�����Ĭ���ǲ����յ�Ⱥ���ʼ������·������������

    private final String name;
    private final String chinese;

    EbizNurseGroupTypeEnum(String name,String chinese) {
        this.name = name;
        this.chinese=chinese;
    }

    public String getName() {
        return name;
    }
    public String getChinese(){
    	return chinese;
    }

}
