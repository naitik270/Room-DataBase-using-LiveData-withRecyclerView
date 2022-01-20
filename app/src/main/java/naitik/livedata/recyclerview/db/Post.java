package naitik.livedata.recyclerview.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "posts_db")
public class Post {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "post_title")
    private String title;
    @ColumnInfo(name = "post_content")
    private String content;

    public Post() {
    }

    @Ignore
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
