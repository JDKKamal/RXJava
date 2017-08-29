package com.jdkgroup.baseclasses;

import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.jdkgroup.rxjava.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class BaseApplication extends MultiDexApplication {

    private static BaseApplication baseApplication = null;

    public static BaseApplication getBaseApplication() {
        return baseApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        baseApplication = this;

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/aileron_regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());

     /*   Realm.init(this);
        RealmConfiguration cfg = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(cfg);*/
    }
}
