

http://kuangc2008.github.io/react_native.html


<h1> qq团队的reactnative实践： <a href="http://mp.weixin.qq.com/s?__biz=MzI1MTA1MzM2Nw==&mid=401483604&idx=1&sn=399cdf7e13fe6125108de1bfd045f2cf&scene=1&srcid=0201jjP1taWIXhov43NwnoRq&from=groupmessage&isappinstalled=0#wechat_redirect"> 地址</a></h1>
<p>
1 启动优化点
  <ol>
    <li> 首屏渲染完成后，开始始化RN上下文，需要<strong>增加预初始化的接口</stong>  </li>
    <li>将数据保存到本地，优先使用本地数据</li>
    <li>将view也初始化出来？ 将来秒开？</li>
  </ol>
</p>

<p>
2 包大小优化点
  <ul>
    <li>去掉小平台的so库，改用网页？</li>
    <li>v4jar包的复用，无关jar包的去除，同时需要编译修改源代码</li>
    <li>Okhttp和Fresco的去除，改用系统自身的？</li>
  </ul>
</p>


<p>
3 fps优化点
  <ul>
    <li>listview的优化；dom设置透明背景 </li>
  </ul>
</p>
