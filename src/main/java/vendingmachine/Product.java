package vendingmachine;

public class Product {

    private String name;
    private int price;
    private int quantity;

    // 입력 받는걸 여기서 구현하자
    public Product(String input) {
        String[] parts = input.substring(1,input.length()-1).split(",");
        this.name = parts[0];
        this.price = Integer.parseInt(parts[1]);
        this.quantity = Integer.parseInt(parts[2]);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public boolean getQuantity() {
        if (quantity > 0)
            return true;
        else
            return false;
    }


    public void buy() {
        quantity--;
    }
}
