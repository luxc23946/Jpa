package hkd.lxc.one2many;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * µ•œÓ  n-1 ”≥…‰
 * @author luxc
 */
@Table(name="12N_ORDER")
@Entity(name="12N_ORDER")
public class Order {
   
	
	private Integer id;
	
	private String orderName;
	
	
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



	@Override
	public String toString() {
		return "Order [id=" + id + ", orderName=" + orderName + "]";
	}

	
	
}
