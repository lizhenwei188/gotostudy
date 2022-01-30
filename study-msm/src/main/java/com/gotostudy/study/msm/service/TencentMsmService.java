package com.gotostudy.study.msm.service;


import com.gotostudy.study.com.utils.resultutil.R;
import org.springframework.stereotype.Service;

/**
 * @author LiZhenwei
 */
@Service
public interface TencentMsmService {

    R sendMsm(String phoneNum);
}
