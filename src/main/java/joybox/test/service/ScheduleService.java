package joybox.test.service;

import com.google.gson.Gson;
import joybox.test.exception.HttpException;
import joybox.test.model.BaseResponse;
import joybox.test.model.Book;
import joybox.test.model.Schedule;
import joybox.test.model.ScheduleRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import javax.transaction.Transactional;

@Service
public class ScheduleService {
    @Transactional
    public Schedule submit(ScheduleRequest request) throws HttpException {
        try {
            return new Schedule(request.getUser(), new Book(request.getBook().getTitle(), request.getBook().getAuthor(), request.getBook().getEditionCount()), request.getPickUpDate());
        } catch (HttpStatusCodeException e){
            Gson gson = new Gson();
            BaseResponse errorBody = gson.fromJson(e.getResponseBodyAsString(), BaseResponse.class);
            throw new HttpException(errorBody.getMessage(), e.getStatusCode());
        } catch (Exception e) {
            throw new HttpException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
