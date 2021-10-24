import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnchorDatum {

@SerializedName("tagId")
@Expose
public String tagId;
@SerializedName("anchorId")
@Expose
public String anchorId;
@SerializedName("rss")
@Expose
public Float rss;

}