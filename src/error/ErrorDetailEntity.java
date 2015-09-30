package error;

import java.util.List;

public interface ErrorDetailEntity {

     String getRequestKey();
     
     List<String> getRequestValues();
}
