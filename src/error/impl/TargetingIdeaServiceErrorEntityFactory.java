package error.impl;

import java.util.ArrayList;
import java.util.List;

import error.ErrorDetailEntity;
import error.ErrorEntity;
import error.ErrorEntityFactory;

import jp.yahooapis.ss.V5.TargetingIdeaService.Error;
import jp.yahooapis.ss.V5.TargetingIdeaService.ErrorDetail;

public class TargetingIdeaServiceErrorEntityFactory implements ErrorEntityFactory {

    private final List<Error> errors;

    public TargetingIdeaServiceErrorEntityFactory(List<Error> errors) {
        this.errors = errors;
    }

    @Override
    public List<ErrorEntity> create() {
        ArrayList<ErrorEntity> list = new ArrayList<ErrorEntity>();
        if(errors != null){
            for (final Error error : errors) {
                list.add(
                        new ErrorEntity() {
                            @Override
                            public List<ErrorDetailEntity> getErrorDetail(){
                                List<ErrorDetailEntity> details = new ArrayList<ErrorDetailEntity>();
                                if(error.getDetail() != null){
                                   for (final ErrorDetail errorDetail : error.getDetail()) {
                                      details.add(new ErrorDetailEntity() {
                                        
                                        @Override
                                        public List<String> getRequestValues() {
                                            return errorDetail.getRequestValue();
                                        }
                                        
                                        @Override
                                        public String getRequestKey() {
                                            return errorDetail.getRequestKey();
                                        }
                                    });
                                   }
                                }
                                return details;
                            }

                            @Override
                            public String getMessage() {
                                return error.getMessage();
                            }

                            @Override
                            public String getCode() {
                                return error.getCode();
                            }
                        }
                );
            }
        }
        return list;
   }

}