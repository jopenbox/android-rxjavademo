package com.example.rxjava2;

import com.example.rxjava2.bean.NetBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 定义接口.
 */
public interface ApiService {
    @FormUrlEncoded
    @POST("/post")
    Observable<NetBean> getUserInfo(@Field("username")String username,
                                    @Field("password")String password);
    @GET("/get")
    Observable<NetBean> getCartList (@Query("shopName")String shopName);
}
