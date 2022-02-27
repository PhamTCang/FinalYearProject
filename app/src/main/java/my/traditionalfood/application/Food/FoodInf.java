package my.traditionalfood.application.Food;

public class FoodInf {

    public String FoodName, Description, Quantity, Price, ImageURL, RandomUID, ChefId;

    public FoodInf(String foodname, String price, String description, String quantity, String imageURL, String id, String chefId) {
        FoodName = foodname;
        Description = description;
        Quantity = quantity;
        Price = price;
        ImageURL = imageURL;
        RandomUID = id;
        ChefId = chefId;
    }
}
