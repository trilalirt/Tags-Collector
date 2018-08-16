package com.triladroid.a2houses;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface InstaServiceInterface {
    @GET("{tag}") Observable<ResponseBody> getTagsPage(@Path("tag") String tag);
}
