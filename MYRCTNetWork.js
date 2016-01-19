
'use strict'

var MyRCTNetworkingNative = require('NativeModules').Http;

var _requestId = 1;
var generateRequestId = function() {
  return _requestId++;
}

class MyRCTNetworking {
  static sendRequest(method, url, headers, data, useIncrementalUpdates) {
    var requestId = generateRequestId();
    MyRCTNetworkingNative.sendRequest(
      method,
      url,
      requestId,
      headers,
      data,
      useIncrementalUpdates
    );
    return requestId;
  }

  static abortRequest(requestId) {
    MyRCTNetworkingNative.abortRequest(requestId);
  }

  static clearCookies(callback) {
    MyRCTNetworkingNative.clearCookies(callback);
  }
}

module.exports = MyRCTNetworking;
