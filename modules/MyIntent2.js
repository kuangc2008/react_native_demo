'use strict'

var MyIntentAndroidModule2 = require('NativeModules').MyIntent2;

class MyIntentAndroid2 {
  static canOpenOpen(action, type, uri, extra, callback) {
    MyIntentAndroidModule2.canOpen(action, type, uri, extra, callback);
  }
  static openOpen(action, type, uri, extra) {
    MyIntentAndroidModule2.open(action, type, uri, extra);
  }
}

module.exports = MyIntentAndroid2;
