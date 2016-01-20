/**
@providesModule MyIntent
*/
'use strict'

var MyIntentAndroidModule = require('NativeModules').MyIntent;
var invariant = require('invariant');

class MyIntentAndroid {
  static openURL(url : string) {
    this._validateURL(url);
    MyIntentAndroidModule.openURL(url);
  }

  static canOpenUrl(url, callback) {
    this._validateURL(url);
    invariant(
      typeof callback === 'function',
      'Invalid URL: cannot be empty'
    );
    MyIntentAndroidModule.canOpenUrl(url, callback);
  }

  static getIntialURL(callback) {
    invariant(
      typeof callback === 'function',
      'Invalid URL: cannot be empty'
    );
    MyIntentAndroidModule.getInitialURL(callback);
  }


  static _validateURL(url: string) {
    invariant(
      typeof url === 'string',
      'Invalid URL: should be a string. Was: ' + url
    );
    invariant(
      url,
      'Invalid URL: cannot be empty'
    );
  }
}

module.exports = MyIntentAndroid;
