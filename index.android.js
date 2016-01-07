/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';

var {React, DeviceEventEmitter} = require('react-native');
var Common = require('./CommonAndroid');

var {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  NetInfo,
} = React;

var url = "http://api.comm.miui.com/cspmisc/patch/info?appkey=yellowpage&sign=6DB3CA7885DA35E584917DD938156F534627BAE5&_encparam=6JYHtCJiPyMjfyVsVZ0Btt9YrnfrVNahuvKKsVW%2BX8BTE5AqkpVIX4Y47OOApE41c%2FMFFpmZ2wrEZYFvls8HKudQl7gQ9ZT63ewruejHCG2s0JtE94Y4QO%2F76fWvfRnx65Yq1rsOiHCJergdRt5TXGvUBuXr6hKtDu1%2B5wNNXHfxWUUvaPkqD7fKwLW2soqv";

var HelloWorld = React.createClass({

	componentDidMount: function() {

		Common.getCookie("good job", (message) => {
			console.log(message);
		});
		
		
		DeviceEventEmitter.addListener('onActivityCreate', function(gaga) {
			console.log(gaga);
		}
		//this.printNetwork();
		//this.getData();
		
		
	},
	
	
  render: function() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
			kuangcheng2
        </Text>
        <Text style={styles.instructions}>
          To get started, edit index.android.js
        </Text>
        <Text style={styles.instructions}>
          Shake or press menu button for dev menu
        </Text>
      </View>
    );
  },
  	
	getData : function() {

		fetch(url)
			.then( (response) => response.json(), (noresponse) => {
				console.log("hehe22222");
			})
			.then( (jsonStr) => {
				console.log("hehe");
				console.log(jsonStr);
			})
			.catch( (error) => {
				console.log("hehe3333333");
				console.log(error);
			})

	},


    printNetwork : function() {
       NetInfo.fetch().done( (reach) => {
		   console.log('Initial:' + reach);
	   });
	   
	   NetInfo.isConnectionExpensive( (isConnectionExpensive) => {
			console.log('connection is ' + (isConnectionExpensive ? '3g' : 'wifi'));
	   });
    },
  
  
});

var styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});

AppRegistry.registerComponent('HelloWorld', () => HelloWorld);
