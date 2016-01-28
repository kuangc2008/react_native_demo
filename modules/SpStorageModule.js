'use strict'

var NativeSpStorage = require('NativeModules').SPStorage;

class AsyncSpStorage {
  static getString(name, defaultValue,callback) {
    NativeSpStorage.getString(name, defaultValue, callback);
  }

  static getInt(name, defaultValue,callback){
    NativeSpStorage.getInt(name, defaultValue, callback);
  }

  static getBoolean(key, defaultValue) {
    NativeSpStorage.getBoolean(key, defaultValue, callback);
  }

  static setString(name, value) {
    NativeSpStorage.setString(name, value);
  }

  static setInt(name, value) {
    NativeSpStorage.setInt(name, value);
  }

  static setBoolean(name, value) {
    NativeSpStorage.setBoolean(name, value);
  }
};

module.exports = AsyncSpStorage;
