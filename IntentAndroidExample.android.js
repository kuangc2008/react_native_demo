

'use strict';

var React = require('react-native');
var MyIntentAndroid = require('./modules/MyIntent');
var MyToastAndroid = require('./modules/MyToastAndroid');
var MyIntent2 = require('./modules/MyIntent2');
//var FormData = require('form-data');
// var MyTextViewGG = require('./js/MyImage');

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
    // MyToastAndroid.show("hehe", MyToastAndroid.LONG, function(message) {
    //   console.log("my anme is " + MyToastAndroid.CUSTOM);
    // });

    // fetch('https://www.baidu.com')
    // .then((response) => response.text())   //箭头函数，返回文本
    // .then((responseText) => {             //箭头函数，打印文本
    //   console.log(responseText);
    // }, (responseText) => {              //第二个参数，如果网络为404，则会回调这个
    //   console.log(responseText);
    // })
    // .catch((error) => {             //加入没有网络，则打印错误
    //   console.log(error);
    // });


    // var url = 'http://m.baidu.com/searchbox?action=publicsrv&type=bdprofile&service=bdbox&uid=0a2qt_aS-f0OOv8wgi2Zi0aqvugN8Hfz_8vPagurSaioav8xgu2Pi_az28_OP2f1A&from=1011012a&ua=_a-qi4uq-igBNE6lI5me6NNy2IgUI2i0AqqqB&ut=Yu-W8otN2i4TasiDouD58gNV36yuFqqSB&osname=baiduboxapp&osbranch=a0&pkgname=com.baidu.searchbox&network=1_0&cfrom=1000813a&ctv=2&cen=uid_ua_ut&typeid=0&puid=_u2K8gu7viSeA';
    // var form = new FormData();
    // form.append('data', '{"lottery":{"passportid":"625075334"}}');
    // fetch(url, {
    //   method: 'POST',
    //   body: form
    // })
    //   .then((response) => response.json())
    //   .then((responsejson) => {
    //     console.log(responsejson);
    //   });


    // var xhr = new XMLHttpRequest();
    // xhr.open('GET', 'http://zhushou.huihui.cn/api/hui/share.json?channel=baicai~share');
    // xhr.onload = () => {
    //   if (xhr.status !== 200) {
    //     console.log(
    //       'Upload failed',
    //       'Expected HTTP 200 OK response, got ' + xhr.status
    //     );
    //     return;
    //   }
    //   console.log(xhr.responseText);
    //
    //
    //
    // };
    // xhr.onerror = () => {
    //   console.log('error');
    // };
    // xhr.onprogress = (event) => {
    //   console.log('upload onprogress', event);
    // };
    // xhr.send(null);


    
  },
    render: function() {
      return (
        <TouchableNativeFeedback
        onPress = {this.handleClick}>
        <View style = {styles.button}>
          <Text style={styles.text}>联网</Text>
        </View>
        </TouchableNativeFeedback>
      );
    },
});

var ShareModule = React.createClass({
  handleClick : function() {
    MyIntent2.canOpenOpen("android.intent.action.SEND",
      "text/plain",
      null,
      {'android.intent.extra.TEXT' : 'This is my text to send.'},
      (open) => {
        if(open) {
          MyIntent2.openOpen("android.intent.action.SEND",
            "text/plain",
            null,
            {'android.intent.extra.TEXT' : 'This is my text to send.'} //this.state.extra
          );
        } else {
          console.log("no");
        }
      }) ;
  },
  render: function() {
    return (
      <TouchableNativeFeedback
      onPress = {this.handleClick}>
      <View style = {styles.button}>
        <Text style={styles.text}>分享文字</Text>
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
        <ShareModule />
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
