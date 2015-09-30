package error;

import java.util.List;

public interface ErrorEntity {

    String getCode();
    
    String getMessage();
    
    /*String getRequestKey();
    List<String> getRequestValue();
    */
    
    List<ErrorDetailEntity> getErrorDetail();
     
    
}
