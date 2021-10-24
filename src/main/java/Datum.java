import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

@SerializedName("version")
@Expose
public String version;
@SerializedName("tagId")
@Expose
public String tagId;
@SerializedName("timestamp")
@Expose
public Float timestamp;
@SerializedName("data")
@Expose
public Data data;
@SerializedName("success")
@Expose
public Boolean success;

}
