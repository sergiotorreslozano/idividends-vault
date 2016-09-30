package com.idividends.vault.domain;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Operation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private Double price;

	@Column(nullable = false)
	private Long quantity;

	@Column(nullable = false)
	private Double commission;

	@Column(nullable = false)
	private String currency;

	@Column(nullable = false)
	private Instant instant;

	@Column(nullable = false)
	private String type; // buy/sell

	@Column(name = "product_id", nullable = false)
	private Long productId;

	@ManyToOne
	@JoinColumn(name = "product_id", insertable = false, updatable = false)
	private Product product;

	@Column(name = "portfolio_id", nullable = false)
	private Long portfolioId;

	@ManyToOne
	@JoinColumn(name = "portfolio_id", insertable = false, updatable = false)
	private Portfolio portfolio;

	/**
	 * 
	 */
	public Operation() {
		super();
	}

	/**
	 * @param price
	 * @param quantity
	 * @param commission
	 * @param currency
	 * @param instant
	 * @param type
	 * @param productId
	 * @param portfolioId
	 */
	public Operation(Double price, Long quantity, Double commission, String currency, Instant instant, String type,
			Long productId, Long portfolioId) {
		super();
		this.price = price;
		this.quantity = quantity;
		this.commission = commission;
		this.currency = currency;
		this.instant = instant;
		this.type = type;
		this.productId = productId;
		this.portfolioId = portfolioId;
	}

	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * @return the quantity
	 */
	public Long getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the commission
	 */
	public Double getCommission() {
		return commission;
	}

	/**
	 * @param commission
	 *            the commission to set
	 */
	public void setCommission(Double commission) {
		this.commission = commission;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency
	 *            the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the instant
	 */
	public Instant getInstant() {
		return instant;
	}

	/**
	 * @param instant
	 *            the instant to set
	 */
	public void setInstant(Instant instant) {
		this.instant = instant;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the product
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * @param product
	 *            the product to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the portfolio
	 */
	public Portfolio getPortfolio() {
		return portfolio;
	}

	/**
	 * @param portfolio
	 *            the portfolio to set
	 */
	public void setPortfolio(Portfolio portfolio) {
		this.portfolio = portfolio;
	}

	/**
	 * @return the productId
	 */
	public Long getProductId() {
		return productId;
	}

	/**
	 * @param productId
	 *            the productId to set
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	/**
	 * @return the portfolioId
	 */
	public Long getPortfolioId() {
		return portfolioId;
	}

	/**
	 * @param portfolioId
	 *            the portfolioId to set
	 */
	public void setPortfolioId(Long portfolioId) {
		this.portfolioId = portfolioId;
	}

}
