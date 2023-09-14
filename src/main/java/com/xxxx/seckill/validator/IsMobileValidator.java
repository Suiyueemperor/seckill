package com.xxxx.seckill.validator;


import com.xxxx.seckill.utils.ValidatorUtil;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <p>
 * 手机号码校验规则
 * </p>
 *
 * @author Mr.Shi
 * @since 2023-09-12 16:06
 **/
//项目的验证 校验都放在这里
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {//使用了链接了IsMobile注解
    private boolean required = false;//初始化 必填标识

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (required) {//必填
            return ValidatorUtil.isMobile(value);
        }else {
            if (StringUtils.isEmpty(value)){
                return true;//如果不是非必要 那么空是合法的
            }else {
                return ValidatorUtil.isMobile(value);
            }
        }
    }
}
