'use strict'

var MyToastAndroidNative = require('NativeModules').MyToast;

class MyToastAndroid {
  static show(message, duration, callback) {
    MyToastAndroidNative.show(message, duration, callback);
  }
};

MyToastAndroid.SHORT = MyToastAndroidNative.SHORT;
MyToastAndroid.LONG = MyToastAndroidNative.LONG;
MyToastAndroid.CUSTOM = MyToastAndroidNative.custom;

module.exports = MyToastAndroid;
