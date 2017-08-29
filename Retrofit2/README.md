# Retrofit2 Sample

Retrofit is a REST Client for Android and Java by Square. It makes it relatively easy to retrieve and upload JSON (or other structured data) via a REST based webservice. In Retrofit you configure which converter is used for the data serialization. Typically for JSON you use GSon, but you can add custom converters to process XML or other protocols. Retrofit uses the OkHttp library for HTTP requests.

## Set up your environment
1. Open your app’s build.gradle file. This is usually not the top-level build.gradle file but app/build.gradle.
2. Add the following lines inside dependencies:
```java
compile 'com.squareup.retrofit2:retrofit:2.3.0'
```
If you are using ProGuard in your project add the following lines to your configuration:
```java
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions
```

## Using Retrofit
Every method of an interface represents one possible API call. It must have a HTTP annotation (GET, POST, etc.) to specify the request type and the relative URL. The return value wraps the response in a Call object with the type of the expected result.
```java
@GET("users")
Call<ResponseBody> getUsers()
```
You can use replacement blocks and query parameters to adjust the URL. A replacement block is added to the relative URL with {}. With the help of the @Path annotation on the method parameter, the value of that parameter is bound to the specific replacement block.
```java
@GET("users/{name}/commits")
Call<ResponseBody> getCommitsByName(@Path("name") String name)
```
Query parameters are added with the @Query annotation on a method parameter. They are automatically added at the end of the URL.
```java
@GET("users")
Call<ResponseBody> getUserById(@Query("id") Integer id)
```
The @Body annotation on a method parameter tells Retrofit to use the object as the request body for the call.
```java
@POST("users")
Call<ResponseBody> postUser(@Body User user)
```
### Example
Webservice.java:
```java
public interface Webservice {
    @GET("user/{id}")
    Call<ResponseBody> getUser(@Path("id") int id);
}
```
MainActivity.java:
```java
Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("http://domain/")
        .build();
Webservice service = retrofit.create(Webservice.class);
Call<ResponseBody> call = service.getUser(2);
call.enqueue(new Callback<ResponseBody>() {
    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        try {
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        t.printStackTrace();
    }
});
```      

### Retrofit Converters
Retrofit can be configured to use a specific converter. This converter handles the data (de)serialization. Several converters are already available for various serialization formats.
To convert to and from JSON, add this line to build.gradle:
```java
compile 'com.squareup.retrofit2:converter-gson:2.2.0'
```
Webservice.java
```java
public interface Webservice {
  @GET("user/{id}")
  Call<User> getUser(@Path("id") int id);
}
```
MainActivity.java
```java
Retrofit retrofit = new Retrofit.Builder()
      .baseUrl("http://domain/")
      // convert to and from JSON
      .addConverterFactory(GsonConverterFactory.create())
      .build();  
Call<User> call = service.getUser(2);
call.enqueue(new Callback<User>() {
    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        try {
            System.out.println(response.getName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {
        t.printStackTrace();
    }
});
```
### Retrofit Adapters
Retrofit can also be extended by adapters to get involved with other libraries like RxJava 2.x, Java 8 and Guava.
RxJava 2.x adapter can be obtained by using Gradle:
```java
compile 'com.squareup.retrofit2:adapter-rxjava2:2.3.0'
```
Webservice.java
```java
public interface Webservice {
  @POST("/users")
  Observable<List<User>> getUsers();
}
```
MainActivity.java
```java
Retrofit retrofit = new Retrofit.Builder()
      .baseUrl("http://domain/")
      // convert to and from JSON
      .addConverterFactory(GsonConverterFactory.create())
      // retrofit adapter
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) 
      .build();
Webservice service = retrofit.create(Webservice.class);
service.getBlogs()
  .subscribeOn(Schedulers.io())
  .subscribe(new Subscriber<List<User>>() {
      @Override
      public void onCompleted() {
        System.out.println("onCompleted");
      }

      @Override
      public void onError(Throwable e) {
        System.err.println("onError");
      }

      @Override
      public void onNext(List<User> users) {
        System.out.println(users.toString());
      }
  });
```
With this adapter being applied the Retrofit interfaces are able to return RxJava 2.x types, e.g., Observable, Flowable or Single and so on.
```java
@GET("users")
Observable<List<User>> getUsers();
```



























