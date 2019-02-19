package hkd.lxc.many2one;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ����  n-1 ӳ��
 * @author luxc
 */
@Table(name="N21_ORDER")
@Entity(name="N21_ORDER")
public class Order {
   
	
	private Integer id;
	
	private String orderName;
	
	private Customer customer;
	
	public Order() {}

	
	
	public Order(String orderName) {
		super();
		this.orderName = orderName;
	}



	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}



    @Column(name="ORDER_NAME",length=50)
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	
	/**
	 *  ӳ�䵥�� n-1 �Ĺ�����ϵ
	 *  1). ʹ�� @ManyToOne ��ӳ����һ�Ĺ�����ϵ
	 *  2). ʹ�� @JoinColumn ��ӳ�����. 
	 *  3). ��ʹ�� @ManyToOne �� fetch �������޸�Ĭ�ϵĹ������Եļ��ز���
	 */
    @JoinColumn(name="CUSTOMER_ID")
    @ManyToOne(fetch=FetchType.LAZY)
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}




	@Override
	public String toString() {
		return "Order [id=" + id + ", orderName=" + orderName + ", customer="
				+ customer + "]";
	}
	
	
}
