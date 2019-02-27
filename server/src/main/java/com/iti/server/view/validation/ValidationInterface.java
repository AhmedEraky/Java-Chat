package com.iti.server.view.validation;

import com.iti.ChatCommanServices.model.entity.user.User;

public interface ValidationInterface {
    public boolean validate(User user, String confirmPassword);
}
