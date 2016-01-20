

'use strict';

var React = require('react-native');
var MyIntentAndroid = require('./modules/MyIntent');
var MyToastAndroid = require('./modules/MyToastAndroid');

var {
  StyleSheet,
  Text,
  TouchableNativeFeedback,
  View,
  AppRegistry,
} = React;

var UIExplorerBlock = require('./UIExplorerBlock');

var OpenURLButton = React.createClass({
  handleClick : function() {
    MyIntentAndroid.canOpenUrl(this.props.url, (supported) => {
      if (supported) {
        MyIntentAndroid.openURL(this.props.url);
      } else {
        console.log("can not oepn");
      }
    });
  },
  render : function() {
    return (
      <TouchableNativeFeedback
        onPress = {this.handleClick}>
        <View style = {styles.button}>
          <Text style={styles.text}>open {this.props.url}</Text>
        </View>
      </TouchableNativeFeedback>
    );
  }
});


var ToastModule = React.createClass({
  handleClick : function() {
    MyToastAndroid.show("hehe", MyToastAndroid.LONG, function(message) {
      console.log("my anme is " + MyToastAndroid.CUSTOM);
    });
  },
  render: function() {
    return (
      <TouchableNativeFeedback
      onPress = {this.handleClick}>
      <View style = {styles.button}>
        <Text style={styles.text}>点击显示toast</Text>
      </View>
      </TouchableNativeFeedback>
    );
  },
});

var IntentAndroidExample = React.createClass({
  statics : {
    title : 'IntentAndroid',
    description : 'show use android Intent',
  },
  render : function() {
    return (
      <UIExplorerBlock title="open external URL">
        <OpenURLButton url={'https://www.facebook.com'} />
        <ToastModule />
      </UIExplorerBlock>

    );
  }
});


var styles = StyleSheet.create({
  container : {
    flex : 1,
    backgroundColor : 'white',
    padding : 10,
    paddingTop : 30,
  },
  button : {
    padding : 10,
    backgroundColor : '#3B5998',
    marginBottom : 10,
  },
  text : {
    color : 'white',
  },
});


 //module.exports = IntentAndroidExample;
AppRegistry.registerComponent('HelloWorld', () => IntentAndroidExample);
