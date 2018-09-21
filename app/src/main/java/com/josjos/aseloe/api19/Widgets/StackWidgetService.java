package com.josjos.aseloe.api19.Widgets;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class StackWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new HomeRemoteViewsFactory(this.getApplicationContext(), intent);
    }

}
