package cart.domain;

public class Product {
    private final Long id;
    private final Name name;
    private final String image;
    private final Price price;

    public Product(Long id, String name, String image, Integer price) {
        this.id = id;
        this.name = new Name(name);
        this.image = image;
        this.price = new Price(price);
    }

    public Product(String name, String image, Integer price) {
        this(null, name, image, price);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public String getImage() {
        return image;
    }

    public Integer getPrice() {
        return price.getAmount();
    }
}