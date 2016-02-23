package top.kass.model;

import javax.persistence.*;

@Entity
@Table(name = "book_item", schema = "dhs")
public class BookItem {
    private int id;
    private Book book;
    private int number;
    private double price;
    private Product product;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "book_id")
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Basic
    @Column(name = "number")
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Basic
    @Column(name = "price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @OneToOne
    @JoinColumn(name = "product_id")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookItem bookItem = (BookItem) o;

        if (id != bookItem.id) return false;
        if (book.getId() != bookItem.getBook().getId()) return false;
        if (product.getId() != bookItem.getProduct().getId()) return false;
        if (number != bookItem.number) return false;
        if (Double.compare(bookItem.price, price) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + book.getId();
        result = 31 * result + product.getId();
        result = 31 * result + number;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
