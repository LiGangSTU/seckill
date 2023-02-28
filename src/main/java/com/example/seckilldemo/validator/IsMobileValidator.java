package com.example.seckilldemo.validator;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.seckilldemo.utils.ValidatorUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {
    private boolean required = false;

    @Override
    public void initialize(IsMobile constrainAnnotation){
        required = constrainAnnotation.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(required)
        {
            return ValidatorUtil.isMobile(s);
        }else {
            if(StringUtils.isEmpty(s))return true;
            else return ValidatorUtil.isMobile(s);
        }
    }
}
