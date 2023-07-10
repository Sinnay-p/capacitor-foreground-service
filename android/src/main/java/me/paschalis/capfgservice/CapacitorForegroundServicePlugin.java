package me.paschalis.capfgservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "CapacitorForegroundService")
public class CapacitorForegroundServicePlugin extends Plugin {

    private CapacitorForegroundService implementation = new CapacitorForegroundService();

    @Override
    public void handleOnDestroy() {
        super.handleOnDestroy();
        stopServiceForeground();
    }

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }

    @PluginMethod
    public void startService(PluginCall call) {
        String title = call.getData().getString("title");
        String description = call.getData().getString("description");
        String icon = call.getData().getString("icon");
        Integer importance = call.getData().getInteger("importance");
        Activity activity = getActivity();
        Intent intent = new Intent(activity,CapacitorForegroundService.class);
        intent.setAction("start");
        intent.putExtra("title", title)
            .putExtra("description", description)
            .putExtra("icon", icon)
            .putExtra("importance", importance);
            // .putExtra("id", args.getString(4));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            activity.getApplicationContext().startForegroundService(intent);
        }
        JSObject result = new JSObject();
        result.put("resolved",true);
        call.resolve(result);
    }

    @PluginMethod
    public void stopService(PluginCall call) {
        stopServiceForeground();
        call.resolve();
    }

    private void stopServiceForeground() {
        Activity activity = getActivity();
        Intent intent = new Intent(activity, CapacitorForegroundService.class);
        intent.setAction("stop");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        activity.getApplicationContext().startForegroundService(intent);
        }
    }
}
