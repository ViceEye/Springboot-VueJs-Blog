import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: localStorage.getItem("token"),
    userInfo: localStorage.getItem("userInfo") === "undefined" ? "" : JSON.parse(localStorage.getItem("userInfo"))
  },
  // setter
  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token;
      localStorage.setItem("token", token);
    },
    SET_USERINFO: (state, userInfo) => {
      state.userInfo = userInfo;
      localStorage.setItem("userInfo", JSON.stringify(userInfo));
    },

    REMOVE_INFO: (state) => {
      state.token = '';
      state.userInfo = {};
      localStorage.setItem("token", '');
      localStorage.setItem("userInfo", JSON.stringify(''));
    }
  },
  // getter
  getters: {
    getToken: state => {
      return state.token;
    },
    getUser: state => {
      return state.userInfo;
    }
  },
  actions: {
  },
  modules: {
  }
})
