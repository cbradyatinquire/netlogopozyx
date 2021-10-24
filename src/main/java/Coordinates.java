import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coordinates {

@SerializedName("x")
@Expose
public Integer x;
@SerializedName("y")
@Expose
public Integer y;
@SerializedName("z")
@Expose
public Integer z;

}