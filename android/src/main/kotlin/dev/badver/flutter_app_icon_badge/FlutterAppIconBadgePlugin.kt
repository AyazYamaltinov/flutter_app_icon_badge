package dev.badver.flutter_app_icon_badge

import androidx.annotation.NonNull
import android.content.Context
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import me.leolin.shortcutbadger.ShortcutBadger

/** FlutterAppIconBadgePlugin */
class FlutterAppIconBadgePlugin: FlutterPlugin, MethodCallHandler {

  private lateinit var channel: MethodChannel
  private lateinit var context: Context

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    context = flutterPluginBinding.applicationContext
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_app_icon_badge")
    channel.setMethodCallHandler(this)
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    when (call.method) {
      "updateBadge" -> {
        val count = call.argument<Int>("count") ?: 0
        ShortcutBadger.applyCount(context, count)
        result.success(null)
      }
      "removeBadge" -> {
        ShortcutBadger.removeCount(context)
        result.success(null)
      }
      "isAppBadgeSupported" -> {
        result.success(ShortcutBadger.isBadgeCounterSupported(context))
      }
      else -> result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }
}
