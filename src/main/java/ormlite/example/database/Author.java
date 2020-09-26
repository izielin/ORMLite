package ormlite.example.database;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "AUTHORS")
public class Author {
    public Author() {
    }

    @DatabaseField(generatedId = true, columnName = "ID")
    private int id;

    @DatabaseField(columnName = "AUTHOR FIRST NAME", canBeNull = false)
    private String authorFirstName;

    @DatabaseField(columnName = "AUTHOR LAST NAME", canBeNull = false)
    private String authorLastName;

    @ForeignCollectionField
    private ForeignCollection<Book> books;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public ForeignCollection<Book> getBooks() {
        return books;
    }

    public void setBooks(ForeignCollection<Book> books) {
        this.books = books;
    }

    public String toString() {
        return "Book{" +
                "id=" + id +
                ", first name='" + authorFirstName + '\'' +
                ", last name='" + authorLastName + '\'' +
                ", books=" + books +
                '}';
    }
}
