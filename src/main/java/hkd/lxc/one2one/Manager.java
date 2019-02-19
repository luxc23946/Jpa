package hkd.lxc.one2one;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name="121_BOTH_MANAGER")
@Entity
public class Manager {

	private Integer id;
	private String mgrName;
	
	private Department dept;

	public Manager() {}
	
	
	
	public Manager(String mgrName) {
		super();
		this.mgrName = mgrName;
	}



	@GeneratedValue
	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="MGR_NAME")
	public String getMgrName() {
		return mgrName;
	}

	public void setMgrName(String mgrName) {
		this.mgrName = mgrName;
	}

	//���ڲ�ά��������ϵ, û�������һ��, ʹ�� @OneToOne ������ӳ��, �������� mappedBy����
	@OneToOne(mappedBy="mgr")
	public Department getDept() {
		return dept;
	}

	public void setDept(Department dept) {
		this.dept = dept;
	}
}
