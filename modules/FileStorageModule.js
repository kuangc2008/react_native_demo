'use strict'

var NativeFileStorage = require('NativeModules').FileStorage;

class AsyncFileStorage {
  static isFileExist(name, callback) {
    NativeFileStorage.isFileExist(name, callback);
  }

  static saveFile(obj, callback){
    NativeFileStorage.saveFile(obj, callback);
  }

  static getFileData(name, callback) {
    NativeFileStorage.getFileData(name, callback);
  }
};

AsyncFileStorage.KEY_NAME = NativeFileStorage.name;
AsyncFileStorage.KEY_REPLEACE = NativeFileStorage.isReplace;
AsyncFileStorage.KEY_CONTENT = NativeFileStorage.content;

module.exports = AsyncFileStorage;
