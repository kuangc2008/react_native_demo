

'use strict';

var React = require('react-native');
var {
  Image,
  StyleSheet,
  Text,
  TouchableWithoutFeedback,
  TouchableHighlight,
  TouchableOpacity,
  View,
  ViewPagerAndroid,
  AppRegistry,
} = React;


var Button = React.createClass({
  _handlePress : function() {
    if(this.props.enabled && this.props.onPress) {
      this.props.onPress();
    }
  },
  render : function() {
    return (
      <TouchableWithoutFeedback onPress={this._handlePress}>
        <View style={[styles.button, this.props.enabled ? [] : styles.buttonDisabled]}>
          <Text style={styles.buttonText}>{this.props.text}</Text>
        </View>
      </TouchableWithoutFeedback>
    );
  }
})

var NetworkDemo = React.createClass({
  statics : {
    title : '<ViewPagerAndroid>',
    description : 'Container that allow to flip left and right',
  },
  getInitialState : function () {
    return {
    };
  },

  click : function() {
    console.log("hehe")
  },
  render:function() {
    return (
      <Button text="发送请求" onPress={this.click} enabled ={true}>
      </Button>
    );
  }
});




var styles = StyleSheet.create({
  buttons : {
    flexDirection : 'row',
    height : 30,
    backgroundColor : 'black',
    alignItems : 'center',
    justifyContent : 'space-between',
  },
  button: {
  flex: 1,
  width: 0,
  margin: 5,
  borderColor: 'gray',
  borderWidth: 1,
  backgroundColor: 'gray',
},
buttonDisabled: {
  backgroundColor: 'black',
  opacity: 0.5,
},
buttonText: {
  color: 'white',
},
container: {
  flex: 1,
  backgroundColor: 'white',
},
image: {
  width: 300,
  height: 200,
  padding: 20,
},
likeButton: {
  backgroundColor: 'rgba(0, 0, 0, 0.1)',
  borderColor: '#333333',
  borderWidth: 1,
  borderRadius: 5,
  flex: 1,
  margin: 8,
  padding: 8,
},
likeContainer: {
  flexDirection: 'row',
},
likesText: {
  flex: 1,
  fontSize: 18,
  alignSelf: 'center',
},
progressBarContainer: {
  height: 10,
  margin: 10,
  borderColor: '#eeeeee',
  borderWidth: 2,
},
progressBar: {
  alignSelf: 'flex-start',
  flex: 1,
  backgroundColor: '#eeeeee',
},
viewPager: {
  flex: 1,
},
});


//module.exports = NetworkDemo;

AppRegistry.registerComponent('HelloWorld', () => NetworkDemo);
