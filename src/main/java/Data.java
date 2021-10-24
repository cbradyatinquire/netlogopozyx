import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

@SerializedName("metrics")
@Expose
public Metrics metrics;
@SerializedName("tagData")
@Expose
public TagData tagData;
@SerializedName("anchorData")
@Expose
public List<AnchorDatum> anchorData = null;
@SerializedName("coordinates")
@Expose
public Coordinates coordinates;
@SerializedName("score")
@Expose
public Float score;
@SerializedName("velocity")
@Expose
public Velocity velocity;
@SerializedName("type")
@Expose
public Integer type;
@SerializedName("zones")
@Expose
public List<Object> zones = null;

}