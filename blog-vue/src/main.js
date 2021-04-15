import Vue from "vue";
import App from './App.vue';
import router from './router/router';
import store from './store';

import axios from 'axios';
import "./plugins/permission";

import heart from './plugins/heart';

import ElementUI from "element-ui";
import './assets/style.css'
import "element-ui/lib/theme-chalk/index.css";

import ElementTiptapPlugin from 'element-tiptap';
// import element-tiptap 样式
import 'element-tiptap/lib/index.css';

// 安装 element-tiptap 插件
Vue.use(ElementTiptapPlugin, {
    lang: 'zh',
});

import VideoPlayer from 'vue-video-player'
require('video.js/dist/video-js.css')
require('vue-video-player/src/custom-theme.css')

Vue.use(VideoPlayer)

import "./plugins/axios"

Vue.use(ElementUI);
Vue.use(heart);

Vue.config.productionTip = false;
Vue.prototype.$axios = axios

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount("#app");


