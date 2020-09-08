package ormlite.example;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "BOOKS")
public class Book {
    public Book() {
    }

    @DatabaseField(generatedId = true, columnName = "ID")
    private int id;

    @DatabaseField(columnName = "TITLE")
    private String title;

    @DatabaseField(columnName = "AUTHOR")
    private String author;

    @DatabaseField(columnName = "PRICE")
    private double price;

}
