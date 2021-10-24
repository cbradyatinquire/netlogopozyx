import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TagData {

@SerializedName("blinkIndex")
@Expose
public Integer blinkIndex;
@SerializedName("status")
@Expose
public String status;
@SerializedName("events")
@Expose
public List<Object> events = null;
@SerializedName("accelerometer")
@Expose
public List<List<Integer>> accelerometer = null;

}