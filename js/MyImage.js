
var PropTypes = require('ReactPropTypes');
var requireNativeComponent = require('requireNativeComponent');

var iface = {
  name : 'MyTextViewGG',
  propTypes : {
    text : PropTypes.string,
  }
};

module.exports = requireNativeComponent('MyTextView', iface);
