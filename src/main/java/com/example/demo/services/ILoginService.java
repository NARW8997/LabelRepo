package com.example.demo.services;

import com.example.demo.domain.LoginQuery;
import com.example.demo.domain.UserWithLabels;
import org.springframework.stereotype.Service;

public interface ILoginService {
    UserWithLabels login(LoginQuery query);

}
