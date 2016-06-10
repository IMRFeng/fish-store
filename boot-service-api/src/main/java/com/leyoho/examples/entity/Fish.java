package com.leyoho.examples.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


@Entity
public class Fish implements Serializable {

	private static final long serialVersionUID = 1L;
	/** Fish ID **/
	@Id
	@GeneratedValue
	private Long id;
	/** Fish name **/
	private String name;
	/** The fish image URL **/
	@Column(name="image_url")
	private String imageUrl;
	/** The description of the fish **/
	@Column(name="description")
	private String desc;
	/** Fish price **/
	private Double price;
	/** The status **/
	private String status;

	public Fish() {
	}

	public Fish(Long id, String fishName, String imgUrl, String desc, Double price, String status) {
		this.id = id;
		this.name = fishName;
		this.imageUrl = imgUrl;
		this.desc = desc;
		this.price = price;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Overrides hash code method.
	 * 
	 * @return New hash code.
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.id).append(this.name).toHashCode();
	}

	/**
	 * Overrides equals method.
	 * 
	 * @param obj The object to compare for equality.
	 * 
	 * @return true if the two objects are equal; false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Fish == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}

		final Fish otherFish = (Fish) obj;

		return new EqualsBuilder().append(this.id, otherFish.id).append(this.name, otherFish.name).isEquals();

	}

	/**
	 * Overrides toString method.
	 * 
	 * @return String representation of the fish.
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("ID", this.id).append("Name", this.name).toString();
		// return "Fish [id=" + id + ", name=" + name + ", description=" + desc
		// + ", price=" + price + "]";
	}
}
