
'use strict'

var React = require('react-native');

var {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  Alert,
  TouchableHighlight,
  Platform,
} = React;

var UIExplorerBlock = require('./UIExplorerBlock');

var alertMessage = 'show alert message';

var SimpleAlertExampleBlock = React.createClass( {
    render : function() {
      return (
        <View>
          <TouchableHighlight style = {styles.wrapper}
            onPress= { () => Alert.alert(
              'Alert title',
              alertMessage,
            )}>
            <View style={styles.button}>
              <Text>alert default</Text>
            </View>
          </TouchableHighlight>

          <TouchableHighlight style = {styles.wrapper}
            onPress = { () => Alert.alert(
              'Alert Title',
              alertMessage,
              [
                {text:'Ok', onPress : ()=> console.log('ok pressed!')},
              ]
            )}>
            <View style = {styles.button}>
              <Text>ok button</Text>
            </View>
          </TouchableHighlight>

          <TouchableHighlight style= {styles.wrapper}
            onPress = { () => Alert.alert(
              'Alert Title3',
              alertMessage,
              [
                {text : 'Cancle', onPress : () => console.log('Cancle pressed')},
                {text : 'OK', onPress : () => console.log('Ok Pressed')},
              ]
            )}>
            <View style={styles.button}>
              <Text>two button</Text>
            </View>
          </TouchableHighlight>

          <TouchableHighlight style={styles.wrapper}
            onPress={ () => Alert.alert(
              'Foo title',
              AlertExample,
              '..............'.split('').map( (dot, index) => ({
                text : 'Button ' + index,
                onPress : () => console.log('Pressed ' + index)
              }))
            )}>
            <View style={styles.button}>
              <Text>Alert with too many buttons></Text>
            </View>
          </TouchableHighlight>
        </View>
      );
    }
});


var AlertExample = React.createClass( {
    statics : {
      title : 'Alert',
      description : 'Alert display a concise and informative message ' +
        'and prompt the user to make a decision.',
    },
    render : function() {
      return (
        <UIExplorerBlock title = {'Alert2'}>
          <SimpleAlertExampleBlock />
        </UIExplorerBlock>
      );
    }
});

var styles = StyleSheet.create( {
  wrapper : {
    borderRadius : 30,
    marginBottom : 5,
  },
  button : {
    backgroundColor : '#F0F000',
    padding : 40,
  },
});


// AppRegistry.registerComponent('HelloWorld', () => AlertExample);
module.exports = {
  AlertExample,
  SimpleAlertExampleBlock,
}
