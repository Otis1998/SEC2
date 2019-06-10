package nju.edu.cinema.vo;



/**
 * Created by liying on 2019/4/15.
 */
public class VIPInfoVO {
	private int descriptionId;
    private String description;
    private double price;
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public int getDescriptionId() {
		return descriptionId;
	}

	public void setDescriptionId(int descriptionId) {
		this.descriptionId = descriptionId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
