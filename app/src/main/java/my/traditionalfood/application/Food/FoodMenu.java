package my.traditionalfood.application.Food;

public class FoodMenu {

    private String Food, RandomUID, Description, Quantity, Price, ImageURL, ChefId;

    public FoodMenu(String food, String randomUID, String description, String quantity, String price, String imageURL, String chefId) {
        Food = food;
        RandomUID = randomUID;
        Description = description;
        Quantity = quantity;
        Price = price;
        ImageURL = imageURL;
        ChefId = chefId;
    }

    public FoodMenu() {

    }

    public String getFood() {
        return Food;
    }

    public void setFood(String food) {
        Food = food;
    }

    public String getRandomUID() {
        return RandomUID;
    }

    public void setRandomUID(String randomUID) {
        RandomUID = randomUID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getChefId() {
        return ChefId;
    }

    public void setChefId(String chefId) {
        ChefId = chefId;
    }
}
