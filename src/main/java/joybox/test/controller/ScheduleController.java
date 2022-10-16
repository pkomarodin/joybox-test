package joybox.test.controller;

import joybox.test.exception.HttpException;
import joybox.test.model.BaseResponse;
import joybox.test.model.Schedule;
import joybox.test.model.ScheduleRequest;
import joybox.test.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private BaseResponse response;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/submit")
    public ResponseEntity<BaseResponse> submit (
            @RequestBody ScheduleRequest request
    ) {
        try {
            Schedule res = scheduleService.submit(request);
            response = new BaseResponse(HttpStatus.OK.value(), "success", res);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (HttpException e) {
            response = new BaseResponse(e.getHttpStatus().value(), e.getMessage(), null);
            return new ResponseEntity<>(response, e.getHttpStatus());
        } catch (Exception e) {
            response = new BaseResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
