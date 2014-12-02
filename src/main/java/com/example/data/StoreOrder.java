package com.example.data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 */
@XmlRootElement(name = "StoreOrder")
public class StoreOrder
{
  private int orderId;
  private String itemName;
  private int quantity;
  private String customerName;
  private String shippingAddress;

  public int getOrderId()
  {
    return orderId;
  }

  public void setOrderId(int orderId)
  {
    this.orderId = orderId;
  }

  public String getItemName()
  {
    return itemName;
  }

  public void setItemName(String itemName)
  {
    this.itemName = itemName;
  }

  public int getQuantity()
  {
    return quantity;
  }

  public void setQuantity(int quantity)
  {
    this.quantity = quantity;
  }

  public String getCustomerName()
  {
    return customerName;
  }

  public void setCustomerName(String customerName)
  {
    this.customerName = customerName;
  }

  public String getShippingAddress()
  {
    return shippingAddress;
  }

  public void setShippingAddress(String shippingAddress)
  {
    this.shippingAddress = shippingAddress;
  }
}
