package com.example.apptualidad.Services;

import com.example.apptualidad.Model.EditPass;
import com.example.apptualidad.Model.EditUser;
import com.example.apptualidad.Model.NoticiaRes;
import com.example.apptualidad.Model.User;
import com.example.apptualidad.Responses.UploadPhotoUser;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserService {
    @PUT("/users/fav/{noticia}")
    Call<User> addFav(@Path("noticia") String noticia);

    @GET("/users/me")
    Call<User> getMe();

    @PUT("/users/{id}")
    Call<User> editMe(@Body EditUser editUser, @Path("id") String id);

    @PUT("/users/{id}/password")
    Call<User> editPass(@Header("Authorization") String authorization,@Path("id") String id, @Body EditPass editPass);

    @Multipart
    @POST("/photos/user")
    Call<UploadPhotoUser> addPhoto(@Part MultipartBody.Part photo,
                                   @Part("noticia") RequestBody user);
}
