

'use strict';

var Platform = require('Platform');
var React = require('react-native');
var {
  StyleSheet,
  Text,
  View,
  AppRegistry,
} = React;

var TouchableWithoutFeedback = require('TouchableWithoutFeedback');

var styles = StyleSheet.create({
  box : {
    backgroundColor : '#ff0000',
    borderColor : '#000033',
    borderWidth : 1,
  }
});

var ViewBorderStyleExample = React.createClass({
  getInitialState() {
    return {
      showBorder : true
    };
  },

  render() {
    return (
      <TouchableWithoutFeedback onPress={this._handlePress}>
        <View>
          <View style = {{
            borderWidth : 1,
            borderRadius : 5,
            borderStyle : this.state.showBorder ? 'dashed' : null,
            padding : 5
          }}>
            <Text style = {{fontSize : 20}}>
              Dashed border Style
            </Text>
          </View>

          <View style={{
            marginTop : 5,
            borderWidth : 1,
            borderRadius : 5,
            borderStyle : this.state.showBorder ? 'dotted' : null,
            padding : 5
          }}>
            <Text style = {{fontSize : 20}}>
              Dotted border style
            </Text>
          </View>
        </View>
      </TouchableWithoutFeedback>
    );
  },

  _handlePress() {
    this.setState( {showBorder : !this.state.showBorder} );
  },
});





// module.exports = IntentAndroidExample;
AppRegistry.registerComponent('HelloWorld', () => ViewBorderStyleExample);
