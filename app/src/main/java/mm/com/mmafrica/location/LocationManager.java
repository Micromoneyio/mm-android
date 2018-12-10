package mm.com.mmafrica.location;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.IBinder;

import rx.subjects.PublishSubject;

/**
 * Created by Ruslan Mingaliev on 16/03/2017
 * Email: mingaliev.rr@gmail.com
 * Skype: doinktheclown_ln
 * All rights reserved.
 */

public class LocationManager implements ServiceConnection{

    private LocationService mLocaionService;
    private PublishSubject<Location> mWrapperSubject = PublishSubject.create();

    public LocationManager(Context context) {
        context.bindService(new Intent(context, LocationService.class), this, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (iBinder instanceof LocationService.LocationBinder) {
            mLocaionService = ((LocationService.LocationBinder) iBinder).getService();
            mLocaionService.getSubject().subscribe(location -> mWrapperSubject.onNext(location));
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        mLocaionService = null;
    }

    /**
     * Попытка подписаться на обновления
     */
    public void reconnect() {
        mLocaionService.reconnect();
    }

    public PublishSubject<Location> getSubject() {
        return mWrapperSubject;
    }

    /**
     * Получение местоположения
     * @return - Текущее местоположение
     */
    public Location getLastKnownLocation() {
        if (mLocaionService == null)
            return null;
        return mLocaionService.getLastLocation();
    }
}
