package top.venja.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.venja.common.lang.Result;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final boolean debug = true;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public Result handler(RuntimeException e) {
        if (debug) {
            log.error("运行时异常: ---------------{}", e);
        } else {
            log.error("运行时异常: ---------------");
            log.error(e.getMessage());
            log.error("--------------------------");
        }
        return Result.fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = ShiroException.class)
    public Result handler(ShiroException e) {
        if (debug) {
            log.error("Shiro异常: ---------------{}", e);
        } else {
            log.error("Shiro异常: ---------------");
            log.error(e.getMessage());
            log.error("--------------------------");
        }
        return Result.fail(Result.CODE.SHIRO_FAIL, e.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result handler(IllegalArgumentException e) {
        if (debug) {
            log.error("Assert异常: ---------------{}", e);
        } else {
            log.error("Assert异常: ---------------");
            log.error(e.getMessage());
            log.error("--------------------------");
        }
        return Result.fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handler(MethodArgumentNotValidException e) {
        if (debug) {
            log.error("实体校验异常: ---------------{}", e);
        } else {
            log.error("实体校验异常: -------------");
            log.error(e.getMessage());
            log.error("--------------------------");
        }
        BindingResult result = e.getBindingResult();
        ObjectError objectError =
                result.getAllErrors().stream().findFirst().isPresent()
                ? result.getAllErrors().stream().findFirst().get()
                : new ObjectError("Unknown", e.getMessage());
        return Result.fail(objectError.getDefaultMessage());
    }



}
