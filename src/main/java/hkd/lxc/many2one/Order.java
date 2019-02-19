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
 * 单项  n-1 映射
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
	 *  映射单向 n-1 的关联关系
	 *  1). 使用 @ManyToOne 来映射多对一的关联关系
	 *  2). 使用 @JoinColumn 来映射外键. 
	 *  3). 可使用 @ManyToOne 的 fetch 属性来修改默认的关联属性的加载策略
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
