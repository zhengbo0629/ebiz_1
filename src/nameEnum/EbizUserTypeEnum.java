package nameEnum;

public enum EbizUserTypeEnum {
	Doctor("Doctor"), //ҽ�����������ʿ����������ʿ�ȵ�
	Nurse("Nurse"),//�����ҽ��������
	TrustedNurse("TrustedNurse"),//�����յ�ҽ����deal�ʼ���
	UnTrustedNurse("UnTrustedNurse"),//�ղ���ҽ����deal�ʼ���
	SelfEmployedDoctor("SelfEmployedDoctor"),//���ҹ�Ӷ��ҽ��������ҽ����û�й���ʿ��Ȩ�ޣ������˺ţ�
	Oversea_Buyer("Oversea Buyer"),
	WarehouseNurse("Warehouse Nurse");
	
    private final String name;

    EbizUserTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
