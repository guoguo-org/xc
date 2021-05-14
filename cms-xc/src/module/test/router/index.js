import Home from '@/module/home/page/home.vue';
import test from '@/module/test/page/test.vue';

export default [{
  path: '/test',
  component: Home,
  name: 'test',
  hidden: false,
  children: [
    {path: '/test/page/test', name: '页面测试', component: test, hidden: false}
  ]
  // ,
  // redirect: '/home',
  // children: [
  //   {path: 'home', component: Home}
  // ]
}/*,
  {
    path: '/login',
    component: Login,
    name: 'Login',
    hidden: true
  }*/
]
