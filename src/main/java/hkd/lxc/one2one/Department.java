package hkd.lxc.one2one;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name="121_BOTH_DEPARTMENT")
@Entity
public class Department {

	private Integer id;
	private String deptName;
	
	private Manager mgr;
	
	public Department() {}
	

	public Department(String deptName) {
		super();
		this.deptName = deptName;
	}


	@GeneratedValue
	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="DEPT_NAME")
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	//ʹ�� @OneToOne ��ӳ�� 1-1 ������ϵ��
	//����Ҫ�ڵ�ǰ���ݱ��������������Ҫʹ�� @JoinColumn ������ӳ��. ע��, 1-1 ������ϵ, ������Ҫ��� unique=true
	@JoinColumn(name="MGR_ID", unique=true)
	@OneToOne(fetch=FetchType.LAZY)
	public Manager getMgr() {
		return mgr;
	}

	public void setMgr(Manager mgr) {
		this.mgr = mgr;
	}
}
