'use strict'

var { NativeModules} = require('react-native');

var a =  {
	getCookie(str) {
		console.log("fesesf");
	}
}


//module.exports = a;
module.exports = NativeModules.Utility;