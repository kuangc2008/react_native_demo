

'user strict';

var React = require('react-native');
//var Tdd= require('./ddd');
var {
  StyleSheet,
  Text,
  View,
} = React;

var UIExplorerBlock = React.createClass({
  // propTypes : {
  //   title : React.propTypes.string,
  //   description : React.propTypes.string,
  // },

  getInitialState : function() {
    return {description : (null: ?string)};
  },

  render : function() {
    var description;
    if (this.props.description) {
      description =
        <Text style = {styles.descriptionText}>
          {this.propTypes.description}
        </Text>;
    }

    return (
      <View style = {styles.container}>
        <View style = {styles.titleContainer} >
          <Text style = {styles.titleText} >
            {this.props.title}
          </Text>
          {description}
          <View style={styles.children} >
            {this.props.children}
          </View>
        </View>
      </View>
    );
  }
});

var styles = StyleSheet.create({
  container: {
    borderRadius: 3,
    borderWidth: 2,
    borderColor: '#ff0000',
    backgroundColor: '#0000ff',
    margin: 10,
    marginVertical: 5,
    overflow: 'hidden',
  },
    titleContainer : {
      borderBottomWidth : 0.5,
      borderTopLeftRadius : 3,
      borderTopRightRadius : 2.5,
      borderBottomColor : '#d6d7da',
      backgroundColor : '#f6f7f8',
      paddingHorizontal : 10,
      paddingVertical : 5,
    },
    titleText : {
      fontSize: 14,
      fontWeight: '500',
    },
    descriptionText : {
      fontSize : 14,
    },
    disclosure : {
      position : 'absolute',
      top : 0,
      right: 0,
      padding : 10,
    },
    disclosureIcon : {
      width : 12,
      height : 8,
    },
    children : {
      margin : 10,
    }
});

//AppRegistry.registerComponent('HelloWorld', () => UIExplorerBlock);
module.exports = UIExplorerBlock;
