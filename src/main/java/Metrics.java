import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Metrics {

@SerializedName("latency")
@Expose
public Integer latency;
@SerializedName("rates")
@Expose
public Rates rates;

}