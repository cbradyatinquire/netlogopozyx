import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rates {

@SerializedName("success")
@Expose
public Float success;
@SerializedName("update")
@Expose
public Float update;
@SerializedName("packetLoss")
@Expose
public Float packetLoss;

}