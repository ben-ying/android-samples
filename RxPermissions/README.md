# RxPermissions


## Set up your environment
1. Open your app’s build.gradle file. This is usually not the top-level build.gradle file but app/build.gradle.
2. Add the following lines inside dependencies:
```java
// rxJava
compile "io.reactivex.rxjava2:rxjava:2.1.3"
// rxPermissions
compile 'com.tbruyelle.rxpermissions2:rxpermissions:0.9.4@aar'
// rxBinding
compile 'com.jakewharton.rxbinding2:rxbinding:2.0.0'
```
#### As mentioned above, because your app may be restarted during the permission request, the request must be done during an initialization phase. This may be Activity.onCreate, or View.onFinishInflate, but not pausing methods like onResume, because you'll potentially create an infinite request loop, as your requesting activity is paused by the framework during the permission request.

#### If not, and if your app is restarted during the permission request (because of a configuration change for instance), the user's answer will never be emitted to the subscriber.

## Usage
#### Create a RxPermissions instance:
```java
// this is an activity instance
RxPermissions rxPermissions = new RxPermissions(this);
```
#### Request the CAMERA permission
```java
rxPermission
        .request(Manifest.permission.CAMERA)
        .subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) throws Exception {
                // Always true pre-M
                if (granted) {
                    Toast.makeText(MainActivity.this, "granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "denied", Toast.LENGTH_SHORT).show();
                }
            }
        });
```
#### Trigger the permission request from a specific event with rxBinding
```java
 RxView.clicks(findViewById(R.id.text_view))
        .compose(rxPermission.ensure(Manifest.permission.CALL_PHONE))
        .subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) throws Exception {
                // Always true pre-M
                if (granted) {
                    Toast.makeText(MainActivity.this, "granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "denied", Toast.LENGTH_SHORT).show();
                }
            }
        });
```
#### Request multiply mRxPermission
```java
rxPermission
        .request(Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE)
        .subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) throws Exception {
                if (granted) {
                    Toast.makeText(MainActivity.this,
                            "all granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this,
                            "at least one permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        });
```
#### A detailed result with requestEach or ensureEach
```java
rxPermission
        .requestEach(Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE)
        .subscribe(new Consumer<Permission>() {
                       @Override
                       public void accept(Permission permission) throws Exception {
                           if (permission.granted) {
                               Toast.makeText(MainActivity.this,
                                       "granted", Toast.LENGTH_SHORT).show();
                           } else if (permission.shouldShowRequestPermissionRationale) {
                               // Denied permission without ask never again
                               Toast.makeText(MainActivity.this,
                                       "shouldShowRequestPermissionRationale", Toast.LENGTH_SHORT).show();
                           } else {
                               // Denied permission with ask never again
                               Toast.makeText(MainActivity.this,
                                       "denied", Toast.LENGTH_SHORT).show();
                           }
                       }
                   }
        );
```
