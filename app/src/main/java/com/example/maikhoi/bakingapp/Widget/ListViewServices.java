package com.example.maikhoi.bakingapp.Widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by MaiKhoi on 2/6/18.
 */

public class ListViewServices extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new ListProviderFactory(this.getApplicationContext()));
    }
}
